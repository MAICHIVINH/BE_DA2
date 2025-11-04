package com.example.ql_phongmay.services.implement;

import com.example.ql_phongmay.dto.request.RolePermissionRequest;
import com.example.ql_phongmay.dto.response.RolePermissionResponse;
import com.example.ql_phongmay.entities.Permission;
import com.example.ql_phongmay.entities.Role;
import com.example.ql_phongmay.entities.RolePermission;
import com.example.ql_phongmay.repositories.PermissionRepository;
import com.example.ql_phongmay.repositories.RolePermissionRepository;
import com.example.ql_phongmay.repositories.RoleRepository;
import com.example.ql_phongmay.services.RolePermissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RolePermissionServiceImpl implements RolePermissionService {
    private final RolePermissionRepository rolePermissionRepository;
    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;

    // Entity -> Response
    private RolePermissionResponse mapToResponse(RolePermission rp) {
        return RolePermissionResponse.builder()
                .roleId(rp.getRoleId())
                .permissionId(rp.getPermissionId())
                .roleName(rp.getRole() != null ? rp.getRole().getRoleName() : null)
                .permissionName(rp.getPermission() != null ? rp.getPermission().getPermissionName() : null)
                .build();
    }

    // Request -> Entity
    private RolePermission mapToEntity(RolePermissionRequest request) {
        return RolePermission.builder()
                .roleId(request.getRoleId())
                .permissionId(request.getPermissionId())
                .build();
    }

    @Override
    public RolePermissionResponse assignPermission(RolePermissionRequest request) {
        RolePermission entity = mapToEntity(request);
        rolePermissionRepository.save(entity);

        // fetch Role và Permission để map ra response
        Role role = roleRepository.findById(request.getRoleId())
                .orElseThrow(() -> new RuntimeException("Role not found"));
        Permission permission = permissionRepository.findById(request.getPermissionId())
                .orElseThrow(() -> new RuntimeException("Permission not found"));

        return RolePermissionResponse.builder()
                .roleId(role.getRoleId())
                .permissionId(permission.getPermissionId())
                .roleName(role.getRoleName())
                .permissionName(permission.getPermissionName())
                .build();
    }

    @Override
    public List<RolePermissionResponse> getPermissionsByRole(Integer roleId) {
        return rolePermissionRepository.findByRoleId(roleId)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }
}
