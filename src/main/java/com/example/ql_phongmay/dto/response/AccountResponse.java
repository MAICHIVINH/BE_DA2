package com.example.ql_phongmay.dto.response;

import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountResponse {
    private Integer accountId;
    private String accountUserName;
    private String email;
    private String fullName;
    private String avatarUrl;
    private String roleName;
    private LocalDateTime accountCreateAt;
    private Boolean isDeleted;
}
