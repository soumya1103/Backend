package com.libraryManagement.backend.repository;

import com.libraryManagement.backend.entity.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsersRepository extends JpaRepository<Users, Integer> {

    Page<Users> findByRole(Pageable pageable, String role);

    @Query("SELECT COUNT(u) FROM Users u WHERE u.role = 'ROLE_USER'")
    long count();

    Users findByUserCredential(String userCredential);

    List<Users> findUserByRole(String roleUser);

    @Query("SELECT u FROM Users u WHERE u.userCredential like :keyword OR u.userName like :keyword")
    List<Users> findByUserCredentialOrUserNameContaining(@Param("keyword") String keywords);

}