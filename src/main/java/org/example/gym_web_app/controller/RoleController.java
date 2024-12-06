package org.example.gym_web_app.controller;

import org.example.gym_web_app.dto.RoleDTO;
import org.example.gym_web_app.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/roles")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @GetMapping
    public ResponseEntity<List<RoleDTO>> getAllRoles() {
        List<RoleDTO> roles = roleService.getAllRoles();
        return ResponseEntity.ok(roles);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoleDTO> getRoleById(@PathVariable Long id) {
        Optional<RoleDTO> role = roleService.getRoleById(id);
        return role.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<RoleDTO> addRole(@RequestBody RoleDTO roleDTO) {
        try {
            RoleDTO createdRole = roleService.addRole(roleDTO);
            return ResponseEntity.status(201).body(createdRole);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<RoleDTO> updateRole(@PathVariable Long id, @RequestBody RoleDTO roleDTO) {
        try {
            RoleDTO updatedRole = roleService.updateRole(id, roleDTO);
            return ResponseEntity.ok(updatedRole);
        } catch (RuntimeException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRole(@PathVariable Long id) {
        try {
            if (roleService.deleteRole(id)) {
                return ResponseEntity.noContent().build();
            }
        } catch (RuntimeException ex) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.notFound().build();
    }
}
