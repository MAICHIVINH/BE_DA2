package com.example.ql_phongmay.services.implement;

import com.example.ql_phongmay.dto.request.RoleRequest;
import com.example.ql_phongmay.dto.response.RoleResponse;
import com.example.ql_phongmay.entities.Role;
import com.example.ql_phongmay.repositories.RoleRepository;
import com.example.ql_phongmay.services.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    //Entity -> Response
    private RoleResponse mapToResponse(Role role) {
        return RoleResponse.builder()
                .roleId(role.getRoleId())
                .roleName(role.getRoleName())
                .isDeleted(role.getDeleted())
                .build();
    }

    //Request -> Entity
    private Role mapToEntity(RoleRequest request) {
        return Role.builder()
                .roleName(request.getRoleName())
                .deleted(false)
                .build();
    }

    @Override
    public List<RoleResponse> getAllRole() {
        return roleRepository.findByDeletedFalse()
                .stream().map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public Page<RoleResponse> getRolePaging(Pageable pageable) {
        return roleRepository.findAll(pageable).map(this::mapToResponse);
    }

    @Override
    public Optional<RoleResponse> getRoleById(Integer id) {
        return roleRepository.findById(id)
                .map(this::mapToResponse);
    }

    @Override
    public RoleResponse createRole(RoleRequest roleRequest) {
        Role role = mapToEntity(roleRequest);
        return mapToResponse(roleRepository.save(role));
    }

    @Override
    public RoleResponse updateRole(Integer id, RoleRequest roleRequest) {
        Role existing = roleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Role not found with id: " + id));

        //cập nhật từng field
        existing.setRoleName(roleRequest.getRoleName());
        return mapToResponse(roleRepository.save(existing));
    }

    @Override
    public void deleteRole(Integer id) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Role không tồn tại"));

        role.setDeleted(true); // đánh dấu là đã xóa
        roleRepository.save(role);
    }

    @Override
    public void deleteRoleForever(Integer id) {
        if(!roleRepository.existsById(id)) {
            throw new RuntimeException("Role not found with id: " + id);
        }
        roleRepository.deleteById(id);
    }

}
