package com.github.heisdanielade.pamietampsa.dto.pet;

import io.swagger.v3.oas.annotations.media.Schema;

public record PetResponseDto(
        @Schema(description = "Link to cloud storage of pet profile image")
        String profileImageURL,

        @Schema(description = "Pet's name", example = "Kori")
        String name,

        @Schema(description = "Pet's age (in years)", example = "2")
        Integer age,

        @Schema(description = "Pet's species", example = "Cat")
        String species,

        @Schema(description = "Pet's breed", example = "American Curl")
        String breed,

        @Schema(description = "Pet's sex", example = "Male")
        String sex)
{}
