package com.github.heisdanielade.pamietampsa.util;

import com.github.heisdanielade.pamietampsa.dto.pet.PetResponseDto;
import com.github.heisdanielade.pamietampsa.dto.user.UserResponseDto;
import com.github.heisdanielade.pamietampsa.entity.AppUser;
import com.github.heisdanielade.pamietampsa.entity.Pet;

public class DtoMapper {

    public static UserResponseDto toUserDto(AppUser user) {
        return new UserResponseDto(
                user.getEmail(),
                user.getName(),
                user.getInitial(),
                String.valueOf(user.isEnabled()),
                String.valueOf(user.getRole())
        );
    }

    public static PetResponseDto toPetDto(Pet pet) {
        return new PetResponseDto(
                pet.getProfileImageURL(),
                pet.getName(),
                pet.getAge(),
                pet.getSpecies(),
                pet.getBreed(),
                String.valueOf(pet.getSex())
        );
    }
}
