package com.example.transaction_base.repository;

import com.example.transaction_base.model.Invoice;
import com.example.transaction_base.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class InvoiceRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void createInvoice(Invoice invoice) {
        String sql = "INSERT INTO invoice (invoice_number, service_code, service_name, transaction_type, total_amount) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, invoice.getInvoiceNumber(), invoice.getServiceCode(), invoice.getServiceName(), invoice.getTransactionType(), invoice.getTotalAmount());
    }

}
