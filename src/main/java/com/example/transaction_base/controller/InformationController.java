package com.example.transaction_base.controller;

import com.example.transaction_base.service.InformationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "2. Module Information")
public class InformationController {

    @Autowired
    private InformationService informationService;

    @GetMapping("/banner")
    @Operation(description =
            "**API Banner Public (tidak memerlukan Token untuk mengaksesnya)**\n" +
            "\n" +
            "Digunakan untuk mendapatkan list banner\n" +
            "\n" +
            "*Ketentuan* :\n" +
            "\n" +
            "1. Buat data list banner sesuai dokumentasi Response dibawah, usahakan banner ini tidak di hardcode, melainkan ambil dari database\n" +
            "2. Tidak perlu membuatkan module CRUD banner\n" +
            "3. Handling Response sesuai dokumentasi Response dibawah")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Banners found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(example = "{ \"success\": true, \"content\": \"[BANNERS]\", \"status\": 200, \"timestamp\": \"DATE\" }")))
    })
    public ResponseEntity<Object> banner(HttpServletRequest request) {
        return informationService.banner(request);
    }

    @GetMapping("/services")
    @Operation(description =
            "**API Profile Private (memerlukan Token untuk mengaksesnya)**\n" +
            "\n" +
            "Digunakan untuk mendapatkan list Service/Layanan PPOB\n" +
            "\n" +
            "*Ketentuan* :\n" +
            "\n" +
            "1. Buat data list Service/Layanan sesuai dokumentasi Response dibawah, usahakan data list Service atau Layanan ini tidak di hardcode, melainkan ambil dari database\n" +
            "2. Tidak perlu membuatkan module CRUD Service/Layanan\n" +
            "3. Handling Response sesuai dokumentasi Response dibawah")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Services found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(example = "{ \"success\": true, \"content\": \"[SERVICES]\", \"status\": 200, \"timestamp\": \"DATE\" }")))
    })
    public ResponseEntity<Object> services(HttpServletRequest request) {
        return informationService.services(request);
    }

}
