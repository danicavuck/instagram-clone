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
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
    private String email;

    @Column(nullable = true, length = 64)
    private String profileImage;

    @OneToMany
    private List<Post> posts = new ArrayList<>();

    @ManyToMany
    private List<User> followers = new ArrayList<>();
    @ManyToMany
    private List<User> following = new ArrayList<>();

    private String description;


    public int getPostCount(){
        return posts.size();
    }

    public int getFollowerCount(){
        return followers.size();
    }

    public int getFollowingCount(){
        return following.size();
    }

    public void addPost(Post post){
        this.posts.add(post);
    }


}
