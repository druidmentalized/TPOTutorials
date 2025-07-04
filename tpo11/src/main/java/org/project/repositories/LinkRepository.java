package org.project.repositories;

import org.project.entities.Link;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LinkRepository extends CrudRepository<Link, String> {
    boolean existsByTargetUrl(String url);

    boolean existsByTargetUrlAndIdNot(String targetUrl, String id);
}
