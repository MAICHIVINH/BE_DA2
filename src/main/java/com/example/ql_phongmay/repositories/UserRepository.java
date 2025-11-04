package com.example.ql_phongmay.repositories;

import com.example.ql_phongmay.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    List<User> findByDeletedFalse();
    List<User> findByUserNameContainingIgnoreCase(String keyword);
    Page<User> findByDeletedFalse(Pageable pageable);
    Optional<User> findByUserIdAndDeletedFalse(Integer id);
    Optional<User> findByUserNameAndDeletedFalse(String userName);
    Optional<User> findByUserEmailAndDeletedFalse(String email);
}
