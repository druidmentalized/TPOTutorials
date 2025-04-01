package org.blog.controllers;

import org.blog.exceptions.DuplicateEntityException;
import org.blog.exceptions.EntityNotFoundException;
import org.blog.exceptions.EntityPersistenceException;
import org.blog.exceptions.InvalidEntityException;
import org.blog.models.Article;
import org.blog.models.Blog;
import org.blog.models.User;
import org.blog.services.ArticleService;
import org.blog.services.BlogService;
import org.blog.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Scanner;

@Controller
public class BlogController implements CrudController {
    private static final Logger log = LoggerFactory.getLogger(BlogController.class);

    private final BlogService blogService;
    private final UserService userService;
    private final ArticleService articleService;

    public BlogController(BlogService blogService, UserService userService, ArticleService articleService) {
        this.blogService = blogService;
        this.userService = userService;
        this.articleService = articleService;
    }

    @Override
    public void list() {
        try {
            List<Blog> blogs = blogService.getAll();

            if (blogs.isEmpty()) {
                System.out.println("No blogs found.");
                log.info("No blogs found in the database.");
                return;
            }
            blogs.forEach(System.out::println);
        } catch (EntityPersistenceException e) {
            System.err.println("Failed to retrieve all blogs");
            log.error("Error while getting blogs: {}", e.getMessage());
        }
    }

    @Override
    public void add(Scanner scanner) {
        System.out.println("Enter blog name:");
        String name = scanner.nextLine();

        Blog blog = new Blog();
        blog.setName(name);

        System.out.println("Enter blog manager id or email:");
        String input = scanner.nextLine();
        User manager;
        try {
            manager = userService.getByIdOrEmail(input);
        } catch (EntityNotFoundException e) {
            System.err.println("Manager not found.");
            log.error("Manager not found: {}", e.getMessage());
            return;
        }
        blog.setManager(manager);

        try {
            blogService.save(blog);
            System.out.println("Blog added successfully.");
            log.info("User added: {}", blog.getName());
        } catch (InvalidEntityException | DuplicateEntityException e) {
            System.err.println("Could not add user: " + e.getMessage());
            log.warn("Validation failed while adding user: {}", e.getMessage());
        } catch (EntityPersistenceException e) {
            System.err.println("System error: failed to save the user.");
            log.error("Failed to save the user due to the system error: {}", e.getMessage());
        }
    }

    @Override
    public void edit(Scanner scanner) {
        System.out.println("Enter blog ID or name:");
        String input = scanner.nextLine();

        Blog blog;
        try {
            blog = blogService.getByIdOrName(input);
        } catch (EntityNotFoundException e) {
            System.err.println("Blog not found.");
            log.warn("User lookup failed for input: {}", input);
            return;
        }

        System.out.println("Enter new blog name (leave blank to keep current):");
        String newEmail = scanner.nextLine();
        if (!newEmail.isEmpty()) {
            blog.setName(newEmail);
        }

        System.out.println("Would you like to assign another manager for this blog? (y/n)");
        String answer = scanner.nextLine();
        if (answer.equalsIgnoreCase("y")) {
            System.out.println("Enter new manager id or email:");
            input = scanner.nextLine();
            try {
                User newManager = userService.getByIdOrEmail(input);
                blog.setManager(newManager);
                System.out.println("Manager changed successfully.");
                log.info("Blog'{}' is now managed by '{}'", blog.getName(), newManager.getEmail());
            } catch (EntityNotFoundException e) {
                System.err.println("Manager not found. Keeping the old one.");
                log.warn("Manager lookup failed for input: {}", input);
            }
        }

        System.out.println("Add articles to this blog? (y/n)");
        answer = scanner.nextLine();
        while (answer.equalsIgnoreCase("y")) {
            System.out.println("Enter article ID or title:");
            input = scanner.nextLine();

            try {
                Article article = articleService.getByIdOrTitle(input);
                if (blog.getArticles().contains(article)) {
                    System.out.println("Article already assigned to this blog.");
                    log.info("Article '{}' already assigned to blog '{}'", article.getTitle(), blog.getName());
                } else {
                    article.setBlog(blog);
                    blog.getArticles().add(article);
                    System.out.println("Article assigned successfully.");
                    log.info("Article '{}' assigned to blog '{}'", article.getTitle(), blog.getName());
                }
            } catch (EntityNotFoundException e) {
                System.err.println("Article not found.");
                log.warn("Article lookup failed for input: {}", input);
            }
            System.out.println("Assign another article? (y/n)");
            answer = scanner.nextLine();
        }


        try {
            blogService.update(blog);
            System.out.println("Blog updated successfully.");
            log.info("Blog updated: {}", blog.getName());
        } catch (InvalidEntityException | DuplicateEntityException e) {
            System.err.println("Could not update blog: " + e.getMessage());
            log.warn("Validation error while updating blog: {}", e.getMessage());
        } catch (EntityPersistenceException e) {
            System.err.println("System error: failed to update the blog.");
            log.error("Failed to update blog due to system error: {}", e.getMessage(), e);
        }
    }

    @Override
    public void delete(Scanner scanner) {
        System.out.println("Enter blog ID or name to delete:");
        String input = scanner.nextLine();

        Blog blog;
        try {
            blog = blogService.getByIdOrName(input);
            System.out.println("Found blog: " + blog.getName());
        } catch (EntityNotFoundException e) {
            System.err.println("Blog not found: " + e.getMessage());
            log.warn("Attempted to delete a non-existent blog: {}", input);
            return;
        }

        try {
            blogService.delete(blog);
            System.out.println("Blog deleted successfully.");
            log.info("Blog '{}' deleted successfully.", blog.getName());
        } catch (EntityPersistenceException e) {
            System.err.println("Failed to delete blog due to system error.");
            log.error("Failed to delete blog '{}': {}", blog.getName(), e.getMessage(), e);
        }
    }
}
