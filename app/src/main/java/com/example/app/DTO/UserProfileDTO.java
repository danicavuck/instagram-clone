package com.example.app.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserProfileDTO {

    private String username;
    private String description;
    private int postsCount;
    private int followerCount;
    private int followingCount;

}
