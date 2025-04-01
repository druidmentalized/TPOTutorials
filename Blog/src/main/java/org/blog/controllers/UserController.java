package org.blog.controllers;

import org.blog.exceptions.DuplicateEntityException;
import org.blog.exceptions.EntityNotFoundException;
import org.blog.exceptions.EntityPersistenceException;
import org.blog.exceptions.InvalidEntityException;
import org.blog.models.Blog;
import org.blog.models.Role;
import org.blog.models.User;
import org.blog.services.BlogService;
import org.blog.services.RoleService;
import org.blog.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Scanner;

@Controller
public class UserController implements CrudController {
    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;
    private final BlogService blogService;
    private final RoleService roleService;

    public UserController(UserService userService, BlogService blogService, RoleService roleService) {
        this.userService = userService;
        this.blogService = blogService;
        this.roleService = roleService;
    }

    @Override
    public void list() {
        try {
            List<User> users = userService.getAll();
            if (users.isEmpty()) {
                System.out.println("No users found.");
                log.info("No users found in the database.");
                return;
            }
            for (User user : users) {
                System.out.println(user);
            }
        } catch (EntityPersistenceException e) {
            System.err.println("Failed to retrieve users.");
            log.error("Error retrieving users: {}", e.getMessage(), e);
        }
    }

    @Override
    public void add(Scanner scanner) {
        System.out.println("Enter user email:");
        String email = scanner.nextLine();

        User user = new User();
        user.setEmail(email);

        System.out.println("Would you like to assign a blog for this user to manage? (y/n)");
        String answer = scanner.nextLine();
        if (answer.equalsIgnoreCase("y")) {
            System.out.println("Enter blog ID or name:");
            String blogInput = scanner.nextLine();
            try {
                Blog blog = blogService.getByIdOrName(blogInput);
                user.setManagedBlog(blog);
                System.out.println("Assigned blog: " + blog.getName());
            } catch (EntityNotFoundException e) {
                System.err.println("Blog not found. Skipping blog assignment.");
                log.warn("Blog lookup failed for input '{}'", blogInput);
            }
        }

        System.out.println("Do you want to assign roles to this user? (y/n)");
        answer = scanner.nextLine();
        while (answer.equalsIgnoreCase("y")) {
            System.out.println("Enter role ID or name:");
            String roleInput = scanner.nextLine();
            try {
                Role role = roleService.getByIdOrName(roleInput);
                user.getRoles().add(role);
                System.out.println("Role assigned: " + role.getName());
            } catch (EntityNotFoundException e) {
                System.err.println("Role not found.");
                log.warn("Role lookup failed for input '{}'", roleInput);
            }
            System.out.println("Assign another role? (y/n)");
            answer = scanner.nextLine();
        }

        try {
            userService.save(user);
            System.out.println("User added successfully.");
            log.info("User added: {}", user.getEmail());
        } catch (InvalidEntityException | DuplicateEntityException e) {
            System.err.println("Could not add user: " + e.getMessage());
            log.warn("Validation error while adding user: {}", e.getMessage());
        } catch (EntityPersistenceException e) {
            System.err.println("System error: failed to save the user.");
            log.error("Failed to save user due to system error: {}", e.getMessage(), e);
        }
    }

    @Override
    public void edit(Scanner scanner) {
        System.out.println("Enter user email or ID:");
        String input = scanner.nextLine();

        User user;
        try {
            user = userService.getByIdOrEmail(input);
        } catch (EntityNotFoundException e) {
            System.err.println("User not found.");
            log.warn("User lookup failed for input '{}'", input);
            return;
        }

        // Change email
        System.out.println("Enter new email (leave blank to keep current):");
        String newEmail = scanner.nextLine();
        if (!newEmail.isBlank()) {
            user.setEmail(newEmail);
        }

        // Change managed blog
        System.out.println("Change managed blog? (y/n)");
        String answer = scanner.nextLine();
        if (answer.equalsIgnoreCase("y")) {
            System.out.println("Enter blog ID or name:");
            String blogInput = scanner.nextLine();
            try {
                Blog newBlog = blogService.getByIdOrName(blogInput);
                user.setManagedBlog(newBlog);
                System.out.println("Managed blog changed successfully.");
                log.info("User '{}' now manages blog '{}'", user.getId(), newBlog.getName());
            } catch (EntityNotFoundException e) {
                System.err.println("Blog not found. Keeping the old blog.");
                log.warn("Blog lookup failed for input '{}'", blogInput);
            }
        }

        // Change roles
        System.out.println("Change roles for this user? (y/n)");
        answer = scanner.nextLine();
        while (answer.equalsIgnoreCase("y")) {
            System.out.println("1) Add a role");
            System.out.println("2) Remove a role");
            System.out.print("Your choice: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1" -> {
                    System.out.println("Enter role ID or name to add:");
                    String roleInput = scanner.nextLine();
                    try {
                        Role role = roleService.getByIdOrName(roleInput);
                        if (user.getRoles().contains(role)) {
                            System.out.println("User already has this role.");
                        } else {
                            user.getRoles().add(role);
                            System.out.println("Role added: " + role.getName());
                        }
                    } catch (EntityNotFoundException e) {
                        System.err.println("Role not found.");
                        log.warn("Role lookup failed for input '{}'", roleInput);
                    }
                }
                case "2" -> {
                    System.out.println("Enter role ID or name to remove:");
                    String roleInput = scanner.nextLine();
                    try {
                        Role role = roleService.getByIdOrName(roleInput);
                        if (user.getRoles().contains(role)) {
                            user.getRoles().remove(role);
                            System.out.println("Role removed: " + role.getName());
                        } else {
                            System.out.println("User does not have this role.");
                        }
                    } catch (EntityNotFoundException e) {
                        System.err.println("Role not found. Could not remove.");
                        log.warn("Role lookup failed for input '{}'", roleInput);
                    }
                }
                default -> System.out.println("Invalid choice. Skipping.");
            }
            System.out.println("Modify more roles? (y/n)");
            answer = scanner.nextLine();
        }

        try {
            userService.update(user);
            System.out.println("User updated successfully.");
            log.info("User updated: {}", user);
        } catch (InvalidEntityException | DuplicateEntityException e) {
            System.err.println("Could not update user: " + e.getMessage());
            log.warn("Validation error while updating user: {}", e.getMessage());
        } catch (EntityPersistenceException e) {
            System.err.println("System error: failed to update the user.");
            log.error("Failed to update user due to system error: {}", e.getMessage(), e);
        }
    }

    @Override
    public void delete(Scanner scanner) {
        System.out.println("Enter user email or ID to delete:");
        String input = scanner.nextLine();

        User user;
        try {
            user = userService.getByIdOrEmail(input);
            System.err.println("Found user: " + user);
        } catch (EntityNotFoundException e) {
            log.warn("Attempted to delete a non-existent user: {}", input);
            System.out.println("User not found.");
            return;
        }

        try {
            userService.delete(user);
            log.info("User {} deleted successfully.", user.getId());
            System.out.println("User deleted successfully.");
        } catch (EntityPersistenceException e) {
            log.error("Failed to delete user: {}", e.getMessage(), e);
            System.out.println("Could not delete user due to system error.");
        }
    }
}