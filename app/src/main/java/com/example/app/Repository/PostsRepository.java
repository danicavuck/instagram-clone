package com.example.app.Repository;

import com.example.app.Model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostsRepository extends JpaRepository<Post,Long> {

    Post findPostById(Long id);


}
