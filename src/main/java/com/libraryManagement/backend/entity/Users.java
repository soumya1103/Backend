package com.libraryManagement.backend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "users")
@Setter @Getter @ToString @AllArgsConstructor @NoArgsConstructor
public class Users {

    @Id
    @Column(name = "user_id")
    private int userId;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "user_credential")
    private String userCredential;

    @Column(name = "password")
    private String password;

    @Column(name = "role")
    private String role;

}
