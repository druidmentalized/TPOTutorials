package org.books.controllers;

import org.books.exceptions.*;
import org.books.models.Author;
import org.books.models.Book;
import org.books.services.AuthorService;
import org.books.services.BookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Scanner;

@Controller
public class AuthorController implements CrudController {

    private final static Logger log = LoggerFactory.getLogger(AuthorController.class);

    private final AuthorService authorService;
    private final BookService bookService;

    public AuthorController(AuthorService authorService, BookService bookService) {
        this.authorService = authorService;
        this.bookService = bookService;
    }

    @Override
    public void list() {
        try {
            List<Author> authors = authorService.getAll();

            if (authors.isEmpty()) {
                System.out.println("No authors found.");
                log.info("No authors found in the database.");
                return;
            }

            for (Author author : authors) {
                System.out.println(author);
            }

        } catch (EntityPersistenceException e) {
            System.err.println("Failed to retrieve authors.");
            log.error("Error retrieving authors: {}", e.getMessage(), e);
        }
    }

    @Override
    public void add(Scanner scanner) {
        System.out.println("Enter name:");
        String name = scanner.nextLine();

        System.out.println("Enter surname:");
        String surname = scanner.nextLine();

        try {
            Author author = new Author(name, surname);
            authorService.save(author);
            System.out.println("Author added successfully.");
            log.info("Author added: {}", author.getName());
        } catch (InvalidEntityException | DuplicateEntityException e) {
            System.err.println("Could not add author: " + e.getMessage());
            log.warn("Validation error while adding author: {}", e.getMessage());
        } catch (EntityPersistenceException e) {
            System.err.println("System error: failed to save the author.");
            log.error("Failed to save author due to system error: {}", e.getMessage(), e);
        }
    }

    @Override
    public void edit(Scanner scanner) {
        System.out.println("Enter any of the author characteristics:");
        String input = scanner.nextLine();

        Author author;
        try {
            author = authorService.getFromInput(input);
        } catch (EntityNotFoundException e) {
            System.err.println("Author not found.");
            log.warn("Author lookup failed with input '{}'", input);
            return;
        }

        System.out.println("Enter new name (leave blank to keep current):");
        String newName = scanner.nextLine();
        if (!newName.isBlank()) {
            author.setName(newName);
        }

        System.out.println("Enter new surname (leave blank to keep current):");
        String newSurname = scanner.nextLine();
        if (!newSurname.isBlank()) {
            author.setSurname(newSurname);
        }

        System.out.println("Do you want to assign books to this author? (y/n)");
        String answer = scanner.nextLine();
        while (answer.equalsIgnoreCase("y")) {
            System.out.println("Enter book ID, Title or ISBN to assign:");
            input = scanner.nextLine();

            Book book;
            try {
                book = bookService.getByIdOrTitleOrISBN(input);
            } catch (EntityNotFoundException e) {
                System.err.println("Book not found.");
                log.warn("Book lookup failed with input '{}'", input);
                break;
            }

            if (author.getBooks().contains(book)) {
                System.out.println("Book already assigned to this author.");
                log.info("Book '{}' already linked to author '{}'", book.getTitle(), author.getId());
            } else {
                book.getAuthors().add(author);
                author.getBooks().add(book);
                System.out.println("Book assigned successfully.");
                log.info("Book '{}' assigned to author '{}'", book.getTitle(), author.getId());
            }

            System.out.println("Assign another book? (y/n)");
            answer = scanner.nextLine();
        }

        try {
            authorService.update(author);
            System.out.println("Author updated successfully.");
            log.info("Author '{}' updated successfully.", author.getId());
        } catch (EntityPersistenceException e) {
            System.err.println("Failed to update the author.");
            log.error("Failed to update author '{}': {}", author.getId(), e.getMessage(), e);
        }
    }

    @Override
    public void delete(Scanner scanner) {
        System.out.println("Enter any of the author characteristics:");
        String input = scanner.nextLine();

        Author author;
        try {
            author = authorService.getFromInput(input);
            authorService.delete(author);
            log.info("Author {} deleted successfully.", author.getId());
        } catch (EntityNotFoundException e) {
            log.warn("Attempted to delete a non-existent author: {}", input);
            System.out.println("Author not found.");
        } catch (EntityPersistenceException e) {
            log.error("Failed to delete author: {}", e.getMessage(), e);
            System.out.println("Could not delete author due to system error.");
        }
    }
}