package com.example.ql_phongmay.dto.response;

import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponse {
    private Integer userId;
    private String userName;
    private String userEmail;
    private String userPassword;
    private String userFullName;
    private String avatarUrl;
    private LocalDateTime userCreateAt;
    private Boolean isDeleted;
}
