package com.example.authservice.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Set;

@Entity
@Data
@Table(name = "user")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "number_phone", unique = true)
    private String numberPhone;

    @Column(name = "status")
    private Integer status;

    @Column(name = "create_date")
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private Date createDate;

    @Column(name = "modified_date")
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private Date modifiedDate;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_role",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id")})
    private Set<Role> roles;
}