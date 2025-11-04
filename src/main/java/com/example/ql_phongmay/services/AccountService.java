package com.example.ql_phongmay.services;

import com.example.ql_phongmay.dto.request.AccountRequest;
import com.example.ql_phongmay.dto.request.LoginRequest;
import com.example.ql_phongmay.dto.response.AccountResponse;
import com.example.ql_phongmay.dto.response.LoginResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface AccountService {
    List<AccountResponse> getAllAccounts();
    Page<AccountResponse> getAccountPaging(Pageable pageable);
    AccountResponse getAccountById(Integer id);
    AccountResponse createAccount(AccountRequest request, MultipartFile file);
    AccountResponse updateAccount(Integer id, AccountRequest request, MultipartFile file);
    void deleteAccount(Integer id);          // xóa mềm
    void deleteAccountForever(Integer id);   // xóa cứng
    List<AccountResponse> searchAccountByName(String keyword);
    LoginResponse login(LoginRequest request);
}
