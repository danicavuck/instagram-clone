package com.example.app.Model;


import jakarta.persistence.*;

import lombok.*;

@Builder
@ToString
@Entity
@Data
@Table(name="user")
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @Column(name="user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="username")
    private String username;

    @Column(name="password")
    private String password;

    @Column(name="email")
    private String email;


}
