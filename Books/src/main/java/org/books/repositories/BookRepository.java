package org.books.repositories;

import jakarta.transaction.Transactional;
import org.books.models.Book;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Transactional
@Repository
public interface BookRepository extends CrudRepository<Book, Long> {
}
