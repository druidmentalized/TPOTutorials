package org.project.tpo07.services;

import com.google.googlejavaformat.java.Formatter;
import com.google.googlejavaformat.java.FormatterException;
import org.project.tpo07.dto.FormatRequest;
import org.project.tpo07.dto.FormatResult;
import org.project.tpo07.exceptions.ResultNotFoundException;
import org.project.tpo07.exceptions.ResultPersistenceException;
import org.project.tpo07.repositories.FileFormatResultsRepository;
import org.project.tpo07.repositories.FormatResultsRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class FormatService {
    private final FormatResultsRepository formatResultsRepository;

    private final Formatter formatter = new Formatter();

    public FormatService(FileFormatResultsRepository fileFormatResultsRepository) {
        this.formatResultsRepository = fileFormatResultsRepository;
    }

    public String formatCode(String sourceCode) throws FormatterException {
        return formatter.formatSource(sourceCode);
    }

    public void saveResult(FormatRequest formatRequest) throws ResultPersistenceException {
        FormatResult formatResult = new FormatResult();

        formatResult.setId(formatRequest.getId());
        formatResult.setSourceCode(formatRequest.getSourceCode());
        formatResult.setFormattedCode(formatRequest.getFormattedCode());
        formatResult.setFormatDate(LocalDateTime.now());
        formatResult.setExpiryDate(LocalDateTime.now().plusSeconds(formatRequest.getDuration()));

        formatResultsRepository.save(formatResult);
    }

    public FormatResult getResultById(String id) throws ResultNotFoundException {
        FormatResult formatResult = formatResultsRepository.findById(id);

        if (formatResult == null) {
            throw new ResultNotFoundException("Result not found for id: " + id);
        }

        return formatResult;
    }

    public List<FormatResult> getAllResults(){
        List<FormatResult> formatResults = formatResultsRepository.findAll();

        if (formatResults == null || formatResults.isEmpty()) {
            throw new RuntimeException("No format results found");
        }

        return formatResults;
    }

    public void deleteResults(List<FormatResult> expiredResults) {
        try {
            for (FormatResult expiredResult : expiredResults) {
                formatResultsRepository.deleteById(expiredResult.getId());
            }
        } catch (ResultPersistenceException e) {
            System.err.println(e.getMessage());
        }
    }
}
