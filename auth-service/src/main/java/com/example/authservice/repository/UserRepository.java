package com.example.authservice.repository;

import com.example.authservice.dto.response.UserResponse;
import com.example.authservice.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    @Query("select new com.example.authservice.dto.response.UserResponse(u.id,u.username,u.numberPhone,u.status,u.createDate,u.modifiedDate)from User u " +
            "where (:search is null or u.username like %:search% ) " +
            "and (:search is null or u.numberPhone like :search) " +
            "and u.status = :status")
    Page<UserResponse> getAll(Pageable pageable, String search , Integer status);

}