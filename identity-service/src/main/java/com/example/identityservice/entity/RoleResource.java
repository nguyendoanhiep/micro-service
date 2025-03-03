package com.example.identityservice.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "role_resource")
public class RoleResource {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "role_id")
    private Long roleId;

    @Column(name = "resource_id")
    private Long resourceId;
}
