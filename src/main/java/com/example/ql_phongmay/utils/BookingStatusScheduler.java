package com.example.ql_phongmay.utils;

import com.example.ql_phongmay.services.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BookingStatusScheduler {
    private final BookingService bookingService;

    // Chạy mỗi phút để cập nhật trạng thái tự động
    @Scheduled(fixedRate = 60000)
    public void autoUpdateStatus() {
        bookingService.autoUpdateBookingStatus();
    }
}
