package org.example.gym_web_app.service;

import org.example.gym_web_app.dto.MemberDTO;
import org.example.gym_web_app.util.MemberMapper;
import org.example.gym_web_app.model.Member;
import org.example.gym_web_app.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
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

    public Optional<MemberDTO> getMemberById(Long id) {
        return memberRepository.findById(id).map(MemberMapper::toDTO);
    }

    public MemberDTO addMember(MemberDTO memberDTO) {
        Member member = MemberMapper.toEntity(memberDTO);
        Member savedMember = memberRepository.save(member);
        return MemberMapper.toDTO(savedMember);
    }

    public MemberDTO updateMember(Long id, MemberDTO memberDTO) {
        Optional<Member> optionalMember = memberRepository.findById(id);
        if (optionalMember.isEmpty()) {
            throw new RuntimeException("Member not found with id " + id);
        }

        Member member = optionalMember.get();
        member.setFirstName(memberDTO.getFirstName());
        member.setLastName(memberDTO.getLastName());
        member.setEmail(memberDTO.getEmail());
        member.setDateOfBirth(memberDTO.getDateOfBirth());
        member.setMembershipType(memberDTO.getMembershipType());
        member.setActive(memberDTO.isActive());
        Member updatedMember = memberRepository.save(member);
        return MemberMapper.toDTO(updatedMember);
    }

    public boolean deleteMember(Long id) {
        if (memberRepository.existsById(id)) {
            memberRepository.deleteById(id);
            return true;
        } else {
            throw new RuntimeException("Member not found with id " + id);
        }
    }
}
