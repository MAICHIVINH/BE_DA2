package com.example.ql_phongmay.services;

import com.example.ql_phongmay.dto.request.DeviceTypeRequest;
import com.example.ql_phongmay.dto.response.DeviceTypeResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface DeviceTypeService {
    List<DeviceTypeResponse> getAllDeviceType();
    Page<DeviceTypeResponse> getDeviceTypePaging(Pageable pageable);
    Optional<DeviceTypeResponse> getDeviceTypeById(Integer id);
    DeviceTypeResponse createDeviceType(DeviceTypeRequest deviceTypeRequest);
    DeviceTypeResponse updateDeviceType(Integer id,DeviceTypeRequest deviceTypeRequest);
    void deleteDeviceType(Integer id);
    void deleteDeviceTypeForever(Integer id);
}
