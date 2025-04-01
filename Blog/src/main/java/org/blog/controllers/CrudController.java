package org.blog.controllers;

import java.util.Scanner;

public interface CrudController {
    void list();
    void add(Scanner scanner);
    void edit(Scanner scanner);
    void delete(Scanner scanner);
}
