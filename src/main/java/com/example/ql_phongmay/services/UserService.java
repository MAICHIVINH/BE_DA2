package com.example.ql_phongmay.services;

import com.example.ql_phongmay.dto.request.LoginRequest;
import com.example.ql_phongmay.dto.request.UserRequest;
import com.example.ql_phongmay.dto.response.LoginUserResponse;
import com.example.ql_phongmay.dto.response.UserResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UserService {
    List<UserResponse> getAllUser();

    Page<UserResponse> getUserPaging(Pageable pageable);

    UserResponse getUserById(Integer id);

    UserResponse createUser(UserRequest request, MultipartFile file);

    UserResponse updateUser(Integer id, UserRequest request, MultipartFile file);

    void deleteUser(Integer id);

    void deleteUserForever(Integer id);

    List<UserResponse> searchUserByUserName(String keyword);

    LoginUserResponse login(LoginRequest request);
}
