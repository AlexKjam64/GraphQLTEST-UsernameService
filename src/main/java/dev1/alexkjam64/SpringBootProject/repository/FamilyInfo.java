package dev1.alexkjam64.SpringBootProject.repository;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_ABSENT)
public record FamilyInfo(int familyId, int userId){
    public MapSqlParameterSource mapInfo(){
        return new MapSqlParameterSource()
            .addValue("FAMILYID", familyId)
            .addValue("USERID", userId);
    }
}
