package com.example.ql_phongmay.services.implement;

import com.example.ql_phongmay.dto.request.DeviceStatusRequest;
import com.example.ql_phongmay.dto.response.DeviceStatusResponse;
import com.example.ql_phongmay.entities.DeviceStatus;
import com.example.ql_phongmay.repositories.DeviceStatusRepository;
import com.example.ql_phongmay.services.DeviceStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DeviceStatusServiceImpl implements DeviceStatusService {
    private final DeviceStatusRepository deviceStatusRepository;

    //Entity -> Response
    private DeviceStatusResponse mapToResponse(DeviceStatus deviceStatus) {
        return DeviceStatusResponse.builder()
                .deviceStatusId(deviceStatus.getDeviceStatusId())
                .deviceStatusName(deviceStatus.getDeviceStatusName())
                .isDeleted(deviceStatus.getDeleted())
                .build();
    }

    //Request -> Entity
    private DeviceStatus mapToEntity(DeviceStatusRequest request) {
        return DeviceStatus.builder()
                .deviceStatusName(request.getDeviceStatusName())
                .deleted(false)
                .build();
    }

    @Override
    public List<DeviceStatusResponse> getAllDeviceStatus() {
        return deviceStatusRepository.findByDeletedFalse()
                .stream().map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public Page<DeviceStatusResponse> getDeviceStatusPaging(Pageable pageable) {
        return deviceStatusRepository.findAll(pageable).map(this::mapToResponse);
    }

    @Override
    public Optional<DeviceStatusResponse> getDeviceStatusById(Integer id) {
        return deviceStatusRepository.findById(id)
                .map(this::mapToResponse);
    }

    @Override
    public DeviceStatusResponse createDeviceStatus(DeviceStatusRequest deviceStatusRequest) {
        DeviceStatus deviceStatus = mapToEntity(deviceStatusRequest);
        return mapToResponse(deviceStatusRepository.save(deviceStatus));
    }

    @Override
    public DeviceStatusResponse updateDeviceStatus(Integer id, DeviceStatusRequest deviceStatusRequest) {
        DeviceStatus existing = deviceStatusRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("DeviceStatus not found with id: " + id));

        //cập nhật từng field
        existing.setDeviceStatusName(deviceStatusRequest.getDeviceStatusName());
        return mapToResponse(deviceStatusRepository.save(existing));
    }

    @Override
    public void deleteDeviceStatus(Integer id) {
        DeviceStatus deviceStatus = deviceStatusRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("DeviceStatus không tồn tại"));

        deviceStatus.setDeleted(true); // đánh dấu là đã xóa
        deviceStatusRepository.save(deviceStatus);
    }

    @Override
    public void deleteDeviceStatusForever(Integer id) {
        if(!deviceStatusRepository.existsById(id)) {
            throw new RuntimeException("DeviceStatus not found with id: " + id);
        }
        deviceStatusRepository.deleteById(id);
    }
}
