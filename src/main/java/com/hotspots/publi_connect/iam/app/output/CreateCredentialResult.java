package com.hotspots.publi_connect.iam.app.output;

import java.time.LocalDateTime;
import java.util.UUID;

public record CreateCredentialResult(

    UUID userId,

    LocalDateTime createdeAt,
    
    LocalDateTime updatedAt

) {}
