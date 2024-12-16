package org.example.gym_web_app.service;

import org.example.gym_web_app.dto.SignupDTO;
import org.example.gym_web_app.repository.SignupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SignupService {

    @Autowired
    private SignupRepository signupRepository;

    public List<SignupDTO> getAllSignups() {
        return signupRepository.findAll();
    }

    public Optional<SignupDTO> getSignupById(Long id) {
        return signupRepository.findById(id);
    }

    public SignupDTO addSignup(SignupDTO signupDTO) {
        // Check for maxParticipants
        long count = signupRepository.countByClassScheduleId(signupDTO.getClassScheduleId());
        long maxParticipants = signupRepository.getMaxParticipantsByClassId(signupDTO.getClassScheduleId());
        if (count >= maxParticipants) {
            throw new IllegalArgumentException("Class is fully booked.");
        }
        return signupRepository.save(signupDTO);
    }

    public boolean deleteSignup(Long id) {
        if (signupRepository.existsById(id)) {
            signupRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
