package org.example.gym_web_app.repository;

import org.example.gym_web_app.model.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Test
    void save_shouldPersistMember() {
        Member member = new Member("Jane", "Doe", "jane.doe@example.com", LocalDate.of(1995, 5, 15), "Standard");

        Member savedMember = memberRepository.save(member);

        assertNotNull(savedMember.getId());
        assertEquals("jane.doe@example.com", savedMember.getEmail());
    }

    @Test
    void findById_shouldReturnMemberIfExists() {
        Member member = new Member("John", "Smith", "john.smith@example.com", LocalDate.of(1985, 3, 10), "Premium");
        memberRepository.save(member);

        Optional<Member> foundMember = memberRepository.findById(member.getId());

        assertTrue(foundMember.isPresent());
        assertEquals("john.smith@example.com", foundMember.get().getEmail());
    }

    @Test
    void delete_shouldRemoveMember() {
        Member member = new Member("Alice", "Brown", "alice.brown@example.com", LocalDate.of(1998, 7, 20), "Premium");
        Member savedMember = memberRepository.save(member);

        memberRepository.deleteById(savedMember.getId());

        Optional<Member> foundMember = memberRepository.findById(savedMember.getId());
        assertTrue(foundMember.isEmpty());
    }
}
