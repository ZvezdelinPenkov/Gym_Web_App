package org.example.gym_web_app.controller;

import org.example.gym_web_app.dto.SignupDTO;
import org.example.gym_web_app.service.SignupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/signups")
public class SignupController {

    @Autowired
    private SignupService signupService;

    @GetMapping
    public ResponseEntity<List<SignupDTO>> getAllSignups() {
        return ResponseEntity.ok(signupService.getAllSignups());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SignupDTO> getSignupById(@PathVariable Long id) {
        Optional<SignupDTO> signup = signupService.getSignupById(id);
        return signup.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<SignupDTO> addSignup(@RequestBody SignupDTO signupDTO) {
        try {
            SignupDTO createdSignup = signupService.addSignup(signupDTO);
            return ResponseEntity.status(201).body(createdSignup);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSignup(@PathVariable Long id) {
        if (signupService.deleteSignup(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
