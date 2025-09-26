package com.security.auth.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table() // if needed custom table name then use @Table(name="custom_table_name")
@AllArgsConstructor
@NoArgsConstructor
public class UserRole {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "role_id")
    private Long roleId;

    private String name;
}