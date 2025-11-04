package com.example.ql_phongmay.services;

import com.example.ql_phongmay.dto.request.RolePermissionRequest;
import com.example.ql_phongmay.dto.response.RolePermissionResponse;

import java.util.List;

public interface RolePermissionService {
    RolePermissionResponse assignPermission(RolePermissionRequest request);
    List<RolePermissionResponse> getPermissionsByRole(Integer roleId);
}
