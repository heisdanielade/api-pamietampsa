package com.github.heisdanielade.pamietampsa.dto.pet;

import com.github.heisdanielade.pamietampsa.enums.Sex;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Getter
@Setter
public class PetCreateDto {
    @Schema(description = "Pet's name", example = "Emi")
    @NotBlank(message = "Name is required")
    private String name;

    private MultipartFile profileImage;

    @Schema(description = "Pet's species", example = "Dog")
    @NotBlank(message = "Species is required")
    private String species;

    @Schema(description = "Pet's breed", example = "German Shepherd")
    private String breed;
    @NotBlank(message = "Sex is required")

    @Schema(description = "Pet's sex", example = "Female")
    private Sex sex;

    @Schema(description = "Pet's date of birth in ISO format", example = "2025-02-24")
    private LocalDate birthDate;
}
