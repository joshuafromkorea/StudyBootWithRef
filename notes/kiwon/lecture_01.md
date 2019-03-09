## 레퍼런스로 스터디하는 스프링부트 1강

#### 8. Introducing Spring Boot

* 상품화 가능한 수준의 스탠드얼론 스프링기반 어플리케이션을 손쉽게 만들 수 있게 하는 도구
* 복잡함을 단순화 하기 위해서, 제작자들이 Spring 과 Third party 라이브러리에 대한 주관적 해석을 함
* Spring 설정을 간단히 하였음 (하지만 백기선님은 동의 안함)
* Runnable jar로 배포가능한 것이 장점
* 스프링 부트의 목표
	1. 스프링 개발을 위한 준비과정을 더 빨리 할 수 있게 해줌
	2. 독선적이고 독창적인 방법을 통해 요구사항 변화가 일어나는것에 대해 빠르게 대처
	3. 넓은 계층의 프로젝트에서 공통적으로 사용되는 비기능적인 요소들을 제공
	4. Code Generation과 XML 설정의 완전한 배제

#### 9. System Requirements

Java 8부터 11까지 지원하며, Spring Framework 5.1.5 이상의 버젼이 필요하며, Maven 혹은 Gradle 을 통한 빌드를 지원함

##### 9.1 Servlet Containers

Servlet 3.1+ 이상의 버젼을 지원하는 어떠한 Container든지 설치가능함

#### 10 Installing Spring Boot

Java SDK 1.8이상을 필요로 하며, Java 버젼을 확인해보자.

```Powershell
joshua@joshua-Lenovo-ideapad-330S-14IKB:~ $ java -version
java version "1.8.0_181"
Java(TM) SE Runtime Environment (build 1.8.0_181-b13)
Java HotSpot(TM) 64-Bit Server VM (build 25.181-b13, mixed mode)
```

##### 10.1  Installation Instruction for the Java Developer

Spring Boot 자체는 그 자체로 Java의 라이브러리 이기 때문에, 일반적인 자바 라이브러리처럼 `spring-boot-*.jar` 파일을 class path에 포함하기만 된다. 즉 의존성 추가만 해주면 별다른 통합 tool을 필요로 하지 않으며, 어떠한 editor를 사용해도 무관하다. 파일 카피로 할수도 있지만 아래와 같이 빌드도구를 사용하는 것이 낫다.

###### 10.1.1 Maven Installation

Maven을 설치하기 위해서 레퍼런스의 설치 메뉴얼을 따라 진행해보자. Mint Linux는 sudo apt-get 명령어로 설치할 수 있다.

```powershell
joshua@joshua-Lenovo-ideapad-330S-14IKB:~ $ sudo apt-get install maven
```

Spring Boot Dependency 구조

* `groupId` : `org.springframework.boot`
* `parent`를 통한 상속관계를 만들어서 아래와 같이, 부모 pom.xml을 통해서 상속 받아서 할 수 있다.

```xml
<!-- Inherit defaults from Spring Boot -->
	<parent>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter-parent</artifactId>
		<version>2.1.3.RELEASE</version>
	</parent>
```

즉 `artifcatId`로 정한 `spring-boot-starter-parent` 내부에 정의된 부모 dependency들을 활용할 수 있기 때문에 이하에 필요한 dependecy들은 버젼정보들을 제외할 수 있다. 

* 유사하게 `starter`라는 이름을 가진 다른 라이브러리들을 사용하게 된다.

```xml
<!-- Add typical dependencies for a web application -->
	<dependencies>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-web</artifactId>
		</dependency>
	</dependencies>
```

* 또한 Runnable Jar를 만들기 위한 플러그인도 사용하게 된다.

대부분의 경우에 `spring-boot-starter-parent` 버젼을 통해 Spring Boot를 사용할 수 있지만, 특수한 경우에 다른 부모 POM을 사용할 수도 있거나 기본 세팅을 변경하고자 할 때는 Section 13.2.2를 통해 해결할 수 있다.

IDE를 통해 Maven 프로젝트를 생성하고, 설정하는 과정을 진행해보자. 나의 경우에는 강사인 백기선님과 같이 IntelliJ를 사용하였다.

