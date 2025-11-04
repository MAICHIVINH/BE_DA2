package com.example.ql_phongmay.services.implement;

import com.example.ql_phongmay.dto.request.LoginRequest;
import com.example.ql_phongmay.dto.request.UserRequest;
import com.example.ql_phongmay.dto.response.LoginUserResponse;
import com.example.ql_phongmay.dto.response.UserResponse;
import com.example.ql_phongmay.entities.User;
import com.example.ql_phongmay.repositories.UserRepository;
import com.example.ql_phongmay.services.UserService;
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
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final CloudinaryService cloudinaryService;
//    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    // Entity -> Response
    private UserResponse mapToResponse(User user) {
        return UserResponse.builder()
                .userId(user.getUserId())
                .userName(user.getUserName())
                .userEmail(user.getUserEmail())
                .userPassword(user.getUserPassword())
                .userFullName(user.getUserFullName())
                .avatarUrl(user.getAvatarUrl())
                .userCreateAt(user.getUserCreateAt())
                .isDeleted(user.getDeleted())
                .build();
    }

    // Request -> Entity
    private User mapToEntity(UserRequest request) {
        return User.builder()
                .userName(request.getUserName())
                .userEmail(request.getUserEmail())
                .userPassword(request.getUserPassword())
                .userFullName(request.getUserFullName())
                .deleted(false)
                .build();
    }

    @Override
    public LoginUserResponse login(LoginRequest request) {
        User user = userRepository.findByUserNameAndDeletedFalse(request.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!request.getPassword().equals(user.getUserPassword())) {
            throw new RuntimeException("Invalid password");
        }

        return LoginUserResponse.builder()
                .userId(user.getUserId())
                .username(user.getUserName())
                .email(user.getUserEmail())
                .fullName(user.getUserFullName())
                .avatarUrl(user.getAvatarUrl())
                .message("Login successful")
                .build();
    }

    @Override
    public List<UserResponse> getAllUser() {
        return userRepository.findByDeletedFalse()
                .stream().map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public Page<UserResponse> getUserPaging(Pageable pageable) {
        return userRepository.findByDeletedFalse(pageable)
                .map(this::mapToResponse);
    }

    @Override
    public UserResponse getUserById(Integer id) {
        User user = userRepository.findByUserIdAndDeletedFalse(id)
                .orElseThrow(() -> new RuntimeException("Admin not found with id: " + id));
        return mapToResponse(user);
    }

    @Override
    public UserResponse createUser(UserRequest request, MultipartFile file) {
        User user = mapToEntity(request);

        if (file != null && !file.isEmpty()) {
            try {
                String imageUrl = cloudinaryService.uploadFile(file);
                user.setAvatarUrl(imageUrl);
            } catch (IOException e) {
                throw new RuntimeException("Upload avatar failed: " + e.getMessage(), e);
            }
        }

        return mapToResponse(userRepository.save(user));
    }

    @Override
    public UserResponse updateUser(Integer id, UserRequest request, MultipartFile file) {
        User existing = userRepository.findByUserIdAndDeletedFalse(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));

        existing.setUserName(request.getUserName());
        existing.setUserEmail(request.getUserEmail());
        existing.setUserPassword(request.getUserPassword());
        existing.setUserFullName(request.getUserFullName());

        if (file != null && !file.isEmpty()) {
            try {
                String imageUrl = cloudinaryService.uploadFile(file);
                existing.setAvatarUrl(imageUrl);
            } catch (IOException e) {
                throw new RuntimeException("Upload avatar failed: " + e.getMessage(), e);
            }
        }

        return mapToResponse(userRepository.save(existing));
    }

    @Override
    public void deleteUser(Integer id) {
        User user = userRepository.findByUserIdAndDeletedFalse(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
        user.setDeleted(true);
        userRepository.save(user);
    }

    @Override
    public void deleteUserForever(Integer id) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("User not found with id: " + id);
        }
        userRepository.deleteById(id);
    }

    @Override
    public List<UserResponse> searchUserByUserName(String keyword) {
        return userRepository.findByUserNameContainingIgnoreCase(keyword)
                .stream()
                .filter(a -> Boolean.FALSE.equals(a.getDeleted()))
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }
}
