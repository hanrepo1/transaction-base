package com.example.transaction_base.repository;

import com.example.transaction_base.model.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class TransactionRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void createTransaction(Transaction transaction) {
        String sql = "INSERT INTO transactions (user_id, invoice_number, transaction_type, description, total_amount) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, transaction.getUserId(), transaction.getInvoiceNumber(), transaction.getTransactionType(), transaction.getDescription(), transaction.getTotalAmount());
    }

    private RowMapper<Transaction> rowMapper = new RowMapper<Transaction>() {
        @Override
        public Transaction mapRow(ResultSet rs, int rowNum) throws SQLException {
            Transaction transaction = new Transaction();
            transaction.setInvoiceNumber(rs.getString("invoice_number"));
            transaction.setTransactionType(rs.getString("transaction_type"));
            transaction.setDescription(rs.getString("description"));
            transaction.setTotalAmount(rs.getInt("total_amount"));
            transaction.setCreatedOn(rs.getString("created_on"));
            return transaction;
        }
    };

    public List<Transaction> findTransactionHistory(Long userId, int offset, int limit) {
        String sql = "SELECT * FROM transactions WHERE user_id = ? ORDER BY created_on DESC LIMIT ? OFFSET ?";
        return jdbcTemplate.query(sql, new Object[]{userId, limit, offset}, rowMapper);
    }

}
