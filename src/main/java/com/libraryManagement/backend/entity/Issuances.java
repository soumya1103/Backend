package com.libraryManagement.backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "issuances")
@Setter @Getter @ToString @NoArgsConstructor @AllArgsConstructor
public class Issuances {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "issuance_id")
    private int issuanceId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private Users users;

    @ManyToOne
    @JoinColumn(name = "book_id", nullable = false)
    private Books books;

    @Column(name = "issue_date")
    private LocalDateTime issueDate;

    @Column(name = "return_date")
    private LocalDateTime returnDate;

    @Column(name = "status")
    private String status;

    @Column(name = "issuance_type")
    private String issuanceType;
}
