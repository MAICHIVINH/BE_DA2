package com.example.ql_phongmay.services;

import com.example.ql_phongmay.dto.request.RoomRequest;
import com.example.ql_phongmay.dto.response.RoomResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface RoomService {
    List<RoomResponse> getAllRoom();
    Page<RoomResponse> getRoomPaging(Pageable pageable);
    Optional<RoomResponse> getRoomById(Integer id);
    RoomResponse createRoom(RoomRequest request);
    RoomResponse updateRoom(Integer id,RoomRequest request);
    void deleteRoom(Integer id);
    void deleteRoomForever(Integer id);
    List<RoomResponse> searchRoomByRoomName(String keyword);
}
