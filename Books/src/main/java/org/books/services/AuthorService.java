package org.books.services;

import org.books.exceptions.*;
import org.books.models.Author;
import org.books.repositories.AuthorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthorService {
    private final AuthorRepository authorRepository;

    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public List<Author> getAllAuthors() {
        return (List<Author>) authorRepository.findAll();
    }

    public void save(Author author) {
        if (author.getName() == null || author.getName().isBlank() ||
                author.getSurname() == null || author.getSurname().isBlank()) {
            throw new InvalidEntityException("Name and surname cannot be empty.");
        }

        boolean exists = authorRepository.existsByNameIgnoreCaseAndSurnameIgnoreCase(
                author.getName(), author.getSurname());
        if (exists) {
            throw new DuplicateEntityException("Author already exists: "
                    + author.getName() + " " + author.getSurname());
        }

        try {
            authorRepository.save(author);
        } catch (Exception e) {
            throw new EntityPersistenceException("Error while saving author to the database.", e);
        }
    }


    public Author getFromInput(String input) {
        try {
            Long id = Long.parseLong(input);
            Optional<Author> authorById = authorRepository.findById(id);
            if (authorById.isPresent()) return authorById.get();
        } catch (NumberFormatException ignored) {
            // Not an ID, continue
        }

        Optional<Author> authorByNameOrSurname = authorRepository
                .findFirstByNameIgnoreCaseOrSurnameIgnoreCase(input, input);
        return authorByNameOrSurname.orElseThrow(() ->
                new EntityNotFoundException("No author found for input: " + input));
    }

    public void update(Author author) {
        if (author.getName() == null || author.getName().isBlank() ||
                author.getSurname() == null || author.getSurname().isBlank()) {
            throw new InvalidEntityException("Name and surname cannot be empty.");
        }

        if (!authorRepository.existsById(author.getId())) {
            throw new EntityNotFoundException("Author with ID=" + author.getId() + " does not exist.");
        }

        Optional<Author> existingAuthor = authorRepository
                .findFirstByNameIgnoreCaseOrSurnameIgnoreCase(author.getName(), author.getSurname());
        if (existingAuthor.isPresent() && !existingAuthor.get().getId().equals(author.getId())) {
            throw new DuplicateEntityException("Another author already exists with name: "
                    + author.getName() + " " + author.getSurname());
        }

        try {
            authorRepository.save(author);
        } catch (Exception e) {
            throw new EntityPersistenceException("Error while updating author in the database.", e);
        }
    }


    public void delete(Author author) {
        if (!author.getBooks().isEmpty()) {
            throw new EntityDeletionNotAllowedException("Author has books assigned and cannot be deleted.");
        }

        try {
            authorRepository.delete(author);
            if (authorRepository.existsById(author.getId())) {
                throw new EntityPersistenceException("Author could not be deleted.");
            }
        } catch (Exception e) {
            throw new EntityPersistenceException("Failed to delete author", e);
        }
    }
}
