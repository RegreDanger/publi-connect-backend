package com.hotspots.publi_connect.iam.domain.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import com.hotspots.publi_connect.iam.vo.RefreshTokenVo;
import com.hotspots.publi_connect.iam.vo.SessionStampsVo;
import com.hotspots.publi_connect.iam.vo.UUIDVo;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@ToString
@EqualsAndHashCode
@Getter
@Table("session")
/* Just for reading on DB */
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class Session {

    @Id
    private UUID sessionId;
    private UUID deviceId;
    private String refreshToken;
    private LocalDateTime createdAt;
    private LocalDateTime expiresAt;

    public Session(UUIDVo deviceId, RefreshTokenVo token, SessionStampsVo stamps) {
        this.deviceId = deviceId.id();
        this.refreshToken = token.refreshToken();
        this.createdAt = stamps.createdAt();
        this.expiresAt = stamps.expiresAt();
    }

}
