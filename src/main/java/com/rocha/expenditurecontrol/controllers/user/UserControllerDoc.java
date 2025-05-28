package com.rocha.expenditurecontrol.controllers.user;

import com.rocha.expenditurecontrol.dtos.auth.JWTUserDataDTO;
import com.rocha.expenditurecontrol.dtos.user.UserRequestDTO;
import com.rocha.expenditurecontrol.dtos.user.UserResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@Tag(name = "Usuários", description = "Gerenciamento de dados de usuários e imagens de perfil.")
public interface UserControllerDoc {

    @Operation(summary = "Retorna os dados do usuário autenticado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Dados retornados com sucesso",
                    content = @Content(schema = @Schema(implementation = UserResponseDTO.class))),
            @ApiResponse(responseCode = "401", description = "Usuário não autenticado", content = @Content),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado", content = @Content)
    })
    ResponseEntity<UserResponseDTO> getDateUser(JWTUserDataDTO user);

    @Operation(summary = "Retorna uma imagem de perfil pelo nome do arquivo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Imagem encontrada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Imagem não encontrada", content = @Content),
            @ApiResponse(responseCode = "400", description = "Erro ao carregar a imagem", content = @Content)
    })
    ResponseEntity<Resource> getProfileImage(@PathVariable String filename);

    @Operation(summary = "Faz upload de uma imagem de perfil para o usuário")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Upload realizado com sucesso",
                    content = @Content(schema = @Schema(implementation = Map.class)))
    })
    ResponseEntity<Map<String, String>> uploadProfileImage(
            @Parameter(description = "ID do usuário") Long id,
            @Parameter(description = "Arquivo de imagem") MultipartFile file
    );

    @Operation(summary = "Atualiza os dados de um usuário",
            description = "Atualiza nome e/ou e-mail do usuário. Se o e-mail for alterado, o usuário será deslogado no front-end.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário atualizado com sucesso",
                    content = @Content(schema = @Schema(implementation = UserResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos ou e-mail já em uso", content = @Content),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado", content = @Content)
    })
    ResponseEntity<UserResponseDTO> editUser(
            @Parameter(description = "ID do usuário a ser editado") Long id,
            @Parameter(description = "Dados atualizados (nome e/ou e-mail)") UserRequestDTO request
    );

}
