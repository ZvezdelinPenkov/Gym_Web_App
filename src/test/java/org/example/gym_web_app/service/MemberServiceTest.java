package org.example.gym_web_app.service;

import org.example.gym_web_app.dto.MemberDTO;
import org.example.gym_web_app.model.Member;
import org.example.gym_web_app.repository.MemberRepository;
import org.example.gym_web_app.util.MemberMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MemberServiceTest {

    @Mock
    private MemberRepository memberRepository;

    @InjectMocks
    private MemberService memberService;

    private Member testMember;
    private MemberDTO testMemberDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        testMember = new Member("John", "Doe", "john.doe@example.com", LocalDate.of(1990, 1, 1), "Premium");
        testMember.setId(1L);

        testMemberDTO = MemberMapper.toDTO(testMember);
    }

    @Test
    void getAllMembers_shouldReturnListOfMembers() {
        when(memberRepository.findAll()).thenReturn(Arrays.asList(testMember));

        List<MemberDTO> members = memberService.getAllMembers();

        assertEquals(1, members.size());
        assertEquals(testMember.getEmail(), members.get(0).getEmail());
        verify(memberRepository, times(1)).findAll();
    }

    @Test
    void getMemberById_shouldReturnMemberIfExists() {
        when(memberRepository.findById(1L)).thenReturn(Optional.of(testMember));

        Optional<MemberDTO> result = memberService.getMemberById(1L);

        assertTrue(result.isPresent());
        assertEquals(testMember.getEmail(), result.get().getEmail());
        verify(memberRepository, times(1)).findById(1L);
    }

    @Test
    void getMemberById_shouldReturnEmptyIfNotExists() {
        when(memberRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<MemberDTO> result = memberService.getMemberById(1L);

        assertTrue(result.isEmpty());
        verify(memberRepository, times(1)).findById(1L);
    }

    @Test
    void addMember_shouldSaveAndReturnMember() {
        when(memberRepository.save(any(Member.class))).thenReturn(testMember);

        MemberDTO result = memberService.addMember(testMemberDTO);

        assertNotNull(result);
        assertEquals(testMemberDTO.getEmail(), result.getEmail());
        verify(memberRepository, times(1)).save(any(Member.class));
    }

    @Test
    void updateMember_shouldUpdateAndReturnMember() {
        when(memberRepository.findById(1L)).thenReturn(Optional.of(testMember));
        when(memberRepository.save(any(Member.class))).thenReturn(testMember);

        testMemberDTO.setFirstName("UpdatedName");
        MemberDTO result = memberService.updateMember(1L, testMemberDTO);

        assertNotNull(result);
        assertEquals("UpdatedName", result.getFirstName());
        verify(memberRepository, times(1)).findById(1L);
        verify(memberRepository, times(1)).save(any(Member.class));
    }

    @Test
    void deleteMember_shouldDeleteIfExists() {
        when(memberRepository.existsById(1L)).thenReturn(true);

        boolean result = memberService.deleteMember(1L);

        assertTrue(result);
        verify(memberRepository, times(1)).existsById(1L);
        verify(memberRepository, times(1)).deleteById(1L);
    }

    @Test
    void deleteMember_shouldThrowIfNotExists() {
        when(memberRepository.existsById(1L)).thenReturn(false);

        assertThrows(RuntimeException.class, () -> memberService.deleteMember(1L));
        verify(memberRepository, times(1)).existsById(1L);
        verify(memberRepository, never()).deleteById(1L);
    }
}
