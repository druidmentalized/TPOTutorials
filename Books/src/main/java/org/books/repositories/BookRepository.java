package org.books.repositories;

import org.books.models.Book;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookRepository extends CrudRepository<Book, Long> {
    Optional<Book> findByTitleIgnoreCaseAndISBNIgnoreCase(String title, String isbn);

    Optional<Book> findByTitleIgnoreCaseOrISBNIgnoreCase(String title, String isbn);
}
