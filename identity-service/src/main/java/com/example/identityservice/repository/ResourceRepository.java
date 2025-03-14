package com.example.identityservice.repository;

import com.example.identityservice.entity.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Set;

public interface ResourceRepository extends JpaRepository<Resource, Long> {
    @Query(value = "select re.* from resource re " +
            "join role_resource rr on re.id = rr.resource_id " +
            "join role r on r.id = rr.role_id " +
            "where r.id in (:roleIds)",nativeQuery = true)
    Set<Resource> getResourcesByRoleIds(Set<Long> roleIds);

    @Query("select r from Resource r where r.parentId is null")
    List<Resource> getResourceParent();

    List<Resource> getResourcesByParentId(Long parentId);

    @Query("select r from Resource r where r.id in (:ids)")
    Set<Resource> getResourcesByIds(Set<Long> ids);
}
