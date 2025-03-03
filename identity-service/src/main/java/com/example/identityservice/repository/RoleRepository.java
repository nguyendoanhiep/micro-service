package com.example.identityservice.repository;

import com.example.identityservice.entity.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(String roleName);

    @Query("select r from Role r where (:name is null or r.name = :name) and (:status is null or r.status = :status) ")
    Page<Role>getAll(Pageable pageable , String name , Integer status);
}
