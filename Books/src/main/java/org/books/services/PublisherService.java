package org.books.services;

import org.books.models.Publisher;
import org.books.repositories.PublisherRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PublisherService {
    private final PublisherRepository publisherRepository;

    public PublisherService(PublisherRepository publisherRepository) {
        this.publisherRepository = publisherRepository;
    }

    public List<Publisher> getAllPublishers() {
        return (List<Publisher>) publisherRepository.findAll();
    }
}
