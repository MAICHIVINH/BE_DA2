package com.example.ql_phongmay.services.implement;

import com.example.ql_phongmay.dto.request.PermissionRequest;
import com.example.ql_phongmay.dto.response.PermissionResponse;
import com.example.ql_phongmay.entities.Permission;
import com.example.ql_phongmay.repositories.PermissionRepository;
import com.example.ql_phongmay.services.PermissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PermissionServiceImpl implements PermissionService {
    private final PermissionRepository permissionRepository;

    // Entity -> Response
    private PermissionResponse mapToResponse(Permission permission) {
        return PermissionResponse.builder()
                .permissionId(permission.getPermissionId())
                .permissionName(permission.getPermissionName())
                .deleted(permission.getDeleted())
                .build();
    }

    // Request -> Entity
    private Permission mapToEntity(PermissionRequest request) {
        return Permission.builder()
                .permissionName(request.getPermissionName())
                .deleted(false)
                .build();
    }

    @Override
    public PermissionResponse createPermission(PermissionRequest request) {
        Permission entity = mapToEntity(request);
        return mapToResponse(permissionRepository.save(entity));
    }

    @Override
    public List<PermissionResponse> getAllPermissions() {
        return permissionRepository.findByDeletedFalse()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public void deletePermission(Integer id) {
        Permission permission = permissionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Permission không tồn tại"));

        permission.setDeleted(true); // đánh dấu là đã xóa
        permissionRepository.save(permission);
    }

    @Override
    public void deletePermissionForever(Integer id) {
        if(!permissionRepository.existsById(id)) {
            throw new RuntimeException("Permission not found with id: " + id);
        }
        permissionRepository.deleteById(id);
    }
}
