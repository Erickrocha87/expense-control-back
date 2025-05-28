package com.rocha.expenditurecontrol.controllers;

import com.rocha.expenditurecontrol.dtos.JWTUserDataDTO;
import com.rocha.expenditurecontrol.dtos.UserRequestDTO;
import com.rocha.expenditurecontrol.dtos.UserResponseDTO;
import com.rocha.expenditurecontrol.entities.User;
import com.rocha.expenditurecontrol.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;


import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController implements UserControllerDoc {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<UserResponseDTO> getDateUser (@AuthenticationPrincipal JWTUserDataDTO user) {
        if (user == null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        Optional<User> dataUser = userService.findUserByEmail(user.email());
        if(dataUser.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(userService.getDataUser(dataUser.get().getId()));
    }

    @GetMapping("/images/profile/{filename:.+}")
    public ResponseEntity<Resource> getProfileImage(@PathVariable String filename) {
        try {
            Path filePath = Paths.get("uploads/profile-images").resolve(filename).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if (!resource.exists()) {
                return ResponseEntity.notFound().build();
            }

            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_JPEG)
                    .cacheControl(CacheControl.maxAge(7, TimeUnit.DAYS).cachePublic())
                    .body(resource);

        } catch (MalformedURLException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/{id}/upload-profile-image")
    public ResponseEntity<Map<String, String>> uploadProfileImage(@PathVariable Long id, @RequestParam("file") MultipartFile file){
        String imgUrl = userService.saveProfileImage(id, file);
        Map<String, String> response = new HashMap<>();
        response.put("profileImageUrl", imgUrl);
        return ResponseEntity.ok().body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDTO> editUser (@PathVariable Long id, @RequestBody UserRequestDTO request) {
        if (request == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().body(userService.updateUser(id,request));
    }


}

