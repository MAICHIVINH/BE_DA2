package com.example.ql_phongmay.services.implement;

import com.example.ql_phongmay.dto.request.BookingRequest;
import com.example.ql_phongmay.dto.response.BookingResponse;
import com.example.ql_phongmay.entities.*;
import com.example.ql_phongmay.repositories.BookingRepository;
import com.example.ql_phongmay.repositories.BookingStatusRepository;
import com.example.ql_phongmay.repositories.RoomRepository;
import com.example.ql_phongmay.repositories.UserRepository;
import com.example.ql_phongmay.services.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {
    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final RoomRepository roomRepository;
    private final BookingStatusRepository bookingStatusRepository;

    // mapping entity -> response
    private BookingResponse mapToResponse(Booking booking) {
        return BookingResponse.builder()
                .bookingId(booking.getBookingId())
                .userName(booking.getUser() != null ? booking.getUser().getUserName() : null)
                .roomName(booking.getRoom() != null ? booking.getRoom().getRoomName() : null)
                .bookingStatusName(booking.getBookingStatus() != null ? booking.getBookingStatus().getBookingStatusName() : null)
                .startTime(booking.getStartTime())
                .endTime(booking.getEndTime())
                .purpose(booking.getPurpose())
                .bookingCreateAt(booking.getBookingCreateAt())
                .isDeleted(booking.getDeleted())
                .build();
    }

    // mapping request -> entity
    private Booking mapToEntity(BookingRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found with id: " + request.getUserId()));
        Room room = roomRepository.findById(request.getRoomId())
                .orElseThrow(() -> new RuntimeException("Room not found with id: " + request.getRoomId()));
        BookingStatus bookingStatus;
        if (request.getBookingStatusId() == null) {
            bookingStatus = bookingStatusRepository.findById(1)
                    .orElseThrow(() -> new RuntimeException("Default BookingStatus (id=1) not found"));
        } else {
            bookingStatus = bookingStatusRepository.findById(request.getBookingStatusId())
                    .orElseThrow(() -> new RuntimeException("BookingStatus not found with id: " + request.getBookingStatusId()));
        }

        return Booking.builder()
                .user(user)
                .room(room)
                .bookingStatus(bookingStatus)
                .startTime(request.getStartTime())
                .endTime(request.getEndTime())
                .purpose(request.getPurpose())
                .deleted(false)
                .build();
    }

    @Override
    public List<BookingResponse> getAllBooking() {
        return bookingRepository.findByDeletedFalse()
                .stream().map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public Page<BookingResponse> getBookingPaging(Pageable pageable) {
        return bookingRepository.findByDeletedFalse(pageable)
                .map(this::mapToResponse);
    }

//    @Override
//    public BookingResponse getBookingById(Integer id) {
//        Booking booking = BookingResponse.findByBookingIdAndDeletedFalse(id)
//                .orElseThrow(() -> new RuntimeException("Account not found with id: " + id));
//        return mapToResponse(account);
//    }

    @Override
    public BookingResponse createBooking(BookingRequest request) {
        Booking booking = mapToEntity(request);
        return mapToResponse(bookingRepository.save(booking));
    }

    @Override
    public BookingResponse updateBooking(Integer id, BookingRequest request) {
        Booking existing = bookingRepository.findByBookingIdAndDeletedFalse(id)
                .orElseThrow(() -> new RuntimeException("Booking not found with id: " + id));

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found with id: " + request.getUserId()));
        Room room = roomRepository.findById(request.getRoomId())
                        .orElseThrow(() -> new RuntimeException("Room not found with id: " + request.getRoomId()));
        BookingStatus bookingStatus = bookingStatusRepository.findById(request.getBookingStatusId())
                        .orElseThrow(()-> new RuntimeException("BookingStatus not found with id: " + request.getBookingStatusId()));

        existing.setUser(user);
        existing.setRoom(room);
        existing.setBookingStatus(bookingStatus);
        existing.setStartTime(request.getStartTime());
        existing.setEndTime(request.getEndTime());
        existing.setPurpose(request.getPurpose());

        return mapToResponse(bookingRepository.save(existing));
    }

    @Override
    public void deleteBooking(Integer id) {
        Booking booking = bookingRepository.findByBookingIdAndDeletedFalse(id)
                .orElseThrow(() -> new RuntimeException("Booking not found with id: " + id));
        booking.setDeleted(true);
        bookingRepository.save(booking);
    }

    @Override
    public void deleteBookingForever(Integer id) {
        if (!bookingRepository.existsById(id)) {
            throw new RuntimeException("Booking not found with id: " + id);
        }
        bookingRepository.deleteById(id);
    }

    // Duyệt đặt phòng (Admin)
    public BookingResponse approveBooking(Integer id) {
        Booking booking = bookingRepository.findByBookingIdAndDeletedFalse(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy booking id: " + id));

        BookingStatus approvedStatus = bookingStatusRepository.findById(2)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy trạng thái 'ĐÃ DUYỆT'"));

        booking.setBookingStatus(approvedStatus);
        bookingRepository.save(booking);
        return mapToResponse(booking);
    }

    // Hủy đặt phòng (User hoặc Admin)
    public BookingResponse cancelBooking(Integer id) {
        Booking booking = bookingRepository.findByBookingIdAndDeletedFalse(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy booking id: " + id));

        BookingStatus canceledStatus = bookingStatusRepository.findById(5)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy trạng thái 'ĐÃ HỦY'"));

        booking.setBookingStatus(canceledStatus);
        bookingRepository.save(booking);
        return mapToResponse(booking);
    }

    // Cập nhật tự động trạng thái theo thời gian
    public void autoUpdateBookingStatus() {
        LocalDateTime now = LocalDateTime.now();

        BookingStatus approved = bookingStatusRepository.findById(2).orElse(null); // ĐÃ DUYỆT
        BookingStatus using = bookingStatusRepository.findById(3).orElse(null);    // ĐANG MƯỢN
        BookingStatus done = bookingStatusRepository.findById(4).orElse(null);     // HOÀN THÀNH

        if (approved != null && using != null) {
            List<Booking> toStart = bookingRepository.findByBookingStatus(approved);
            for (Booking booking : toStart) {
                if (now.isAfter(booking.getStartTime())) {
                    booking.setBookingStatus(using);
                    bookingRepository.save(booking);
                }
            }
        }

        if (using != null && done != null) {
            List<Booking> toFinish = bookingRepository.findByBookingStatus(using);
            for (Booking booking : toFinish) {
                if (now.isAfter(booking.getEndTime())) {
                    booking.setBookingStatus(done);
                    bookingRepository.save(booking);
                }
            }
        }
    }

    @Override
    public Page<BookingResponse> getBookingPagingByUser(Integer userId, Pageable pageable) {
        return bookingRepository.findByUser_UserIdAndDeletedFalse(userId, pageable)
                .map(this::mapToResponse);
    }



}