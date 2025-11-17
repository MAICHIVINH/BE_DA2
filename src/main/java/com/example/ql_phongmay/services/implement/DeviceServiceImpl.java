package com.example.ql_phongmay.services.implement;

import com.example.ql_phongmay.dto.request.DeviceRequest;
import com.example.ql_phongmay.dto.response.DeviceResponse;
import com.example.ql_phongmay.entities.*;
import com.example.ql_phongmay.repositories.DeviceRepository;
import com.example.ql_phongmay.repositories.DeviceStatusRepository;
import com.example.ql_phongmay.repositories.DeviceTypeRepository;
import com.example.ql_phongmay.repositories.RoomRepository;
import com.example.ql_phongmay.services.DeviceService;
import com.example.ql_phongmay.utils.CloudinaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DeviceServiceImpl implements DeviceService {
    private final DeviceRepository deviceRepository;
    private final DeviceTypeRepository deviceTypeRepository;
    private final RoomRepository roomRepository;
    private final DeviceStatusRepository deviceStatusRepository;
    private final CloudinaryService cloudinaryService;

    // mapping entity -> response
    private DeviceResponse mapToResponse(Device device) {
        return DeviceResponse.builder()
                .deviceId(device.getDeviceId())
                .assetTag(device.getAssetTag())
                .serialNumber(device.getSerialNumber())
                .model(device.getModel())
                .deviceTypeName(device.getDeviceType() != null ? device.getDeviceType().getDeviceTypeName() : null)
                .imageUrl(device.getImageUrl())
                .roomName(device.getRoom() != null ? device.getRoom().getRoomName() : null)
                .assignedMachineSlot(device.getAssignedMachineSlot())
                .deviceStatusName(device.getDeviceStatus() != null ? device.getDeviceStatus().getDeviceStatusName() : null)
                .purchaseDate(device.getPurchaseDate())
                .warrantyEnd(device.getWarrantyEnd())
                .note(device.getNote())
                .deviceCreateAt(device.getDeviceCreateAt())
                .isDeleted(device.getDeleted())
                .build();
    }

    // mapping request -> entity
    private Device mapToEntity(DeviceRequest request) {
        DeviceType deviceType = deviceTypeRepository.findById(request.getDeviceTypeId())
                .orElseThrow(() -> new RuntimeException("Device type not found with id: " + request.getDeviceTypeId()));
        Room room = roomRepository.findById(request.getRoomId())
                .orElseThrow(() -> new RuntimeException("Room not found with id: " + request.getRoomId()));
        DeviceStatus deviceStatus;
        if (request.getDeviceStatusId() == null) {
            deviceStatus = deviceStatusRepository.findById(1)
                    .orElseThrow(() -> new RuntimeException("Default DeviceStatus (id=1) not found"));
        } else {
            deviceStatus = deviceStatusRepository.findById(request.getDeviceStatusId())
                    .orElseThrow(() -> new RuntimeException("DeviceStatus not found with id: " + request.getDeviceStatusId()));
        }

        return Device.builder()
                .assetTag(request.getAssetTag())
                .serialNumber(request.getSerialNumber())
                .model(request.getModel())
                .deviceType(deviceType)
                .room(room)
                .assignedMachineSlot(request.getAssignedMachineSlot())
                .deviceStatus(deviceStatus)
                .purchaseDate(request.getPurchaseDate())
                .warrantyEnd(request.getWarrantyEnd())
                .note(request.getNote())
                .deleted(false)
                .build();
    }

    @Override
    public List<DeviceResponse> getAllDevice() {
        return deviceRepository.findByDeletedFalse()
                .stream().map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public Page<DeviceResponse> getDevicePaging(Pageable pageable) {
        return deviceRepository.findByDeletedFalse(pageable)
                .map(this::mapToResponse);
    }

    @Override
    public DeviceResponse getDeviceById(Integer id) {
        Device device = deviceRepository.findByDeviceIdAndDeletedFalse(id)
                .orElseThrow(() -> new RuntimeException("Device not found with id: " + id));
        return mapToResponse(device);
    }

    @Override
    public DeviceResponse createDevice(DeviceRequest request, MultipartFile file) {
        Device device = mapToEntity(request);
        if (file != null && !file.isEmpty()) {
            try {
                String imageUrl = cloudinaryService.uploadFile(file);
                device.setImageUrl(imageUrl);
            } catch (IOException e) {
                throw new RuntimeException("Upload image failed: " + e.getMessage(), e);
            }
        }
        return mapToResponse(deviceRepository.save(device));
    }

    @Override
    public DeviceResponse updateDevice(Integer id, DeviceRequest request, MultipartFile file) {

        Device existing = deviceRepository.findByDeviceIdAndDeletedFalse(id)
                .orElseThrow(() -> new RuntimeException("Device not found with id: " + id));
        DeviceType deviceType = deviceTypeRepository.findById(request.getDeviceTypeId())
                .orElseThrow(() -> new RuntimeException("Device type not found with id: " + request.getDeviceTypeId()));
        Room room = roomRepository.findById(request.getRoomId())
                .orElseThrow(() -> new RuntimeException("Room not found with id: " + request.getRoomId()));
        DeviceStatus deviceStatus = deviceStatusRepository.findById(request.getDeviceStatusId())
                .orElseThrow(()-> new RuntimeException("Device status not found with id: " + request.getDeviceStatusId()));

        existing.setAssetTag(request.getAssetTag());
        existing.setSerialNumber(request.getSerialNumber());
        existing.setModel(request.getModel());
        existing.setDeviceType(deviceType);
        existing.setRoom(room);
        existing.setAssignedMachineSlot(request.getAssignedMachineSlot());
        existing.setDeviceStatus(deviceStatus);
        existing.setPurchaseDate(request.getPurchaseDate());
        existing.setWarrantyEnd(request.getWarrantyEnd());
        existing.setNote(request.getNote());

        if (file != null && !file.isEmpty()) {
            try {
                String imageUrl = cloudinaryService.uploadFile(file);
                existing.setImageUrl(imageUrl);
            } catch (IOException e) {
                throw new RuntimeException("Upload image failed: " + e.getMessage(), e);
            }
        }

        return mapToResponse(deviceRepository.save(existing));
    }

    @Override
    public void deleteDevice(Integer id) {
        Device device = deviceRepository.findByDeviceIdAndDeletedFalse(id)
                .orElseThrow(() -> new RuntimeException("Device not found with id: " + id));
        device.setDeleted(true);
        deviceRepository.save(device);
    }

    @Override
    public void deleteDeviceForever(Integer id) {
        if (!deviceRepository.existsById(id)) {
            throw new RuntimeException("Device not found with id: " + id);
        }
        deviceRepository.deleteById(id);
    }
}