package com.example.ql_phongmay.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TicketRequest {
    @NotBlank(message = "Tiêu đề không được để trống")
    @Size(max = 255, message = "Tiêu đề không vượt quá 255 ký tự")
    private String ticketTitle;

    private String ticketDescription;

    @NotNull(message = "Người báo lỗi không được để trống")
    private Integer userId;

    private Integer accountId;

    @NotNull(message = "Thiết bị lỗi không được để trống")
    private Integer deviceId;

    @NotNull(message = "Phòng máy không được để trống")
    private Integer roomId;

    private Integer priorityId;
    private Integer ticketStatusId;
}
