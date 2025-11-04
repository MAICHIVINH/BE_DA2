package com.example.ql_phongmay.services.implement;

import com.example.ql_phongmay.dto.request.AccountRequest;
import com.example.ql_phongmay.dto.request.LoginRequest;
import com.example.ql_phongmay.dto.response.AccountResponse;
import com.example.ql_phongmay.dto.response.LoginResponse;
import com.example.ql_phongmay.dto.response.PermissionResponse;
import com.example.ql_phongmay.entities.Account;
import com.example.ql_phongmay.entities.Role;
import com.example.ql_phongmay.repositories.AccountRepository;
import com.example.ql_phongmay.repositories.RoleRepository;
import com.example.ql_phongmay.services.AccountService;
import com.example.ql_phongmay.utils.CloudinaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;
    private final RoleRepository roleRepository;
    private final CloudinaryService cloudinaryService;

    // mapping entity -> response
    private AccountResponse mapToResponse(Account account) {
        return AccountResponse.builder()
                .accountId(account.getAccountId())
                .accountUserName(account.getAccountUserName())
                .email(account.getEmail())
                .fullName(account.getFullName())
                .avatarUrl(account.getAvatarUrl())
                .roleName(account.getRole() != null ? account.getRole().getRoleName() : null)
                .accountCreateAt(account.getAccountCreateAt())
                .isDeleted(account.getDeleted())
                .build();
    }

    // mapping request -> entity
    private Account mapToEntity(AccountRequest request) {
        Role role = roleRepository.findById(request.getRoleId())
                .orElseThrow(() -> new RuntimeException("Role not found with id: " + request.getRoleId()));

        return Account.builder()
                .accountUserName(request.getAccountUserName())
                .email(request.getEmail())
                .passwordHash(request.getPasswordHash())
                .fullName(request.getFullName())
                .role(role)
                .deleted(false)
                .build();
    }

    @Override
    public LoginResponse login(LoginRequest request) {
        Account account = accountRepository.findByAccountUserNameAndDeletedFalse(request.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!account.getPasswordHash().equals(request.getPassword())) {
            throw new RuntimeException("Invalid password");
        }
        List<PermissionResponse> permissions = account.getRole().getRolePermissions()
                .stream()
                .filter(rp -> Boolean.FALSE.equals(rp.getPermission().getDeleted())) // chỉ lấy permission chưa xóa
                .map(rp -> PermissionResponse.builder()
                        .permissionId(rp.getPermission().getPermissionId())
                        .permissionName(rp.getPermission().getPermissionName())
                        .deleted(rp.getPermission().getDeleted())
                        .build())
                .collect(Collectors.toList());

        return LoginResponse.builder()
                .accountId(account.getAccountId())
                .username(account.getAccountUserName())
                .email(account.getEmail())
                .fullName(account.getFullName())
                .avatarUrl(account.getAvatarUrl())
                .role(account.getRole() != null ? account.getRole().getRoleName() : null)
                .permissions(permissions)
                .message("Login successful")
                .build();
    }

    @Override
    public List<AccountResponse> getAllAccounts() {
        return accountRepository.findByDeletedFalse()
                .stream().map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public Page<AccountResponse> getAccountPaging(Pageable pageable) {
        return accountRepository.findByDeletedFalse(pageable)
                .map(this::mapToResponse);
    }

    @Override
    public AccountResponse getAccountById(Integer id) {
        Account account = accountRepository.findByAccountIdAndDeletedFalse(id)
                .orElseThrow(() -> new RuntimeException("Account not found with id: " + id));
        return mapToResponse(account);
    }

    @Override
    public AccountResponse createAccount(AccountRequest request, MultipartFile file) {
        Account account = mapToEntity(request);

        if (file != null && !file.isEmpty()) {
            try {
                String imageUrl = cloudinaryService.uploadFile(file);
                account.setAvatarUrl(imageUrl);
            } catch (IOException e) {
                throw new RuntimeException("Upload avatar failed: " + e.getMessage(), e);
            }
        }

        return mapToResponse(accountRepository.save(account));
    }

    @Override
    public AccountResponse updateAccount(Integer id, AccountRequest request, MultipartFile file) {
        Account existing = accountRepository.findByAccountIdAndDeletedFalse(id)
                .orElseThrow(() -> new RuntimeException("Account not found with id: " + id));

        Role role = roleRepository.findById(request.getRoleId())
                .orElseThrow(() -> new RuntimeException("Role not found with id: " + request.getRoleId()));

        existing.setAccountUserName(request.getAccountUserName());
        existing.setEmail(request.getEmail());
        existing.setPasswordHash(request.getPasswordHash());
        existing.setFullName(request.getFullName());
        existing.setRole(role);

        if (file != null && !file.isEmpty()) {
            try {
                String imageUrl = cloudinaryService.uploadFile(file);
                existing.setAvatarUrl(imageUrl);
            } catch (IOException e) {
                throw new RuntimeException("Upload avatar failed: " + e.getMessage(), e);
            }
        }

        return mapToResponse(accountRepository.save(existing));
    }

    @Override
    public void deleteAccount(Integer id) {
        Account account = accountRepository.findByAccountIdAndDeletedFalse(id)
                .orElseThrow(() -> new RuntimeException("Account not found with id: " + id));
        account.setDeleted(true);
        accountRepository.save(account);
    }

    @Override
    public void deleteAccountForever(Integer id) {
        if (!accountRepository.existsById(id)) {
            throw new RuntimeException("Account not found with id: " + id);
        }
        accountRepository.deleteById(id);
    }

    @Override
    public List<AccountResponse> searchAccountByName(String keyword) {
        return accountRepository.findByAccountUserNameContainingIgnoreCase(keyword)
                .stream()
                .filter(a -> Boolean.FALSE.equals(a.getDeleted()))
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }
}
