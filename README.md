# Newsfeed Project
![이미지](https://github.com/user-attachments/assets/69ad1acc-5a89-4d3c-9a67-2a4aeb278e2e)

### 👊 Spring 팀 단위 개발 숙달을 위한 뉴스피드형 SNS 백엔드 애플리케이션 서버 프로젝트 👊
#### 프로젝트 진행 기간: 24.10.18 ~ 24.10.24

## 프로젝트 목표
#### C R U D 를 중심으로, Spring을 활용하여 SNS의 다양한 기술들을 개발❗ 협업 커뮤니케이션 능력을 곁들인➕ <br>


## 👨‍👨‍👧‍👧 팀 구성
| 이름 | 역할 |담당 기능|
|-----|-----|-----|
|권준석|리더|Buddies|
|박가온누리|팀원|Post|
|김현정|팀원|Member|
|장재혁|팀원|Common|
|홍주영|팀원|Comment|


## Tools
### 🖥 language & Server 🖥

<img src="https://img.shields.io/badge/intellij idea-207BEA?style=for-the-badge&logo=intellij%20idea&logoColor=white"> <br>
<img src="https://img.shields.io/badge/java-007396?style=for-the-badge&logo=java&logoColor=white"> <img src="https://img.shields.io/badge/spring-6DB33F?style=for-the-badge&logo=spring&logoColor=white"> <br>
<img src="https://img.shields.io/badge/mysql-4479A1?style=for-the-badge&logo=mysql&logoColor=white"> <img src="https://img.shields.io/badge/firebase-DD2C00?style=for-the-badge&logo=firebase&logoColor=white"> <hr>
### 👏 Cowork Tools 👏
<img src="https://img.shields.io/badge/git-F05032?style=for-the-badge&logo=git&logoColor=white"> <img src="https://img.shields.io/badge/github-181717?style=for-the-badge&logo=github&logoColor=white"> <br> 
<img src="https://img.shields.io/badge/notion-000000?style=or-the-badge&logo=notion&logoColor=white"/> <img src="https://img.shields.io/badge/Slack-FE5196?style=or-the-badge&logo=slack&logoColor=white"/>
<br>
<hr/>

## 와이어 프레임
![image](https://github.com/user-attachments/assets/e71627f4-af82-40ac-ac00-6d3e5caedc77)


## API 명세
<table>
    <tr>
        <th>API&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</th>
        <th>Method</th>
        <th>EndPoint</th>
        <th>Request</th>
        <th>Request Type</th>
        <th>Response</th>
        <th>Response Type</th>
        <th>Status</th>
        <th>Role</th>
    </tr>
    <tr>
        <td>로그인</td>
        <td>POST</td>
        <td><code>/api/members/login</code></td>
        <td><pre lang="json">{
    "email": "hong@example.com",
    "password": "1q2w3e4r#"
}</pre></td>
        <td><code>application/json</code></td>
        <td><pre lang="json">{
    "state": 200,
    "message": "로그인 성공"
}</pre></td>
        <td><code>application/json</code></td>
        <td>200</td>
        <td></td>
    </tr>
     <tr>
        <td>회원 가입</td>
        <td>POST</td>
        <td><code>/api/members/signup</code></td>
        <td><pre lang="json">{
    "email": "hong@example.com",
    "password": "1q2w3e4r#",
    "nickname": "honggil",
    "username": "홍길동"
}</pre></td>
        <td><code>application/json</code></td>
        <td><pre lang="json">{
    "state": 201,
    "message": "회원 가입 성공"
}</pre></td>
        <td><code>application/json</code></td>
        <td>201</td>
        <td></td>
    </tr>
    <tr>
        <td>회원 정보 조회</td>
        <td>GET</td>
        <td><code>/api/members/{회원 id}</code></td>
        <td><code>N/A</code></td>
        <td><code>PathVariable</code></td>
        <td><pre lang="json">{
    "member": {
        "id": 2,
        "email": "lee@example.com",
        "password": null,
        "nickname": "우우까까",
        "username": "이길동",
        "introduce": "서에 번쩍",
        "fromBuddyCount": 0,
        "toBuddyCount": 0,
        "postCount": 0,
        "createAt": "2024-10-23T09:15:43.432104",
        "updateAt": "2024-10-23T09:18:33.338109"
    },
    "status": {
        "state": 200,
        "message": "유저 조회 성공"
    }
}</pre></td>
        <td><code>application/json</code></td>
        <td>200</td>
        <td>User</td>
    </tr>
    <tr>
        <td>회원 정보 수정</td>
        <td>PUT</td>
        <td><code>/api/members</code></td>
        <td><pre lang="json">{
    "id": 1,
    "email": "kim@example.com",
    "password": "Admin123!",
    "nickname": "kimgil",
    "username": "김길동",
    "introduce": "안녕하세요! 동에 번쩍 서에 번쩍 홍길동입니다!"
}</pre></td>
        <td><code>application/json</code></td>
        <td><pre lang="json">{
    "state": 200,
    "message": "유저 수정 성공"
}</pre></td>
        <td><code>application/json</code></td>
        <td>200</td>
        <td>User</td>
    </tr>
    <tr>
        <td>회원 정보 삭제</td>
        <td>DELETE</td>
        <td><code>/api/members</code></td>
        <td><pre lang="json">{
    "password": "Admin123!"
}</pre></td>
        <td><code>application/json</code></td>
        <td><pre lang="json">{
    "state": 401,
    "message": "비밀번호가 일치하지 않습니다."
}</pre></td>
        <td><code>application/json</code></td>
        <td>204</td>
        <td>User</td>
    </tr>
        <tr>
        <td>친구 요청</td>
        <td>POST</td>
        <td><code>/api/buddies/{요청할 상대 id}</code></td>
        <td><code>N/A</code></td>
        <td><code>PathVariable</code></td>
        <td><code>N/A</code></td>
        <td><code>N/A</code></td>
        <td>200</td>
        <td>User</td>
    </tr>
    </tr>
        <tr>
        <td>친구 조회</td>
        <td>GET</td>
        <td><code>/api/buddies</code></td>
        <td><code>N/A</code></td>
        <td><code>N/A</code></td>
        <td><pre lang="json">"Buddy List" : [{
    "id": 2,
    "nickname":"team7_수정",
    "username":"홍길동"
}]</pre></td>
        <td><code>application/json</code></td>
        <td>200</td>
        <td>User</td>
    </tr>
    </tr>
        <tr>
        <td>친구 수락</td>
        <td>PUT</td>
        <td><code>/api/buddies/{수락할 상대 id}</code></td>
        <td><code>N/A</code></td>
        <td><code>PathVariable</code></td>
        <td><code>N/A</code></td>
        <td><code>N/A</code></td>
        <td>200</td>
        <td>User</td>
    </tr>
    </tr>
        <tr>
        <td>친구 해제</td>
        <td>DELETE</td>
        <td><code>/api/buddies/{해제할 상대 id}</code></td>
        <td><code>N/A</code></td>
        <td><code>PathVariable</code></td>
        <td><code>N/A</code></td>
        <td><code>N/A</code></td>
        <td>200</td>
        <td>User</td>
    </tr>
    </tr>
        <tr>
        <td>게시물<br/>등록</td>
        <td>POST</td>
        <td><code>/api/posts</code></td>
        <td><pre lang="json">{
    "imgData": "data:image/jpeg;base64,/9j/4AAQSkZJR…",
    "post" : "안녕하세요"
}</pre></td>
        <td><code>application/json</code></td>
        <td><pre lang="json">{
    "id": 11, "caption":
    "내용 좋네요", "imgUrl": "https://firebasestorage.googleapis.com/v0...",
    "createAt": "2024-10-22T12:27:29.172967",
    "updateAt": "2024-10-22T12:27:29.172967"
}</pre></td>
        <td><code>application/json</code></td>
        <td>201</td>
        <td>User</td>
    </tr>
    </tr>
        <tr>
        <td>게시물<br/>전체 조회</td>
        <td>GET</td>
        <td><code>/api/posts</code></td>
        <td><code>?page=1</code></td>
        <td><code>RequestParam</code></td>
        <td><pre lang="json">"posts List": [{
    "id": 2,
    "nickname":"team7_수정",
    "post" : "안녕하세요",
    "createAt":"2024-10-18T23:00:00",
    "updateAt":"2024-10-18T23:00:00"
}]</pre></td>
        <td><code>application/json</code></td>
        <td>200</td>
        <td>User</td>
    </tr>
    </tr>
        <tr>
        <td>게시물<br/>수정</td>
        <td>PUT</td>
        <td><code>/api/posts/{게시물 id}</code></td>
        <td><pre lang="json">{
    "post" : "수정합니다."
}</pre></td>
        <td><code>application/json + PathVariable</code></td>
        <td><pre lang="json">{
    "id":1, "post" : "안녕하세요",
    "userId" : 1, "createAt":"2024-10-18T23:00:00",
    "updateAt":"2024-10-18T23:00:00"
}</pre></td>
        <td><code>application/json</code></td>
        <td>200</td>
        <td>User</td>
    </tr>
    </tr>
        <tr>
        <td>게시물<br/>삭제</td>
        <td>DELETE</td>
        <td><code>/api/posts/{게시물 id}</code></td>
        <td><code>N/A</code></td>
        <td><code>PathVariable</code></td>
        <td><code>N/A</code></td>
        <td><code>N/A</code></td>
        <td>204</td>
        <td>User</td>
    </tr>
    </tr>
    <tr>
        <td>댓글 등록</td>
        <td>POST</td>
        <td><code>/api/comments/{등록할 게시물 id}</code></td>
        <td><pre lang="json">{
    "comment" : "재밌게 봤습니다."
}</pre></td>
        <td><code>application/json + PathVariable</code></td>
        <td><pre lang="json">{
    "id":1,
    "comment" : "안녕하세요",
    "userId" : 1,
    "createAt":"2024-10-18T23:00:00",
    "updateAt":"2024-10-18T23:00:00"
}</pre></td>
        <td><code>application/json</code></td>
        <td>201</td>
        <td>User</td>
    </tr>
    <tr>
        <td>댓글 조회</td>
        <td>GET</td>
        <td><code>/api/comments/post/{조회할 게시물 id}</code></td>
        <td><code>/api/comments/post/{postId}?page=1&size=10</code></td>
        <td><code>PathVariable</code></td>
        <td><pre lang="json">"comment List": [{
    "id":1, "comment" : "안녕하세요",
    "userId" :"1",
    "createAt":"2024-10-18T23:00:00",
    "updateAt":"2024-10-18T23:00:00"
}]</pre></td>
        <td><code>application/json</code></td>
        <td>200</td>
        <td>User</td>
    </tr>
    <tr>
        <td>댓글 수정</td>
        <td>PUT</td>
        <td><code>/api/comments/{댓글 id}</code></td>
        <td><pre lang="json">{
    "comment" : "수정했습니다."
}</pre></td>
        <td><code>application/json + PathVariable</code></td>
        <td><pre lang="json">{
    "id":1, "comment" : "수정했습니다.",
    "userId" : 1,
    "createAt":"2024-10-18T23:00:00",
    "updateAt":"2024-10-18T23:00:00"
}</pre></td>
        <td><code>application/json</code></td>
        <td>200</td>
        <td>User</td>
    </tr>
    <tr>
        <td>댓글 삭제</td>
        <td>DELETE</td>
        <td><code>/api/comments/{댓글 id}</code></td>
        <td><code>N/A</code></td>
        <td><code>PathVariable</code></td>
        <td><code>N/A</code></td>
        <td><code>N/A</code></td>
        <td>204</td>
        <td>User</td>
    </tr>
</table>

## 프로젝트 구조

```plaintext
/domain
    ├─ buddies
    │  ├─ controller
    │  ├─ dto
    │  ├─ entity
    │  ├─ repository
    │  └─ service
    ├─ comment
    │  ├─ controller
    │  ├─ dto
    │  ├─ entity
    │  ├─ repository
    │  └─ service
    ├─ common
    │  ├─ config
    │  ├─ entity
    │  ├─ dto
    │  ├─ exception
    │  ├─ filter
    │  └─ util
    ├─ member
    │  ├─ controller
    │  ├─ dto
    │  ├─ entity
    │  ├─ repository
    │  └─ service
    └─ post
       ├─ controller
       ├─ dto
       ├─ entity
       ├─ repository
       └─ service
```

## 개체 관계도 (ERD)
```mermaid
erDiagram

MEMBER {
   Long id PK
   String email
   String password
   String nickname
   String username
   String introduce
   boolean deleted
   LocalDateTime createAt
   LocalDateTime updateAt
}
POST {
   Long id PK
   Long memberId FK
   String caption
   String imgUrl
   LocalDateTime createAt
   LocalDateTime updateAt
}
COMMENT {
   Long id PK
   Long postId FK
   Long memberId FK
   String content
   LocalDateTime createAt
   LocalDateTime updateAt
}
BUDDIES {
   Long id PK
   Long fromUserId
   Long toUserId
   boolean approved
}
MEMBER ||--o{ POST : memberId
MEMBER ||--o{ COMMENT : memberId
POST ||--o{ COMMENT : postId
```

## Application 기능 구현

### $\bf{\normalsize{\color{yellow}POST}}$ 회원가입
```
http://localhost:8080/api/members/signup
```
```json
{
     "email":"test1@example.com",
     "password":"a!123456789",
     "nickname": "User1",
     "username":"홍길동"
}
```
### $\bf{\normalsize{\color{yellow}POST}}$ 로그인
```
http://localhost:8080/api/members/login
```
```json
{
    "email":"test1@example.com",
    "password":"a!123456789"
}
```
### $\bf{\normalsize{\color{#86E57F}GET}}$ 회원 프로필 조회
```
http://localhost:8080/api/members/{memberId}
```
### $\bf{\normalsize{\color{#6799FF}PUT}}$ 회원 정보 수정
```
http://localhost:8080/api/members
```
```json
{
    "email":"test1@example.com",
    "password":"a!123456789",
    "newPassword": "1q2w3e4r#",
    "nickname": "User1",
    "username":"홍길동",
    "introduce": "안녕하세요"
}
```
### $\bf{\normalsize{\color{#F15F5F}DELETE}}$ 회원 탈퇴
```
http://localhost:8080/api/members
```
### $\bf{\small{\color{yellow}POST}}$ 피드 등록
```
http://localhost:8080/api/posts
```
```json
{
    "imgData": "data:image/jpeg;base64,/9j/4AAQSkZJR…",
    "caption" : "안녕하세요"
}
```
### $\bf{\small{\color{#86E57F}GET}}$ 전체 피드 조회
```
http://localhost:8080/api/posts?page=1
```
### $\bf{\normalsize{\color{#6799FF}PUT}}$ 피드 수정
```
http://localhost:8080/api/posts/{postId}
```
```json
{
  "imgData": "data:image/jpeg;base64,/9j/4AAQSkZJR…",
  "caption" : "피드 수정합니다.",
  "password": "1q2w3e4r#"
}
```
### $\bf{\normalsize{\color{#F15F5F}DELETE}}$ 피드 삭제
```
http://localhost:8080/api/posts/{postId}
```
### $\bf{\small{\color{yellow}POST}}$ 댓글 등록
```
http://localhost:8080/api/comments/{postId}
```
```json
{
    "content" : "재밌게 봤습니다."
}
```
### $\bf{\small{\color{#86E57F}GET}}$ 댓글 조회
```
http://localhost:8080/api/comments/post/{postId}
```
### $\bf{\normalsize{\color{#6799FF}PUT}}$ 댓글 수정
```
http://localhost:8080/api/comments/{commentId}
```
```json
{
    "content" : "수정했습니다."
}
```
### $\bf{\normalsize{\color{#F15F5F}DELETE}}$ 댓글 삭제
```
http://localhost:8080/api/comments/{commentId}
```
### $\bf{\small{\color{yellow}POST}}$ 팔로우
```
http://localhost:8080/api/buddies/{toUserId}
```
```json

{
    "toUserId": 2
}
```
### $\bf{\normalsize{\color{#86E57F}GET}}$ 팔로우 조회
```
http://localhost:8080/api/buddies/{memberId}
```
### $\bf{\normalsize{\color{#6799FF}PUT}}$ 팔로우 수락
```
http://localhost:8080/api/buddies/{toUserId}
```
### $\bf{\normalsize{\color{#F15F5F}DELETE}}$ 팔로우 해제
```
http://localhost:8080/api/buddies/{toUserId}
```





## Application 핵심 기능 시연 영상
[![Watch the video](https://img.youtube.com/vi/tDi-tSmUjvs/0.jpg)](https://www.youtube.com/watch?v=tDi-tSmUjvs)


