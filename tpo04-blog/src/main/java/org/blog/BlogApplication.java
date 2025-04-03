package org.blog;

import org.blog.controllers.*;
import org.blog.enums.ActionType;
import org.blog.enums.EntityType;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Map;
import java.util.Scanner;

@SpringBootApplication
public class BlogApplication implements CommandLineRunner {

	private final UserController userController;
	private final ArticleController articleController;
	private final BlogController blogController;
	private final RoleController roleController;

	public BlogApplication(UserController userController,
						   ArticleController articleController,
						   BlogController blogController,
						   RoleController roleController)  {
		this.userController = userController;
		this.articleController = articleController;
		this.blogController = blogController;
		this.roleController = roleController;
	}

	public static void main(String[] args) {
		SpringApplication.run(BlogApplication.class, args);
	}

	@Override
	public void run(String... args) {
		Scanner scanner = new Scanner(System.in);
		System.out.println("Welcome to the Blog!");

		Map<EntityType, CrudController> controllerMap = Map.of(
				EntityType.USER, userController,
				EntityType.ARTICLE, articleController,
				EntityType.BLOG, blogController,
				EntityType.ROLE, roleController
		);

		while (true) {
			EntityType entityType = null;
			while (entityType == null) {
				System.out.println("Choose entity:");
				System.out.println("1. User");
				System.out.println("2. Article");
				System.out.println("3. Blog");
				System.out.println("4. Role");
				System.out.println("e. Exit");
				System.out.println();

				System.out.print("Your choice: ");
				switch (scanner.nextLine()) {
					case "1" -> entityType = EntityType.USER;
					case "2" -> entityType = EntityType.ARTICLE;
					case "3" -> entityType = EntityType.BLOG;
					case "4" -> entityType = EntityType.ROLE;
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
