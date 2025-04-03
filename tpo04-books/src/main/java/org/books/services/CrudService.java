package org.books.services;

import org.books.exceptions.DuplicateEntityException;
import org.books.exceptions.EntityNotFoundException;
import org.books.exceptions.EntityPersistenceException;
import org.books.exceptions.InvalidEntityException;

import java.util.List;

public interface CrudService<E, ID> {
    List<E> getAll();

    E getById(ID id) throws EntityNotFoundException;

    void save(E entity)
            throws InvalidEntityException, DuplicateEntityException, EntityPersistenceException;

    void update(E entity)
            throws InvalidEntityException, DuplicateEntityException, EntityPersistenceException, EntityNotFoundException;

    void delete(E entity)
            throws EntityPersistenceException;
}