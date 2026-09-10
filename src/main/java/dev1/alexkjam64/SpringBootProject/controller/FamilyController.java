package dev1.alexkjam64.SpringBootProject.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev1.alexkjam64.SpringBootProject.repository.FamilyInfo;
import dev1.alexkjam64.SpringBootProject.service.FamilyService;
import dev1.alexkjam64.SpringBootProject.service.InvalidDataException;
import dev1.alexkjam64.SpringBootProject.service.NoDataException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/family")
public class FamilyController extends Exception{
    private final FamilyService familyService;

    public FamilyController(FamilyService familyService){
        this.familyService = familyService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getInfo(@PathVariable int id){
        try{
            var idVal = familyService.retrieveAllUsersByUserId(id);
            return ResponseEntity.ok(idVal);
        }catch(NoDataException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
    
    @PostMapping("/{id}")
    public ResponseEntity<?> postInfo(@RequestBody FamilyInfo request){
        try{
            familyService.create(request);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }catch(InvalidDataException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }catch(NoDataException e){
            return new ResponseEntity<>("Family already exist!", HttpStatus.CONFLICT);
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteInfo(@PathVariable int familyId, @PathVariable int userId){
        try{
            familyService.delete(familyId, userId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch(NoDataException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
