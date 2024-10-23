package com.sparta.newsfeed_project.domain.post.service;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.sparta.newsfeed_project.domain.buddies.repository.BuddiesRepository;
import com.sparta.newsfeed_project.domain.post.dto.RequestPostDto;
import com.sparta.newsfeed_project.domain.post.dto.ResponsePostDto;
import com.sparta.newsfeed_project.domain.post.dto.ResponsePostPage;
import com.sparta.newsfeed_project.domain.post.entity.Post;
import com.sparta.newsfeed_project.domain.post.image.Base64ToImage;
import com.sparta.newsfeed_project.domain.post.image.Storage;
import com.sparta.newsfeed_project.domain.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final BuddiesRepository buddiesRepository;

    @EventListener
    public void init(ApplicationReadyEvent event) {
        try {
            FileInputStream serviceAccount = new FileInputStream("firebase.json");

            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .setStorageBucket("nuri-8c58d.appspot.com")
                    .build();

            FirebaseApp.initializeApp(options);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public ResponsePostDto createPost(RequestPostDto requestDto) {

        try {

            // Base64 -> Image
            MultipartFile multipartFile = new Base64ToImage(requestDto.getImgData());

            // firebase storage upload/download
            Storage storage = new Storage(multipartFile);
            storage.upload();
            String downloadLink = storage.download();

            // Member 정보 추가(코드 추가 필요)
//            Member member = (Member) req.getAttribute("member");
//            Post post = postRepository.save(Post.from(requestDto.getCaption(), downloadLink, member));

            Post post = postRepository.save(Post.from(requestDto.getCaption(), downloadLink));
            return post.to();

        } catch (Exception e) {

            e.printStackTrace();
            throw new IllegalArgumentException("이미지 업로드/다운로드에 실패했습니다.");
        }

    }

    public ResponsePostPage findAllPost(int page, int size, String criteria) {
        try {

            // Cookie에서 멤버ID를 추출하여 맞팔 목록 조회
//            Member member = (Member) req.getAttribute("member");
//            List<Long> idArray = buddiesRepository.findAllIdByFromUserId(member.getId());
//            idArray.add(member.getId());

            Pageable pageable = PageRequest.of(page - 1, size, Sort.by(Sort.Direction.DESC, criteria));
//            Page<Post> pages = postRepository.findAllPost(idArray, pageable);
//            return new ResponsePostPage(pages);
            return null;

        } catch (Exception e) {

            e.printStackTrace();
            throw new IllegalArgumentException("게시글을 조회하는데 실패했습니다.");
        }

    }

    public ResponsePostDto findByPostId(Long id) {
        try {

            Post post = postRepository.findById(id).orElseThrow();
            return post.to();

        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalArgumentException("게시글을 조회하는데 실패했습니다.");
        }

    }

    @Transactional
    public ResponsePostDto modifyPost(Long id, RequestPostDto requestDto) {
        try {

            // Member 패스워드 확인(코드 추가 필요)

            Post post = postRepository.findById(id).orElseThrow();

            // Base64 -> Image
            MultipartFile multipartFile = new Base64ToImage(requestDto.getImgData());

            // firebase storage upload/download
            Storage storage = new Storage(multipartFile);
            storage.upload();
            String downloadLink = storage.download();

            post.update(requestDto.getCaption(), downloadLink);
            return post.to();


        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalArgumentException("게시글을 수정하는데 실패했습니다.");
        }
    }

    public void deletePost(Long id) {
        try {

            // Member 패스워드 확인(코드 추가 필요)
            postRepository.deleteById(id);

        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalArgumentException("게시글을 삭제하는데 실패했습니다.");
        }

    }

    /**
     * 회원의 게시물 수를 조회합니다.
     *
     * @param memberId 회원 ID
     * @return 게시물 수
     * @since 2024-10-23
     */
    public int getPostCount(Long memberId) {
        return postRepository.countByMemberId(memberId).intValue();
    }

    /**
     * 회원이 작성한 게시글을 모두 삭제합니다.
     *
     * @param memberId 회원 ID
     * @since 2024-10-23
     */
    public void deleteMemberPosts(Long memberId) {
        List<Post> posts = postRepository.findAllByMemberId(memberId);
        if (posts != null && !posts.isEmpty())
            postRepository.deleteAll(posts);
    }
}
