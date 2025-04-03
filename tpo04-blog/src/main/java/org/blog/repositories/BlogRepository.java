package org.blog.repositories;

import org.blog.models.Blog;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BlogRepository extends CrudRepository<Blog, Long> {

    Optional<Blog> findByNameIgnoreCase(String name);
}
