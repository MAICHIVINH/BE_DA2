package com.example.ql_phongmay.services.implement;

import com.example.ql_phongmay.dto.request.RoomStatusRequest;
import com.example.ql_phongmay.dto.response.RoomStatusResponse;
import com.example.ql_phongmay.entities.RoomStatus;
import com.example.ql_phongmay.repositories.RoomStatusRepository;
import com.example.ql_phongmay.services.RoomStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoomStatusServiceImpl implements RoomStatusService {
    private final RoomStatusRepository roomStatusRepository;

    //Entity -> Response
    private RoomStatusResponse mapToResponse(RoomStatus roomStatus) {
        return RoomStatusResponse.builder()
                .roomStatusId(roomStatus.getRoomStatusId())
                .roomStatusName(roomStatus.getRoomStatusName())
                .isDeleted(roomStatus.getDeleted())
                .build();
    }

    //Request -> Entity
    private RoomStatus mapToEntity(RoomStatusRequest request) {
        return RoomStatus.builder()
                .roomStatusName(request.getRoomStatusName())
                .deleted(false)
                .build();
    }

    @Override
    public List<RoomStatusResponse> getAllRoomStatus() {
        return roomStatusRepository.findByDeletedFalse()
                .stream().map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public Page<RoomStatusResponse> getRoomStatusPaging(Pageable pageable) {
        return roomStatusRepository.findAll(pageable).map(this::mapToResponse);
    }

    @Override
    public Optional<RoomStatusResponse> getRoomStatusById(Integer id) {
        return roomStatusRepository.findById(id)
                .map(this::mapToResponse);
    }

    @Override
    public RoomStatusResponse createRoomStatus(RoomStatusRequest roomStatusRequest) {
        RoomStatus roomStatus = mapToEntity(roomStatusRequest);
        return mapToResponse(roomStatusRepository.save(roomStatus));
    }

    @Override
    public RoomStatusResponse updateRoomStatus(Integer id, RoomStatusRequest roomStatusRequest) {
        RoomStatus existing = roomStatusRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("RoomStatus not found with id: " + id));

        //cập nhật từng field
        existing.setRoomStatusName(roomStatusRequest.getRoomStatusName());
        return mapToResponse(roomStatusRepository.save(existing));
    }

    @Override
    public void deleteRoomStatus(Integer id) {
        RoomStatus roomStatus = roomStatusRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("RoomStatus không tồn tại"));

        roomStatus.setDeleted(true); // đánh dấu là đã xóa
        roomStatusRepository.save(roomStatus);
    }

    @Override
    public void deleteRoomStatusForever(Integer id) {
        if(!roomStatusRepository.existsById(id)) {
            throw new RuntimeException("RoomStatus not found with id: " + id);
        }
        roomStatusRepository.deleteById(id);
    }
}
