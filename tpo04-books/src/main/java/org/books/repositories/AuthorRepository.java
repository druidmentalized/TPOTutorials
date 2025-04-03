package org.books.repositories;

import org.books.models.Author;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthorRepository extends CrudRepository<Author, Long> {
    Optional<Author> findFirstByNameIgnoreCaseOrSurnameIgnoreCase(String name, String surname);

    boolean existsByNameIgnoreCaseAndSurnameIgnoreCase(String name, String surname);
}
