package com.example.ql_phongmay.services.implement;

import com.example.ql_phongmay.dto.request.DeviceTypeRequest;
import com.example.ql_phongmay.dto.request.RoleRequest;
import com.example.ql_phongmay.dto.response.DeviceTypeResponse;
import com.example.ql_phongmay.dto.response.RoleResponse;
import com.example.ql_phongmay.entities.DeviceType;
import com.example.ql_phongmay.entities.Role;
import com.example.ql_phongmay.repositories.DeviceTypeRepository;
import com.example.ql_phongmay.services.DeviceTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DeviceTypeServiceIpl implements DeviceTypeService {
    private final DeviceTypeRepository deviceTypeRepository;

    //Entity -> Response
    private DeviceTypeResponse mapToResponse(DeviceType deviceType) {
        return DeviceTypeResponse.builder()
                .deviceTypeId(deviceType.getDeviceTypeId())
                .deviceTypeName(deviceType.getDeviceTypeName())
                .deviceTypeDescription(deviceType.getDeviceTypeDescription())
                .isDeleted(deviceType.getDeleted())
                .build();
    }

    //Request -> Entity
    private DeviceType mapToEntity(DeviceTypeRequest request) {
        return DeviceType.builder()
                .deviceTypeName(request.getDeviceTypeName())
                .deviceTypeDescription(request.getDeviceTypeDescription())
                .deleted(false)
                .build();
    }

    @Override
    public List<DeviceTypeResponse> getAllDeviceType() {
        return deviceTypeRepository.findByDeletedFalse()
                .stream().map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public Page<DeviceTypeResponse> getDeviceTypePaging(Pageable pageable) {
        return deviceTypeRepository.findAll(pageable).map(this::mapToResponse);
    }

    @Override
    public Optional<DeviceTypeResponse> getDeviceTypeById(Integer id) {
        return deviceTypeRepository.findById(id)
                .map(this::mapToResponse);
    }

    @Override
    public DeviceTypeResponse createDeviceType(DeviceTypeRequest deviceTypeRequest) {
        DeviceType deviceType = mapToEntity(deviceTypeRequest);
        return mapToResponse(deviceTypeRepository.save(deviceType));
    }

    @Override
    public DeviceTypeResponse updateDeviceType(Integer id, DeviceTypeRequest deviceTypeRequest) {
        DeviceType existing = deviceTypeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("DeviceType not found with id: " + id));

        //cập nhật từng field
        existing.setDeviceTypeName(deviceTypeRequest.getDeviceTypeName());
        existing.setDeviceTypeDescription(deviceTypeRequest.getDeviceTypeDescription());
        return mapToResponse(deviceTypeRepository.save(existing));
    }

    @Override
    public void deleteDeviceType(Integer id) {
        DeviceType deviceType = deviceTypeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("DeviceType không tồn tại"));

        deviceType.setDeleted(true); // đánh dấu là đã xóa
        deviceTypeRepository.save(deviceType);
    }

    @Override
    public void deleteDeviceTypeForever(Integer id) {
        if(!deviceTypeRepository.existsById(id)) {
            throw new RuntimeException("DeviceType not found with id: " + id);
        }
        deviceTypeRepository.deleteById(id);
    }

}