기본적으로 생성된 POM파일에 레퍼런스나 Spring Boot 홈페이지에서 제공하는 빌드 가이드를 복사해서, 생성된 POM파일에 붙여넣기 하면 자동적으로 의존성설정이 완료된다.

#### 11. Devloping Your First Spring Boot Application

개발 시작의 기본인 "Hello World" web application을 만들어 보기위해 아래의 방법을 소개한다.
* spring.io 의 "Getting Start"를 따라하는법
* start.spring.io를 통해서 "Web"을 선택해서 필요한 의존성을 선설정 하는법

나의 경우는 백기선님 강좌대로, 아까 POM 파일 의존성 설정을 실습하기 위해 사용했던 소스에서 이미 `spring-boot-starter-web`을 설치 했기 때문에 이어서 진행하면 된다.

###### 11.1 Creating the POM

위의 내용과 상동, 다른점이라면, 백기선님 강좌에서는 RC버젼이라 사설저장소 정보가 존재하지만, 현재 최신버젼인 2.1.3에서는 해당내용이 빠지게 된다. 빌드 실행에 대한 테스트는 `mvn package`명령어를 build path에서 실행하면 된다.

실행시 아래와같이 에러가 발생한다 (강의에서도 동일)
```powershell
[ERROR] Failed to execute goal org.springframework.boot:spring-boot-maven-plugin:2.1.3.RELEASE:repackage (repackage) on project spring.boot.study: Execution repackage of goal org.springframework.boot:spring-boot-maven-plugin:2.1.3.RELEASE:repackage failed: Unable to find main class -> [Help 1]
```

`spring-boot-maven-plugin` 라이브러리가 `main`클래스를 필요로 하기 때문이다. 제외하면 빌드 성공!

##### 11.2 Adding Classpath Dependencies

IDE기능을 사용하는게 나아서 스킵

##### 11.3 Writing Code

예제 Application을 완성하기 위해서는 아까 빌드 에러에서도 살펴본대로 Java 파일이 필요하다. Maven은 `src/main/java`에 있는 Java 파일을 컴파일 하기 때문에 해당 direcotry 구조를 만들고 이하에 java파일을 하나 추가해주면 된다. 기본적으로 IDE를 사용한 Maven 프로젝트를 생성하면 해당 디렉토리 구조가 완성되어있으니 파일만 추가해주면 된다.

생성한 Java 파일에 레퍼런스에 나온 예제 코드를 작성해본다.

```java
package me.joshua;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableAutoConfiguration
public class Exmaple {

    @RequestMapping("/")
    String home(){
        return "Hello World!";
    }

    public static void main(String[] args) {
        SpringApplication.run(Exmaple.class, args);
    }
}
```

매우 간단한 코드이지만, 저 코드로 인해 여러 작업들이 일어난다 앞으로 이어지는 레퍼런스의 내용들을 통해 하나하나 살펴보도록 하자

###### 11.3.1 The `@RestController` and `@RequestMapping` Annotations

* `@RestController` : _streotype_ 어노테이션[^1]으로서, 작성된 소스 코드를 읽는 다른 개발자들에게 힌트를 제공하며, Spring이 해당 클래스가 특정한 역할을 수행하도록 한다.
  * 이 경우에는 `Example` 클래스가 `@Controller`의 역할을 수행하도록 표기하여, 스프링이 해당 클래스를 인바운드 web request를 핸들링하는 클래스로 인식하게 한다.
* `@RequestMapping`: Spring에게 HTTP request의 routing 정보를 제공하며, 예제의 경우에는 `/` , 즉 루트 경로로 들어오는 request를 `home()` 메소드로 routing  해준다. 그리고 마지막으로 `@RestController` 어노테이션이 스프링이 caller에게 해당 호출 결과를 string 결과로 전달한도록 한다.

[^1]: 스프링에게 특정 클래스를 어떻게 취급할지 정해주는 marker같은 역할을 하며 예를들어 JPA에서 사용하는 `@Repository`등이 있다.

