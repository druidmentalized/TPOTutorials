package org.books.repositories;

import jakarta.transaction.Transactional;
import org.books.models.Publisher;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Transactional
@Repository
public interface PublisherRepository extends CrudRepository<Publisher, Long> {

}
