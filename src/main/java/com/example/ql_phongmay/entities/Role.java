package com.example.ql_phongmay.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "roles")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Integer roleId;

//    @Column(name = "role_code", unique = true)
//    private String roleCode;

    @Column(name = "role_name")
    private String roleName;

    @Column(name = "is_deleted")
    private Boolean deleted;

    // Quan hệ 1-nhiều với role_permissions
    @OneToMany(mappedBy = "role", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<RolePermission> rolePermissions;
}
