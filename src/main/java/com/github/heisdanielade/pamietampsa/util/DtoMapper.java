package com.github.heisdanielade.pamietampsa.util;

import com.github.heisdanielade.pamietampsa.dto.pet.PetDto;
import com.github.heisdanielade.pamietampsa.dto.user.UserDto;
import com.github.heisdanielade.pamietampsa.entity.AppUser;
import com.github.heisdanielade.pamietampsa.entity.Pet;

public class DtoMapper {

    public static UserDto toUserDto(AppUser user) {
        return new UserDto(
                user.getEmail(),
                user.getName(),
                user.getInitial(),
                String.valueOf(user.isEnabled()),
                String.valueOf(user.getRole())
        );
    }

    public static PetDto toPetDto(Pet pet) {
        return new PetDto(
                pet.getProfileImageURL(),
                pet.getName(),
                pet.getAge(),
                pet.getSpecies(),
                pet.getBreed(),
                String.valueOf(pet.getSex())
        );
    }
}
