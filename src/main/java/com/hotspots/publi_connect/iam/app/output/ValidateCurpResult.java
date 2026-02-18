package com.hotspots.publi_connect.iam.app.output;

public record ValidateCurpResult(
    String curp,
    String nombres,
    String apellidoPaterno,
    String apellidoMaterno,
    String genero,
    String fechaNacimiento,
    String estado
) {}
