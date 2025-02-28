package com.example.authservice.repository;

import com.example.authservice.entity.RoleResource;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleResourceRepository extends JpaRepository<RoleResource,Long> {
}
