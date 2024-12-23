package org.example.gym_web_app.util;

import org.example.gym_web_app.dto.UsersDTO;
import org.example.gym_web_app.model.Role;
import org.example.gym_web_app.model.Users;

import java.util.HashSet;
import java.util.stream.Collectors;

public class UsersMapper {
    public static UsersDTO toDTO(Users user) {
        if (user == null) {
            return null;
        }

        UsersDTO dto = new UsersDTO();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        dto.setRoleIds(user.getRoles() != null ?
                user.getRoles().stream()
                        .map(Role::getId)
                        .collect(Collectors.toSet()) :
                new HashSet<>());
        return dto;
    }

    public static Users toEntity(UsersDTO dto) {
        Users user = new Users();
        user.setId(dto.getId());
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        // Roles need to be set in the service layer.
        return user;
    }
}
