package com.example.ql_phongmay.dto.response;

import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginResponse {
    private Integer accountId;
    private String username;
    private String email;
    private String fullName;
    private String avatarUrl;
    private String role;
    private List<PermissionResponse> permissions;
    private String message;
}
