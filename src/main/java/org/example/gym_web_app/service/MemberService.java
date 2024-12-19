package org.example.gym_web_app.service;

import org.example.gym_web_app.dto.MemberDTO;
import org.example.gym_web_app.exception.DuplicateResourceException;
import org.example.gym_web_app.exception.InvalidRequestException;
import org.example.gym_web_app.exception.ResourceNotFoundException;
import org.example.gym_web_app.model.Member;
import org.example.gym_web_app.repository.MemberRepository;
import org.example.gym_web_app.util.MemberMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MemberService {

    @Autowired
    private MemberRepository memberRepository;

    public List<MemberDTO> getAllMembers() {
        return memberRepository.findAll()
                .stream()
                .map(MemberMapper::toDTO)
                .collect(Collectors.toList());
    }

    public MemberDTO getMemberById(Long id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Member not found with id: " + id));
        return MemberMapper.toDTO(member);
    }

    public MemberDTO addMember(MemberDTO memberDTO) {
        if (memberDTO.getEmail() == null || memberDTO.getFirstName() == null || memberDTO.getLastName() == null) {
            throw new InvalidRequestException("All required fields must be provided");
        }

        if (memberRepository.findByEmail(memberDTO.getEmail()).isPresent()) {
            throw new DuplicateResourceException("Email already exists: " + memberDTO.getEmail());
        }

        Member member = MemberMapper.toEntity(memberDTO);
        Member savedMember = memberRepository.save(member);

        return MemberMapper.toDTO(savedMember);
    }

    public MemberDTO updateMember(Long id, MemberDTO memberDTO) {
        Member existingMember = memberRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Member not found with id: " + id));

        existingMember.setFirstName(memberDTO.getFirstName());
        existingMember.setLastName(memberDTO.getLastName());
        existingMember.setEmail(memberDTO.getEmail());
        existingMember.setDateOfBirth(memberDTO.getDateOfBirth());
        existingMember.setMembershipType(memberDTO.getMembershipType());
        existingMember.setActive(memberDTO.isActive());

        Member updatedMember = memberRepository.save(existingMember);

        return MemberMapper.toDTO(updatedMember);
    }

    public boolean deleteMember(Long id) {
        if (!memberRepository.existsById(id)) {
            throw new ResourceNotFoundException("Member not found with id: " + id);
        }
        memberRepository.deleteById(id);
        return false;
    }
}
