package com.rocha.expenditurecontrol.controllers;

import com.rocha.expenditurecontrol.dtos.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.Map;

@Tag(name = "Autenticação", description = "Endpoints de autenticação e gerenciamento de usuários")
public interface AuthControllerDoc {

    @Operation(summary = "Registrar um novo usuário")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Usuário registrado com sucesso",
                    content = @Content(schema = @Schema(implementation = AuthResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Requisição inválida",
                    content = @Content)
    })
    @PostMapping("/register")
    ResponseEntity<AuthResponseDTO> register(
            @RequestBody(description = "Dados para registro de usuário", required = true,
                    content = @Content(schema = @Schema(implementation = AuthRequestDTO.class)))
            @Valid @org.springframework.web.bind.annotation.RequestBody AuthRequestDTO user);

    @Operation(summary = "Autenticar um usuário (login)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login realizado com sucesso",
                    content = @Content(schema = @Schema(implementation = LoginResponseDTO.class))),
            @ApiResponse(responseCode = "401", description = "Credenciais inválidas",
                    content = @Content)
    })
    @PostMapping("/login")
    ResponseEntity<LoginResponseDTO> login(
            @RequestBody(description = "Credenciais de login", required = true,
                    content = @Content(schema = @Schema(implementation = LoginRequestDTO.class)))
            @Valid @org.springframework.web.bind.annotation.RequestBody LoginRequestDTO request);

    @Operation(summary = "Alterar senha do usuário autenticado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Senha alterada com sucesso",
                    content = @Content(schema = @Schema(implementation = Map.class))),
            @ApiResponse(responseCode = "400", description = "Senha atual incorreta",
                    content = @Content(schema = @Schema(implementation = Map.class))),
            @ApiResponse(responseCode = "401", description = "Usuário não autenticado",
                    content = @Content)
    })
    @PutMapping("/users/change-password")
    ResponseEntity<Map<String, String>> changePassword(
            @RequestBody(description = "Dados para alteração de senha", required = true,
                    content = @Content(schema = @Schema(implementation = ChangePasswordDTO.class)))
            @org.springframework.web.bind.annotation.RequestBody ChangePasswordDTO dto,
            @AuthenticationPrincipal JWTUserDataDTO user);
}
