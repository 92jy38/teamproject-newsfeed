package com.sparta.newsfeed_project.domain.post.image;

import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Bucket;
import com.google.firebase.cloud.StorageClient;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

public class Storage {

    private String imageName;
    private String contentType;
    private InputStream image;
    private Bucket bucket;
    private Blob blob;

    public Storage(MultipartFile multipartFile) throws IOException {

        this.imageName = multipartFile.getName();
        this.contentType = multipartFile.getContentType();
        this.image = multipartFile.getInputStream();
    }

    public void upload() {

        bucket = StorageClient.getInstance().bucket();
        blob = bucket.create(imageName, image, contentType);

    }

    public String download() {

        String downloadToken = UUID.randomUUID().toString();
        return String.format("https://firebasestorage.googleapis.com/v0/b/%s/o/%s?alt=media&token=%s",
                bucket.getName(),
                blob.getName(),
                downloadToken
        );
    }
}
