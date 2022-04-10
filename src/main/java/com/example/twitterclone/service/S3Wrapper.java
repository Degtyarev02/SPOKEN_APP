package com.example.twitterclone.service;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;

@Service
public class S3Wrapper {

    private String uploadFile(File file, String fileName) throws IOException {
        BlobId blobId = BlobId.of("spoken-3c30d.appspot.com", fileName);
        String[] parts = fileName.split("\\.");
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType("image/" + parts[parts.length - 1]).build();
        GoogleCredentials credentials = GoogleCredentials.fromStream(new FileInputStream("src/main/resources/spoken-3c30d-firebase-adminsdk-wgzuf-def0cfc076.json"));
        Storage storage = StorageOptions.newBuilder().setCredentials(credentials).build().getService();
        storage.create(blobInfo, Files.readAllBytes(file.toPath()));
        return "gs://spoken-3c30d.appspot.com/";
    }

    private File convertToFile(MultipartFile multipartFile, String fileName) throws IOException {
        File tempFile = new File(fileName);
        try (FileOutputStream fos = new FileOutputStream(tempFile)) {
            fos.write(multipartFile.getBytes());
        }
        return tempFile;
    }

    //Метод загрузки фоток на aws s3
    public void upload(MultipartFile multipartFile, String fileName) {
        try {
            File file = this.convertToFile(multipartFile, fileName);                      // to convert multipartFile to File
            String TEMP_URL = this.uploadFile(file, fileName);                                   // to get uploaded file link
            file.delete();                                                                // to delete the copy of uploaded file stored in the project folder// Your customized response
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
