package com.github.heisdanielade.pamietampsa.dto.pet;

import com.github.heisdanielade.pamietampsa.enums.Sex;
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
    private Sex sex;
    private LocalDate birthDate;
}
