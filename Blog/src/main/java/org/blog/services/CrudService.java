package org.blog.services;

import org.blog.exceptions.DuplicateEntityException;
import org.blog.exceptions.EntityNotFoundException;
import org.blog.exceptions.EntityPersistenceException;
import org.blog.exceptions.InvalidEntityException;

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