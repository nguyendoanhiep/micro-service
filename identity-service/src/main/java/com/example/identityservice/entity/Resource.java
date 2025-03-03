package com.example.identityservice.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "resource")
public class Resource {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "parentId")
    private Long parentId;

    @Column(name = "status")
    private Integer status;

    @Column(name = "path")
    private String path;

    @Column(name = "method")
    private String method;
}
