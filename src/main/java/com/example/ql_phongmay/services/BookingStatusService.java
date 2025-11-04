package com.example.ql_phongmay.services;

import com.example.ql_phongmay.dto.request.BookingStatusRequest;
import com.example.ql_phongmay.dto.response.BookingStatusResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface BookingStatusService {
    List<BookingStatusResponse> getAllBookingStatus();
    Page<BookingStatusResponse> getBookingStatusPaging(Pageable pageable);
    Optional<BookingStatusResponse> getBookingStatusById(Integer id);
    BookingStatusResponse createBookingStatus(BookingStatusRequest bookingStatusRequest);
    BookingStatusResponse updateBookingStatus(Integer id, BookingStatusRequest bookingStatusRequest);
    void deleteBookingStatus(Integer id);
    void deleteBookingStatusForever(Integer id);
}
