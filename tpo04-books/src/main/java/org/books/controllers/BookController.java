package org.books.controllers;

import org.books.exceptions.*;
import org.books.models.Author;
import org.books.models.Book;
import org.books.models.Publisher;
import org.books.services.AuthorService;
import org.books.services.BookService;
import org.books.services.PublisherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Scanner;

@Controller
public class BookController implements CrudController {
    private final static Logger log = LoggerFactory.getLogger(BookController.class);

    private final BookService bookService;
    private final AuthorService authorService;
    private final PublisherService publisherService;

    public BookController(BookService bookService, AuthorService authorService, PublisherService publisherService) {
        this.bookService = bookService;
        this.authorService = authorService;
        this.publisherService = publisherService;
    }

    @Override
    public void list() {
        try {
            List<Book> books = bookService.getAll();

            if (books.isEmpty()) {
                System.out.println("No books found.");
                log.info("No books found in the database.");
            }
            books.forEach(System.out::println);
        } catch (EntityPersistenceException e) {
            System.err.println("Failed to retrieve books.");
            log.error("Error retrieving books: {}", e.getMessage(), e);
        }
    }

    @Override
    public void add(Scanner scanner) {
        System.out.println("Enter book title:");
        String title = scanner.nextLine();

        System.out.println("Enter publication year:");
        int publicationYear;
        try {
            publicationYear = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.err.println("Invalid publication year.");
            log.warn("User entered invalid publication year", e);
            return;
        }

        System.out.println("Enter book ISBN as <number>:");
        String isbn = scanner.nextLine();

        System.out.println("Enter publisher ID or name:");
        String publisherInput = scanner.nextLine();

        Publisher publisher;
        try {
            publisher = publisherService.getByIdOrName(publisherInput);
        } catch (EntityNotFoundException e) {
            System.err.println("Publisher not found.");
            log.warn("Publisher lookup failed for input '{}'", publisherInput);
            return;
        }

        Book book = new Book(title, publicationYear, "ISBN-" + isbn);
        book.setPublisher(publisher);

        try {
            bookService.save(book);
            System.out.println("Book added successfully.");
            log.info("Book added: {}", book.getTitle());
        } catch (InvalidEntityException | DuplicateEntityException e) {
            System.err.println("Could not add book: " + e.getMessage());
            log.warn("Validation error while adding book: {}", e.getMessage());
        } catch (EntityPersistenceException e) {
            System.err.println("System error: failed to save the book.");
            log.error("Failed to save book due to system error: {}", e.getMessage(), e);
        }
    }

    @Override
    public void edit(Scanner scanner) {
        System.out.println("Enter book title or ISBN:");
        String input = scanner.nextLine();

        Book book;
        try {
            book = bookService.getByIdOrTitleOrISBN(input);
        } catch (EntityNotFoundException e) {
            System.err.println("Book not found.");
            log.warn("Book lookup failed for input '{}'", input);
            return;
        }

        System.out.println("Enter book new title (leave blank to keep current):");
        String newTitle = scanner.nextLine();
        if (!newTitle.isBlank()) {
            book.setTitle(newTitle);
        }

        System.out.println("Enter book new ISBN as <number>, (leave blank to keep current):");
        String newISBN = scanner.nextLine();
        if (!newISBN.isBlank()) {
            book.setISBN("ISBN-" + newISBN);
        }

        System.out.println("Enter new publication year (leave blank to keep current):");
        int publicationYear;
        try {
            String yearStr = scanner.nextLine();
            if (!yearStr.isBlank()) {
                publicationYear = Integer.parseInt(yearStr);
                book.setPublicationYear(publicationYear);
            }
        } catch (NumberFormatException e) {
            System.err.println("Invalid publication year.");
            log.warn("User entered invalid publication year", e);
            return;
        }

        System.out.println("Do you want to change the publisher? (y/n)");
        String answer = scanner.nextLine();
        if (answer.equalsIgnoreCase("y")) {
            System.out.println("Enter publisher ID or name:");
            String pubInput = scanner.nextLine();
            try {
                Publisher newPublisher = publisherService.getByIdOrName(pubInput);
                book.setPublisher(newPublisher);
                System.out.println("Publisher changed successfully.");
                log.info("Book '{}' publisher changed to '{}'", book.getTitle(), newPublisher.getName());
            } catch (EntityNotFoundException e) {
                System.err.println("Publisher not found. Keeping the old publisher.");
                log.warn("Publisher lookup failed for input '{}'", pubInput);
            }
        }

        System.out.println("Do you want to assign or modify authors for this book? (y/n)");
        answer = scanner.nextLine();
        while (answer.equalsIgnoreCase("y")) {
            System.out.println("Enter author ID, name, or surname to assign:");
            String authorInput = scanner.nextLine();
            try {
                Author author = authorService.getFromInput(authorInput);
                if (book.getAuthors().contains(author)) {
                    System.out.println("Author already assigned to this book.");
                    log.info("Author '{}' already linked to book '{}'", author.getId(), book.getId());
                } else {
                    author.getBooks().add(book);
                    book.getAuthors().add(author);
                    authorService.update(author);
                    System.out.println("Author assigned successfully.");
                    log.info("Book '{}' assigned author '{}'", book.getId(), author.getId());
                }
            } catch (EntityNotFoundException e) {
                System.err.println("Author not found.");
                log.warn("Author lookup failed for input '{}'", authorInput);
            }

            System.out.println("Assign another author? (y/n)");
            answer = scanner.nextLine();
        }

        try {
            bookService.update(book);
            System.out.println("Book updated successfully.");
            log.info("Book updated: {}", book);
        } catch (InvalidEntityException | DuplicateEntityException e) {
            System.err.println("Could not update book: " + e.getMessage());
            log.warn("Validation error while updating book: {}", e.getMessage());
        } catch (EntityPersistenceException e) {
            System.err.println("System error: failed to update the book.");
            log.error("Failed to update book due to system error: {}", e.getMessage(), e);
        }
    }

    @Override
    public void delete(Scanner scanner) {
        System.out.println("Enter book Id, Title or ISBN:");
        String input = scanner.nextLine();

        Book book;
        try {
            book = bookService.getByIdOrTitleOrISBN(input);
            System.err.println("Found book: " + book);
            bookService.delete(book);
            log.info("Book {} deleted successfully.", book.getId());
        } catch (EntityNotFoundException e) {
            log.warn("Attempted to delete a non-existent book: {}", input);
            System.out.println("Book not found.");
        } catch (EntityPersistenceException e) {
            log.error("Failed to delete book: {}", e.getMessage(), e);
            System.out.println("Could not delete book due to system error.");
        }
    }
}
