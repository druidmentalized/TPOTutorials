package org.books.controllers;

import org.books.services.PublisherService;
import org.springframework.stereotype.Controller;

import java.util.Scanner;

@Controller
public class PublisherController implements CrudController {
    private PublisherService publisherService;

    public PublisherController(PublisherService publisherService) {
        this.publisherService = publisherService;
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
