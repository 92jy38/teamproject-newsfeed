package com.sparta.newsfeed_project.domain.post.image;

import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;
import java.util.UUID;

public class Base64ToImage implements MultipartFile {

    private String[] parts = new String[2];

    public Base64ToImage(String imgData) {
        this.parts = imgData.split(",");
    }


    @Override
    public String getName() {
        return UUID.randomUUID().toString();
    }


    @Override
    public String getContentType() {

        String metadata = parts[0];
        // metadata에서 MIME 타입 추출
        String mimeType = metadata.split(":")[1].split(";")[0];
        return mimeType;

    }

    @Override
    public byte[] getBytes() throws IOException {
        String base64Image = parts[1];
        return Base64.getDecoder().decode(base64Image);

    }

    @Override
    public InputStream getInputStream() throws IOException {
        return new ByteArrayInputStream(getBytes());
    }

    // 왜 필요없는 인터페이스 메소드도 오버라이딩 해야 하나?
    @Override
    public String getOriginalFilename() {
        return "";
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public long getSize() {
        return 0;
    }


    @Override
    public void transferTo(File dest) throws IOException, IllegalStateException {

    }

}
