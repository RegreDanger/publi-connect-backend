package com.hotspots.publi_connect.iam.domain.service;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.hotspots.publi_connect.iam.app.input.CreateDeviceInput;
import com.hotspots.publi_connect.iam.domain.entity.Device;
import com.hotspots.publi_connect.iam.repository.DeviceRepository;
import com.hotspots.publi_connect.iam.vo.DeviceAccountIdsVo;
import com.hotspots.publi_connect.iam.vo.UUIDVo;

import jakarta.validation.Valid;
import lombok.Data;
import reactor.core.publisher.Mono;

@Service
@Validated
@Data
public class DeviceService {    
    private final DeviceRepository repo;

    public Mono<DeviceAccountIdsVo> createDevice(@Valid CreateDeviceInput request) {
        Device device = new Device(request.accountIdVo(), request.macAddressVo(), true);
        return repo.save(device).map(deviceSaved -> {
            UUIDVo accountIdVo = new UUIDVo(deviceSaved.getAccountId().toString());
            UUIDVo deviceIdVo = new UUIDVo(deviceSaved.getDeviceId().toString());
            return new DeviceAccountIdsVo(deviceIdVo, accountIdVo);
        });
    }

}
