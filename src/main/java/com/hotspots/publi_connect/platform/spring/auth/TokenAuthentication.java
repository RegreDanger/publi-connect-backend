package com.hotspots.publi_connect.platform.spring.auth;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode(callSuper = false)
@ToString
public class TokenAuthentication extends AbstractAuthenticationToken {

    private final String sessionToken;
    private final String refreshToken;
    private final String credential;
    private final transient Object principal;

    public TokenAuthentication(String sessionToken, String refreshToken) {
        super(null);
        this.principal = null;
        this.sessionToken = sessionToken;
        this.refreshToken = refreshToken;
        this.credential = null;
        setAuthenticated(false);
    }

    public TokenAuthentication(Object principal, String credential, String sessionToken, String refreshToken, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.principal = principal;
        this.credential = credential;
        this.sessionToken = sessionToken;
        this.refreshToken = refreshToken;
        setAuthenticated(true);
    }

    @Override
    public Object getPrincipal() {
        return principal;
    }

    @Override
    public Object getCredentials() {
        return credential;
    }

    public Map<String, String> getSessionTokens() {
        HashMap<String, String> tokens = new HashMap<>();
        tokens.put("session_token", sessionToken);
        tokens.put("refresh_token", refreshToken);
        return tokens;
    }

}
