package com.example.authservice.repository;

import com.example.authservice.entity.Resource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Set;

public interface ResourceRepository extends JpaRepository<Resource, Long> {
    @Query("select re from Resource re " +
            "join RoleResource rr on re.id = rr.resourceId " +
            "join Role r on r.id = rr.roleId " +
            "where r.id in (:roleIds)")
    Set<Resource> getResourcesByRoleIds(Set<Long> roleIds);
}
