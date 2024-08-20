package com.libraryManagement.backend.repository;

import com.libraryManagement.backend.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsersRepository extends JpaRepository<Users, Integer> {

    List<Users> findByRole(String role);

}
