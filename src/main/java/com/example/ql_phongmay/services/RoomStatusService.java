package com.example.ql_phongmay.services;

import com.example.ql_phongmay.dto.request.RoomStatusRequest;
import com.example.ql_phongmay.dto.response.RoomStatusResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface RoomStatusService {
    List<RoomStatusResponse> getAllRoomStatus();
    Page<RoomStatusResponse> getRoomStatusPaging(Pageable pageable);
    Optional<RoomStatusResponse> getRoomStatusById(Integer id);
    RoomStatusResponse createRoomStatus(RoomStatusRequest roomStatusRequest);
    RoomStatusResponse updateRoomStatus(Integer id,RoomStatusRequest roomStatusRequest);
    void deleteRoomStatus(Integer id);
    void deleteRoomStatusForever(Integer id);
}
