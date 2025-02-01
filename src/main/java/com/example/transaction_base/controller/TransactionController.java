package com.example.transaction_base.controller;

import com.example.transaction_base.dto.TopUpDTO;
import com.example.transaction_base.dto.TransactionServiceDTO;
import com.example.transaction_base.service.TransactionService;
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

@RestController
@Tag(name = "3.Module Transaction")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @GetMapping("/balance")
    @Operation(description =
            "**API Profile Private (memerlukan Token untuk mengaksesnya)**\n" +
            "\n" +
            "Digunakan untuk mendapatkan informasi balance / saldo terakhir dari User\n" +
            "\n" +
            "*Ketentuan* :\n" +
            "\n" +
            "1. Service ini harus menggunakan Bearer Token JWT untuk mengaksesnya\n" +
            "2. Tidak ada parameter email di query param url ataupun request body, parameter email diambil dari payload JWT yang didapatkan dari hasil login\n" +
            "3. Handling Response sesuai dokumentasi Response dibawah")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Get Balance",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(example = "{ \"success\": true, \"content\": \"{BALANCE}\", \"status\": 200, \"timestamp\": \"DATE\" }")))
    })
    public ResponseEntity<Object> balance(HttpServletRequest request) {
        return transactionService.balance(request);
    }

    @PostMapping("/topup")
    @Operation(description =
            "**API Transaction Private (memerlukan Token untuk mengaksesnya)**\n" +
            "\n" +
            "Digunakan untuk melakukan transaksi dari services / layanan yang tersedia\n" +
            "\n" +
            "*Ketentuan* :\n" +
            "\n" +
            "1. Service ini harus menggunakan Bearer Token JWT untuk mengaksesnya\n" +
            "2. Tidak ada parameter email di query param url ataupun request body, parameter email diambil dari payload JWT yang didapatkan dari hasil login\n" +
            "3. Setiap kali melakukan Top Up maka balance / saldo dari User otomatis bertambah\n" +
            "4. Parameter amount hanya boleh angka saja dan tidak boleh lebih kecil dari 0\n" +
            "5. Pada saat Top Up set transaction_type di database menjadi TOPUP\n" +
            "6. Handling Response sesuai dokumentasi Response dibawah\n")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Top Up Success",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(example = "{ \"success\": true, \"content\": \"{UPDATED BALANCE}\", \"status\": 200, \"timestamp\": \"DATE\" }")))
    })
    public ResponseEntity<Object> topup(HttpServletRequest request, @RequestBody TopUpDTO topUpAmount) {
        return transactionService.topup(topUpAmount, request);
    }

    @PostMapping("/transaction")
    @Operation(description =
            "**API Transaction Private (memerlukan Token untuk mengaksesnya)**\n" +
            "\n" +
            "Digunakan untuk melakukan transaksi dari services / layanan yang tersedia\n" +
            "\n" +
            "*Ketentuan* :\n" +
            "\n" +
            "1. Service ini harus menggunakan Bearer Token JWT untuk mengaksesnya\n" +
            "2. Tidak ada parameter email di query param url ataupun request body, parameter email diambil dari payload JWT yang didapatkan dari hasil login\n" +
            "3. Setiap kali melakukan Transaksi harus dipastikan balance / saldo mencukupi\n" +
            "4. Pada saat Transaction set transaction_type di database menjadi PAYMENT\n" +
            "5. Handling Response sesuai dokumentasi Response dibawah\n" +
            "6. Response invoice_number untuk formatnya generate bebas\n")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Transaction Success",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(example = "{ \"success\": true, \"content\": \"{INVOICE DETAIL}\", \"status\": 200, \"timestamp\": \"DATE\" }")))
    })
    public ResponseEntity<Object> transaction(HttpServletRequest request, @RequestBody TransactionServiceDTO transactionServiceDTO) {
        return transactionService.transaction(transactionServiceDTO, request);
    }

    @GetMapping("/transaction/history")
    @Operation(description =
            "**API Profile Private (memerlukan Token untuk mengaksesnya)**\n" +
            "\n" +
            "Digunakan untuk mendapatkan informasi history transaksi\n" +
            "\n" +
            "*Ketentuan* :\n" +
            "\n" +
            "1. Service ini harus menggunakan Bearer Token JWT untuk mengaksesnya\n" +
            "2. Tidak ada parameter email di query param url ataupun request body, parameter email diambil dari payload JWT yang didapatkan dari hasil login\n" +
            "3. Terdapat parameter limit yang bersifat opsional, jika limit tidak dikirim maka tampilkan semua data\n" +
            "4. Data di order dari yang paling baru berdasarkan transaction date (created_on)\n" +
            "5. Handling Response sesuai dokumentasi Response dibawah")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Transaction History Found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(example = "{ \"success\": true, \"content\": \"[TRANSACTION HISTORY]\", \"status\": 200, \"timestamp\": \"DATE\" }")))
    })
    public ResponseEntity<Object> transactionHistory(HttpServletRequest request, @RequestParam(defaultValue = "0") Integer offset, @RequestParam(defaultValue = "1") Integer limit) {
        return transactionService.transactionHistory(request, offset, limit);
    }

}
