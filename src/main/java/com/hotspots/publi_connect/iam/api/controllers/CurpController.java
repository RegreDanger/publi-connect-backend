package com.hotspots.publi_connect.iam.api.controllers;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hotspots.publi_connect.iam.api.dto.curp.ValidateCurpResponse;
import com.hotspots.publi_connect.iam.app.ValidateCurpUseCase;
import com.hotspots.publi_connect.iam.app.input.ValidateCurpInput;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/public/curp")
@Validated
@Data
public class CurpController {

    private final ValidateCurpUseCase validateCurpUseCase;

    @GetMapping("/{curp}")
    public Mono<ValidateCurpResponse> validate(@PathVariable @NotBlank String curp) {
        return validateCurpUseCase.validate(new ValidateCurpInput(curp))
            .map(result -> new ValidateCurpResponse(
                result.curp(),
                result.nombres(),
                result.apellidoPaterno(),
                result.apellidoMaterno(),
                result.genero(),
                result.fechaNacimiento(),
                result.estado()
            ));
    }
}
