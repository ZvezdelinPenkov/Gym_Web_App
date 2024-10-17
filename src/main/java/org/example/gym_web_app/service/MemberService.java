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
        return memberRepository.save(member);
    }

    public Member updateMember(Long id, Member memberDetails) {
        Optional<Member> optionalMember = memberRepository.findById(id);

        if (optionalMember.isPresent()) {
            Member member = optionalMember.get();
            member.setFirstName(memberDetails.getFirstName());
            member.setLastName(memberDetails.getLastName());
            member.setEmail(memberDetails.getEmail());
            member.setDateOfBirth(memberDetails.getDateOfBirth());
            member.setMembershipType(memberDetails.getMembershipType());
            return memberRepository.save(member);
        } else {
            throw new RuntimeException("Member not found with id " + id);
        }
    }

    public void deleteMember(Long id) {
        memberRepository.deleteById(id);
    }
}
