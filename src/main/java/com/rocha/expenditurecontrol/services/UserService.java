package com.rocha.expenditurecontrol.services;

import com.rocha.expenditurecontrol.dtos.JWTUserDataDTO;
import com.rocha.expenditurecontrol.dtos.UserRequestDTO;
import com.rocha.expenditurecontrol.dtos.UserResponseDTO;
import com.rocha.expenditurecontrol.entities.User;
import com.rocha.expenditurecontrol.exceptions.UserNotFoundException;
import com.rocha.expenditurecontrol.mapper.UserMapper;
import com.rocha.expenditurecontrol.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User getAuthUser(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        JWTUserDataDTO data = (JWTUserDataDTO) auth.getPrincipal();
        String email = data.email();
        Long id = data.id();
        UserDetails userVerify = userRepository.findUserByEmail(email).orElseThrow(() -> new UserNotFoundException("User Not Found. Id: " + id));
        return (User)userVerify;
    }

    public Optional<User> findUserByEmail(String email) {
//        Optional<User> user = userRepository.findByEmail(email);
//        return UserMapper.toResponse(user.get());
        return userRepository.findByEmail(email);
    }

    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User Not Found. Id: " + id));
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public UserResponseDTO updateUser(Long id, UserRequestDTO requestDTO){
        User updateUser = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));
        if (requestDTO.username() != null && !requestDTO.username().equals(updateUser.getUsername())) {
            updateUser.setUsername(requestDTO.username());
        }
        if (requestDTO.email() != null && !requestDTO.email().equals(updateUser.getEmail())) {
            Optional<User> existingUser = userRepository.findByEmail(requestDTO.email());
            if (existingUser.isPresent() && !existingUser.get().getId().equals(updateUser.getId())) {
                throw new RuntimeException("This email already exists");
            }
            updateUser.setEmail(requestDTO.email());
        }
        return UserMapper.toResponse(userRepository.save(updateUser));
    };

    public UserResponseDTO getDataUser(Long id){
        return userRepository.findById(id).map(UserMapper::toResponse).orElse(null);
    }

    public String saveProfileImage(Long id, MultipartFile file){
        try{
            Optional<User> user = userRepository.findById(id);
            if (user.isEmpty()) {
                throw new RuntimeException("User not found with id: " + id);
            }
            String originalFilename = file.getOriginalFilename();
            String sanitizedFilename = originalFilename.replaceAll("\\s+", "_");
            String filename  = UUID.randomUUID() + "_" + sanitizedFilename;
            Path uploadPath = Paths.get("uploads/profile-images");
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            Path filePath = uploadPath.resolve(filename);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
            user.get().setProfileImageUrl("/users/images/profile/" + filename);
            userRepository.save(user.get());

            return "http://localhost:8080/users/images/profile/" + filename;
        }catch (IOException e){
            throw new RuntimeException("Erro ao salvar a imagem");
        }
    }
}
