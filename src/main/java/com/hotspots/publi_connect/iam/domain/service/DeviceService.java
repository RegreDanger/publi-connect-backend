package com.hotspots.publi_connect.iam.domain.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.hotspots.publi_connect.iam.api.dto.CreateDeviceRequest;
import com.hotspots.publi_connect.iam.api.dto.CreateDeviceResponse;
import com.hotspots.publi_connect.iam.domain.entity.Device;
import com.hotspots.publi_connect.iam.repository.DeviceRepository;
import com.hotspots.publi_connect.iam.vo.DeviceUserIdsVo;
import com.hotspots.publi_connect.iam.vo.UUIDVo;
import com.hotspots.publi_connect.iam.vo.ValidUntilVo;

import jakarta.validation.Valid;
import lombok.Data;
import reactor.core.publisher.Mono;

@Service
@Validated
@Data
public class DeviceService {
    private final DeviceRepository repo;

    @Valid
    public Mono<CreateDeviceResponse> createDevice(CreateDeviceRequest request) {
        LocalDateTime validUntilDate = LocalDateTime.now().plusHours(1);
        ValidUntilVo validUntilVo = new ValidUntilVo(validUntilDate);
        Device device = new Device(request.userId(), request.macAddress(), validUntilVo, true);
        return repo.save(device).map(deviceSaved -> {
            UUIDVo userIdVo = new UUIDVo(deviceSaved.getUserId());
            UUIDVo deviceIdVo = new UUIDVo(deviceSaved.getDeviceId());
            DeviceUserIdsVo deviceUserIdsVo = new DeviceUserIdsVo(deviceIdVo, userIdVo);
            return new CreateDeviceResponse(deviceUserIdsVo);
        });
    }

}
