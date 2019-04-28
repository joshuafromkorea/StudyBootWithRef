## 레퍼런스로 스터디하는 스프링부트 8강

### 24. Externalized Configuration

스프링부트는 동일한 어플리케이션 코드가 다른환경에서 사용되도록, 소스 외부에 설정을 두도록 제공한다. 앞서서 살펴본 command-line arguments를 포함하여, 프로퍼티 파일이나, YAML형식의 파일이 그러한 외부 설정 자원이 될 수 있다.

이러한 설정을 소스 내부에서 주입받아서 사용하기 위해 여러가지 방법을 지원한다. `@Value` 어노테이션, 스프링의 `Environment` 추상화, `@ConfigurationProperties`를 통한 객체에 귀속시키는 법들이다.

##### 설정파일 우선순위와 오버라이딩 정의

1. 스프링부트 개발자도구(devtools)가 활성화 되었을 때, 해당 글로벌 설정 (home directory 이하의 `.spring-boot-devtools.properties`파일)
2. 테스트에 선언한 `@TestPropertySource` 설정
3. `@SpringBootTest` 어노테이션이나 테스트 관련 기타 어노테이션에 `properties` 어트리뷰트로 선언한 설정
4. 커맨드라인에 입력한 arguments (`java -jar`로 실행할 때 전달하는 값들)
5. `SPRING_APPLICATION_JSON` 의 형태로 만든 프로퍼티, 시스템변수나 환경변수로 전달하여 사용
6. `ServeletConfig`의 초기화 파라미터
7. `ServletContext`의 초기화 파라미터
8. `java:comp/env` 의 JNDI 어트리뷰트
9. 자바 시스템 변수(`System.getProperties()`)
10. OS 환경변수
11. `random.*` 이하에 위치한  `RandomValuePropertySource` 설정
12. 패키지 된 jar **외부의** 프로필이 명시된 어플리케이션 프로퍼티(`application-{profile}.properties` )설정
13. 패키지 된 jar **내부의**  프로필이 명시된 어플리케이션 프로퍼티(`application-{profile}.properties` )설정

14. 패키지 된 jar **외부의** 어플리케이션 프로퍼티(`application.properties`) 설정
15. 패키지 된 jar **내부의** 어플리케이션 프로퍼티 `application.properties` 설정
16. `@Configuration` 클래스내부에 사용한 `@PropertySource` 어노테이션의 설정
17. 스프링 어플리케이션의 default 프로퍼티 (`SpringApplication.setDefaultProperties)

##### 설정 파일 우선순위와 오버라이딩 예제

###### `HelloService.java`

```java
@Service
public class HelloService {

    //name으로 정의된 프로퍼티 값을 가져오는 어노테이션
    @Value("${name}")
    String name;

    public String getMeassage(){return "Hello "+name; }

...
}
```

##### `application.properties`

```properties
name=joshua
```

