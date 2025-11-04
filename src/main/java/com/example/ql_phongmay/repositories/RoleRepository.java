package com.example.ql_phongmay.repositories;

import com.example.ql_phongmay.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    List<Role> findByDeletedFalse();
}
