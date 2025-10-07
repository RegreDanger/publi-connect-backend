package com.hotspots.publi_connect.kernel.utils.enums;

import lombok.Getter;

@Getter
public enum AuthProvider {
    BASIC("BASIC"),
    GOOGLE("GOOGLE"),
    APPLE("APPLE"),
    FACEBOOK("FACEBOOK");

    private final String value;

    private AuthProvider(String value) {
        this.value = value;
    }

}
