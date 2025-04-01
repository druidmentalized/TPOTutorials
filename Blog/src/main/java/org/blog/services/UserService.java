package org.blog.services;

import org.blog.exceptions.DuplicateEntityException;
import org.blog.exceptions.EntityNotFoundException;
import org.blog.exceptions.EntityPersistenceException;
import org.blog.exceptions.InvalidEntityException;
import org.blog.models.User;
import org.blog.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements CrudService<User, Long> {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> getAll() {
        return (List<User>) userRepository.findAll();
    }

    @Override
    public User getById(Long id) throws EntityNotFoundException {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User with ID " + id + " not found."));
    }

    public User getByIdOrEmail(String input) {
        try {
            Long id = Long.parseLong(input);
            Optional<User> userById = userRepository.findById(id);
            if (userById.isPresent()) {
                return userById.get();
            }
        } catch (NumberFormatException e) {
            // Input is not numeric, continue to search by email
        }

        return userRepository.findByEmailIgnoreCase(input)
                .orElseThrow(() -> new EntityNotFoundException("User with email '" + input + "' not found."));
    }

    @Override
    public void save(User user) throws InvalidEntityException, DuplicateEntityException, EntityPersistenceException {
        if (user.getEmail() == null || user.getEmail().isBlank()) {
            throw new InvalidEntityException("User email cannot be empty.");
        }

        boolean exists = userRepository.existsByEmailIgnoreCase(user.getEmail());
        if (exists) {
            throw new DuplicateEntityException("User already exists with email: " + user.getEmail());
        }

        try {
            userRepository.save(user);
        } catch (Exception e) {
            throw new EntityPersistenceException("Error while saving user to the database.", e);
        }
    }

    @Override
    public void update(User user) throws InvalidEntityException, DuplicateEntityException, EntityPersistenceException, EntityNotFoundException {
        if (user.getEmail() == null || user.getEmail().isBlank()) {
            throw new InvalidEntityException("User email cannot be empty.");
        }

        if (!userRepository.existsById(user.getId())) {
            throw new EntityNotFoundException("User with ID " + user.getId() + " does not exist.");
        }

        Optional<User> existingUser = userRepository.findByEmailIgnoreCase(user.getEmail());
        if (existingUser.isPresent() && !existingUser.get().getId().equals(user.getId())) {
            throw new DuplicateEntityException("Another user already exists with email: " + user.getEmail());
        }

        try {
            userRepository.save(user);
        } catch (Exception e) {
            throw new EntityPersistenceException("Error while updating user in the database.", e);
        }
    }

    @Override
    public void delete(User user) throws EntityPersistenceException {
        try {
            userRepository.delete(user);
            if (userRepository.existsById(user.getId())) {
                throw new EntityPersistenceException("User could not be deleted.");
            }
        } catch (Exception e) {
            throw new EntityPersistenceException("Failed to delete user", e);
        }
    }
}
