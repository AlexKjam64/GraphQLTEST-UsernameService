package dev1.alexkjam64.SpringBootProject.repository;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class FamilyMapper implements RowMapper<FamilyInfo>{

    @Override
    public FamilyInfo mapRow(@SuppressWarnings("null") ResultSet rs, int rowNum) throws SQLException {
        return new FamilyInfo(rs.getInt("familyId"),
                            rs.getInt("userId"));
    }
}
