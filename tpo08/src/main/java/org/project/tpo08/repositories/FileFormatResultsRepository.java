package org.project.tpo08.repositories;

import org.project.tpo08.dto.FormatResult;
import org.project.tpo08.exceptions.ResultNotFoundException;
import org.project.tpo08.exceptions.ResultPersistenceException;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Repository
public class FileFormatResultsRepository implements FormatResultsRepository {
    private final Path storagePath;

    public FileFormatResultsRepository(Path storagePath) throws IOException {
        this.storagePath = storagePath;
        Files.createDirectories(storagePath);
    }

    @Override
    public void save(FormatResult result) {
        try (var out = new ObjectOutputStream(Files.newOutputStream(storagePath.resolve(result.getId() + ".ser")))) {
            out.writeObject(result);
        } catch (IOException e) {
            throw new ResultPersistenceException("Error saving format result", e);
        }
    }

    @Override
    public FormatResult findById(String id) {
        try (var in = new ObjectInputStream(Files.newInputStream(storagePath.resolve(id + ".ser")))) {
            return (FormatResult) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new ResultNotFoundException("Error retrieving result from the file " + storagePath.resolve(id + ".ser"), e);
        }
    }

    @Override
    public List<FormatResult> findAll() {
        try (Stream<Path> files = Files.list(storagePath)) {
            return files.map(path -> {
                try (var in = new ObjectInputStream(Files.newInputStream(path))) {
                    return (FormatResult) in.readObject();
                } catch (IOException | ClassNotFoundException e) {
                    return null;
                }
            }).filter(Objects::nonNull).collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException("Error getting files from the storage", e);
        }
    }

    @Override
    public void deleteById(String id) {
        try {
            Path filePath = storagePath.resolve(id + ".ser");
            if (Files.exists(filePath)) {
                Files.delete(filePath);
            }
            else throw new ResultNotFoundException("File not found " + storagePath.resolve(id + ".ser"));
        } catch (IOException e) {
            throw new ResultPersistenceException("Error deleting the file from the system", e);
        }
    }
}