package org.blog.services;

import org.blog.exceptions.DuplicateEntityException;
import org.blog.exceptions.EntityPersistenceException;
import org.blog.exceptions.InvalidEntityException;
import org.blog.models.Blog;
import org.blog.repositories.BlogRepository;
import org.blog.exceptions.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BlogService implements CrudService<Blog, Long> {

    private final BlogRepository blogRepository;

    public BlogService(BlogRepository blogRepository) {
        this.blogRepository = blogRepository;
    }

    @Override
    public List<Blog> getAll() {
        try {
            return (List<Blog>) blogRepository.findAll();
        } catch (Exception e) {
            throw new EntityPersistenceException("Failed to retrieve blogs", e);
        }
    }

    @Override
    public Blog getById(Long id) throws EntityNotFoundException {
        return blogRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Blog with id " + id + " not found."));
    }

    public Blog getByIdOrName(String input) {
        try {
            Long id = Long.parseLong(input);
            Optional<Blog> blogById = blogRepository.findById(id);
            if (blogById.isPresent()) {
                return blogById.get();
            }
        } catch (NumberFormatException e) {
            // Not a number; try name
        }
        Optional<Blog> blogByName = blogRepository.findByNameIgnoreCase(input);
        return blogByName.orElseThrow(() ->
                new EntityNotFoundException("No blog found for input: " + input));
    }

    @Override
    public void save(Blog blog) {
        if (blog.getName() == null || blog.getName().isBlank()) {
            throw new InvalidEntityException("Blog name is required.");
        }
        Optional<Blog> existingBlog = blogRepository.findByNameIgnoreCase(blog.getName());
        if (existingBlog.isPresent()) {
            throw new DuplicateEntityException("Blog already exists with name: " + blog.getName());
        }
        try {
            blogRepository.save(blog);
        } catch (Exception e) {
            throw new EntityPersistenceException("Error while saving blog", e);
        }
    }

    @Override
    public void update(Blog blog) {
        if (blog.getName() == null || blog.getName().isBlank()) {
            throw new InvalidEntityException("Blog name is required.");
        }
        if (!blogRepository.existsById(blog.getId())) {
            throw new EntityNotFoundException("Blog with id " + blog.getId() + " does not exist.");
        }
        Optional<Blog> existingBlog = blogRepository.findByNameIgnoreCase(blog.getName());
        if (existingBlog.isPresent() && !existingBlog.get().getId().equals(blog.getId())) {
            throw new DuplicateEntityException("Another blog exists with name: " + blog.getName());
        }
        try {
            blogRepository.save(blog);
        } catch (Exception e) {
            throw new EntityPersistenceException("Error while updating blog", e);
        }
    }

    @Override
    public void delete(Blog blog) throws EntityPersistenceException {
        try {
            blogRepository.delete(blog);
            if (blogRepository.existsById(blog.getId())) {
                throw new EntityPersistenceException("Blog could not be deleted.");
            }
        } catch (Exception e) {
            throw new EntityPersistenceException("Failed to delete blog", e);
        }
    }
}