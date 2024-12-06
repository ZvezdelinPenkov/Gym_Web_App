package org.example.gym_web_app.service;

import org.example.gym_web_app.model.Role;
import org.example.gym_web_app.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    public List<Role> getAllRoles() {
        return  roleRepository.findAll();
    }

    public Optional<Role> getRoleById(Long id) {
        return roleRepository.findById(id);
    }

    public Role addRole(Role role) {
        if (role.getName() == null || role.getName().isBlank()) {
            throw new IllegalArgumentException("Role name cannot be null or empty");
        }
        if (roleRepository.existsByName(role.getName())) {
            throw new IllegalArgumentException("Role name already exists");
        }
        return roleRepository.save(role);
    }

    public Role updateRole(Long id, Role roleDetails) {
        Optional<Role> optionalRole = roleRepository.findById(id);
        if (optionalRole.isEmpty()) {
            throw new RuntimeException("Role not found with id " + id);
        }

        Role role = optionalRole.get();
        role.setName(roleDetails.getName());
        return roleRepository.save(role);
    }

    public boolean deleteRole(Long id) {
        if (roleRepository.existsById(id)) {
            roleRepository.deleteById(id);
            return true;
        } else {
            throw new RuntimeException("Role not found with id " + id);
        }
    }
}
