package org.books.controllers;

import org.books.services.AuthorService;
import org.springframework.stereotype.Controller;

import java.util.Scanner;

@Controller
public class AuthorController implements CrudController {
    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @Override
    public void list() {

    }

    @Override
    public void add(Scanner scanner) {

    }

    @Override
    public void edit(Scanner scanner) {

    }

    @Override
    public void delete(Scanner scanner) {

    }
}
