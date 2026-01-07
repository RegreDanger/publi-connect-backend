package com.hotspots.publi_connect.iam.domain.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import com.hotspots.publi_connect.iam.vo.StampsVo;
import com.hotspots.publi_connect.iam.vo.UUIDVo;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@ToString
@EqualsAndHashCode
@Getter
@Table("credential")
/* Just for reading on DB */
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class Credential {
    
    @Id
    private UUID credentialId;
    private UUID userId;
    private String hashedPwd;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Credential(UUIDVo userId, String hashedPwd, StampsVo stamps) {
        this.userId = UUID.fromString(userId.id());
        this.hashedPwd = hashedPwd;
        this.createdAt = stamps.createdAt();
        this.updatedAt = stamps.updatedAt();
    }

}
