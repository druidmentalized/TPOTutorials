package org.books.services;

import org.books.exceptions.EntityNotFoundException;
import org.books.models.Publisher;
import org.books.repositories.PublisherRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PublisherService {
    private final PublisherRepository publisherRepository;

    public PublisherService(PublisherRepository publisherRepository) {
        this.publisherRepository = publisherRepository;
    }

    public List<Publisher> getAllPublishers() {
        return publisherRepository.findAllWithBooks();
    }

    public Publisher getByIdOrName(String input) {
        try {
            Long id = Long.parseLong(input);
            Optional<Publisher> publisherById = publisherRepository.findById(id);
            if (publisherById.isPresent()) return publisherById.get();
        } catch (NumberFormatException e) {
            // Not a number, going to the next step
        }

        Optional<Publisher> publisher = publisherRepository
                .findFirstByNameIgnoreCase(input);
        return publisher.orElseThrow(() ->
                new EntityNotFoundException("No publisher found for input: " + input));
    }
}
