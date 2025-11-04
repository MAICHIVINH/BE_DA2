package com.example.ql_phongmay.services.implement;

import com.example.ql_phongmay.dto.request.RoomRequest;
import com.example.ql_phongmay.dto.response.RoomResponse;
import com.example.ql_phongmay.entities.Room;
import com.example.ql_phongmay.entities.RoomStatus;
import com.example.ql_phongmay.repositories.RoomRepository;
import com.example.ql_phongmay.repositories.RoomStatusRepository;
import com.example.ql_phongmay.services.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {
    private final RoomRepository roomRepository;
    private final RoomStatusRepository roomStatusRepository;

    // Entity -> Response
    private RoomResponse mapToResponse(Room room) {
        return RoomResponse.builder()
                .roomId(room.getRoomId())
                .roomCode(room.getRoomCode())
                .roomName(room.getRoomName())
                .roomLocation(room.getRoomLocation())
                .roomCapacity(room.getRoomCapacity())
                .roomDescription(room.getRoomDescription())
                .roomStatusName(room.getRoomStatus() != null ? room.getRoomStatus().getRoomStatusName() : null)
                .isDeleted(room.getDeleted())
                .build();
    }

    // Request -> Entity
    private Room mapToEntity(RoomRequest request) {
        RoomStatus roomStatus = roomStatusRepository.findById(request.getRoomStatusId())
                .orElseThrow(() -> new RuntimeException("Role not found with id: " + request.getRoomStatusId()));
        return Room.builder()
                .roomCode(request.getRoomCode())
                .roomName(request.getRoomName())
                .roomLocation(request.getRoomLocation())
                .roomCapacity(request.getRoomCapacity())
                .roomDescription(request.getRoomDescription())
                .roomStatus(roomStatus)
                .deleted(false)
                .build();
    }
    @Override
    public List<RoomResponse> getAllRoom() {
        return roomRepository.findByDeletedFalse()
                .stream().map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public Page<RoomResponse> getRoomPaging(Pageable pageable) {
        return roomRepository.findAll(pageable).map(this::mapToResponse);
    }

    @Override
    public Optional<RoomResponse> getRoomById(Integer id) {
        return roomRepository.findById(id)
                .map(this::mapToResponse);
    }

    @Override
    public RoomResponse createRoom(RoomRequest request) {
        Integer statusId = (request.getRoomStatusId() == null) ? 1 : request.getRoomStatusId();

        RoomStatus roomStatus = roomStatusRepository.findById(statusId)
                .orElseThrow(() -> new RuntimeException("RoomStatus not found with id: " + statusId));
        Room room = Room.builder()
                .roomCode(request.getRoomCode())
                .roomName(request.getRoomName())
                .roomLocation(request.getRoomLocation())
                .roomCapacity(request.getRoomCapacity())
                .roomDescription(request.getRoomDescription())
                .roomStatus(roomStatus)
                .deleted(false)
                .build();

        return mapToResponse(roomRepository.save(room));
    }

    @Override
    public RoomResponse updateRoom(Integer id, RoomRequest request) {
        Room existing = roomRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Room not found with id: " + id));

        RoomStatus roomStatus = roomStatusRepository.findById(request.getRoomStatusId())
                .orElseThrow(() -> new RuntimeException("Role not found with id: " + request.getRoomStatusId()));

        //cập nhật từng field
        existing.setRoomCode(request.getRoomCode());
        existing.setRoomName(request.getRoomName());
        existing.setRoomLocation(request.getRoomLocation());
        existing.setRoomCapacity(request.getRoomCapacity());
        existing.setRoomDescription(request.getRoomDescription());
        existing.setRoomStatus(roomStatus);
        return mapToResponse(roomRepository.save(existing));
    }

    @Override
    public void deleteRoom(Integer id) {
        Room room = roomRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Room không tồn tại"));

        room.setDeleted(true); // đánh dấu là đã xóa
        roomRepository.save(room);
    }

    @Override
    public void deleteRoomForever(Integer id) {
        if(!roomRepository.existsById(id)) {
            throw new RuntimeException("Room not found with id: " + id);
        }
        roomRepository.deleteById(id);
    }

    @Override
    public List<RoomResponse> searchRoomByRoomName(String keyword) {
        return roomRepository.findByRoomNameContainingIgnoreCase(keyword)
                .stream()
                .filter(a -> Boolean.FALSE.equals(a.getDeleted()))
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }
}
