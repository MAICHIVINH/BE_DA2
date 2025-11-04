package com.example.ql_phongmay.dto.request;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountRequest {
    private String accountUserName;
    private String email;
    private String passwordHash;
    private String fullName;
    private Integer roleId;
}
