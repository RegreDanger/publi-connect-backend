package com.hotspots.publi_connect.kernel.utils.enums;

import lombok.Getter;

@Getter
public enum AccountType {
    ENTERPRISE("ENTERPRISE"),
    USER("USER");

    private final String type;

    private AccountType(String type) {
        this.type = type;
    }
}
