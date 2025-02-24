package com.example.authservice.repository;

import com.example.authservice.entity.Resource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Set;

public interface ResourceRepository extends JpaRepository<Resource, Long> {
    @Query("select r from Resource r where r.roleId in (:roleId)")
    Set<Resource> getResourcesByRoleIds(List<Long> roleId);
}
