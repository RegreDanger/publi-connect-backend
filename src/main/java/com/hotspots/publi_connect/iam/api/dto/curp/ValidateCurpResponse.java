package com.hotspots.publi_connect.iam.api.dto.curp;

public record ValidateCurpResponse(
    String curp,
    String nombres,
    String apellidoPaterno,
    String apellidoMaterno,
    String genero,
    String fechaNacimiento,
    String estado
) {}
