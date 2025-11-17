package com.example.ql_phongmay.services;

import com.example.ql_phongmay.dto.request.BookingRequest;
import com.example.ql_phongmay.dto.response.BookingResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BookingService {
    List<BookingResponse> getAllBooking();
    Page<BookingResponse> getBookingPaging(Pageable pageable);
//    BookingResponse getBookingById(Integer id);
    BookingResponse createBooking(BookingRequest request);
    BookingResponse updateBooking(Integer id, BookingRequest request);
    void deleteBooking(Integer id);          // xóa mềm
    void deleteBookingForever(Integer id);   // xóa cứng

    BookingResponse approveBooking(Integer id);
    BookingResponse cancelBooking(Integer id);
    void autoUpdateBookingStatus();
    Page<BookingResponse> getBookingPagingByUser(Integer userId, Pageable pageable);
}
