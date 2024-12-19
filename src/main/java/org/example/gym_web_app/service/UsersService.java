package org.example.gym_web_app.service;

import org.example.gym_web_app.dto.UsersDTO;
import org.example.gym_web_app.exception.DuplicateResourceException;
import org.example.gym_web_app.exception.ResourceNotFoundException;
import org.example.gym_web_app.model.Role;
import org.example.gym_web_app.model.Users;
import org.example.gym_web_app.repository.UsersRepository;
import org.example.gym_web_app.repository.RoleRepository;
import org.example.gym_web_app.util.UsersMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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

    public UsersDTO getUserById(Long id) {
        Users user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
        return UsersMapper.toDTO(user);
    }

    public Users register(UsersDTO userDTO) {
        if (userRepository.findByUsername(userDTO.getUsername()).isPresent()) {
            throw new DuplicateResourceException("Username already exists: " + userDTO.getUsername());
        }

        Role memberRole = roleRepository.findByName("MEMBER")
                .orElseThrow(() -> new ResourceNotFoundException("Role MEMBER not found"));

        Users user = new Users();
        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setRoles(Collections.singleton(memberRole));

        return userRepository.save(user);
    }

    public UsersDTO addUser(UsersDTO userDTO) {
        if (userRepository.findByUsername(userDTO.getUsername()).isPresent()) {
            throw new DuplicateResourceException("Username already exists: " + userDTO.getUsername());
        }

        if (userRepository.findByEmail(userDTO.getEmail()).isPresent()) {
            throw new DuplicateResourceException("Email already exists: " + userDTO.getEmail());
        }

        Users user = UsersMapper.toEntity(userDTO);
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        Users savedUser = userRepository.save(user);
        return UsersMapper.toDTO(savedUser);
    }

    public UsersDTO updateUser(Long id, UsersDTO userDTO) {
        Users user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));

        if (!user.getUsername().equals(userDTO.getUsername()) &&
                userRepository.findByUsername(userDTO.getUsername()).isPresent()) {
            throw new DuplicateResourceException("Username already exists: " + userDTO.getUsername());
        }

        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        Users updatedUser = userRepository.save(user);
        return UsersMapper.toDTO(updatedUser);
    }

    public boolean deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException("User not found with id: " + id);
        }
        userRepository.deleteById(id);
        return false;
    }
}
