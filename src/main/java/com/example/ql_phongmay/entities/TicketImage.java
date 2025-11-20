//package com.example.ql_phongmay.entities;
//
//import jakarta.persistence.*;
//import lombok.*;
//
//@Entity
//@Table(name = "ticket_images")
//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//@Builder
//public class TicketImage {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "ticket_image_id")
//    private Integer ticketImageId;
//
//    @Column(name = "image_url")
//    private String imageUrl;
//
//    @ManyToOne
//    @JoinColumn(name = "ticket_id")
//    private Ticket ticket;
//
//    @Column(name = "is_main")
//    private Boolean main;
//
//    @Column(name = "is_deleted", nullable = false)
//    private Boolean deleted = false;
//}
