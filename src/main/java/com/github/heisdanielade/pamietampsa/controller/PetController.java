package com.github.heisdanielade.pamietampsa.controller;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.github.heisdanielade.pamietampsa.dto.pet.PetCreateDto;
import com.github.heisdanielade.pamietampsa.dto.pet.PetResponseDto;
import com.github.heisdanielade.pamietampsa.exception.media.FileSizeTooLargeException;
import com.github.heisdanielade.pamietampsa.exception.media.InvalidFileTypeException;
import com.github.heisdanielade.pamietampsa.response.BaseApiResponse;
import com.github.heisdanielade.pamietampsa.service.PetService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Tag(name = "Pet", description = "Pet management (CRUD) endpoints")
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/v1/pets", produces = "application/json")
public class PetController {
    private final PetService petService;
    private final Cloudinary cloudinary;

    @Operation(
            summary = "Add a new pet",
            description = "Creates a new pet record for the currently authenticated user"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Pet added successfully"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "409", description = "Conflict, Pet already exists")
    })
    @PostMapping(path = "/add", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<BaseApiResponse<Map<String, Object>>> addPet(
            @ModelAttribute PetCreateDto input,
            Principal principal) throws IOException {

        String userEmail = principal.getName();
        MultipartFile imageFile = input.getProfileImage();

        String imageUrl = null;

        if (imageFile != null && !imageFile.isEmpty()) {
            long maxSize = 3 * 1024 * 1024; // 3 MB in bytes
            String filename = imageFile.getOriginalFilename();
            String type = imageFile.getContentType();

            if (type == null ||
                !(type.equalsIgnoreCase("image/jpeg") || type.equalsIgnoreCase("image/png")) ||
                  (filename != null && !filename.toLowerCase().matches(".*\\.(jpg|jpeg|png)$"))
            ) {
                throw new InvalidFileTypeException("Only JPG and PNG images are allowed.");
            }
            if (imageFile.getSize() > maxSize) {
                throw new FileSizeTooLargeException("Image size is too large.");
            }
            Map uploadResult = cloudinary.uploader().upload(imageFile.getBytes(), ObjectUtils.emptyMap());
            imageUrl = (String) uploadResult.get("secure_url");
        }
        petService.addPetToUser(userEmail, input, imageUrl);

        Map<String, Object> data = new HashMap<>();
        data.put("petName", input.getName());
        data.put("species", input.getSpecies());
        data.put("sex", input.getSex());

        BaseApiResponse<Map<String, Object>> response = new BaseApiResponse<>(
                HttpStatus.CREATED.value(),
                "Pet added successfully",
                data
        );
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }


    @Operation(
            summary = "Get all pets for a user",
            description = "Returns all pets associated with the currently authenticated user"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @GetMapping(path = "/all")
    public ResponseEntity<BaseApiResponse<List<PetResponseDto>>> getAllPets(Principal principal) {
        String userEmail = principal.getName();

        List<PetResponseDto> petResponseDtoList = petService.getPetsForUser(userEmail);

        BaseApiResponse<List<PetResponseDto>> response = new BaseApiResponse<>(
                HttpStatus.OK.value(),
                "Pets fetched successfully.",
                petResponseDtoList
        );
        return ResponseEntity.ok(response);
    }
}