위 어노테이션들은 SPRING MVC의 어노테이션들이며, Spring Boot 전용은 아니며 [MVC section](https://docs.spring.io/spring/docs/5.1.5.RELEASE/spring-framework-reference/web.html#mvc)링크를 통해 자세한 내용을 확인할 수 있다.

###### 11.3.2 The `@EnableAutoConfiguration` 어노테이션

해당 어노테이션은 Spring boot에서 제공하는 **자동설정을 하도록하는** 어노테이션으로,  기본적으로는 default 설정값을 사용하도록 한다. 예제에서는, 우리가 POM에서 선언한 `spring-boot-starter-web`의존성을 따라서, Tomcat과 Spring MVC가 의존성에추가되었기 때문에, 자동설정이 이뤄지는데 이경우 이하의 개발 코드들이 web application과 상응하는 스프링 설정이라는 것을 추론한다.

> **Starter들과 Auto-configuration**
>
> 자동설정 기능은 Starter들과 연동되어서 작동하도록 설계되어 있지만, 두 컨셉이 직접적으로 연결되어있는 것은 아니다. 개발자는 Starter가 제공하는 의존성 이외에도 jar 의존성을 선택할 수 있다. 이경우에도 Spring Boot는 Application을 위한 최적의 자동설정을 제공할 것이다.

###### 11.3.3 The "main" Method

`main` 메소드는 자바의 전통을 따르는 어플리케이션의 **entry-point**역할을 담당한다. `SpringApplication` 클래스의 `run`메소드를 실행함으로서, 어플리케이션의 실행에 필요한 클래스를 파라미터로 넘겨주어서에게, `SpringApplication` 클래스가 어플리케이션을 실행한다.

##### 11.4 Running the Example

가장 간단한 방법은 `main()` 메소드를 직접 실행하는 것이다. IDE가 제공하는 실행 기능을 사용해서 실행한다.

**실행결과**

```powershell
 .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::        (v2.1.3.RELEASE)

2019-03-09 20:17:41.022  INFO 6006 --- [           main] me.joshua.Exmaple                        : Starting Exmaple on joshua-Lenovo-ideapad-330S-14IKB with PID 6006 (/home/joshua/git/springbootstudy/target/classes started by joshua in /home/joshua/git/springbootstudy)
2019-03-09 20:17:41.043  INFO 6006 --- [           main] me.joshua.Exmaple                        : No active profile set, falling back to default profiles: default
2019-03-09 20:17:44.383  INFO 6006 --- [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat initialized with port(s): 8080 (http)
2019-03-09 20:17:44.458  INFO 6006 --- [           main] o.apache.catalina.core.StandardService   : Starting service [Tomcat]
2019-03-09 20:17:44.458  INFO 6006 --- [           main] org.apache.catalina.core.StandardEngine  : Starting Servlet engine: [Apache Tomcat/9.0.16]
2019-03-09 20:17:44.480  INFO 6006 --- [           main] o.a.catalina.core.AprLifecycleListener   : The APR based Apache Tomcat Native library which allows optimal performance in production environments was not found on the java.library.path: [/usr/java/packages/lib/amd64:/usr/lib64:/lib64:/lib:/usr/lib]
2019-03-09 20:17:44.724  INFO 6006 --- [           main] o.a.c.c.C.[Tomcat].[localhost].[/]       : Initializing Spring embedded WebApplicationContext
2019-03-09 20:17:44.724  INFO 6006 --- [           main] o.s.web.context.ContextLoader            : Root WebApplicationContext: initialization completed in 3483 ms
2019-03-09 20:17:45.238  INFO 6006 --- [           main] o.s.s.concurrent.ThreadPoolTaskExecutor  : Initializing ExecutorService 'applicationTaskExecutor'
2019-03-09 20:17:45.612  INFO 6006 --- [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat started on port(s): 8080 (http) with context path ''
2019-03-09 20:17:45.624  INFO 6006 --- [           main] me.joshua.Exmaple                        : Started Exmaple in 6.203 seconds (JVM running for 7.534)

```

이렇게 정상적으로 Spring Boot가 기동되면 [`http://localhost:8080`](http://localhost:8080)을 통해 접근하면 된다. 접속하면 당연하게도 Hello World!가 보인다.

추가적으로 레퍼런스에서는 Maven 명령어를 통해서 실행하는 법이 있어서 해당 방법으로도 실행해보았다.

```powershell
joshua@joshua-Lenovo-ideapad-330S-14IKB:~/git/springbootstudy $ mvn spring-boot:run
[INFO] Scanning for projects...
[INFO] 
[INFO] ------------------------------------------------------------------------
[INFO] Building spring.boot.study 1.0-SNAPSHOT
[INFO] ------------------------------------------------------------------------
[INFO] 
[INFO] >>> spring-boot-maven-plugin:2.1.3.RELEASE:run (default-cli) > test-compile @ spring.boot.study >>>
[INFO] 
[INFO] --- maven-resources-plugin:3.1.0:resources (default-resources) @ spring.boot.study ---
[INFO] Using 'UTF-8' encoding to copy filtered resources.
[INFO] Copying 0 resource
[INFO] Copying 0 resource
[INFO] 
[INFO] --- maven-compiler-plugin:3.8.0:compile (default-compile) @ spring.boot.study ---
[INFO] Changes detected - recompiling the module!
[INFO] Compiling 1 source file to /home/joshua/git/springbootstudy/target/classes
[INFO] 
[INFO] --- maven-resources-plugin:3.1.0:testResources (default-testResources) @ spring.boot.study ---
[INFO] Using 'UTF-8' encoding to copy filtered resources.
[INFO] skip non existing resourceDirectory /home/joshua/git/springbootstudy/src/test/resources
[INFO] 
[INFO] --- maven-compiler-plugin:3.8.0:testCompile (default-testCompile) @ spring.boot.study ---
[INFO] Nothing to compile - all classes are up to date
[INFO] 
[INFO] <<< spring-boot-maven-plugin:2.1.3.RELEASE:run (default-cli) < test-compile @ spring.boot.study <<<
[INFO] 
[INFO] 
[INFO] --- spring-boot-maven-plugin:2.1.3.RELEASE:run (default-cli) @ spring.boot.study ---

  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::        (v2.1.3.RELEASE)

2019-03-09 20:23:21.477  INFO 6279 --- [           main] me.joshua.Exmaple                        : Starting Exmaple on joshua-Lenovo-ideapad-330S-14IKB with PID 6279 (/home/joshua/git/springbootstudy/target/classes started by joshua in /home/joshua/git/springbootstudy)
2019-03-09 20:23:21.482  INFO 6279 --- [           main] me.joshua.Exmaple                        : No active profile set, falling back to default profiles: default
2019-03-09 20:23:24.707  INFO 6279 --- [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat initialized with port(s): 8080 (http)
2019-03-09 20:23:24.779  INFO 6279 --- [           main] o.apache.catalina.core.StandardService   : Starting service [Tomcat]
2019-03-09 20:23:24.779  INFO 6279 --- [           main] org.apache.catalina.core.StandardEngine  : Starting Servlet engine: [Apache Tomcat/9.0.16]
2019-03-09 20:23:24.809  INFO 6279 --- [           main] o.a.catalina.core.AprLifecycleListener   : The APR based Apache Tomcat Native library which allows optimal performance in production environments was not found on the java.library.path: [/usr/java/packages/lib/amd64:/usr/lib64:/lib64:/lib:/usr/lib]
2019-03-09 20:23:25.007  INFO 6279 --- [           main] o.a.c.c.C.[Tomcat].[localhost].[/]       : Initializing Spring embedded WebApplicationContext
2019-03-09 20:23:25.008  INFO 6279 --- [           main] o.s.web.context.ContextLoader            : Root WebApplicationContext: initialization completed in 3065 ms
2019-03-09 20:23:25.399  INFO 6279 --- [           main] o.s.s.concurrent.ThreadPoolTaskExecutor  : Initializing ExecutorService 'applicationTaskExecutor'
2019-03-09 20:23:25.813  INFO 6279 --- [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat started on port(s): 8080 (http) with context path ''
2019-03-09 20:23:25.818  INFO 6279 --- [           main] me.joshua.Exmaple                        : Started Exmaple in 5.218 seconds (JVM running for 11.529)

```

IDE 보다 실행내용은 길기는 하지만, 결과는 동일하다. 위처럼 콘솔에서 실행시에는 `ctrl`+`c` 로 종료하면 된다.