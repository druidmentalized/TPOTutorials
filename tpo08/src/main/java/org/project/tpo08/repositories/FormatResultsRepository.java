package org.project.tpo08.repositories;

import org.project.tpo08.dto.FormatResult;

import java.util.List;

public interface FormatResultsRepository {
    void save(FormatResult formatResult);
    FormatResult findById(String id);
    List<FormatResult> findAll();
    void deleteById(String id);
}
