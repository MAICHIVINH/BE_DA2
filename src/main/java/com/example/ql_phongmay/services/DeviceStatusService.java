package com.example.ql_phongmay.services;

import com.example.ql_phongmay.dto.request.DeviceStatusRequest;
import com.example.ql_phongmay.dto.request.DeviceTypeRequest;
import com.example.ql_phongmay.dto.response.DeviceStatusResponse;
import com.example.ql_phongmay.dto.response.DeviceTypeResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface DeviceStatusService {
    List<DeviceStatusResponse> getAllDeviceStatus();
    Page<DeviceStatusResponse> getDeviceStatusPaging(Pageable pageable);
    Optional<DeviceStatusResponse> getDeviceStatusById(Integer id);
    DeviceStatusResponse createDeviceStatus(DeviceStatusRequest deviceStatusRequest);
    DeviceStatusResponse updateDeviceStatus(Integer id, DeviceStatusRequest deviceStatusRequest);
    void deleteDeviceStatus(Integer id);
    void deleteDeviceStatusForever(Integer id);
}
