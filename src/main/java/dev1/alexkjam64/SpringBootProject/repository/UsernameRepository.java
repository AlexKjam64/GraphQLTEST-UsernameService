package dev1.alexkjam64.SpringBootProject.repository;

import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import dev1.alexkjam64.SpringBootProject.service.NoDataException;

import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;

@Repository
public class UsernameRepository {
    private final NamedParameterJdbcTemplate template;

    public UsernameRepository(NamedParameterJdbcTemplate template){
        this.template = template;
    }

    private static final String getQuery = """
            SELECT "id", "username", "familyId"
	        FROM "Nintendo"."Username"
            WHERE "id" = (:ID)
            """;

    public List<UsernameInfo> getAllUsernames(List<Integer> ids){
        return template.query(getQuery, new MapSqlParameterSource("ID", ids), new UsernameMapper());
    }

    public UsernameInfo getUsername(int id){
        try{
            return template.queryForObject(getQuery, new MapSqlParameterSource("ID", id), new UsernameMapper());
        }catch(EmptyResultDataAccessException e){
            return null;
        }
    }

    private static final String familyIdQuery = """
            SELECT "familyId"
            FROM "Nintendo"."Username"
            WHERE "id" IN (:ID)
            """;

    public Integer getFamilyId(int id){
        try{
            return template.queryForObject(familyIdQuery, new MapSqlParameterSource("ID", id), new SingleColumnRowMapper<>(Integer.class));
        }catch(EmptyResultDataAccessException e){
            return null;
        }
    }

    private static final String familyMemberQuery = """
            SELECT "id", "username", "familyId"
            FROM "Nintendo"."Username"
            WHERE "familyId" IN (:FAMILYID)
            """;

    public List<UsernameInfo> getAllFamilyMembers(int familyId){
        return template.query(familyMemberQuery, new MapSqlParameterSource("FAMILYID", familyId), new UsernameMapper());
    }

    private static final String usernameQuery = """
            SELECT "id", "username", "familyId"
	        FROM "Nintendo"."Username"
            WHERE "username" = :USERNAME
            """;

    public UsernameInfo getID(String username) throws NoDataException{
        try{
            return template.queryForObject(usernameQuery, new MapSqlParameterSource("USERNAME", username), new UsernameMapper());
        }catch(EmptyResultDataAccessException e){
            throw new NoDataException("No username found!");
        }
    }

    private static final String insertQuery = """
            INSERT INTO "Nintendo"."Username" ("username")
            VALUES (:USERNAME)
            """;

    // Inserting new data into database
    public void addClient(UsernameInfo newClient){
        template.update(insertQuery, newClient.createMap());
    }

    // Updating data into database
    private static final String updateQuery = """
            UPDATE "Nintendo"."Username"
            SET "username" = :USERNAME
            WHERE "id" = :ID
            """;

    public void updateClient(UsernameInfo updateClient, int id){
        template.update(updateQuery, updateClient.updateMap(id));
    }

    // Deleting data from database
    private static final String deleteQuery = """
            DELETE FROM "Nintendo"."Username"
            WHERE "id" = :ID
            """;

    public void deleteClient(int id){
        template.update(deleteQuery, new MapSqlParameterSource("ID", id));
    }
}
