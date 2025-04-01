package org.blog.services;

import org.blog.exceptions.DuplicateEntityException;
import org.blog.exceptions.EntityNotFoundException;
import org.blog.exceptions.EntityPersistenceException;
import org.blog.exceptions.InvalidEntityException;
import org.blog.models.Role;
import org.blog.repositories.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleService implements CrudService<Role, Long> {

    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public List<Role> getAll() {
        try {
            return (List<Role>) roleRepository.findAll();
        } catch (Exception e) {
            throw new EntityPersistenceException("Error retrieving roles", e);
        }
    }

    @Override
    public Role getById(Long id) {
        return roleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Role with id " + id + " not found."));
    }

    public Role getByIdOrName(String input) {
        try {
            Long id = Long.parseLong(input);
            Optional<Role> roleById = roleRepository.findById(id);
            if (roleById.isPresent()) {
                return roleById.get();
            }
        } catch (NumberFormatException e) {
            // Not a valid id, try name next.
        }
        Optional<Role> role = roleRepository.findByNameIgnoreCase(input);
        return role.orElseThrow(() ->
                new EntityNotFoundException("Role with name " + input + " not found."));
    }

    @Override
    public void save(Role role) {
        if (role.getName() == null || role.getName().isBlank()) {
            throw new InvalidEntityException("Role name cannot be empty.");
        }
        if (roleRepository.existsByNameIgnoreCase(role.getName())) {
            throw new DuplicateEntityException("Role already exists: " + role.getName());
        }
        try {
            roleRepository.save(role);
        } catch (Exception e) {
            throw new EntityPersistenceException("Error while saving role", e);
        }
    }

    @Override
    public void update(Role role) {
        if (role.getName() == null || role.getName().isBlank()) {
            throw new InvalidEntityException("Role name cannot be empty.");
        }
        if (!roleRepository.existsById(role.getId())) {
            throw new EntityNotFoundException("Role with id " + role.getId() + " not found.");
        }
        Optional<Role> existingRole = roleRepository.findByNameIgnoreCase(role.getName());
        if (existingRole.isPresent() && !existingRole.get().getId().equals(role.getId())) {
            throw new DuplicateEntityException("Another role already exists with name: " + role.getName());
        }
        try {
            roleRepository.save(role);
        } catch (Exception e) {
            throw new EntityPersistenceException("Error while updating role", e);
        }
    }

    @Override
    public void delete(Role entity) throws EntityPersistenceException {
        try {
            roleRepository.delete(entity);
        } catch (Exception e) {
            throw new EntityPersistenceException("Failed to delete role: " + entity.getName(), e);
        }
    }
}