package com.example.ql_phongmay.repositories;

import com.example.ql_phongmay.entities.Account;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Integer> {
    List<Account> findByDeletedFalse();
    Page<Account> findByDeletedFalse(Pageable pageable);
    Optional<Account> findByAccountIdAndDeletedFalse(Integer id);
    List<Account> findByAccountUserNameContainingIgnoreCase(String keyword);
    Optional<Account> findByAccountUserNameAndDeletedFalse(String accountUserName);
}
