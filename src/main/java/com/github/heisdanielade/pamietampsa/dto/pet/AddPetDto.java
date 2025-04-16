package com.github.heisdanielade.pamietampsa.dto.pet;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class AddPetDto {
    @NotBlank(message = "Name is required")
    private String name;
    @NotBlank(message = "Species is required")
    private String species;
    private String breed;
    private LocalDate birthDate;

}
