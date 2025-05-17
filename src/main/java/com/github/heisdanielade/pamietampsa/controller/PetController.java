package com.github.heisdanielade.pamietampsa.controller;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.github.heisdanielade.pamietampsa.dto.pet.AddPetDto;
import com.github.heisdanielade.pamietampsa.exception.media.FileSizeTooLargeException;
import com.github.heisdanielade.pamietampsa.exception.media.InvalidFileTypeException;
import com.github.heisdanielade.pamietampsa.response.ApiResponse;
import com.github.heisdanielade.pamietampsa.service.PetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/v1/pets", produces = "application/json")
public class PetController {

    private final PetService petService;
    private final Cloudinary cloudinary;

    @PostMapping(path = "/add", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<Map<String, Object>>> addPet(
            @ModelAttribute AddPetDto input,
            Principal principal) throws IOException {

        String userEmail = principal.getName();
        MultipartFile imageFile = input.getProfileImage();

        String imageUrl = null;
        if(imageFile != null && !imageFile.isEmpty()){
            long maxSize = 3 * 1024 * 1024; // 3 MB in bytes
            long imageSize = imageFile.getSize();
            String originalFilename = imageFile.getOriginalFilename();
            String contentType = imageFile.getContentType();

            if (contentType == null ||
                (!contentType.equalsIgnoreCase("image/jpeg") &&
                 !contentType.equalsIgnoreCase("image/png"))) {
                throw new InvalidFileTypeException("Only JPG and PNG images are allowed.");
            }
            if (originalFilename != null &&
                !originalFilename.toLowerCase().matches(".*\\.(jpg|jpeg|png)$")) {
                throw new InvalidFileTypeException("Only JPG and PNG images are allowed.");
            }
            if (imageSize > maxSize){
                throw new FileSizeTooLargeException("Profile Image size is too large.");
            }
            Map uploadResult = cloudinary.uploader().upload(imageFile.getBytes(), ObjectUtils.emptyMap());
            imageUrl = (String) uploadResult.get("secure_url");
        }


        petService.addPetToUser(userEmail, input, imageUrl);

        Map<String, Object> data = new HashMap<>();
        data.put("petName", input.getName());
        data.put("species", input.getSpecies());
        data.put("sex", input.getSex());

        ApiResponse<Map<String, Object>> response = new ApiResponse<>(
                HttpStatus.CREATED.value(),
                "Pet added for user successfully",
                data
        );
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping(path = "/all")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getAllPets(Principal principal){
        String userEmail = principal.getName();

        Map<String, Object> data = new HashMap<>();
        data.put("pets", petService.getPetsForUser(userEmail));

        ApiResponse<Map<String, Object>> response = new ApiResponse<>(
                HttpStatus.OK.value(),
                "All pets for current user.",
                data
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
