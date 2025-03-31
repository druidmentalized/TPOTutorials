package org.books;

import org.books.controllers.AuthorController;
import org.books.controllers.BookController;
import org.books.controllers.CrudController;
import org.books.controllers.PublisherController;
import org.books.enums.ActionType;
import org.books.enums.EntityType;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.Map;
import java.util.Scanner;

@SpringBootApplication
public class BooksApplication implements CommandLineRunner {

    private final BookController bookController;
    private final AuthorController authorController;
    private final PublisherController publisherController;

    public BooksApplication(BookController bookController, AuthorController authorController, PublisherController publisherController) {
        this.bookController = bookController;
        this.authorController = authorController;
        this.publisherController = publisherController;
    }

    public static void main(String[] args) {
        SpringApplication.run(BooksApplication.class, args);
    }

    @Override
    public void run(String... args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to the Books Application!");

        Map<EntityType, CrudController> controllerMap = Map.of(
                EntityType.BOOK, bookController,
                EntityType.AUTHOR, authorController,
                EntityType.PUBLISHER, publisherController
        );

        boolean running = true;
        while (running) {
            EntityType entityType = null;
            while (entityType == null) {
                System.out.println("Choose entity:");
                System.out.println("1. Book");
                System.out.println("2. Author");
                System.out.println("3. Publisher");
                System.out.println("e. Exit");
                System.out.println();

                System.out.print("Your choice: ");
                switch (scanner.nextLine()) {
                    case "1" -> entityType = EntityType.BOOK;
                    case "2" -> entityType = EntityType.AUTHOR;
                    case "3" -> entityType = EntityType.PUBLISHER;
                    case "e" -> {
                        System.out.println("Exiting application.");
                        return;
                    }
                    default -> System.out.println("Wrong choice. Try again.");
                }
            }

            ActionType actionType = null;
            while (actionType == null) {
                System.out.println("Choose action:");
                System.out.println("1. List all");
                System.out.println("2. Add new");
                System.out.println("3. Edit existing");
                System.out.println("4. Delete one");
                System.out.println("e. Exit");
                System.out.println();

                System.out.print("Your choice: ");
                switch (scanner.nextLine()) {
                    case "1" -> actionType = ActionType.LIST;
                    case "2" -> actionType = ActionType.ADD;
                    case "3" -> actionType = ActionType.EDIT;
                    case "4" -> actionType = ActionType.DELETE;
                    case "e" -> {
                        System.out.println("Exiting application.");
                        return;
                    }
                    default -> System.out.println("Wrong choice. Try again.");
                }
            }

            CrudController controller = controllerMap.get(entityType);

            switch (actionType) {
                case LIST -> controller.list();
                case ADD -> controller.add(scanner);
                case EDIT -> controller.edit(scanner);
                case DELETE -> controller.delete(scanner);
            }

            System.out.println();
        }
    }
}
