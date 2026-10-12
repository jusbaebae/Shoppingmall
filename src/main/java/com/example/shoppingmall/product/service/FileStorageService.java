package com.example.shoppingmall.product.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileStorageService {

    private final Path uploadPath = Paths.get("uploads/products");

    public String saveFile(MultipartFile file){
        if(file == null || file.isEmpty()){
            throw new IllegalArgumentException("이미지 파일은 필수입니다.");
        }

        try {
            Files.createDirectories(uploadPath);

            String originalFilename = file.getOriginalFilename();

            String extension = "";

            if(originalFilename != null && originalFilename.contains(".")){
                extension = originalFilename.substring(originalFilename.lastIndexOf("."));
            }

            String savedFilename = UUID.randomUUID() + extension;

            Path targetPath = uploadPath.resolve(savedFilename);

            file.transferTo(targetPath);

            return "/uploads/products/" + savedFilename;
        } catch (IOException e){
            throw new RuntimeException("파일 저장에 실패했습니다.", e);
        }
    }

    public void deleteFile(String fileUrl){
        if(fileUrl == null || fileUrl.isBlank()){
            return;
        }
        try{
            String filename = Paths.get(fileUrl).getFileName().toString();

            Path filePath = uploadPath.resolve(filename);

            //파일이 존재하는 경우에만 삭제
            Files.deleteIfExists(filePath);

        } catch (IOException e){
            throw new RuntimeException("파일 삭제에 실패했습니다.", e);
        }
    }
}
