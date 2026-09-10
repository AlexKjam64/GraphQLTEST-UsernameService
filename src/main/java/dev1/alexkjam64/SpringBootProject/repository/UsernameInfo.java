package dev1.alexkjam64.SpringBootProject.repository;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_EMPTY)
public record UsernameInfo(int id, String username, int familyId){
    public MapSqlParameterSource createMap(){
        return new MapSqlParameterSource()
            .addValue("USERNAME", username);
    }

    public MapSqlParameterSource updateMap(int id){
        return new MapSqlParameterSource()
            .addValue("ID", id)
            .addValue("USERNAME", username)
            .addValue("FAMILYID", familyId);
    }
}