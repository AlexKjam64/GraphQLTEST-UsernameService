package dev1.alexkjam64.SpringBootProject.repository;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class UsernameMapper implements RowMapper<UsernameInfo>{
    
    @Override
    public UsernameInfo mapRow(@SuppressWarnings("null") ResultSet rs, int rowNum) throws SQLException {
        return new UsernameInfo(rs.getInt("id"),
                            rs.getString("username"),
                            rs.getInt("familyId"));
    }
}
