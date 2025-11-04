package com.example.ql_phongmay.services.implement;

import com.example.ql_phongmay.dto.request.BookingStatusRequest;
import com.example.ql_phongmay.dto.response.BookingStatusResponse;
import com.example.ql_phongmay.entities.BookingStatus;
import com.example.ql_phongmay.repositories.BookingStatusRepository;
import com.example.ql_phongmay.services.BookingStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.awt.print.Book;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookingStatusServiceImpl implements BookingStatusService {
    private final BookingStatusRepository bookingStatusRepository;

    //Entity -> Response
    private BookingStatusResponse mapToResponse(BookingStatus bookingStatus) {
        return BookingStatusResponse.builder()
                .bookingStatusId(bookingStatus.getBookingStatusId())
                .bookingStatusName(bookingStatus.getBookingStatusName())
                .isDeleted(bookingStatus.getDeleted())
                .build();
    }

    //Request -> Entity
    private BookingStatus mapToEntity(BookingStatusRequest request) {
        return BookingStatus.builder()
                .bookingStatusName(request.getBookingStatusName())
                .deleted(false)
                .build();
    }

    @Override
    public List<BookingStatusResponse> getAllBookingStatus() {
        return bookingStatusRepository.findByDeletedFalse()
                .stream().map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public Page<BookingStatusResponse> getBookingStatusPaging(Pageable pageable) {
        return bookingStatusRepository.findAll(pageable).map(this::mapToResponse);
    }

    @Override
    public Optional<BookingStatusResponse> getBookingStatusById(Integer id) {
        return bookingStatusRepository.findById(id)
                .map(this::mapToResponse);
    }

    @Override
    public BookingStatusResponse createBookingStatus(BookingStatusRequest bookingStatusRequest) {
        BookingStatus bookingStatus = mapToEntity(bookingStatusRequest);
        return mapToResponse(bookingStatusRepository.save(bookingStatus));
    }

    @Override
    public BookingStatusResponse updateBookingStatus(Integer id, BookingStatusRequest bookingStatusRequest) {
        BookingStatus existing = bookingStatusRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("BookingStatus not found with id: " + id));

        //cập nhật từng field
        existing.setBookingStatusName(bookingStatusRequest.getBookingStatusName());
        return mapToResponse(bookingStatusRepository.save(existing));
    }

    @Override
    public void deleteBookingStatus(Integer id) {
        BookingStatus bookingStatus = bookingStatusRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("BookingStatus không tồn tại"));

        bookingStatus.setDeleted(true); // đánh dấu là đã xóa
        bookingStatusRepository.save(bookingStatus);
    }

    @Override
    public void deleteBookingStatusForever(Integer id) {
        if(!bookingStatusRepository.existsById(id)) {
            throw new RuntimeException("BookingStatus not found with id: " + id);
        }
        bookingStatusRepository.deleteById(id);
    }
}
