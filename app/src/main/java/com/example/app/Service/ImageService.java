package com.example.app.Service;

import com.example.app.DTO.PhotoDTO;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.Objects;

@Service
public class ImageService {

    public String UPLOAD_DIRECTORY = System.getProperty("user.dir") + "\\src\\main\\java\\com\\example\\app\\Uploads";

    public ImageService(){}


    public PhotoDTO uploadImage(MultipartFile file) throws IOException {
        Path fileNameAndPath = Paths.get(UPLOAD_DIRECTORY, file.getOriginalFilename());
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        Files.write(fileNameAndPath, file.getBytes());
        return PhotoDTO.builder().path(String.valueOf(fileNameAndPath)).fileName(fileName).file(file).build();
    }

}
