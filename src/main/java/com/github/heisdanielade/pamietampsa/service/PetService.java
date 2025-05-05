package com.github.heisdanielade.pamietampsa.service;

import com.github.heisdanielade.pamietampsa.dto.pet.AddPetDto;
import com.github.heisdanielade.pamietampsa.entity.AppUser;
import com.github.heisdanielade.pamietampsa.entity.Pet;
import com.github.heisdanielade.pamietampsa.exception.auth.AccountNotFoundException;
import com.github.heisdanielade.pamietampsa.exception.pet.PetAlreadyExistsException;
import com.github.heisdanielade.pamietampsa.repository.AppUserRepository;
import com.github.heisdanielade.pamietampsa.repository.PetRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PetService {
    private final PetRepository petRepository;
    private final AppUserRepository appUserRepository;

    public PetService(PetRepository petRepository, AppUserRepository appUserRepository) {
        this.petRepository = petRepository;
        this.appUserRepository = appUserRepository;
    }

    public Pet addPetToUser(String userEmail, AddPetDto input){
        Optional<Pet> existingPet = petRepository.findByName(input.getName());
        if(existingPet.isPresent()){
            throw new PetAlreadyExistsException();
        }
        AppUser user = appUserRepository.findByEmail(userEmail)
                .orElseThrow(AccountNotFoundException::new);

        Pet pet = new Pet(input.getName(), input.getSpecies(), input.getBreed(), input.getBirthDate());
        pet.setOwner(user);
        return petRepository.save(pet);
    }


    public List<Pet> getPetsForUser(String userEmail){
        AppUser user = appUserRepository.findByEmail(userEmail)
                .orElseThrow(AccountNotFoundException::new);

        return petRepository.findByOwner(user);
    }
}
