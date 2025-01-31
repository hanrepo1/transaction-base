package com.example.transaction_base.controller;

import com.example.transaction_base.dto.EditUserDTO;
import com.example.transaction_base.dto.LoginDTO;
import com.example.transaction_base.dto.RegisterDTO;
import com.example.transaction_base.service.MembershipService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@Tag(name = "1. Module Membership")
public class MembershipController {

    @Autowired
    private MembershipService membershipService;

    @PostMapping("/registration")
    @Operation(description =
            "**API Registration Public (Tidak perlu Token untuk mengaksesnya)**\n" +
            "\n" +
            "Digunakan untuk melakukan registrasi User agar bisa Login kedalam aplikasi\n" +
            "\n" +
            "*Ketentuan* :\n" +
            "\n" +
            "1. Parameter request email harus terdapat validasi format email\n" +
            "2. Parameter request password Length minimal 8 karakter\n" +
            "3. Handling Response sesuai dokumentasi Response dibawah")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User registered",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(example = "{ \"success\": true, \"content\": \"Data Saved Successfully\", \"status\": 201, \"timestamp\": \"DATE\" }")))
    })
    public ResponseEntity<Object> registration(HttpServletRequest request, @RequestBody RegisterDTO registerDTO) {
        return membershipService.registration(registerDTO, request);
    }

    @PostMapping("/login")
    @Operation(description =
            "**API Login Public (Tidak perlu Token untuk mengaksesnya)**\n" +
            "\n" +
            "Digunakan untuk melakukan login dan mendapatkan authentication berupa JWT (Json Web Token)\n" +
            "\n" +
            "*Ketentuan* :\n" +
            "\n" +
            "1. Parameter request email harus terdapat validasi format email\n" +
            "2. Parameter request password Length minimal 8 karakter\n" +
            "3. JWT yang digenerate harus memuat payload email dan di set expiration selama 12 jam dari waktu di generate\n" +
            "4. Handling Response sesuai dokumentasi Response dibawah\n")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User Login",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(example = "{ \"success\": true, \"content\": \"TOKEN GENERATED HERE\", \"status\": 200, \"timestamp\": \"DATE\" }")))
    })
    public ResponseEntity<Object> login(HttpServletRequest request, @RequestBody LoginDTO loginDTO) {
        return membershipService.login(loginDTO, request);
    }

    @GetMapping("/profile")
    @Operation(description =
            "**API Profile Private (memerlukan Token untuk mengaksesnya)**\n" +
            "\n" +
            "Digunakan untuk mendapatkan informasi profile User\n" +
            "\n" +
            "*Ketentuan* :\n" +
            "\n" +
            "1. Service ini harus menggunakan Bearer Token JWT untuk mengaksesnya\n" +
            "2. Tidak ada parameter email di query param url ataupun request body, parameter email diambil dari payload JWT yang didapatkan dari hasil login\n" +
            "3. Handling Response sesuai dokumentasi Response dibawah")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(example = "{ \"success\": true, \"content\": \"{USER DETAIL}\", \"status\": 200, \"timestamp\": \"DATE\" }")))
    })
    public ResponseEntity<Object> profile(HttpServletRequest request) {
        return membershipService.profile(request);
    }

    @PutMapping("/profile/update")
    @Operation(description =
            "**API Update Profile Private (memerlukan Token untuk mengaksesnya)**\n" +
            "\n" +
            "Digunakan untuk mengupdate data profile User\n" +
            "\n" +
            "*Ketentuan* :\n" +
            "\n" +
            "1. Service ini harus menggunakan Bearer Token JWT untuk mengaksesnya\n" +
            "2. Tidak ada parameter email di query param url ataupun request body, parameter email diambil dari payload JWT yang didapatkan dari hasil login\n" +
            "3. Handling Response sesuai dokumentasi Response dibawah")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(example = "{ \"success\": true, \"content\": \"{USER DETAIL}\", \"status\": 200, \"timestamp\": \"DATE\" }")))
    })
    public ResponseEntity<Object> profileUpdate(HttpServletRequest request, @RequestBody EditUserDTO editUserDTO) {
        return membershipService.profileUpdate(editUserDTO, request);
    }

    @PutMapping("/profile/image")
    @Operation(description =
            "**API Upload Profile Image Private (memerlukan Token untuk mengaksesnya)**\n" +
            "\n" +
            "Digunakan untuk mengupdate / upload profile image User\n" +
            "\n" +
            "*Ketentuan* :\n" +
            "\n" +
            "1. Service ini harus menggunakan Bearer Token JWT untuk mengaksesnya\n" +
            "2. Tidak ada parameter email di query param url ataupun request body, parameter email diambil dari payload JWT yang didapatkan dari hasil login\n" +
            "3. Format Image yang boleh di upload hanya jpeg dan png\n" +
            "4. Handling Response sesuai dokumentasi Response dibawah")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(example = "{ \"success\": true, \"content\": \"{USER DETAIL}\", \"status\": 200, \"timestamp\": \"DATE\" }")))
    })
    public ResponseEntity<Object> profileImage(HttpServletRequest request, MultipartFile file) {
        return membershipService.profileImage(file, request);
    }

}
