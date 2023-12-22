package com.example.app.Model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Builder
@ToString
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    //User umesto Stringa
    private String comment;
    private LocalDateTime timestamp  = LocalDateTime.now();

    @ManyToOne
    private Post post;


}
