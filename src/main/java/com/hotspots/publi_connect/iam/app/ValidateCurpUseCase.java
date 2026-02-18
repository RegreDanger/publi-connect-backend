package com.hotspots.publi_connect.iam.app;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.hotspots.publi_connect.iam.app.input.ValidateCurpInput;
import com.hotspots.publi_connect.iam.app.output.ValidateCurpResult;
import com.hotspots.publi_connect.iam.domain.service.CurpService;

import jakarta.validation.Valid;
import lombok.Data;
import reactor.core.publisher.Mono;

@Service
@Validated
@Data
public class ValidateCurpUseCase {

    private final CurpService curpService;

    public Mono<ValidateCurpResult> validate(@Valid ValidateCurpInput input) {
        return Mono.fromSupplier(() -> curpService.validateAndExtract(input.curp()));
    }
}
