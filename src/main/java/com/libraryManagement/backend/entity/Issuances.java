package com.libraryManagement.backend.entity;

import com.libraryManagement.backend.dto.UsersOutDto;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "issuances")
@Setter @Getter @ToString @NoArgsConstructor @AllArgsConstructor
public class Issuances {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "issuance_id")
    private int issuanceId;

    @JoinColumn(name = "user_id", nullable = false)
    private Users userId;

    @JoinColumn(name = "book_id", nullable = false)
    private Books bookId;

    @Column(name = "issue_date")
    private LocalDateTime issueDate;

    @Column(name = "return_date")
    private LocalDateTime returnDate;

    @Column(name = "status")
    private String status;

    @Column(name = "issuance_type")
    private String issuanceType;
}
