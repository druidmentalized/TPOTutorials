package org.books.controllers;

import org.books.models.Publisher;
import org.books.services.PublisherService;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Scanner;

@Controller
public class PublisherController implements CrudController {
    private PublisherService publisherService;

    public PublisherController(PublisherService publisherService) {
        this.publisherService = publisherService;
    }

    @Override
    public void list() {
        List<Publisher> publishers = publisherService.getAllPublishers();

        for (Publisher publisher : publishers) {
            System.out.println(publisher);
        }
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
