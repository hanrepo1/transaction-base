package com.example.transaction_base.controller;

import com.example.transaction_base.dto.EditUserDTO;
import com.example.transaction_base.dto.LoginDTO;
import com.example.transaction_base.dto.RegisterDTO;
import com.example.transaction_base.service.MembershipService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class MembershipController {

    @Autowired
    private MembershipService membershipService;

    @PostMapping("/registration")
    public ResponseEntity<Object> registration(HttpServletRequest request, @RequestBody RegisterDTO registerDTO) {
        return membershipService.registration(registerDTO, request);
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(HttpServletRequest request, @RequestParam LoginDTO loginDTO) {
        return membershipService.login(loginDTO, request);
    }
    @GetMapping("/profile")
    public ResponseEntity<Object> profile(HttpServletRequest request) {
        return membershipService.profile(request);
    }

    @PutMapping("/profile/update")
    public ResponseEntity<Object> profileUpdate(HttpServletRequest request, @RequestBody EditUserDTO editUserDTO) {
        return membershipService.profileUpdate(editUserDTO, request);
    }

    @PutMapping("/profile/image")
    public ResponseEntity<Object> profileImage(HttpServletRequest request, @RequestParam("file") MultipartFile file) {
        return membershipService.profileImage(file, request);
    }

}
