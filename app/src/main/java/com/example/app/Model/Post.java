package com.example.app.Model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@ToString
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Post {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    private String caption;
    private int likeCount;
    //lista username-ova
    //lista usera koji su lajkovali post
    private String fileName;


    @OneToMany
    private List<Comment> comments = new ArrayList<>();


    public void addComment(Comment comment){
        this.comments.add(comment);
    }




}
