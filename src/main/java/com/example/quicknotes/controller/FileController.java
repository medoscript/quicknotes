package com.example.quicknotes.controller;


import com.example.quicknotes.exception_handling.Response;
import com.example.quicknotes.service.interfaces.FileService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/files")
public class FileController {

    private FileService service;

    public FileController(FileService service) {
        this.service = service;
    }


    @PostMapping
    public Response upload (
            @RequestParam MultipartFile file,
            @RequestParam String productTitle
    ){

        return new Response("File saved as" + service.upload(file, productTitle));
    }
}
