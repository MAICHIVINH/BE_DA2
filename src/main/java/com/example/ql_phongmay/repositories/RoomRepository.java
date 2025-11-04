package com.example.ql_phongmay.repositories;

import com.example.ql_phongmay.entities.Room;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RoomRepository extends JpaRepository<Room, Integer> {
    List<Room> findByDeletedFalse();
    List<Room> findByRoomNameContainingIgnoreCase(String keyword);
    Page<Room> findByDeletedFalse(Pageable pageable);
    Optional<Room> findByRoomIdAndDeletedFalse(Integer id);
}
