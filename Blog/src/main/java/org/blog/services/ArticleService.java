package org.blog.services;

import org.blog.exceptions.DuplicateEntityException;
import org.blog.exceptions.EntityNotFoundException;
import org.blog.exceptions.EntityPersistenceException;
import org.blog.exceptions.InvalidEntityException;
import org.blog.models.Article;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleService implements CrudService<Article, Long> {
    @Override
    public List<Article> getAll() {
        return List.of();
    }

    @Override
    public Article getById(Long id) throws EntityNotFoundException {
        return null;
    }

    @Override
    public void save(Article entity) throws InvalidEntityException, DuplicateEntityException, EntityPersistenceException {

    }

    @Override
    public void update(Article entity) throws InvalidEntityException, DuplicateEntityException, EntityPersistenceException, EntityNotFoundException {

    }

    @Override
    public void delete(Article entity) throws EntityPersistenceException {

    }
}
