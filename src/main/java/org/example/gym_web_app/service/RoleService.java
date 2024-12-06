package org.example.gym_web_app.service;

import org.example.gym_web_app.dto.RoleDTO;
import org.example.gym_web_app.model.Role;
import org.example.gym_web_app.repository.RoleRepository;
import org.example.gym_web_app.util.RoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
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

    public Optional<RoleDTO> getRoleById(Long id) {
        return roleRepository.findById(id).map(RoleMapper::toDTO);
    }

    public RoleDTO addRole(RoleDTO roleDTO) {
        Role role = RoleMapper.toEntity(roleDTO);
        Role savedRole = roleRepository.save(role);
        return RoleMapper.toDTO(savedRole);
    }

    public RoleDTO updateRole(Long id, RoleDTO roleDTO) {
        Optional<Role> optionalRole = roleRepository.findById(id);
        if (optionalRole.isEmpty()) {
            throw new RuntimeException("Role not found with id " + id);
        }

        Role role = optionalRole.get();
        role.setName(roleDTO.getName());
        Role updatedRole = roleRepository.save(role);
        return RoleMapper.toDTO(updatedRole);
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
