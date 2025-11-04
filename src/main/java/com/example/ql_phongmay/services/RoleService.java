package com.example.ql_phongmay.services;

import com.example.ql_phongmay.dto.request.RoleRequest;
import com.example.ql_phongmay.dto.response.RoleResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface RoleService {
    List<RoleResponse> getAllRole();
    Page<RoleResponse> getRolePaging(Pageable pageable);
    Optional<RoleResponse> getRoleById(Integer id);
    RoleResponse createRole(RoleRequest roleRequest);
    RoleResponse updateRole(Integer id,RoleRequest roleRequest);
    void deleteRole(Integer id);
    void deleteRoleForever(Integer id);
}
