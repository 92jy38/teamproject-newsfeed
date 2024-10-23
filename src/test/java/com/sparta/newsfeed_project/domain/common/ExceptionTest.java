package com.sparta.newsfeed_project.domain.common;

import com.sparta.newsfeed_project.domain.post.dto.ResponsePostDto;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@SpringBootTest
@RestController
public class ExceptionTest {

    @Test
    public void createPost() throws IOException{
        test1();
    }

    void test1() throws IOException {
        throw new IOException();
    }
}
