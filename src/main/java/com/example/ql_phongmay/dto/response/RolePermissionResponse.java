package com.example.ql_phongmay.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RolePermissionResponse {
    private Integer roleId;
    private Integer permissionId;
    private String roleName;
    private String permissionName;
}
