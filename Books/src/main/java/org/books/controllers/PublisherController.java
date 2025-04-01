package org.books.controllers;

import org.books.exceptions.DuplicateEntityException;
import org.books.exceptions.EntityNotFoundException;
import org.books.exceptions.EntityPersistenceException;
import org.books.exceptions.InvalidEntityException;
import org.books.models.Book;
import org.books.models.Publisher;
import org.books.services.BookService;
import org.books.services.PublisherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Scanner;

@Controller
public class PublisherController implements CrudController {

    private static final Logger log = LoggerFactory.getLogger(PublisherController.class);

    private final PublisherService publisherService;
    private final BookService bookService;

    public PublisherController(PublisherService publisherService, BookService bookService) {
        this.publisherService = publisherService;
        this.bookService = bookService;
    }

    @Override
    public void list() {
        try {
            List<Publisher> publishers = publisherService.getAll();

            if (publishers.isEmpty()) {
                System.out.println("No publishers found.");
                log.info("No publishers found in the database.");
            }

            for (Publisher publisher : publishers) {
                System.out.println(publisher);
            }
        } catch (EntityPersistenceException e) {
            System.err.println("Failed to retrieve publishers.");
            log.error("Error retrieving books: {}", e.getMessage(), e);
        }
    }

    @Override
    public void add(Scanner scanner) {
        System.out.println("Enter publisher name:");
        String name = scanner.nextLine();

        System.out.println("Enter publisher address:");
        String address = scanner.nextLine();

        System.out.println("Enter publisher phone number:");
        String phoneNumber = scanner.nextLine();

        Publisher publisher = new Publisher(name, address, phoneNumber);
        try {
            publisherService.save(publisher);
            System.out.println("Publisher added successfully.");
            log.info("Publisher added: {}", publisher.getName());
        } catch (InvalidEntityException | DuplicateEntityException e) {
            System.err.println("Couldn't add publisher: " + e.getMessage());
            log.warn("Validation error while adding publisher: {}", e.getMessage());
        } catch (EntityPersistenceException e) {
            System.err.println("System error: failed to save the publisher.");
            log.error("Failed to save the publisher due to system error: {}", e.getMessage(), e);
        }
    }

    @Override
    public void edit(Scanner scanner) {
        System.out.println("Enter publisher Id or Name:");
        String input = scanner.nextLine();

        Publisher publisher;
        try {
            publisher = publisherService.getByIdOrName(input);
        } catch (EntityNotFoundException e) {
            System.err.println("Publisher not found.");
            log.warn("Publisher lookup failed for input '{}'", input);
            return;
        }

        System.out.println("Enter publisher new name (leave blank to keep current):");
        String newName = scanner.nextLine();
        if (!newName.isBlank()) {
            publisher.setName(newName);
        }

        System.out.println("Enter publisher new address (leave blank to keep current):");
        String newAddress = scanner.nextLine();
        if (!newAddress.isBlank()) {
            publisher.setAddress(newAddress);
        }

        System.out.println("Enter publisher new phone number (leave blank to keep current):");
        String newPhoneNumber = scanner.nextLine();
        if (!newPhoneNumber.isBlank()) {
            publisher.setPhoneNumber(newPhoneNumber);
        }

        System.out.println("Would you like to assign books to this publisher? (y/n)");
        String answer = scanner.nextLine();
        while (answer.equalsIgnoreCase("y")) {
            System.out.println("Enter book ID, Title, or ISBN number:");
            input = scanner.nextLine();

            Book book;
            try {
                book = bookService.getByIdOrTitleOrISBN(input);
            } catch (EntityNotFoundException e) {
                System.err.println("Book not found.");
                log.warn("Book lookup failed for input '{}'", input);
                break;
            }

            if (publisher.getBooks().contains(book)) {
                System.out.println("Book already assigned to this publisher.");
                log.info("Book '{}' already assigned to this publisher", book.getTitle());
            }
            else {
                publisher.getBooks().add(book);
                book.setPublisher(publisher);
                System.out.println("Book successfully assigned to this publisher");
                log.info("Book '{}' assigned to this publisher", book.getTitle());
            }

            System.out.println("Assign another book? (y/n)");
            answer = scanner.nextLine();
        }

        try {
            publisherService.update(publisher);
            System.out.println("Publisher updated successfully.");
            log.info("Publisher '{}' updated successfully.", publisher.getId());
        } catch (EntityPersistenceException e) {
            System.err.println("Failed to update the publisher.");
            log.error("Failed to update publisher '{}': {}", publisher.getId(), e.getMessage(), e);
        }
    }

    @Override
    public void delete(Scanner scanner) {
        System.out.println("Enter publisher Id or Name:");
        String input = scanner.nextLine();

        Publisher publisher;
        try {
            publisher = publisherService.getByIdOrName(input);
            publisherService.delete(publisher);
            log.info("Publisher {} deleted successfully.", publisher.getId());
        } catch (EntityNotFoundException e) {
            log.warn("Attempted to delete a non-existent publisher '{}'", input);
            System.out.println("Author no found.");
        } catch (EntityPersistenceException e) {
            log.error("Failed to delete publisher: {}", e.getMessage());
            System.out.println("Couldn't delete publisher due to system error.");
        }
    }
}
