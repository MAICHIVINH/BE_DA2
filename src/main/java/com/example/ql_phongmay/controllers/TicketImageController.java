package com.example.ql_phongmay.controllers;

import com.example.ql_phongmay.dto.request.TicketImageRequest;
import com.example.ql_phongmay.dto.response.TicketImageResponse;
import com.example.ql_phongmay.services.TicketImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@CrossOrigin(
        origins = {
                "http://localhost:5173",
                "http://localhost:5174",
                "http://localhost:5175",
                "http://localhost:5176",
                "http://localhost:5177",
                "http://localhost:5178",
                "http://localhost:5179"
        },
        methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE},
        allowedHeaders = "*"
)
@RestController
@RequestMapping("/api/ticket-image")
@RequiredArgsConstructor
public class TicketImageController {
    private final TicketImageService ticketImageService;

    @GetMapping
    public List<TicketImageResponse> getAll() {
        return ticketImageService.getAllImage();
    }

    @GetMapping("/{id}")
    public TicketImageResponse getById(@PathVariable Integer id) {
        return ticketImageService.getImageById(id);
    }

    @GetMapping("/ticket/{ticketId}")
    public List<TicketImageResponse> getByTicket(@PathVariable Integer ticketId) {
        return ticketImageService.getImagesByTicketId(ticketId);
    }

    @PostMapping
    public TicketImageResponse create(@RequestBody TicketImageRequest request) {
        return ticketImageService.createImage(request);
    }

    @PutMapping("/{id}")
    public TicketImageResponse update(@PathVariable Integer id,
                                      @RequestBody TicketImageRequest request) {
        return ticketImageService.updateImage(id, request);
    }

    @DeleteMapping("/soft/{id}")
    public String softDelete(@PathVariable Integer id) {
        ticketImageService.softDeleteImage(id);
        return "Xóa mềm thành công";
    }

    @DeleteMapping("/hard/{id}")
    public String hardDelete(@PathVariable Integer id) {
        ticketImageService.hardDeleteImage(id);
        return "Xóa vĩnh viễn thành công";
    }

//    @PostMapping("/upload/{ticketId}")
//    public TicketImageResponse uploadOne(
//            @PathVariable Integer ticketId,
//            @RequestParam("file") MultipartFile file
//    ) throws Exception {
//        return ticketImageService.uploadImageForTicket(ticketId, file);
//    }

    @PostMapping("/upload")
    public List<TicketImageResponse> uploadImages(
            @RequestParam Integer ticketId,
            @RequestParam("files") MultipartFile[] files
    ) throws Exception {
        return ticketImageService.uploadMultipleImages(ticketId, files);
    }
}
