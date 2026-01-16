package com.hotspots.publi_connect.iam.domain.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
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
@Table("credentials")
/* Just for reading on DB */
@AllArgsConstructor(access = AccessLevel.PACKAGE, onConstructor_ = @PersistenceCreator)
public class Credential {
    
    @Id
    private final UUID credentialId;
    private final UUID accountId;
    private final String hashedPwd;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public Credential(UUIDVo accountIdVo, String hashedPwd, StampsVo stamps) {
        this.credentialId = null;
        this.accountId = UUID.fromString(accountIdVo.id());
        this.hashedPwd = hashedPwd;
        this.createdAt = stamps.createdAt();
        this.updatedAt = stamps.updatedAt();
    }

}
