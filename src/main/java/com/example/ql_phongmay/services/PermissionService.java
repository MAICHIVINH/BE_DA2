package com.example.ql_phongmay.services;

import com.example.ql_phongmay.dto.request.PermissionRequest;
import com.example.ql_phongmay.dto.response.PermissionResponse;

import java.util.List;

public interface PermissionService {
    PermissionResponse createPermission(PermissionRequest request);
    List<PermissionResponse> getAllPermissions();
    void deletePermission(Integer id);
    void deletePermissionForever(Integer id);
}
