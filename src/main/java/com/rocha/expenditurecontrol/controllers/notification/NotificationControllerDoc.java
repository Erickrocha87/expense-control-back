package com.rocha.expenditurecontrol.controllers.notification;

import com.rocha.expenditurecontrol.dtos.tokenpassword.EmailDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

import java.util.Map;

@Tag(name = "Notificações", description = "Envio de notificações por e-mail, incluindo tokens para redefinição de senha.")
public interface NotificationControllerDoc {
    @Operation(
            summary = "Envia um token para redefinição de senha",
            description = "Gera um token de verificação e envia por e-mail ao usuário, com validade de 10 minutos."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Token gerado e enviado com sucesso",
                    content = @Content(schema = @Schema(implementation = Map.class))),
            @ApiResponse(responseCode = "400", description = "E-mail ausente ou inválido", content = @Content),
            @ApiResponse(responseCode = "401", description = "Usuário não encontrado ou não autorizado", content = @Content)
    })
    ResponseEntity<Map<String, String>> generateToken(
            @RequestBody(description = "Objeto com o e-mail do usuário",
                    required = true,
                    content = @Content(schema = @Schema(implementation = EmailDTO.class)))
            EmailDTO dto
    );
}
