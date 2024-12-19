package org.example.gym_web_app.service;

import org.example.gym_web_app.dto.RoleDTO;
import org.example.gym_web_app.exception.DuplicateResourceException;
import org.example.gym_web_app.exception.ResourceNotFoundException;
import org.example.gym_web_app.model.Role;
import org.example.gym_web_app.repository.RoleRepository;
import org.example.gym_web_app.util.RoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    public List<RoleDTO> getAllRoles() {
        return roleRepository.findAll()
                .stream()
                .map(RoleMapper::toDTO)
                .collect(Collectors.toList());
    }

    public RoleDTO getRoleById(Long id) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Role not found with id: " + id));
        return RoleMapper.toDTO(role);
    }

    public RoleDTO addRole(RoleDTO roleDTO) {
        if (roleRepository.findByName(roleDTO.getName()).isPresent()) {
            throw new DuplicateResourceException("Role with name '" + roleDTO.getName() + "' already exists");
        }

        Role role = RoleMapper.toEntity(roleDTO);
        Role savedRole = roleRepository.save(role);
        return RoleMapper.toDTO(savedRole);
    }

    public RoleDTO updateRole(Long id, RoleDTO roleDTO) {
        Role existingRole = roleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Role not found with id: " + id));

        if (!existingRole.getName().equals(roleDTO.getName()) &&
                roleRepository.findByName(roleDTO.getName()).isPresent()) {
            throw new DuplicateResourceException("Role with name '" + roleDTO.getName() + "' already exists");
        }

        existingRole.setName(roleDTO.getName());
        Role updatedRole = roleRepository.save(existingRole);
        return RoleMapper.toDTO(updatedRole);
    }

    public boolean deleteRole(Long id) {
        if (!roleRepository.existsById(id)) {
            throw new ResourceNotFoundException("Role not found with id: " + id);
        }
        roleRepository.deleteById(id);
        return false;
    }
}
