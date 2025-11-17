package com.example.ql_phongmay.services.implement;

import com.example.ql_phongmay.dto.request.TicketImageRequest;
import com.example.ql_phongmay.dto.response.TicketImageResponse;
import com.example.ql_phongmay.entities.Ticket;
import com.example.ql_phongmay.entities.TicketImage;
import com.example.ql_phongmay.repositories.TicketImageRepository;
import com.example.ql_phongmay.repositories.TicketRepository;
import com.example.ql_phongmay.services.TicketImageService;
import com.example.ql_phongmay.utils.CloudinaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TicketImageServiceImpl implements TicketImageService {
    private final TicketImageRepository ticketImageRepository;
    private final CloudinaryService cloudinaryService;
    private final TicketRepository ticketRepository;

    private TicketImageResponse mapToResponse(TicketImage ticketImage) {
        return TicketImageResponse.builder()
                .ticketImageId(ticketImage.getTicketImageId())
                .imageUrl(ticketImage.getImageUrl())
                .ticketId(ticketImage.getTicket().getTicketId())
                .isMain(ticketImage.getMain())
                .isDeleted(ticketImage.getDeleted())
                .build();
    }

    private TicketImage mapToEntity(TicketImageRequest request, Ticket ticket) {
        return TicketImage.builder()
                .imageUrl(request.getImageUrl())
                .ticket(ticket)
                .main(request.getMain() != null ? request.getMain() : false)
                .deleted(false)
                .build();
    }

    @Override
    public List<TicketImageResponse> getAllImage() {
        return ticketImageRepository.findByDeletedFalse()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public Page<TicketImageResponse> getImagePaging(Pageable pageable) {
        return ticketImageRepository.findAll(pageable)
                .map(this::mapToResponse);
    }

    @Override
    public TicketImageResponse getImageById(Integer id) {
        TicketImage image = ticketImageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy hình ảnh"));
        return mapToResponse(image);
    }

    @Override
    public List<TicketImageResponse> getImagesByTicketId(Integer ticketId) {
        return ticketImageRepository.findByTicketTicketId(ticketId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public TicketImageResponse createImage(TicketImageRequest request) {
        Ticket ticket = ticketRepository.findById(request.getTicketId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy ticket"));

        TicketImage image = mapToEntity(request, ticket);
        return mapToResponse(ticketImageRepository.save(image));
    }

    @Override
    public TicketImageResponse updateImage(Integer id, TicketImageRequest request) {
        TicketImage image = ticketImageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy hình ảnh"));

        Ticket ticket = ticketRepository.findById(request.getTicketId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy ticket"));

        image.setTicket(ticket);
        image.setImageUrl(request.getImageUrl());
        image.setMain(request.getMain());

        return mapToResponse(ticketImageRepository.save(image));
    }

    @Override
    public void softDeleteImage(Integer id) {
        TicketImage image = ticketImageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy hình ảnh"));
        image.setDeleted(true);
        ticketImageRepository.save(image);
    }

    @Override
    public void hardDeleteImage(Integer id) {
        if (!ticketImageRepository.existsById(id)) {
            throw new RuntimeException("Không tìm thấy hình ảnh");
        }
        ticketImageRepository.deleteById(id);
    }

//    @Override
//    public TicketImageResponse uploadImageForTicket(Integer ticketId, MultipartFile file) throws Exception {
//        Ticket ticket = ticketRepository.findById(ticketId)
//                .orElseThrow(() -> new RuntimeException("Không tìm thấy ticket"));
//
//        String uploadedUrl = cloudinaryService.uploadFile(file);
//
//        TicketImage img = TicketImage.builder()
//                .ticket(ticket)
//                .imageUrl(uploadedUrl)
//                .main(false)
//                .deleted(false)
//                .build();
//
//        return mapToResponse(ticketImageRepository.save(img));
//    }

    @Override
    public List<TicketImageResponse> uploadMultipleImages(Integer ticketId, MultipartFile[] files) throws Exception {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy ticket"));

        if (files == null || files.length == 0) {
            throw new RuntimeException("Không có file nào được upload");
        }

        // kiểm tra xem ticket đã có ảnh chính hay chưa
        List<TicketImage> mainImages = ticketImageRepository.findByTicketAndMain(ticket, true);
        boolean hasMainImage = !mainImages.isEmpty();

        List<TicketImageResponse> responses = new java.util.ArrayList<>();

        for (int i = 0; i < files.length; i++) {
            MultipartFile file = files[i];

            // upload lên Cloudinary
            String imageUrl = cloudinaryService.uploadFile(file);

            // Nếu ticket chưa có ảnh chính thì lấy ảnh đầu tiên làm ảnh chính
            boolean isMain = false;
            if (!hasMainImage && i == 0) {
                isMain = true;
            }

            TicketImage ticketImage = TicketImage.builder()
                    .ticket(ticket)
                    .imageUrl(imageUrl)
                    .main(isMain)
                    .deleted(false)
                    .build();

            ticketImageRepository.save(ticketImage);
            responses.add(mapToResponse(ticketImage));
        }

        return responses;
    }
}
