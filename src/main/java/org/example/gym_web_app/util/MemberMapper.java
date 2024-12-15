package org.example.gym_web_app.util;

import org.example.gym_web_app.dto.MemberDTO;
import org.example.gym_web_app.model.ClassSchedule;
import org.example.gym_web_app.model.Member;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class MemberMapper {

    public static MemberDTO toDTO(Member member) {
        MemberDTO dto = new MemberDTO();
        dto.setId(member.getId());
        dto.setFirstName(member.getFirstName());
        dto.setLastName(member.getLastName());
        dto.setEmail(member.getEmail());
        dto.setDateOfBirth(member.getDateOfBirth());
        dto.setJoinDate(member.getJoinDate());
        dto.setMembershipType(member.getMembershipType());
        dto.setActive(member.isActive());
        dto.setClassScheduleIds(
                member.getClassSchedules().stream()
                        .map(ClassSchedule::getId)
                        .collect(Collectors.toSet())
        );
        dto.setUserId(member.getUser() != null ? member.getUser().getId() : null);
        return dto;
    }

    public static Member toEntity(MemberDTO dto) {
        Member member = new Member();
        member.setId(dto.getId());
        member.setFirstName(dto.getFirstName());
        member.setLastName(dto.getLastName());
        member.setEmail(dto.getEmail());
        member.setDateOfBirth(dto.getDateOfBirth());
        member.setJoinDate(dto.getJoinDate());
        member.setMembershipType(dto.getMembershipType());
        member.setActive(dto.isActive());

        return member;
    }
}
