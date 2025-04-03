package org.blog.controllers;

import org.blog.exceptions.DuplicateEntityException;
import org.blog.exceptions.EntityPersistenceException;
import org.blog.exceptions.InvalidEntityException;
import org.blog.models.Article;
import org.blog.models.Blog;
import org.blog.models.User;
import org.blog.services.ArticleService;
import org.blog.services.BlogService;
import org.blog.services.UserService;
import org.blog.exceptions.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Scanner;

@Controller
public class ArticleController implements CrudController {
    private static final Logger log = LoggerFactory.getLogger(ArticleController.class);

    private final ArticleService articleService;
    private final BlogService blogService;
    private final UserService userService;

    public ArticleController(ArticleService articleService, BlogService blogService, UserService userService) {
        this.articleService = articleService;
        this.blogService = blogService;
        this.userService = userService;
    }

    @Override
    public void list() {
        try {
            List<Article> articles = articleService.getAll();
            if (articles.isEmpty()) {
                System.out.println("No articles found.");
                log.info("No articles found in the database.");
                return;
            }
            articles.forEach(System.out::println);
        } catch (EntityPersistenceException e) {
            System.err.println("Failed to retrieve articles.");
            log.error("Error retrieving articles: {}", e.getMessage(), e);
        }
    }

    @Override
    public void add(Scanner scanner) {
        System.out.println("Enter article title:");
        String title = scanner.nextLine();

        Article article = new Article();
        article.setTitle(title);

        // Assign Blog
        System.out.println("Enter blog ID or name for this article:");
        String blogInput = scanner.nextLine();
        try {
            Blog blog = blogService.getByIdOrName(blogInput);
            article.setBlog(blog);
            System.out.println("Assigned blog: " + blog.getName());
            log.info("Article assigned to blog: {}", blog.getName());
        } catch (EntityNotFoundException e) {
            System.err.println("Blog not found. Article will not be assigned to any blog.");
            log.warn("Blog lookup failed for input: {}", blogInput);
        }

        // Assign Author
        System.out.println("Enter author ID or email for this article:");
        String authorInput = scanner.nextLine();
        try {
            User author = userService.getByIdOrEmail(authorInput);
            article.setAuthor(author);
            System.out.println("Assigned author: " + author.getEmail());
            log.info("Article assigned to author: {}", author.getEmail());
        } catch (EntityNotFoundException e) {
            System.err.println("Author not found. Article will not have an author.");
            log.warn("Author lookup failed for input: {}", authorInput);
        }

        try {
            articleService.save(article);
            System.out.println("Article added successfully.");
            log.info("Article added: {}", article.getTitle());
        } catch (InvalidEntityException | DuplicateEntityException e) {
            System.err.println("Could not add article: " + e.getMessage());
            log.warn("Validation error while adding article: {}", e.getMessage());
        } catch (EntityPersistenceException e) {
            System.err.println("System error: failed to save the article.");
            log.error("Failed to save article due to system error: {}", e.getMessage(), e);
        }
    }

    @Override
    public void edit(Scanner scanner) {
        System.out.println("Enter article ID or title:");
        String input = scanner.nextLine();

        Article article;
        try {
            article = articleService.getByIdOrTitle(input);
        } catch (EntityNotFoundException e) {
            System.err.println("Article not found.");
            log.warn("Article lookup failed for input: {}", input);
            return;
        }

        System.out.println("Enter new article title (leave blank to keep current):");
        String newTitle = scanner.nextLine();
        if (!newTitle.isBlank()) {
            article.setTitle(newTitle);
        }

        // Optionally change Blog assignment
        System.out.println("Change blog assignment? (y/n)");
        String answer = scanner.nextLine();
        if (answer.equalsIgnoreCase("y")) {
            System.out.println("Enter new blog ID or name:");
            String blogInput = scanner.nextLine();
            try {
                Blog newBlog = blogService.getByIdOrName(blogInput);
                article.setBlog(newBlog);
                System.out.println("Blog changed successfully.");
                log.info("Article '{}' now assigned to blog '{}'", article.getTitle(), newBlog.getName());
            } catch (EntityNotFoundException e) {
                System.err.println("Blog not found. Keeping current blog assignment.");
                log.warn("Blog lookup failed for input: {}", blogInput);
            }
        }

        // Optionally change Author assignment
        System.out.println("Change author assignment? (y/n)");
        answer = scanner.nextLine();
        if (answer.equalsIgnoreCase("y")) {
            System.out.println("Enter new author ID or email:");
            String authorInput = scanner.nextLine();
            try {
                User newAuthor = userService.getByIdOrEmail(authorInput);
                article.setAuthor(newAuthor);
                System.out.println("Author changed successfully.");
                log.info("Article '{}' now assigned to author '{}'", article.getTitle(), newAuthor.getEmail());
            } catch (EntityNotFoundException e) {
                System.err.println("Author not found. Keeping current author assignment.");
                log.warn("Author lookup failed for input: {}", authorInput);
            }
        }

        try {
            articleService.update(article);
            System.out.println("Article updated successfully.");
            log.info("Article updated: {}", article.getTitle());
        } catch (InvalidEntityException | DuplicateEntityException e) {
            System.err.println("Could not update article: " + e.getMessage());
            log.warn("Validation error while updating article: {}", e.getMessage());
        } catch (EntityPersistenceException e) {
            System.err.println("System error: failed to update the article.");
            log.error("Failed to update article due to system error: {}", e.getMessage(), e);
        }
    }

    @Override
    public void delete(Scanner scanner) {
        System.out.println("Enter article ID or title to delete:");
        String input = scanner.nextLine();

        Article article;
        try {
            article = articleService.getByIdOrTitle(input);
            System.out.println("Found article: " + article.getTitle());
        } catch (EntityNotFoundException e) {
            System.err.println("Article not found.");
            log.warn("Attempted to delete a non-existent article: {}", input);
            return;
        }

        try {
            articleService.delete(article);
            System.out.println("Article deleted successfully.");
            log.info("Article '{}' deleted successfully.", article.getTitle());
        } catch (EntityPersistenceException e) {
            System.err.println("Failed to delete article: " + e.getMessage());
            log.error("Failed to delete article due to system error: {}", e.getMessage(), e);
        }
    }
}
