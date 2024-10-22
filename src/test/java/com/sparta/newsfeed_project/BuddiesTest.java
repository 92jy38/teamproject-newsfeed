package com.sparta.newsfeed_project;

import com.sparta.newsfeed_project.domain.buddies.repository.BuddiesRepository;
import com.sparta.newsfeed_project.domain.post.entity.Post;
import com.sparta.newsfeed_project.domain.post.repository.PostRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@SpringBootTest
public class BuddiesTest {

    @Autowired
    BuddiesRepository buddiesRepository;

    @Autowired
    PostRepository postRepository;

    @Test
    void test1() {
        List<Long> idArray = buddiesRepository.findAllIdByFromUserId(5L);
        idArray.stream().forEach(id -> System.out.println(id));
    }

    @Test
    void test2() {
        List<Long> idArray = buddiesRepository.findAllIdByFromUserId(1L);
        idArray.add(1L);
        idArray.stream().forEach(id -> {
            System.out.println(id);
        });

        Pageable pageable = PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "createAt"));
        Page<Post> pages = postRepository.findAllPost(idArray, pageable);
        pages.stream().forEach(post -> System.out.println(post));

    }


}
