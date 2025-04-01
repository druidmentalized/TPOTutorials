package org.books.services;

import org.books.exceptions.DuplicateEntityException;
import org.books.exceptions.EntityNotFoundException;
import org.books.exceptions.EntityPersistenceException;
import org.books.exceptions.InvalidEntityException;
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

    public void save(Publisher publisher) {
        if (publisher.getName() == null || publisher.getName().isEmpty() ||
        publisher.getAddress() == null || publisher.getAddress().isEmpty() ||
        publisher.getPhoneNumber() == null || publisher.getPhoneNumber().isEmpty()) {
            throw new InvalidEntityException("Publisher name, address and phone number are required");
        }

        Optional<Publisher> publisherExists = publisherRepository.findByNameIgnoreCaseAndAddressIgnoreCaseOrPhoneNumber(
                publisher.getName(), publisher.getAddress(), publisher.getPhoneNumber()
        );
        if (publisherExists.isPresent()) {
            throw new DuplicateEntityException("Publisher like this already exists: " + publisher.getName());
        }

        try {
            publisherRepository.save(publisher);
        } catch (Exception e) {
            throw new EntityPersistenceException("Could not save publisher: " + e.getMessage());
        }
    }

    public void update(Publisher publisher) {
        if (publisher.getName() == null || publisher.getName().isEmpty() ||
                publisher.getAddress() == null || publisher.getAddress().isEmpty() ||
                publisher.getPhoneNumber() == null || publisher.getPhoneNumber().isEmpty()) {
            throw new InvalidEntityException("Publisher name, address and phone number are required");
        }

        if (!publisherRepository.existsById(publisher.getId())) {
            throw new EntityNotFoundException("No publisher found for id: " + publisher.getId());
        }

        Optional<Publisher> existingPublisher = publisherRepository
                .findByNameIgnoreCaseAndAddressIgnoreCaseOrPhoneNumber(
                        publisher.getName(), publisher.getAddress(), publisher.getPhoneNumber()
                );

        if (existingPublisher.isPresent() && !existingPublisher.get().getId().equals(publisher.getId())) {
            throw new DuplicateEntityException("Another publisher already exists with the same data: " + publisher.getName());
        }

        try {
            publisherRepository.save(publisher);
        } catch (Exception e) {
            throw new EntityPersistenceException("Could not update publisher: " + e.getMessage());
        }
    }

    public void delete(Publisher publisher) {
        try {
            publisherRepository.delete(publisher);
            if (publisherRepository.existsById(publisher.getId())) {
                throw new EntityPersistenceException("Publisher could not be deleted.");
            }
        } catch (Exception e) {
            throw new EntityPersistenceException("Failed ot delete publisher", e);
        }
    }
}
