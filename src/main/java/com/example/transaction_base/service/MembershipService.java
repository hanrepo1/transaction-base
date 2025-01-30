package com.example.transaction_base.service;

import com.example.transaction_base.dto.EditUserDTO;
import com.example.transaction_base.dto.LoginDTO;
import com.example.transaction_base.dto.RegisterDTO;
import com.example.transaction_base.model.User;
import com.example.transaction_base.repository.UserRepository;
import com.example.transaction_base.util.Crypto;
import com.example.transaction_base.util.JwtUtil;
import com.example.transaction_base.util.ResponseUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@Service
public class MembershipService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    public ResponseEntity<Object> registration(RegisterDTO registerDTO, HttpServletRequest request) {
        if (registerDTO.getEmail() == null || registerDTO.getEmail().isEmpty()) {
            return ResponseUtil.validationFailed("Email is required", request);
        }
        if (registerDTO.getPassword() == null || registerDTO.getPassword().isEmpty()) {
            return ResponseUtil.validationFailed("Password is required", request);
        }
        if (registerDTO.getFirstName() == null || registerDTO.getFirstName().isEmpty()) {
            return ResponseUtil.validationFailed("First name is required", request);
        }
        if (registerDTO.getLastName() == null || registerDTO.getLastName().isEmpty()) {
            return ResponseUtil.validationFailed("Last name is required", request);
        }
        if (userRepository.doesEmailExist(registerDTO.getEmail())) {
            return ResponseUtil.validationFailed("Email is already registered", request);
        }

        try {
            String hashedPassword = Crypto.performEncrypt(registerDTO.getPassword());

            User user = new User(registerDTO.getEmail(), hashedPassword, registerDTO.getFirstName(), registerDTO.getLastName());
            userRepository.createUser(user);

            return ResponseUtil.dataSaveSuccess(request);
        } catch (Exception e) {
            return ResponseUtil.validationFailed("An error occurred while saving the user: " + e.getMessage(), request);
        }
    }

    public ResponseEntity<Object> login(LoginDTO loginDTO, HttpServletRequest request) {
        try {
            User existingUser = userRepository.getUserByEmail(loginDTO.getEmail());
            if (existingUser == null) return ResponseUtil.dataNotFound("Email not found", request);
            if (existingUser.getPassword().equals(loginDTO.getPassword())) {
                Map<String,Object> mapForClaims = new HashMap<>();
                mapForClaims.put("uid",existingUser.getId());
                mapForClaims.put("email",existingUser.getEmail());
                String token = jwtUtil.generateToken(loginDTO.getEmail(), mapForClaims);
                return ResponseUtil.dataFound("Data Found", token, request);
            } else {
                return ResponseUtil.validationFailed("Wrong password", request);
            }
        } catch (Exception e) {
            return ResponseUtil.validationFailed("An error occurred while logging in", request);
        }
    }

    public ResponseEntity<Object> profile(HttpServletRequest request) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String email = authentication.getName();

            User existingUser = userRepository.getUserByEmail(email);
            if (existingUser == null) {
                return ResponseUtil.dataNotFound("User not found", request);
            }

            return ResponseUtil.dataFound("Data Found", existingUser , request);
        } catch (Exception e) {
            return ResponseUtil.validationFailed("An error occurred while fetching the user profile: " + e.getMessage(), request);
        }
    }

    public ResponseEntity<Object> profileUpdate(EditUserDTO editUserDTO, HttpServletRequest request) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String email = authentication.getName();

            User existingUser = userRepository.getUserByEmail(email);
            if (existingUser == null) {
                return ResponseUtil.dataNotFound("User not found", request);
            } else {
                if (editUserDTO.getFirstName() != null) {
                    existingUser.setFirstName(editUserDTO.getFirstName());
                }
                if (editUserDTO.getLastName() != null) {
                    existingUser.setLastName(editUserDTO.getLastName());
                }
                userRepository.updateUser(existingUser);
            }

            return ResponseUtil.dataFound("Data Found", existingUser , request);
        } catch (Exception e) {
            return ResponseUtil.validationFailed("An error occurred while fetching the user profile: " + e.getMessage(), request);
        }
    }

    public ResponseEntity<Object> profileImage(MultipartFile file, HttpServletRequest request) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String email = authentication.getName();

            User existingUser = userRepository.getUserByEmail(email);
            if (existingUser == null) {
                return ResponseUtil.dataNotFound("User not found", request);
            }

            if (file.isEmpty()) {
                return ResponseUtil.validationFailed("No file uploaded", request);
            }

            //example if we use a google cloud storage, which in this matter i will not put the code because it exposes my private key
//            String imageUrl = uploadFileToCloudStorage(file);
//            existingUser.setProfileImage(imageUrl);

            userRepository.updateUser(existingUser);

        } catch (Exception e) {
            return ResponseUtil.validationFailed("An error occurred while fetching the user profile: " + e.getMessage(), request);
        }
    }

}
