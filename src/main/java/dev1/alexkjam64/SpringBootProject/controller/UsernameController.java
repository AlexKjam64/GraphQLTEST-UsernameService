package dev1.alexkjam64.SpringBootProject.controller;

import dev1.alexkjam64.SpringBootProject.service.InvalidDataException;
import dev1.alexkjam64.SpringBootProject.service.NoDataException;
import dev1.alexkjam64.SpringBootProject.service.UsernameService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.dao.DuplicateKeyException;

import dev1.alexkjam64.SpringBootProject.repository.UsernameInfo;

import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/username")
public class UsernameController extends Exception{
    private final UsernameService clientService;

    public UsernameController(UsernameService clientService){
        this.clientService = clientService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getInfo(@PathVariable int id){
        try{
            var idVal = clientService.retrieve(id);
            return ResponseEntity.ok(idVal);
        }catch(NoDataException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/idLookUp/{username}")
    public ResponseEntity<?> getInfo(@PathVariable String username) {
        try{
            var unVal = clientService.retrieve(username);
            return ResponseEntity.ok(unVal);
        }catch(InvalidDataException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }catch(NoDataException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
    
    @GetMapping("/familyIdLookUp/{id}")
    public ResponseEntity<?> getFamilyInfo(@PathVariable int id){
        try{
            var famIdVal = clientService.retrieveFamilyMembers(id);
            return ResponseEntity.ok(famIdVal);
        }catch(NoDataException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/{id}")
    public ResponseEntity<?> postInfo(@RequestBody UsernameInfo request){
        try{
            clientService.create(request);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }catch(InvalidDataException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }catch(DuplicateKeyException e){
            return new ResponseEntity<>("Username already exist!", HttpStatus.CONFLICT);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> putInfo(@PathVariable int id, @RequestBody UsernameInfo entity) {
        try{
            clientService.update(entity, id);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch(InvalidDataException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }catch(NoDataException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteInfo(@PathVariable int id){
        try{
            clientService.delete(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch(NoDataException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}