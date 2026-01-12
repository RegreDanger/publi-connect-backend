package com.hotspots.publi_connect.iam.domain.entity;

import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import com.hotspots.publi_connect.iam.vo.MacAddressVo;
import com.hotspots.publi_connect.iam.vo.UUIDVo;

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

    private UUID accountId;
    private String macAddress;
    private boolean isOnline;

    public Device(UUIDVo accountIdVo, MacAddressVo macAddressVo, boolean isOnline) {
        this.accountId = UUID.fromString(accountIdVo.id());
        this.macAddress = macAddressVo.macAddress();
        this.isOnline = isOnline;
    }

}
