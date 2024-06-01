package com.example.quicknotes.service.interfaces;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.example.quicknotes.service.TaskManagerService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {

    private AmazonS3 client;
    private TaskManagerService taskService;

    public FileServiceImpl(AmazonS3 client, TaskManagerService productService){
        this.client = client;
        this.taskService = productService;
    }

    @Override
    public String upload(MultipartFile file, String productTitle) {

        try {

            String uniqueFileName = generateUniqueFileName(file);

//            Files.copy(file.getInputStream(), Path.of("C:\\Users\\BESPREDEL\\Desktop\\g_38_jp_shop\\files\\" +
//                    uniqueFileName));

            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType(file.getContentType());

            PutObjectRequest request = new PutObjectRequest(
                    "cohort-37-bucket", uniqueFileName, file.getInputStream(), metadata
            ).withCannedAcl(CannedAccessControlList.PublicRead);

            client.putObject(request);

            String url = client.getUrl("cohort-37-bucket", uniqueFileName).toString();

            taskService.attachImage(url, productTitle);

            return uniqueFileName;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String generateUniqueFileName(MultipartFile file){
        //banana.jpg -> banana-sjfdge-ewrwefg-wegdsgfd.jpg

        String sourceFileName = file.getOriginalFilename();
        int dotIndex = sourceFileName.lastIndexOf(".");
        String fileName = sourceFileName.substring(0, dotIndex);
        String extension = sourceFileName.substring(dotIndex);

        return String.format("%s-%s%s", fileName, UUID.randomUUID(), extension);
    }
}
