package com.hotspots.publi_connect.iam.app.input;

import com.hotspots.publi_connect.iam.vo.EmailVo;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record LoginAccountInput(

    @NotNull
    @Valid
    EmailVo emailVo,

    @NotBlank
    String pwd

) {}
