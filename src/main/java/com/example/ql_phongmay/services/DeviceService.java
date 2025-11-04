package com.example.ql_phongmay.services;

import com.example.ql_phongmay.dto.request.DeviceRequest;
import com.example.ql_phongmay.dto.response.DeviceResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

    public interface DeviceService {
    List<DeviceResponse> getAllDevice();
    Page<DeviceResponse> getDevicePaging(Pageable pageable);
    DeviceResponse getDeviceById(Integer id);
    DeviceResponse createDevice(DeviceRequest request, MultipartFile file);
    DeviceResponse updateDevice(Integer id, DeviceRequest request, MultipartFile file);
    void deleteDevice(Integer id);          // xóa mềm
    void deleteDeviceForever(Integer id);   // xóa cứng
}
