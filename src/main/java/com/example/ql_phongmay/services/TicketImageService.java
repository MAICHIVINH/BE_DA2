package com.example.ql_phongmay.services;

import com.example.ql_phongmay.dto.request.TicketImageRequest;
import com.example.ql_phongmay.dto.response.TicketImageResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface TicketImageService {
    List<TicketImageResponse> getAllImage();
    Page<TicketImageResponse> getImagePaging(Pageable pageable);
    TicketImageResponse getImageById(Integer id);
    List<TicketImageResponse> getImagesByTicketId(Integer ticketId);
    TicketImageResponse createImage(TicketImageRequest request);
    TicketImageResponse updateImage(Integer id, TicketImageRequest request);
    void softDeleteImage(Integer id);
    void hardDeleteImage(Integer id);
//    // Upload 1 ảnh
//    TicketImageResponse uploadImageForTicket(Integer ticketId, MultipartFile file) throws Exception;
    // Upload nhiều ảnh
    List<TicketImageResponse> uploadMultipleImages(Integer ticketId, MultipartFile[] files) throws Exception;
}
