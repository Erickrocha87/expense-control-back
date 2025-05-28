package com.rocha.expenditurecontrol.controllers;

import com.rocha.expenditurecontrol.dtos.PageResponseDTO;
import com.rocha.expenditurecontrol.dtos.SubscriptionRequestDTO;
import com.rocha.expenditurecontrol.dtos.SubscriptionResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "Assinaturas", description = "Endpoints para gerenciar assinaturas de serviços.")
public interface SubscriptionControllerDoc {

    @Operation(summary = "Cria uma nova assinatura")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Assinatura criada com sucesso",
                    content = @Content(schema = @Schema(implementation = SubscriptionResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos enviados", content = @Content)
    })
    ResponseEntity<SubscriptionResponseDTO> createSubscription(SubscriptionRequestDTO subscription);

    @Operation(summary = "Lista todas as assinaturas ou filtra por status")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Assinaturas listadas com sucesso",
                    content = @Content(schema = @Schema(implementation = PageResponseDTO.class)))
    })
    ResponseEntity<PageResponseDTO<SubscriptionResponseDTO>> getAllSubscriptions(
            @Parameter(description = "Status da assinatura: PAID, PENDING, etc. Use 'ALL' para listar todas.") String status,
            @Parameter(description = "Número da página (começando em 0)") int page,
            @Parameter(description = "Tamanho da página") int size
    );

    @Operation(summary = "Deleta uma assinatura pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Assinatura deletada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Assinatura não encontrada", content = @Content)
    })
    void deleteById(@Parameter(description = "ID da assinatura") Long id);

    @Operation(summary = "Atualiza uma assinatura existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Assinatura atualizada com sucesso",
                    content = @Content(schema = @Schema(implementation = SubscriptionResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos enviados", content = @Content),
            @ApiResponse(responseCode = "404", description = "Assinatura não encontrada", content = @Content)
    })
    ResponseEntity<SubscriptionResponseDTO> updateSubscription(
            @Parameter(description = "ID da assinatura a ser atualizada") Long id,
            SubscriptionRequestDTO subscription
    );
}
