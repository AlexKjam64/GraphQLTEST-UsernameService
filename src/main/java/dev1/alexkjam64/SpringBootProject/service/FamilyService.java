package dev1.alexkjam64.SpringBootProject.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import dev1.alexkjam64.SpringBootProject.repository.FamilyInfo;
import dev1.alexkjam64.SpringBootProject.repository.FamilyRepository;
import dev1.alexkjam64.SpringBootProject.repository.UsernameInfo;
import dev1.alexkjam64.SpringBootProject.repository.UsernameRepository;

@Service
public class FamilyService {
    private final FamilyRepository familyRepository;
    private final UsernameRepository usernameRepository;

    public FamilyService(FamilyRepository familyRepository, UsernameRepository usernameRepository){
        this.familyRepository = familyRepository;
        this.usernameRepository = usernameRepository;
    }

    public List<UsernameInfo> retrieveAllUsersByFamilyId(int familyId) throws NoDataException{
        var familyUsers = familyRepository.getFamily(familyId);

        if(familyUsers.isEmpty()){
            throw new NoDataException("Nothing in family plan!");
        }

        List<Integer> ids = familyUsers.stream().map(FamilyInfo::userId).collect(Collectors.toList());
        return usernameRepository.getAllUsernames(ids);
    }

    public List<UsernameInfo> retrieveAllUsersByUserId(int id) throws NoDataException{
        var familyUsers = familyRepository.getFamily(id);

        if(familyUsers.isEmpty()){
            throw new NoDataException("Nothing in family plan!");
        }

        List<Integer> ids = familyUsers.stream().map(FamilyInfo::userId).collect(Collectors.toList());
        return usernameRepository.getAllUsernames(ids);
    }

    public void create(FamilyInfo request) throws InvalidDataException, NoDataException{
        familyRepository.addFamilyUser(request);
    }

    public void delete(int familyId, int userId) throws NoDataException{
        checkDataExist(familyId, userId);

        familyRepository.deleteFamilyUser(familyId, userId);
    }

    protected void checkDataExist(int familyId, int userId) throws NoDataException{
        if(familyRepository.getOneFamilyUser(familyId, userId) == null){
            throw new NoDataException("Family user does not exist!");
        }
    }
}
