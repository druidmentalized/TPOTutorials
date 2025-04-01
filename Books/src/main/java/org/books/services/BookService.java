package org.books.services;

import org.books.exceptions.DuplicateEntityException;
import org.books.exceptions.EntityNotFoundException;
import org.books.exceptions.EntityPersistenceException;
import org.books.exceptions.InvalidEntityException;
import org.books.models.Book;
import org.books.repositories.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {
    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> getAllBooks() {
        return (List<Book>) bookRepository.findAll();
    }

    public Book getByIdOrTitleOrISBN(String input) {
        try {
            Long id = Long.parseLong(input);
            Optional<Book> bookById = bookRepository.findById(id);
            if (bookById.isPresent()) {
                return bookById.get();
            }
        } catch (NumberFormatException e) {
            //means it is not number
        }

        Optional<Book> bookByTitleOrISBN = bookRepository.findByTitleIgnoreCaseOrISBNIgnoreCase(input, input);
        return bookByTitleOrISBN.orElseThrow(() -> new EntityNotFoundException("No book found for input: " + input));
    }

    public void save(Book book) {
        if (book.getTitle() == null || book.getTitle().isEmpty() ||
        book.getISBN() == null || book.getISBN().isEmpty() ||
        book.getPublicationYear() == null) {
            throw new InvalidEntityException("Book title, ISBN and publication year are required.");
        }

        Optional<Book> bookExists = bookRepository.findByTitleIgnoreCaseAndISBNIgnoreCase(
                book.getTitle(), book.getISBN());
        if (bookExists.isPresent()) {
            throw new DuplicateEntityException("Book like this already exists:" +
                    book.getTitle() + ", ISBN=" + book.getISBN());
        }

        try {
            bookRepository.save(book);
        } catch (Exception e) {
            throw new EntityPersistenceException("Could not save book: " + e.getMessage());
        }
    }

    public void update(Book book) {
        if (book.getTitle() == null || book.getTitle().isEmpty() ||
                book.getISBN() == null || book.getISBN().isEmpty() ||
                book.getPublicationYear() == null) {
            throw new InvalidEntityException("Book title, ISBN and publication year are required.");
        }

        if (!bookRepository.existsById(book.getId())) {
            throw new EntityNotFoundException("No book found for id: " + book.getId());
        }

        Optional<Book> existingBook = bookRepository
                .findByTitleIgnoreCaseAndISBNIgnoreCase(book.getTitle(), book.getISBN());

        if (existingBook.isPresent() && !existingBook.get().getId().equals(book.getId())) {
            throw new DuplicateEntityException("Another book already exists with this title and ISBN.");
        }

        try {
            bookRepository.save(book);
        } catch (Exception e) {
            throw new EntityPersistenceException("Error while updating book in the database", e);
        }
    }

    public void delete(Book book) {
        try {
            bookRepository.delete(book);
            if (bookRepository.existsById(book.getId())) {
                throw new EntityPersistenceException("Book could not be deleted.");
            }
        } catch (Exception e) {
            throw new EntityPersistenceException("Failed to delete book", e);
        }
    }
}
