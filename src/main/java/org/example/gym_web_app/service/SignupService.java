package org.example.gym_web_app.service;

import org.example.gym_web_app.dto.MemberDTO;
import org.example.gym_web_app.dto.SignupDTO;
import org.example.gym_web_app.dto.ClassScheduleDTO;


import org.example.gym_web_app.model.Signup;
import org.example.gym_web_app.model.Member;
import org.example.gym_web_app.model.ClassSchedule;

import org.example.gym_web_app.repository.SignupRepository;
import org.example.gym_web_app.service.MemberService;
import org.example.gym_web_app.util.AttendanceMapper;
import org.example.gym_web_app.util.ClassScheduleMapper;
import org.example.gym_web_app.util.MemberMapper;
import org.example.gym_web_app.util.SignupMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SignupService {

    @Autowired
    private SignupRepository signupRepository;

    @Autowired
    private MemberService memberService;  // Inject MemberService

    @Autowired
    private ClassScheduleService classScheduleService;


    public List<SignupDTO> getAllSignups() {
        return signupRepository.findAll()
                .stream()
                .map(SignupMapper::toDTO)
                .collect(Collectors.toList());
    }

    public Optional<SignupDTO> getSignupById(Long id) {
        return signupRepository.findById(id).map(SignupMapper::toDTO);
    }

    public SignupDTO addSignup(SignupDTO signupDTO) {
        // Check for maxParticipants
        long count = signupRepository.countByClassScheduleId(signupDTO.getClassScheduleId());
        long maxParticipants = signupRepository.getMaxParticipantsByClassId(signupDTO.getClassScheduleId());
        if (count >= maxParticipants) {
            throw new IllegalArgumentException("Class is fully booked.");
        }

        // Fetch the MemberDTO and convert it to entity
        MemberDTO memberDTO = memberService.getMemberById(signupDTO.getMemberId())
                .orElseThrow(() -> new IllegalArgumentException("Member not found for ID: " + signupDTO.getMemberId()));
        Member memberEntity = MemberMapper.toEntity(memberDTO);

        // Fetch the ClassScheduleDTO and convert it to entity
        ClassScheduleDTO classScheduleDTO = classScheduleService.getScheduleById(signupDTO.getClassScheduleId())
                .orElseThrow(() -> new IllegalArgumentException("ClassSchedule not found for ID: " + signupDTO.getClassScheduleId()));
        ClassSchedule classScheduleEntity = ClassScheduleMapper.toEntity(classScheduleDTO);

        // Create the Signup entity
        Signup signup = new Signup();
        signup.setMember(memberEntity);
        signup.setClassSchedule(classScheduleEntity);

        // Save the Signup entity
        Signup savedSignup = signupRepository.save(signup);

        // Map the saved Signup entity back to SignupDTO
        return mapToDTO(savedSignup);
    }


    // Method to map entity to DTO
    private SignupDTO mapToDTO(Signup signup) {
        SignupDTO dto = new SignupDTO();
        dto.setId(signup.getId());
        dto.setMemberId(signup.getMember().getId());
        dto.setClassScheduleId(signup.getClassSchedule().getId());
        dto.setSignupTime(signup.getSignupTime());
        return dto;
    }



    public boolean deleteSignup(Long id) {
        if (signupRepository.existsById(id)) {
            signupRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
