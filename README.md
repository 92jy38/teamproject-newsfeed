### 🔨초기 세팅
- `MySQL`연동 후 사용할 데이터베이스 `newsfeed` 생성 필요.
- `..src/main/resources` 폴더 아래에 `application-aws.properties` 파일 생성 후
```properties
  spring.datasource.url=jdbc:mysql://localhost:3306/{데티터베이스}
  spring.datasource.username={사용자}
  spring.datasource.password={비밀번호}
  spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
```
위와 같이 `DB Connection` 을 적어준다.

##### 💫 Dependencies
- `Lombok`
- `thymeleaf`
- `Spring Web`
- `JDBC API`
- `Spring Data JPA`
- `MySQL Driver`
- `Validation`
- `Spring Security`