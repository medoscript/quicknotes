package com.example.quicknotes.service.interfaces;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {

    String upload (MultipartFile file, String productTitle);

}
