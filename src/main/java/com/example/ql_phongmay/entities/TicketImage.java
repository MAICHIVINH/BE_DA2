package com.example.ql_phongmay.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "ticket_images")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TicketImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ticket_image_id")
    private Integer ticketImageId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ticket_id", nullable = false)
    private Ticket ticket;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "uploaded_at", nullable = false, updatable = false)
    private LocalDateTime imageUploadAt;

    @Column(name = "is_deleted")
    private Boolean deleted;

    @PrePersist
    protected void onCreate() {
        this.imageUploadAt = LocalDateTime.now();
        if (this.deleted == null) {
            this.deleted = false;
        }
    }
}
