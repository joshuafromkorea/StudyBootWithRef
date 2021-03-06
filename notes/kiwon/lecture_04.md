## 레퍼런스로 스터디하는 스프링부트 4강

#### 15.2 Importing XML Configuraiton

* **반드시 XML**을 써야겠어도, `@Configuration` 클래스를 생성후,  `@ImportResource` 하는 방식을 사용하라.
  * 항상 Java 기반 스프링 설정파일을 기본으로 사용하는것을 추천

### 16. Auto-configuration

* 개발자가 추가한 Jar 의존성 설정을 기반을, Spring Boot는 자동설정(auto-configuration)을 **시도** 한다.
  * 예시) `HSQLDB`가 classpath에 존재하고, 수동으로 연결 bean을 만들지 않았다면, in-memory를 설정한다.
* 이 기능을 사용하기 위해선, `@Configuration`을 사용한 클래스에 아래 어노테이션을 추가  하면된다.
  * **`@EnableAutoConfiguration` **
  * **`@SpringBootApplication`**: `@SpringBootConfiguration`과 `@EnableAutoConfiguration`을 동반
    * 따라서 위 어노테이션으로 나머지를 대체 가능

#### 16.1 Gradually Replacing Auto-configuration

* Auto-configuration 기능은 필수도, 아니고 개발자가 수동 설정한 세부 설정은 언제든지 대체될 수 있다.
  * 예시) 상기한 in-memory 설정은, 개발자가 수동으로 작성한 `Datasource` bean으로 대체됨
  * 세부 설정들의 auto-configuration 대체는 각각 발동조건이 다를 수 있다.
* `--debug` 스위치를 사용해서 기동하면, **어떠한 auto-configuration이 왜** 적용되는지 알 수 있다.
  * `conditions report` 이하에 출력되는 console의 로그를 살펴보면 된다.

###### Jar build하기 

```powershell
$ mvn package
[INFO] Scanning for projects...
[INFO]
[INFO] ------------------------------------------------------------------------
[INFO] Building spring.boot.study 1.0-SNAPSHOT
[INFO] ------------------------------------------------------------------------
[INFO]
[INFO] --- maven-resources-plugin:3.1.0:resources (default-resources) @ spring.b
```

###### `--debug`와 함께 실행 하기

```powershell
$ java -jar target/spring.boot.study-1.0-SNAPSHOT.jar --debug
...
============================
CONDITIONS EVALUATION REPORT
============================
... 중략 ...
Positive matches: #의존성 분석을 통해서 일치하여 사용하는 내용들
-----------------
... 중략 ...
Negative matches: #사용하지 않아서 불일치 하는 내용들
-----------------
... 중략 ...
Exclusions: #제외한 내용들
-----------
	None
Unconditional classes:
----------------------
... 중략 ...
```

#### 16.2 Disabling Specific Auto-configuration Classes

* `@EnableAutoConfiguration`에 `exclude` 어트리뷰트에 Class를 제공하여 제외할 수 있다.
  * classpath 에 class가 제공하지 않을 경우 `excludeName` 어트리뷰트를 사용해도 된다.
* `spring.autoconfigure.exclude`의 프로퍼티를 사용해서 조작 가능하다.

### 17. Spring Beans and Dependency Injection

* Bean 정의와 DI와 관련해서는, Spring Framework의 표준 기술이 모두 지원된다.
* **루트 패키지**이하에 application class를 추가 했다면, `@ComponentScan` ,`@Component`, `@Service`, `@Repository`, `@Controller` 등을 자유롭게 쓸 수 있다.

###### `@Service`를 사용한 Bean 작성 예시

```java
@Service
public class UserService {

    private final UserRepository userRepository;

//    @Autowired--생성자가 하나일 때는 생략 가능
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserRepository getUserRepository(){
        return this.userRepository;
    }
}
```

* 생성자가 1개 존재할 경우엔, `@Autowired`를 생략 가능하다. 묵시적 선언
* 생성자 `@Autowired`를 사용할 경우에 해당 필드를 `final`로 선언하여 명시적으로 표현 가능하다.

###### 테스트

```java
@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTest {

    @Autowired
    UserService userService;

    @Test
    public void TestDI(){
        Assert.assertNotNull(userService);
        Assert.assertNotNull(userService.getUserRepository());
    }
}
```

### 18. Using the `@SpringBootApplication` Annotation

* `@SpringBootApplication`에 포함된 내용들
  * `@EnableAutoConfiguration`:  스프링 Boot의 자동설정 활성화
  * `@ComponentScan`: 루트 패키지내의 `@Component` scan 작업 활성화
  * `@Configuration`: 기존 자바코드기반 스프링 설정에 대응
* 위 3개중 일부기능을 제외하기 원하면 선별적으로 어노테이션을 사용하면 된다.

### 19. Running Your Application

* Spring Boot는 실행가능한 Jar를 만들어서 어플리케이션을 실행하고, 내장된 HTTP 서버를 사용한다.
  * 덕분에 특정환경에 종속된 실행이아닌, 단순한 Java Application과 동일하게 실행할 수 있다.
  * 또한 IDE의 Plugin이나 확장프로그램없이도 쉽게 디버깅 할 수 있다.

#### 19.1 Running from an IDE

* IDE 마다, 실행 기능을 가지고 있음
* IDE에서 간혹 어플리케이션 중복 실행으로 인해서 “Port already in use” 에러 발생한다.

#### 19.2 Running as a Package Application

* 앞서서 테스트 해본 것처럼 `java -jar` 명령어로 실행가능한 Jar를 실행하면 어플리케이션이 실행된다.
* **원격 디버깅**을 지원하도록 실행도 가능하다.

```powershell
$ java -Xdebug -Xrunjdwp:server=y,transport=dt_socket,address=8000,suspend=n \
       -jar target/myapplication-0.0.1-SNAPSHOT.jar
```

#### 19.3 Using the Maven Plugin

* Maven 명령어를 통해서, 빌드와 동시에 어플리케이션을 실행할 수도 있다.

```powershell
$ mvn spring-boot:run
```

#### 19.5 Hot Swapping

* JVM의 hot-swapping이 동일하게 Spring Boot에서도 지원된다.
  * JVM의 hot-swapping은 교첵가능한 bytecode로만 제한되서, JRebel등을 고려할 수 있다.
* 바로 이어서 다룰 `spring-boot-devtools` 모듈안에는 이와 유사한 **신속한 어플리케이션 재기동**을 포함하고 있다.

### 20. Developer Tools

* `spring-boot-devtools` 이름을 가진 부트의 모듈은, 개발단계를 위한 추가적인 기능들을 제공해서, 어플리케이션을 개발하는 것을 **즐겁게** 해준다.

###### Maven의 경우

```xml
<dependencies>
	<dependency>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-devtools</artifactId>
		<optional>true</optional>
	</dependency>
</dependencies>
```

* 스프링 부트 개발자도구는, 완전하게 패키징된 어플리케이션에서는 자동적으로 **비활성화** 된다.
  * `java -jar`로 실행되거나, 특수한 클래스로더 실행되어 **상용 어플리케이션**으로 간주되는 경우
* 또한 `<optional>`을  `true`로 해서 종속된 프로젝트나, 상용환경에 영향을 배제 하는것이 바람직하다.

