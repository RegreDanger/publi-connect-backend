package com.hotspots.publi_connect.kernel.utils.enums;

import lombok.Getter;

@Getter
public enum AccountType {
    COMPANY("COMPANY"),
    PERSONAL("PERSONAL");

    private final String type;

    private AccountType(String type) {
        this.type = type;
    }
}
