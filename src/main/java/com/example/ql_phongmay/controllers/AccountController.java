package com.example.ql_phongmay.controllers;

import com.example.ql_phongmay.dto.request.AccountRequest;
import com.example.ql_phongmay.dto.request.LoginRequest;
import com.example.ql_phongmay.dto.response.AccountResponse;
import com.example.ql_phongmay.dto.response.ApiResponse;
import com.example.ql_phongmay.dto.response.LoginResponse;
import com.example.ql_phongmay.services.AccountService;
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
@RequestMapping("/api/account")
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(@RequestBody LoginRequest request) {
        try {
            LoginResponse response = accountService.login(request);
            return ResponseEntity.ok(
                    ApiResponse.<LoginResponse>builder()
                            .status(HttpStatus.OK.value())
                            .message("Login successful")
                            .data(response)
                            .build()
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    ApiResponse.<LoginResponse>builder()
                            .status(HttpStatus.BAD_REQUEST.value())
                            .message("Login failed: " + e.getMessage())
                            .data(null)
                            .build()
            );
        }
    }

    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<AccountResponse>>> getAllAccounts() {
        return ResponseEntity.ok(
                ApiResponse.<List<AccountResponse>>builder()
                        .status(HttpStatus.OK.value())
                        .message("Successfully retrieved account list")
                        .data(accountService.getAllAccounts())
                        .build()
        );
    }

    // Phân trang account
    @GetMapping("/page")
    public ResponseEntity<ApiResponse<Page<AccountResponse>>> getAccountPaging(
            @RequestParam int page,
            @RequestParam int size
    ) {
        return ResponseEntity.ok(
                ApiResponse.<Page<AccountResponse>>builder()
                        .status(HttpStatus.OK.value())
                        .message("Successfully retrieved paginated accounts")
                        .data(accountService.getAccountPaging(PageRequest.of(page, size)))
                        .build()
        );
    }


    // Tìm theo id
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<AccountResponse>> getAccountById(@PathVariable Integer id) {
        return ResponseEntity.ok(
                ApiResponse.<AccountResponse>builder()
                        .status(HttpStatus.OK.value())
                        .message("Successfully retrieved account information")
                        .data(accountService.getAccountById(id))
                        .build()
        );
    }

    // Tạo mới
    @PostMapping(value = "/add", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<AccountResponse>> createAccount(
            @RequestPart("account") String accountJson,
            @RequestPart(value = "file", required = false) MultipartFile file
    ) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            AccountRequest request = mapper.readValue(accountJson, AccountRequest.class);

            AccountResponse saved = accountService.createAccount(request, file);
            return ResponseEntity.status(HttpStatus.CREATED).body(
                    ApiResponse.<AccountResponse>builder()
                            .status(HttpStatus.CREATED.value())
                            .message("Account created successfully")
                            .data(saved)
                            .build()
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    ApiResponse.<AccountResponse>builder()
                            .status(HttpStatus.BAD_REQUEST.value())
                            .message("Error creating account: " + e.getMessage())
                            .data(null)
                            .build()
            );
        }
    }

    // Cập nhật
    @PutMapping(value = "/update/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<AccountResponse>> updateAccount(
            @PathVariable Integer id,
            @RequestPart("account") String accountJson,
            @RequestPart(value = "file", required = false) MultipartFile file
    ) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            AccountRequest request = mapper.readValue(accountJson, AccountRequest.class);

            AccountResponse updated = accountService.updateAccount(id, request, file);
            return ResponseEntity.ok(
                    ApiResponse.<AccountResponse>builder()
                            .status(HttpStatus.OK.value())
                            .message("Account updated successfully")
                            .data(updated)
                            .build()
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    ApiResponse.<AccountResponse>builder()
                            .status(HttpStatus.BAD_REQUEST.value())
                            .message("Error updating account: " + e.getMessage())
                            .data(null)
                            .build()
            );
        }
    }

    // Xóa mềm
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteAccount(@PathVariable Integer id) {
        accountService.deleteAccount(id);
        return ResponseEntity.ok(
                ApiResponse.<Void>builder()
                        .status(HttpStatus.OK.value())
                        .message("Account soft deleted successfully")
                        .data(null)
                        .build()
        );
    }

    // Xóa khỏi database
    @DeleteMapping("/hard-delete/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteAccountForever(@PathVariable Integer id) {
        accountService.deleteAccountForever(id);
        return ResponseEntity.ok(
                ApiResponse.<Void>builder()
                        .status(HttpStatus.OK.value())
                        .message("Account permanently deleted")
                        .data(null)
                        .build()
        );
    }

    // Tìm kiếm theo tên
    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<AccountResponse>>> searchAccounts(@RequestParam String keyword) {
        return ResponseEntity.ok(
                ApiResponse.<List<AccountResponse>>builder()
                        .status(HttpStatus.OK.value())
                        .message("Account search successful")
                        .data(accountService.searchAccountByName(keyword))
                        .build()
        );
    }
}
