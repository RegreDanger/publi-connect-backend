package com.hotspots.publi_connect.iam.domain.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import com.hotspots.publi_connect.iam.vo.MacAddressVo;
import com.hotspots.publi_connect.iam.vo.UUIDVo;
import com.hotspots.publi_connect.iam.vo.ValidUntilVo;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@ToString
@EqualsAndHashCode
@Getter
@Table("devices")
/* Just for reading on DB */
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class Device {
    
    @Id
    private UUID deviceId;

    private UUID userId;
    private String macAddress;
    private LocalDateTime validUntil;
    private boolean isLogged;

    public Device(UUIDVo userId, MacAddressVo macAddress, ValidUntilVo validUntil, boolean isLogged) {
        this.userId = UUID.fromString(userId.id());
        this.macAddress = macAddress.macAddress();
        this.validUntil = validUntil.validUntil();
        this.isLogged = isLogged;
    }

}
