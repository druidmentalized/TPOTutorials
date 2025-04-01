package org.blog.repositories;

import org.blog.models.Blog;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface BlogRepository extends CrudRepository<Blog, Long> {

    Optional<Blog> findByNameIgnoreCase(String name);
}
