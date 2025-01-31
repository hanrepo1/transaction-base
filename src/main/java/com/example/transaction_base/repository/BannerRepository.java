package com.example.transaction_base.repository;

import com.example.transaction_base.model.Banner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class BannerRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private RowMapper<Banner> rowMapper = new RowMapper<Banner>() {
        @Override
        public Banner mapRow(ResultSet rs, int rowNum) throws SQLException {
            Banner banner = new Banner();
            banner.setBannerName(rs.getString("banner_name"));
            banner.setBannerImage(rs.getString("banner_image"));
            banner.setDescription(rs.getString("description"));
            return banner;
        }
    };

    public List<Banner> findAll() {
        String sql = "SELECT * FROM banner";
        return jdbcTemplate.query(sql, rowMapper);
    }

}
