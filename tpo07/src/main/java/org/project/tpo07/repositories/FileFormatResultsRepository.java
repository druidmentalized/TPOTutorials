package org.project.tpo07.repositories;

import org.project.tpo07.dto.FormatResult;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Repository
public class FileFormatResultsRepository implements FormatResultsRepository {
    private final Path storagePath = Paths.get("format-results");

    public FileFormatResultsRepository() throws IOException {
        Files.createDirectories(storagePath);
    }

    @Override
    public void save(FormatResult result) {
        try (var out = new ObjectOutputStream(Files.newOutputStream(storagePath.resolve(result.getId() + ".ser")))) {
            out.writeObject(result);
        } catch (IOException e) {
            throw new RuntimeException("Error saving format result", e);
        }
    }

    @Override
    public FormatResult findById(String id) {
        try (var in = new ObjectInputStream(Files.newInputStream(storagePath.resolve(id + ".ser")))) {
            return (FormatResult) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return null;
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
            return List.of();
        }
    }
}