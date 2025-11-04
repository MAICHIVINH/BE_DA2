package com.example.ql_phongmay.repositories;

import com.example.ql_phongmay.entities.RolePermission;
import com.example.ql_phongmay.entities.RolePermissionId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RolePermissionRepository extends JpaRepository<RolePermission, RolePermissionId> {
    List<RolePermission> findByRoleId(Integer roleId);
}
