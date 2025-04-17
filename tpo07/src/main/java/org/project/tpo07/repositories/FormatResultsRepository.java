package org.project.tpo07.repositories;

import org.project.tpo07.dto.FormatResult;

import java.util.List;

public interface FormatResultsRepository {
    void save(FormatResult formatResult);
    FormatResult findById(String id);
    List<FormatResult> findAll();
}
