package com.example.ql_phongmay.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "tickets")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ticket_id")
    private Integer ticketId;

    @Column(name = "title", nullable = false, unique = true, length = 50)
    private String ticketTitle;

    @Column(name = "description")
    private String ticketDescription;

    @Column(name = "image_url")
    private String imageUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "device_id", nullable = false)
    private Device device;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id", nullable = false)
    private Room room;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "priority_id", nullable = false)
    private TicketPriority ticketPriority;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ticket_status_id", nullable = false)
    private TicketStatus ticketStatus;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime ticketCreateAt;

    @Column(name = "updated_at")
    private LocalDateTime ticketUpdatedAt;

    @Column(name = "is_deleted")
    private Boolean deleted;

//    @OneToMany(mappedBy = "tickets", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<TicketImage> images = new ArrayList<>();

    @PrePersist
    protected void onCreate() {
        this.ticketCreateAt = LocalDateTime.now();
        if (this.deleted == null) {
            this.deleted = false;
        }
    }

    @PreUpdate
    protected void onUpdate() {
        this.ticketUpdatedAt = LocalDateTime.now();
    }
}
