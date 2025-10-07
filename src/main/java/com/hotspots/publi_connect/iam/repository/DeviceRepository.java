package com.hotspots.publi_connect.iam.repository;

import java.util.UUID;

import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

import com.hotspots.publi_connect.iam.domain.entity.Device;

@Repository
public interface DeviceRepository extends R2dbcRepository<Device, UUID> {}
