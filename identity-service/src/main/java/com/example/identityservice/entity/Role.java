package com.example.identityservice.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Entity
@Data
@Table(name = "role")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name",length = 60)
    private String name;

    @Column(name = "status",length = 60)
    private String status;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "role_resource",
            joinColumns = {@JoinColumn(name = "role_id")},
            inverseJoinColumns = {@JoinColumn(name = "resource_id")})
    private Set<Resource> resources;

}