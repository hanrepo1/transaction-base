package com.example.transaction_base.service;

import com.example.transaction_base.dto.TopUpDTO;
import com.example.transaction_base.dto.TransactionServiceDTO;
import com.example.transaction_base.handler.OffsetLimitResponse;
import com.example.transaction_base.model.Invoice;
import com.example.transaction_base.model.Services;
import com.example.transaction_base.model.Transaction;
import com.example.transaction_base.model.User;
import com.example.transaction_base.repository.InvoiceRepository;
import com.example.transaction_base.repository.ServiceRepository;
import com.example.transaction_base.repository.TransactionRepository;
import com.example.transaction_base.repository.UserRepository;
import com.example.transaction_base.util.ResponseUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Service
public class TransactionService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private ServiceRepository serviceRepository;

    @Autowired
    private InvoiceRepository invoiceRepository;

    public ResponseEntity<Object> balance(HttpServletRequest request) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String email = authentication.getName();

            User existingUser = userRepository.getUserByEmail(email);
            if (existingUser == null) {
                return ResponseUtil.dataNotFound("User not found", request);
            }

            return ResponseUtil.dataFound("Balance Found", "Balance : " + existingUser.getBalance() , request);
        } catch (Exception e) {
            return ResponseUtil.validationFailed("An error occurred while fetching the balance: " + e.getMessage(), request);
        }
    }

    public ResponseEntity<Object> topup(TopUpDTO topUpAmount, HttpServletRequest request) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String email = authentication.getName();

            User existingUser = userRepository.getUserByEmail(email);
            if (existingUser == null) return ResponseUtil.dataNotFound("User not found", request);

            if (topUpAmount.getTopUpAmount() <= 0){
                return ResponseUtil.dataNotFound("Top up amount cannot be less than 0", request);
            }

            existingUser.setBalance(existingUser.getBalance() + topUpAmount.getTopUpAmount());
            userRepository.updateUserBalance(existingUser.getId(), existingUser.getBalance());

            return ResponseUtil.dataFound("Balance Updated", "Balance : " + existingUser.getBalance() , request);

        } catch (Exception e) {
            return ResponseUtil.validationFailed("An error occurred while doing topup", request);
        }
    }

    public ResponseEntity<Object> transaction(TransactionServiceDTO transactionServiceDTO, HttpServletRequest request) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String email = authentication.getName();

            User existingUser = userRepository.getUserByEmail(email);
            if (existingUser == null) return ResponseUtil.dataNotFound("User not found", request);

            Services services = serviceRepository.findServiceCode(transactionServiceDTO.getServiceCode());
            if (services == null) return ResponseUtil.dataNotFound("Service Code not found", request);

            int totalAmount = existingUser.getBalance() - services.getServiceTariff();
            if (totalAmount < 0) return ResponseUtil.dataNotFound("Insufficient Balance", request);

            String invoiceNumber = generateInvoiceNumber(transactionServiceDTO.getServiceCode());

            Invoice invoice = new Invoice();
            invoice.setInvoiceNumber(invoiceNumber);
            invoice.setServiceCode(services.getServiceCode());
            invoice.setServiceName(services.getServiceName());
            invoice.setTransactionType("PAYMENT");
            invoice.setTotalAmount(services.getServiceTariff());
            invoice.setCreatedOn(new Date().toString());
            invoiceRepository.createInvoice(invoice);

            Transaction transaction = new Transaction();
            transaction.setUserId(existingUser.getId());
            transaction.setInvoiceNumber(invoiceNumber);
            transaction.setTransactionType(services.getServiceCode());
            transaction.setDescription(services.getServiceName());
            transaction.setTotalAmount(services.getServiceTariff());
            transactionRepository.createTransaction(transaction);

            userRepository.updateUserBalance(existingUser.getId(), totalAmount);

            return ResponseUtil.dataFound("Transaction Success", invoice , request);

        } catch (Exception e) {
            return ResponseUtil.validationFailed("An error occurred while doing transaction: " + e.getMessage(), request);
        }
    }

    public ResponseEntity<Object> transactionHistory(HttpServletRequest request, Integer offset, Integer limit) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String email = authentication.getName();

            User existingUser = userRepository.getUserByEmail(email);
            if (existingUser == null) return ResponseUtil.dataNotFound("User not found", request);

            if (limit <= 0) return ResponseUtil.dataNotFound("Limit cannot be less than 1", request);
            List<Transaction> transactionList = transactionRepository.findTransactionHistory(existingUser.getId(), offset, limit);
            if (transactionList == null || transactionList.isEmpty()) return ResponseUtil.dataNotFound("Transaction not found", request);

            OffsetLimitResponse<Transaction> response = new OffsetLimitResponse<>(offset, limit, transactionList);

            return ResponseUtil.dataFound("Transactions Found", response, request);

        } catch (Exception e) {
            return ResponseUtil.validationFailed("An error occurred while fetching the transactions: " + e.getMessage(), request);
        }
    }

    private String generateInvoiceNumber(String serviceCode) {
        // Get the current date
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        String datePart = dateFormat.format(new Date());

        // Generate a random number or a unique identifier
        Random random = new Random();
        int uniquePart = random.nextInt(10000); // Random number between 0 and 9999

        // Combine date, service code, and unique identifier
        return datePart + "-" + serviceCode + "-" + uniquePart;
    }

}
