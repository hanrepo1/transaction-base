package com.example.transaction_base.repository;

import com.example.transaction_base.model.Services;
import com.example.transaction_base.model.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class ServiceRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private RowMapper<Services> rowMapper = new RowMapper<Services>() {
        @Override
        public Services mapRow(ResultSet rs, int rowNum) throws SQLException {
            Services service = new Services();
            service.setServiceCode(rs.getString("service_code"));
            service.setServiceName(rs.getString("service_name"));
            service.setServiceIcon(rs.getString("service_icon"));
            service.setServiceTariff(rs.getInt("service_tariff"));
            return service;
        }
    };

    public List<Services> findAll() {
        String sql = "SELECT * FROM services";
        return jdbcTemplate.query(sql, rowMapper);
    }

    public Services findServiceCode(String serviceCode) {
        String sql = "SELECT * FROM services WHERE service_code = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new Object[]{serviceCode}, rowMapper);
        } catch (EmptyResultDataAccessException e) {
            // Return null if no service is found
            return null;
        }
    }

}
