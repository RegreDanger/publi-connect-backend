package com.hotspots.publi_connect.iam.api.dto.credential;

import java.time.LocalDateTime;
import java.util.UUID;

public record CreateCredentialResponse(

    UUID userId,

    LocalDateTime createdeAt,
    
    LocalDateTime updatedAt

) {}
