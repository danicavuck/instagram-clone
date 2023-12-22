package com.example.app.Repository;


import com.example.app.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);

    User findByEmail(String email);

    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);


}
