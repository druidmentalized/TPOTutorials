package org.blog.services;

import org.blog.exceptions.EntityPersistenceException;
import org.blog.models.Blog;
import org.blog.repositories.BlogRepository;
import org.books.exceptions.EntityNotFoundException;
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
        return List.of();
    }

    @Override
    public Blog getById(Long id) {
        return null;
    }

    public Blog getByIdOrName(String input) {
        try {
            Long id = Long.parseLong(input);
            Optional<Blog> blogById = blogRepository.findById(id);
        } catch (NumberFormatException e) {
            // Not a number
        }

        Optional<Blog> blog = blogRepository.
                findByNameIgnoreCase(input);
        return blog.orElseThrow(() ->
                new EntityNotFoundException("No blog found with for input: " + input));
    }

    @Override
    public void save(Blog entity) {

    }

    @Override
    public void update(Blog entity) {

    }

    @Override
    public void delete(Blog entity) throws EntityPersistenceException {

    }
}
