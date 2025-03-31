package org.books.controllers;

import org.books.services.BookService;
import org.springframework.stereotype.Controller;

import java.util.Scanner;

@Controller
public class BookController implements CrudController {
    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
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
