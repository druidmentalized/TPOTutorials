package org.blog.controllers;

import org.blog.exceptions.DuplicateEntityException;
import org.blog.exceptions.EntityPersistenceException;
import org.blog.exceptions.InvalidEntityException;
import org.blog.models.Role;
import org.blog.services.RoleService;
import org.blog.exceptions.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Scanner;

@Controller
public class RoleController implements CrudController {
    private static final Logger log = LoggerFactory.getLogger(RoleController.class);

    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @Override
    public void list() {
        try {
            List<Role> roles = roleService.getAll();
            if (roles.isEmpty()) {
                System.out.println("No roles found.");
                log.info("No roles found in the database.");
                return;
            }
            roles.forEach(System.out::println);
        } catch (EntityPersistenceException e) {
            System.err.println("Failed to retrieve roles.");
            log.error("Error retrieving roles: {}", e.getMessage(), e);
        }
    }

    @Override
    public void add(Scanner scanner) {
        System.out.println("Enter role name:");
        String name = scanner.nextLine();

        Role role = new Role();
        role.setName(name);

        try {
            roleService.save(role);
            System.out.println("Role added successfully.");
            log.info("Role added: {}", role.getName());
        } catch (InvalidEntityException | DuplicateEntityException e) {
            System.err.println("Could not add role: " + e.getMessage());
            log.warn("Validation error while adding role: {}", e.getMessage());
        } catch (EntityPersistenceException e) {
            System.err.println("System error: failed to save the role.");
            log.error("Failed to save role due to system error: {}", e.getMessage(), e);
        }
    }

    @Override
    public void edit(Scanner scanner) {
        System.out.println("Enter role ID or name to edit:");
        String input = scanner.nextLine();

        Role role;
        try {
            role = roleService.getByIdOrName(input);
        } catch (EntityNotFoundException e) {
            System.err.println("Role not found.");
            log.warn("Role lookup failed for input: {}", input);
            return;
        }

        System.out.println("Enter new role name (leave blank to keep current):");
        String newName = scanner.nextLine();
        if (!newName.isBlank()) {
            role.setName(newName);
        }

        try {
            roleService.update(role);
            System.out.println("Role updated successfully.");
            log.info("Role updated: {}", role.getName());
        } catch (InvalidEntityException | DuplicateEntityException e) {
            System.err.println("Could not update role: " + e.getMessage());
            log.warn("Validation error while updating role: {}", e.getMessage());
        } catch (EntityPersistenceException e) {
            System.err.println("System error: failed to update the role.");
            log.error("Failed to update role due to system error: {}", e.getMessage(), e);
        }
    }

    @Override
    public void delete(Scanner scanner) {
        System.out.println("Enter role ID or name to delete:");
        String input = scanner.nextLine();

        Role role;
        try {
            role = roleService.getByIdOrName(input);
            System.out.println("Found role: " + role.getName());
        } catch (EntityNotFoundException e) {
            System.err.println("Role not found.");
            log.warn("Attempted to delete a non-existent role: {}", input);
            return;
        }

        try {
            roleService.delete(role);
            System.out.println("Role deleted successfully.");
            log.info("Role deleted: {}", role.getName());
        } catch (EntityPersistenceException e) {
            System.err.println("Failed to delete role: " + e.getMessage());
            log.error("Failed to delete role due to system error: {}", e.getMessage(), e);
        }
    }
}
