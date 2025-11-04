package com.example.ql_phongmay.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "email")
    private String userEmail;

    @Column(name = "password_hash")
    private String userPassword;

    @Column(name = "full_name")
    private String userFullName;

    @Column(name = "avatar_url")
    private String avatarUrl;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime userCreateAt;

    @Column(name = "is_deleted")
    private Boolean deleted;

    @PrePersist
    protected void onCreate() {
        this.userCreateAt = LocalDateTime.now();
        if (this.deleted == null) {
            this.deleted = false;
        }
    }
}
