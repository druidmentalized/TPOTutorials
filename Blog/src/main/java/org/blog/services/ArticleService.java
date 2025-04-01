package org.blog.services;

import org.blog.exceptions.DuplicateEntityException;
import org.blog.exceptions.EntityNotFoundException;
import org.blog.exceptions.EntityPersistenceException;
import org.blog.exceptions.InvalidEntityException;
import org.blog.models.Article;
import org.blog.repositories.ArticleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ArticleService implements CrudService<Article, Long> {

    private final ArticleRepository articleRepository;

    public ArticleService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    @Override
    public List<Article> getAll() {
        try {
            return (List<Article>) articleRepository.findAll();
        } catch (Exception e) {
            throw new EntityPersistenceException("Error retrieving articles", e);
        }
    }

    @Override
    public Article getById(Long id) {
        return articleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Article with id " + id + " not found."));
    }

    public Article getByIdOrTitle(String input) {
        try {
            Long id = Long.parseLong(input);
            Optional<Article> articleById = articleRepository.findById(id);
            if (articleById.isPresent()) {
                return articleById.get();
            }
        } catch (NumberFormatException e) {
            // Not a valid id, so try by title.
        }
        Optional<Article> article = articleRepository.findByTitleIgnoreCase(input);
        return article.orElseThrow(() ->
                new EntityNotFoundException("Article with title '" + input + "' not found."));
    }

    @Override
    public void save(Article article) {
        if (article.getTitle() == null || article.getTitle().isBlank()) {
            throw new InvalidEntityException("Article title cannot be empty.");
        }
        // Additional validations can be added here if needed.

        Optional<Article> existingArticle = articleRepository.findByTitleIgnoreCase(article.getTitle());
        if (existingArticle.isPresent()) {
            throw new DuplicateEntityException("Article with title '" + article.getTitle() + "' already exists.");
        }

        try {
            articleRepository.save(article);
        } catch (Exception e) {
            throw new EntityPersistenceException("Error saving article: " + e.getMessage(), e);
        }
    }

    @Override
    public void update(Article article) {
        if (article.getTitle() == null || article.getTitle().isBlank()) {
            throw new InvalidEntityException("Article title cannot be empty.");
        }
        if (!articleRepository.existsById(article.getId())) {
            throw new EntityNotFoundException("Article with id " + article.getId() + " not found.");
        }
        Optional<Article> existingArticle = articleRepository.findByTitleIgnoreCase(article.getTitle());
        if (existingArticle.isPresent() && !existingArticle.get().getId().equals(article.getId())) {
            throw new DuplicateEntityException("Another article with title '" + article.getTitle() + "' already exists.");
        }
        try {
            articleRepository.save(article);
        } catch (Exception e) {
            throw new EntityPersistenceException("Error updating article: " + e.getMessage(), e);
        }
    }

    @Override
    public void delete(Article article) {
        try {
            articleRepository.delete(article);
            if (articleRepository.existsById(article.getId())) {
                throw new EntityPersistenceException("Article could not be deleted.");
            }
        } catch (Exception e) {
            throw new EntityPersistenceException("Failed to delete article", e);
        }
    }
}
