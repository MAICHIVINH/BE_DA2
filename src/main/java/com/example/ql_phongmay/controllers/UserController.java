package com.example.ql_phongmay.controllers;

import com.example.ql_phongmay.dto.request.LoginRequest;
import com.example.ql_phongmay.dto.request.UserRequest;
import com.example.ql_phongmay.dto.response.ApiResponse;
import com.example.ql_phongmay.dto.response.LoginUserResponse;
import com.example.ql_phongmay.dto.response.UserResponse;
import com.example.ql_phongmay.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@CrossOrigin(
        origins = {
                "http://localhost:5173",
                "http://localhost:5174",
                "http://localhost:5175",
                "http://localhost:5176",
                "http://localhost:5177",
                "http://localhost:5178",
                "http://localhost:5179"
        },
        methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE},
        allowedHeaders = "*"
)
@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    // Đăng nhập
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginUserResponse>> login(@RequestBody LoginRequest request) {
        try {
            LoginUserResponse response = userService.login(request);
            return ResponseEntity.ok(
                    ApiResponse.<LoginUserResponse>builder()
                            .status(HttpStatus.OK.value())
                            .message("Đăng nhập thành công")
                            .data(response)
                            .build()
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    ApiResponse.<LoginUserResponse>builder()
                            .status(HttpStatus.BAD_REQUEST.value())
                            .message("Đăng nhập thất bại: " + e.getMessage())
                            .data(null)
                            .build()
            );
        }
    }

    // Lấy tất cả user
    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<UserResponse>>> getAllUser() {
        return ResponseEntity.ok(
                ApiResponse.<List<UserResponse>>builder()
                        .status(HttpStatus.OK.value())
                        .message("Lấy danh sách user thành công")
                        .data(userService.getAllUser())
                        .build()
        );
    }

    // Phân trang
    @GetMapping("/page")
    public ResponseEntity<ApiResponse<Page<UserResponse>>> getUserPaging(
            @RequestParam int page,
            @RequestParam int size
    ) {
        return ResponseEntity.ok(
                ApiResponse.<Page<UserResponse>>builder()
                        .status(HttpStatus.OK.value())
                        .message("Lấy danh sách phân trang thành công")
                        .data(userService.getUserPaging(PageRequest.of(page, size)))
                        .build()
        );
    }

    // Lấy theo id
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UserResponse>> getUserById(@PathVariable Integer id) {
        return ResponseEntity.ok(
                ApiResponse.<UserResponse>builder()
                        .status(HttpStatus.OK.value())
                        .message("Lấy thông tin user thành công")
                        .data(userService.getUserById(id))
                        .build()
        );
    }

    // Tạo mới
    @PostMapping(value = "/add", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<UserResponse>> createUser(
            @RequestPart("user") String userJson,
            @RequestPart(value = "file", required = false) MultipartFile file
    ) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            UserRequest request = mapper.readValue(userJson, UserRequest.class);

            UserResponse saved = userService.createUser(request, file);

            return ResponseEntity.status(HttpStatus.CREATED).body(
                    ApiResponse.<UserResponse>builder()
                            .status(HttpStatus.CREATED.value())
                            .message("Tạo user thành công")
                            .data(saved)
                            .build()
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    ApiResponse.<UserResponse>builder()
                            .status(HttpStatus.BAD_REQUEST.value())
                            .message("Lỗi khi tạo user: " + e.getMessage())
                            .data(null)
                            .build()
            );
        }
    }

    // Cập nhật
    @PutMapping(value = "/update/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<UserResponse>> updateUser(
            @PathVariable Integer id,
            @RequestPart("user") String userJson,
            @RequestPart(value = "file", required = false) MultipartFile file
    ) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            UserRequest request = mapper.readValue(userJson, UserRequest.class);

            UserResponse updated = userService.updateUser(id, request, file);

            return ResponseEntity.ok(
                    ApiResponse.<UserResponse>builder()
                            .status(HttpStatus.OK.value())
                            .message("Cập nhật user thành công")
                            .data(updated)
                            .build()
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    ApiResponse.<UserResponse>builder()
                            .status(HttpStatus.BAD_REQUEST.value())
                            .message("Lỗi khi cập nhật user: " + e.getMessage())
                            .data(null)
                            .build()
            );
        }
    }

    // Xóa mềm
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse<String>> deleteUser(@PathVariable Integer id) {
        userService.deleteUser(id);
        return ResponseEntity.ok(
                ApiResponse.<String>builder()
                        .status(HttpStatus.OK.value())
                        .message("Đã xóa mềm user thành công")
                        .data(null)
                        .build()
        );
    }

    // Xóa vĩnh viễn
    @DeleteMapping("/hard-delete/{id}")
    public ResponseEntity<ApiResponse<String>> deleteUserForever(@PathVariable Integer id) {
        userService.deleteUserForever(id);
        return ResponseEntity.ok(
                ApiResponse.<String>builder()
                        .status(HttpStatus.OK.value())
                        .message("Đã xóa vĩnh viễn user")
                        .data(null)
                        .build()
        );
    }

    //Tìm kiếm theo tên
    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<UserResponse>>> searchUser(@RequestParam String keyword) {
        return ResponseEntity.ok(
                ApiResponse.<List<UserResponse>>builder()
                        .status(HttpStatus.OK.value())
                        .message("Tìm kiếm user thành công")
                        .data(userService.searchUserByUserName(keyword))
                        .build()
        );
    }
}
