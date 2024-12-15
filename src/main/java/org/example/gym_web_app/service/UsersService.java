package org.example.gym_web_app.service;

import org.example.gym_web_app.dto.UsersDTO;
import org.example.gym_web_app.model.Role;
import org.example.gym_web_app.model.Users;
import org.example.gym_web_app.repository.UsersRepository;
import org.example.gym_web_app.repository.RoleRepository;
import org.example.gym_web_app.util.UsersMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.Collections;


@Service
public class UsersService {

    @Autowired
    private UsersRepository userRepository;


    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public List<UsersDTO> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(UsersMapper::toDTO)
                .collect(Collectors.toList());
    }

    public Optional<UsersDTO> getUserById(Long id) {
        return userRepository.findById(id).map(UsersMapper::toDTO);
    }

    public Users register(UsersDTO userDTO) {
        // Check if the user already exists
        if (userRepository.findByUsername(userDTO.getUsername()).isPresent()) {
            throw new IllegalArgumentException("Username already exists");
        }

        Role memberRole = roleRepository.findByName("MEMBER")
                .orElseThrow(() -> new RuntimeException("Role MEMBER not found"));


        // Create and save the new user
        Users user = new Users();
        user.setUsername(userDTO.getUsername());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setRoles(Collections.singleton(memberRole));

        return userRepository.save(user);
    }


    public UsersDTO addUser(UsersDTO userDTO) {
        Users user = UsersMapper.toEntity(userDTO);
        user.setPassword(passwordEncoder.encode(userDTO.getUsername())); // Password handling
        Users savedUser = userRepository.save(user);
        return UsersMapper.toDTO(savedUser);
    }

    public UsersDTO updateUser(Long id, UsersDTO userDTO) {
        Optional<Users> optionalUser = userRepository.findById(id);
        if (optionalUser.isEmpty()) {
            throw new RuntimeException("User not found with id " + id);
        }

        Users user = optionalUser.get();
        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        Users updatedUser = userRepository.save(user);
        return UsersMapper.toDTO(updatedUser);
    }

    public boolean deleteUser(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return true;
        } else {
            throw new RuntimeException("User not found with id " + id);
        }
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
