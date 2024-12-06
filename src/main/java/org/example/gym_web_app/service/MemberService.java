package org.example.gym_web_app.service;

import org.example.gym_web_app.model.Member;
import org.example.gym_web_app.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MemberService {

    @Autowired
    private MemberRepository memberRepository;

    public List<Member> getAllMembers() {
        return memberRepository.findAll();
    }

    public Optional<Member> getMemberById(Long id) {
        return memberRepository.findById(id);
    }

    public Member addMember(Member member) {
        validateMember(member);
        return memberRepository.save(member);
    }

    public Member updateMember(Long id, Member memberDetails) {
        Optional<Member> optionalMember = memberRepository.findById(id);
        if (optionalMember.isEmpty()) {
            throw new RuntimeException("Member not found with id " + id);
        }

        Member member = optionalMember.get();
        member.setFirstName(memberDetails.getFirstName());
        member.setLastName(memberDetails.getLastName());
        member.setEmail(memberDetails.getEmail());
        member.setDateOfBirth(memberDetails.getDateOfBirth());
        member.setMembershipType(memberDetails.getMembershipType());
        member.setActive(memberDetails.isActive());
        return memberRepository.save(member);
    }

    public boolean deleteMember(Long id) {
        if (memberRepository.existsById(id)) {
            memberRepository.deleteById(id);
            return true;
        } else {
            throw new RuntimeException("Member not found with id " + id);
        }
    }

    private void validateMember(Member member) {
        if (member.getEmail() == null || member.getEmail().isBlank()) {
            throw new IllegalArgumentException("Email cannot be null or empty");
        }
        if (member.getFirstName() == null || member.getFirstName().isBlank()) {
            throw new IllegalArgumentException("First name cannot be null or empty");
        }
        if (member.getLastName() == null || member.getLastName().isBlank()) {
            throw new IllegalArgumentException("Last name cannot be null or empty");
        }
    }
}
