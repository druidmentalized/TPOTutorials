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
        return List.of();
    }

    @Override
    public Role getById(Long id) {
        return null;
    }

    public Role getByIdOrName(String input) {
        try {
            Long id = Long.parseLong(input);
            Optional<Role> roleById = roleRepository.findById(id);
            if (roleById.isPresent()) {
                return roleById.get();
            }
        } catch (NumberFormatException e) {
            // Not a number/ID., continuing
        }

        Optional<Role> role = roleRepository.
                findByNameIgnoreCase(input);
        return role.orElseThrow(() ->
                new EntityNotFoundException("Role with name " + input + " not found"));
    }

    @Override
    public void save(Role entity) {

    }

    @Override
    public void update(Role entity) {

    }

    @Override
    public void delete(Role entity) {

    }
}
