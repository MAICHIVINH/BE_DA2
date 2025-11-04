package com.example.ql_phongmay.repositories;

import com.example.ql_phongmay.entities.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PermissionRepository extends JpaRepository<Permission, Integer> {
    List<Permission> findByDeletedFalse();
}
