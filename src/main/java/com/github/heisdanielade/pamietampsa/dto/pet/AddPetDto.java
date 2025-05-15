package com.github.heisdanielade.pamietampsa.dto.pet;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Getter
@Setter
public class AddPetDto {
    @NotBlank(message = "Name is required")
    private String name;
    private MultipartFile profileImage;
    @NotBlank(message = "Species is required")
    private String species;
    private String breed;
    private LocalDate birthDate;

}
