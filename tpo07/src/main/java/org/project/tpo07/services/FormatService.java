package org.project.tpo07.services;

import com.google.googlejavaformat.java.Formatter;
import com.google.googlejavaformat.java.FormatterException;
import org.project.tpo07.dto.FormatRequest;
import org.project.tpo07.dto.FormatResult;
import org.project.tpo07.repositories.FormatResultsRepository;
import org.springframework.stereotype.Service;

@Service
public class FormatService {
    private final FormatResultsRepository formatResultsRepository;

    private final Formatter formatter = new Formatter();

    public FormatService(FormatResultsRepository formatResultsRepository) {
        this.formatResultsRepository = formatResultsRepository;
    }

    public String formatCode(String sourceCode) throws FormatterException {
        return formatter.formatSource(sourceCode);
    }

    public void saveResult(FormatRequest formatRequest) {

    }

    public FormatResult getResultById(String id) {

    }
}
