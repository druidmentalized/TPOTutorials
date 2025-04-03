package org.books.repositories;

import org.books.models.Publisher;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PublisherRepository extends CrudRepository<Publisher, Long> {
    @Query("SELECT DISTINCT p FROM Publisher p LEFT JOIN FETCH p.books")
    List<Publisher> findAllWithBooks();

    Optional<Publisher> findFirstByNameIgnoreCase(String name);

    Optional<Publisher> findByNameIgnoreCaseAndAddressIgnoreCaseOrPhoneNumber(String name, String address, String phoneNumber);
}
