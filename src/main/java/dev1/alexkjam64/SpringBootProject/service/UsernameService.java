package dev1.alexkjam64.SpringBootProject.service;

import java.util.List;

import org.springframework.stereotype.Service;

import dev1.alexkjam64.SpringBootProject.repository.UsernameInfo;
import dev1.alexkjam64.SpringBootProject.repository.UsernameRepository;

@Service
public class UsernameService {
    private final UsernameRepository clientRepository;

    public UsernameService(UsernameRepository clientRepository){
        this.clientRepository = clientRepository;
    }

    public UsernameInfo retrieve(int id) throws NoDataException{
        // If data does not exist... blow up!
        checkDataExist(id);

        // If the data exist... update using the repo
        return clientRepository.getUsername(id);
    }

    public UsernameInfo retrieve(String username) throws InvalidDataException, NoDataException{
        // If data does not exist... blow up!
        sanitizeData(new UsernameInfo(0, username, 0));

        // If the data exist... update using the repo
        return clientRepository.getID(username);
    }

    public List<UsernameInfo> retrieveFamilyMembers(int id) throws NoDataException{
        checkDataExist(id);
        var familyId = clientRepository.getFamilyId(id);

        return clientRepository.getAllFamilyMembers(familyId);
    }

    // Sanitizes data before adding to database
    public void create(UsernameInfo request) throws InvalidDataException{
        sanitizeData(request);
        
        // Assuming it past all the checks... call the repo to create
        clientRepository.addClient(request);
    }

    // Sanitizes data before updating database
    public void update(UsernameInfo entity, int id) throws InvalidDataException, NoDataException{
        sanitizeData(entity);

        // If data does not exist... blow up!
        checkDataExist(id);

        // If the data exist... update using the repo
        clientRepository.updateClient(entity, id);
    }

    public void delete(int id) throws NoDataException{
        // If data does not exist... blow up!
        checkDataExist(id);

        // If the data exist... update using the repo
        clientRepository.deleteClient(id);
    }

    protected void sanitizeData(UsernameInfo data) throws InvalidDataException{
        // If the username is null or empty... blow up!
        if(data.username() == null || data.username().trim().isEmpty()){
            throw new InvalidDataException("Username is null or empty!");
        }

        // If any of the name attributes are longer than the db columns... blow up!
        if(data.username().length() > 32){
            throw new InvalidDataException("Username surpasses 32 characters!");
        }

        // If the username include special characters... blow up!
        if(data.username().matches(".*[^a-zA-Z].*")){
            throw new InvalidDataException("Username includes special characters!");
        }
    }

    protected void checkDataExist(int id) throws NoDataException{
        if(clientRepository.getUsername(id) == null){
            throw new NoDataException("Data does not exist!");
        }
    }
}
