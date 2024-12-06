package org.example.gym_web_app.controller;

import org.example.gym_web_app.dto.MemberDTO;
import org.example.gym_web_app.model.Member;
import org.example.gym_web_app.service.MemberMapper;
import org.example.gym_web_app.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/members")
public class MemberController {

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberMapper memberMapper;

    @GetMapping
    public ResponseEntity<List<MemberDTO>> getAllMembers() {
        List<MemberDTO> members = memberService.getAllMembers()
                .stream()
                .map(memberMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(members);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MemberDTO> getMemberById(@PathVariable Long id) {
        return memberService.getMemberById(id)
                .map(member -> ResponseEntity.ok(memberMapper.toDTO(member)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<MemberDTO> addMember(@RequestBody MemberDTO memberDTO) {
        try {
            Member member = memberMapper.toEntity(memberDTO);
            Member createdMember = memberService.addMember(member);
            return ResponseEntity.status(201).body(memberMapper.toDTO(createdMember));
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<MemberDTO> updateMember(@PathVariable Long id, @RequestBody MemberDTO memberDTO) {
        try {
            Member member = memberMapper.toEntity(memberDTO);
            Member updatedMember = memberService.updateMember(id, member);
            return ResponseEntity.ok(memberMapper.toDTO(updatedMember));
        } catch (RuntimeException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMember(@PathVariable Long id) {
        try {
            if (memberService.deleteMember(id)) {
                return ResponseEntity.noContent().build();
            }
        } catch (RuntimeException ex) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.notFound().build();
    }
}
