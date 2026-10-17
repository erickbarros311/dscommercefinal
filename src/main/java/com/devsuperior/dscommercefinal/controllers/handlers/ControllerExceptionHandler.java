package com.devsuperior.dscommercefinal.controllers.handlers;


import com.devsuperior.dscommercefinal.dto.CustomErrorDTO;
import com.devsuperior.dscommercefinal.services.exceptions.ForbiddenException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;

@ControllerAdvice
public class ControllerExceptionHandler {

    // Este metodo "escuta" a aplicação. Se o OrderController (ou qualquer outro)
    // deixar escapar uma ForbiddenException, este metodo pega ela antes de ir pro console.
    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<CustomErrorDTO> forbidden(ForbiddenException e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.FORBIDDEN; // 403

        // instancia o DTO de erro para capturar a meg de erro
        CustomErrorDTO err = new CustomErrorDTO(
                Instant.now(),
                status.value(),
                e.getMessage(), // Pega a mensagem "Access denied"
                request.getRequestURI() // Pega o caminho, ex: /orders/2
        );

        return ResponseEntity.status(status).body(err);
    }

    // Outros tratamentos (como ResourceNotFoundException ou MethodArgumentNotValidException),
    // eles também ficam aqui dentro desta mesma classe!
}