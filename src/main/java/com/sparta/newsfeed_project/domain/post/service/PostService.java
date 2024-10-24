package com.sparta.newsfeed_project.domain.post.service;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.sparta.newsfeed_project.domain.buddies.repository.BuddiesRepository;
import com.sparta.newsfeed_project.domain.common.exception.ResponseCode;
import com.sparta.newsfeed_project.domain.common.exception.ResponseException;
import com.sparta.newsfeed_project.domain.common.util.PasswordEncoder;
import com.sparta.newsfeed_project.domain.member.entity.Member;
import com.sparta.newsfeed_project.domain.post.dto.RequestPostDeleteDto;
import com.sparta.newsfeed_project.domain.post.dto.RequestPostDto;
import com.sparta.newsfeed_project.domain.post.dto.ResponsePostDto;
import com.sparta.newsfeed_project.domain.post.dto.ResponsePostPage;
import com.sparta.newsfeed_project.domain.post.entity.Post;
import com.sparta.newsfeed_project.domain.post.image.Base64ToImage;
import com.sparta.newsfeed_project.domain.post.image.Storage;
import com.sparta.newsfeed_project.domain.post.repository.PostRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final BuddiesRepository buddiesRepository;
    private final PasswordEncoder passwordEncoder;

    @EventListener
    public void init(ApplicationReadyEvent event) throws IOException {

        FileInputStream serviceAccount = new FileInputStream("firebase.json");

        FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .setStorageBucket("nuri-8c58d.appspot.com")
                .build();

        FirebaseApp.initializeApp(options);

    }


    public ResponsePostDto createPost(RequestPostDto requestDto, HttpServletRequest req) throws IOException {

        // Base64 -> Image
        MultipartFile multipartFile = new Base64ToImage(requestDto.getImgData());

        // firebase storage upload/download
        Storage storage = new Storage(multipartFile);
        storage.upload();
        String downloadLink = storage.download();

        // Member 정보 추가(코드 추가 필요)
        Member member = (Member) req.getAttribute("loggedInWithId");
        Post post = postRepository.save(Post.from(requestDto.getCaption(), downloadLink, member));

        return post.to();

    }

    public ResponsePostPage findAllPost(int page, int size, String criteria, HttpServletRequest req) {

        // Cookie에서 멤버ID를 추출하여 맞팔 목록 조회
        Member member = (Member) req.getAttribute("loggedInWithId");
        List<Long> idArray = buddiesRepository.findIdListByFromUserId(member.getId());
        idArray.add(member.getId());

        Pageable pageable = PageRequest.of(page - 1, size, Sort.by(Sort.Direction.DESC, criteria));
        Page<Post> pages = postRepository.findAllPost(idArray, pageable);
        return new ResponsePostPage(pages);

    }

    public ResponsePostDto findByPostId(Long id) throws ResponseException {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResponseException(ResponseCode.POST_NOT_FOUND));
        return post.to();
    }

    @Transactional
    public ResponsePostDto modifyPost(Long id, RequestPostDto requestDto, HttpServletRequest req) throws ResponseException, IOException {

        // Member 패스워드 확인(코드 추가 필요)
        Member member = (Member) req.getAttribute("loggedInWithId");
        if (!passwordEncoder.matches(requestDto.getPassword(), member.getPassword())) {
            throw new ResponseException(ResponseCode.MEMBER_PASSWORD_NOT_MATCH);
        }

        // 본인 게시물을 수정하는 것이 맞는지 확인
        Post post = postRepository.findById(id).orElseThrow();
        if (!Objects.equals(member.getId(), post.getMember().getId())) {
            throw new ResponseException(ResponseCode.POST_INVALID);
        }

        // Base64 -> Image
        MultipartFile multipartFile = new Base64ToImage(requestDto.getImgData());

        // firebase storage upload/download
        Storage storage = new Storage(multipartFile);
        storage.upload();
        String downloadLink = storage.download();

        post.update(requestDto.getCaption(), downloadLink);
        return post.to();

    }

    public void deletePost(Long id, RequestPostDeleteDto requestDto, HttpServletRequest req) throws ResponseException {

        // Member 패스워드 확인(코드 추가 필요)
        Member member = (Member) req.getAttribute("loggedInWithId");
        if (!passwordEncoder.matches(requestDto.getPassword(), member.getPassword())) {
            throw new ResponseException(ResponseCode.MEMBER_PASSWORD_NOT_MATCH);
        }

        // 본인 게시물을 수정하는 것이 맞는지 확인
        Post post = postRepository.findById(id).orElseThrow();
        if (!Objects.equals(member.getId(), post.getMember().getId())) {
            throw new ResponseException(ResponseCode.POST_INVALID);
        }

        // Member 패스워드 확인(코드 추가 필요)
        postRepository.deleteById(id);

    }
}
