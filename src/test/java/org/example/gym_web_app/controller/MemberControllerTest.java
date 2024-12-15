package org.example.gym_web_app.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.gym_web_app.dto.MemberDTO;
import org.example.gym_web_app.service.MemberService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MemberController.class)
class MemberControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private MemberService memberService;

    private MemberDTO testMemberDTO;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        objectMapper = new ObjectMapper();

        testMemberDTO = new MemberDTO();
        testMemberDTO.setFirstName("John");
        testMemberDTO.setLastName("Doe");
        testMemberDTO.setEmail("john.doe@example.com");
        testMemberDTO.setDateOfBirth(LocalDate.of(1990, 1, 1));
        testMemberDTO.setMembershipType("Premium");
        testMemberDTO.setActive(true);
    }

    @Test
    void getAllMembers_shouldReturnListOfMembers() throws Exception {
        when(memberService.getAllMembers()).thenReturn(Arrays.asList(testMemberDTO));

        mockMvc.perform(get("/api/members"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].firstName").value("John"))
                .andExpect(jsonPath("$[0].email").value("john.doe@example.com"));

        verify(memberService, times(1)).getAllMembers();
    }

    @Test
    void getMemberById_shouldReturnMemberIfExists() throws Exception {
        when(memberService.getMemberById(1L)).thenReturn(Optional.of(testMemberDTO));

        mockMvc.perform(get("/api/members/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$.email").value("john.doe@example.com"));

        verify(memberService, times(1)).getMemberById(1L);
    }

    @Test
    void getMemberById_shouldReturn404IfNotExists() throws Exception {
        when(memberService.getMemberById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/members/1"))
                .andExpect(status().isNotFound());

        verify(memberService, times(1)).getMemberById(1L);
    }

    @Test
    void addMember_shouldReturnCreatedMember() throws Exception {
        when(memberService.addMember(any(MemberDTO.class))).thenReturn(testMemberDTO);

        mockMvc.perform(post("/api/members")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testMemberDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$.email").value("john.doe@example.com"));

        verify(memberService, times(1)).addMember(any(MemberDTO.class));
    }

    @Test
    void updateMember_shouldReturnUpdatedMember() throws Exception {
        when(memberService.updateMember(eq(1L), any(MemberDTO.class))).thenReturn(testMemberDTO);

        mockMvc.perform(put("/api/members/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testMemberDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$.email").value("john.doe@example.com"));

        verify(memberService, times(1)).updateMember(eq(1L), any(MemberDTO.class));
    }

    @Test
    void deleteMember_shouldReturnNoContentIfDeleted() throws Exception {
        when(memberService.deleteMember(1L)).thenReturn(true);

        mockMvc.perform(delete("/api/members/1"))
                .andExpect(status().isNoContent());

        verify(memberService, times(1)).deleteMember(1L);
    }

    @Test
    void deleteMember_shouldReturn404IfNotFound() throws Exception {
        doThrow(new RuntimeException("Member not found")).when(memberService).deleteMember(1L);

        mockMvc.perform(delete("/api/members/1"))
                .andExpect(status().isNotFound());

        verify(memberService, times(1)).deleteMember(1L);
    }
}
