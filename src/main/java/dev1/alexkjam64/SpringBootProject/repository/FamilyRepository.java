package dev1.alexkjam64.SpringBootProject.repository;

import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class FamilyRepository {
    private final NamedParameterJdbcTemplate template;

    public FamilyRepository(NamedParameterJdbcTemplate template){
        this.template = template;
    }

    private static final String getQuery = """
            SELECT * FROM "Nintendo"."Family"
            WHERE "familyId" = :FAMILYID
            """;

    public List<FamilyInfo> getFamily(int familyId){
        return template.query(getQuery, new MapSqlParameterSource("FAMILYID", familyId), new FamilyMapper());
    }

    private static final String getOneQuery = """
            SELECT "userId"
            FROM "Nintendo"."Family"
            WHERE "familyId" = :FAMILYID
            """;

    public FamilyInfo getOneFamilyUser(int familyId, int userId){
        try{
            return template.queryForObject(getOneQuery, new MapSqlParameterSource("FAMILYID", familyId).addValue("USERID", userId), new FamilyMapper());
        }catch(EmptyResultDataAccessException e){
            return null;
        }
    }

    private static final String insertQuery = """
            INSERT INTO "Nintendo"."Family" ("familyId", "userId")
            VALUES (:FAMILYID, :USERID)
            """;

    public void addFamilyUser(FamilyInfo newFamilyUser){
        template.update(insertQuery, newFamilyUser.mapInfo());
    }

    private static final String deleteQuery = """
            DELETE FROM "Nintendo"."Family"
            WHERE "familyId" = :FAMILYID AND "userId" = :GAMEID
            """;

    public void deleteFamilyUser(int familyId, int userId){
        template.update(deleteQuery, new MapSqlParameterSource("FAMILYID", familyId).addValue("USERID", userId));
    }
}
