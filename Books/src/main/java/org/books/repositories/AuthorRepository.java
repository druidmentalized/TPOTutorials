package org.books.repositories;

import jakarta.transaction.Transactional;
import org.books.models.Author;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Transactional
@Repository
public interface AuthorRepository extends CrudRepository<Author, Long> {

}
