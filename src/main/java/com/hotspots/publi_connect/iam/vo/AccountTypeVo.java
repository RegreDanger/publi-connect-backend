package com.hotspots.publi_connect.iam.vo;

import com.hotspots.publi_connect.kernel.utils.annotations.ValidEnum;
import com.hotspots.publi_connect.kernel.utils.enums.AccountType;

import jakarta.validation.constraints.NotBlank;

public record AccountTypeVo (
    @NotBlank
    @ValidEnum(enumClass = AccountType.class, message = "accountType must be company or personal")
    String accountType
) {}
