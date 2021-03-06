## 레퍼런스로 스터디하는 스프링부트 2강

#### 11.5 Creating an Executable Jar

* 개발자가 만들어서 컴파일 된 클래스 + 코드가 돌아가는데 필요한 jar 의존성 = **실행가능한 Jar 파일**

##### 실행가능한 Jar와 Java

* Java에는 Jar속의 Jar(a.k.a **nested Jar**)를  loading 하는 표준이 없다.
* 일반적으로 "**uber**" jar라고 불리는, 어플리케이션의 모든 의존성을 하나의 Jar로 모으는 방법을 사용
  * 어플리케이션에 사용되는 의존성 파악 어려움
  * 각기 다른 의존성 jar에 같은 파일이름이 존재할 수도 있음
* 따라서, Spring Boot는 [**다른 접근법**](#Appendix E. The Executable Jar Format)을 사용한다.
* 실행가능한 Jar를 만들려면 아래와 같은 의존성이 필요하다.

```xml
<!-- IDE 등을 사용하면 자동으로 생성됨 -->
<build>
	<plugins>
		<plugin>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-maven-plugin</artifactId>
		</plugin>
	</plugins>
</build>
```

* 위의 의존성 설정은 `spring-boot-starter-parent`에 설정된 실행관련 속성정보를 참고하기 때문에, 만약 부모 의존관계설정을 사용하지 않으면 별도로 선언해주어야 한다.

* 프로젝트 경로에서 `mvn package`를 하거나, IDE의 기능을 통해 Jar를 만들 수 있다.

  * 만든 파일은 `target` 디렉토리 안에 생성된다.
  * 내부를 보려면 `jar tvf` 명령어로 살펴볼 수 있다.

  ```powershell
  $ jar tvf spring.boot.study-1.0-SNAPSHOT.jar
  ```

* 동일 한 경로에 `*.original`형식의 파일이 존재하는데, 이는 Maven이 만들어준 버젼으로 스프링 부트가 이를 repackage 한다.

* `java - jar`를 통해서 아래와 같이 실행할 수 있다.

```powershell
$ java -jar spring.boot.study-1.0-SNAPSHOT.jar

  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::        (v2.1.3.RELEASE)
 
....

2019-03-24 15:53:22.271  INFO 6248 --- [           main] o.a.c.c.C.[Tomcat].[localhost].[/]       : Initializing Spring embedded WebApplicationContext
2019-03-24 15:53:22.271  INFO 6248 --- [           main] o.s.web.context.ContextLoader            : Root WebApplicationContext: initialization completed in 1059 ms
2019-03-24 15:53:22.472  INFO 6248 --- [           main] o.s.s.concurrent.ThreadPoolTaskExecutor  : Initializing ExecutorService 'applicationTaskExecutor'
2019-03-24 15:53:22.654  INFO 6248 --- [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat started on port(s): 8080 (http) with context path ''
2019-03-24 15:53:22.656  INFO 6248 --- [           main] me.joshua.Exmaple                        : Started Exmaple in 1.796 seconds (JVM running for 2.061)
2019-03-24 15:54:05.176  INFO 6248 --- [nio-8080-exec-1] o.a.c.c.C.[Tomcat].[localhost].[/]       : Initializing Spring DispatcherServlet 'dispatcherServlet'
2019-03-24 15:54:05.177  INFO 6248 --- [nio-8080-exec-1] o.s.web.servlet.DispatcherServlet        : Initializing Servlet 'dispatcherServlet'
2019-03-24 15:54:05.181  INFO 6248 --- [nio-8080-exec-1] o.s.web.servlet.DispatcherServlet        : Completed initialization in 4 ms

```

### 13. Build Systems

* 의존성 관리<sup>dependency management</sup>를 지원하는 빌드 시스템을 사용하기를 **강력히** 권장 (Maven 혹은 Gradle)

#### 13.1 Dependency Management

* Spring Boot 매니져를 통해, 각각의 release에 맞는 연관버젼을 찾아주기 때문에 버전 명시는 필요없다.

* 스프링 모듈과  3rd 파티 라이브러리의 버전은 변경가능하지만, **하지 않기를 강력히 권장**
  * 특히 Spring Framework의 버전은 부트와 강한 의존성이 있기때문에 하지 말아야 한다.

#### 13.2 Maven

* `spring-boot-starter-parent` 프로젝트를 사용해 기본 값을 사용할 수 있다.

##### 13.2.1 Inheriting the Starter Parent

```xml
<!-- Inherit defaults from Spring Boot -->
<parent>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-parent</artifactId>
    <version>2.1.3.RELEASE</version>
</parent>
```

* 스프링 부트의 POM 설정 중 유일하게 `spring-boot-starter-parent`만 **버전 명시를 해야한다**.

* 이외의 버전은 [`spring-boot-dependencies`](https://github.com/spring-projects/spring-boot/tree/v2.1.3.RELEASE/spring-boot-project/spring-boot-dependencies/pom.xml)에서 관리하는데 아래와 같이 overriding 할 수 있다.

```xml
<properties>
	<spring-data-releasetrain.version>Fowler-SR2</spring-data-releasetrain.version>
</properties>
```
* Parent POM은 `spring-boot-dependencies` 상속 받고 있다.

##### 13.2.2 Using Spring Boot without the Parent POM

* `spring-boot-starter-parent`가 제공하는 버전이 싫다면, (플러그인 관리를 제외하고) 직접 위의 `spring-boot-dependencies`를 사용하면 된다. `dependencyManagement` 블록을 사용한다.

```xml
<dependencyManagement>
		<dependencies>
		<dependency>
			<!-- Import dependency management from Spring Boot -->
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-dependencies</artifactId>
			<version>2.1.3.RELEASE</version>
			<type>pom</type>
			<scope>import</scope>
		</dependency>
	</dependencies>
</dependencyManagement>
```

* 만약 위처럼 Parent POM없이 직접 의존성 관리 블록을 사용할 경우엔, 버전 overriding을 할 경우, 동일 블록내에서 하면 된다.

```xml
<dependencyManagement>
	<dependencies>
		<!-- Override Spring Data release train provided by Spring Boot -->
		<dependency>
			<groupId>org.springframework.data</groupId>
			<artifactId>spring-data-releasetrain</artifactId>
			<version>Fowler-SR2</version>
			<type>pom</type>
			<scope>import</scope>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-dependencies</artifactId>
			<version>2.1.3.RELEASE</version>
			<type>pom</type>
			<scope>import</scope>
		</dependency>
	</dependencies>
</dependencyManagement>
```

* 하지만 이런 방법들은 점점 xml이 복잡해지기 때문에, parent POM을 사용하는게 낫다.

##### 13.2.3 Using the Spring Boot Maven Plugin

* Maven 빌드와 관련된 플러그인들도 추가할 수 있는데, 대표적으로 실행가능한 Jar를 만드는 플러그인이 있다.

```xml
<build>
	<plugins>
		<plugin>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-maven-plugin</artifactId>
		</plugin>
	</plugins>
</build>
```

* Parent POM안에 Maven Plugin에 관련된 정보들이 저장되어 있다. 사용하지 않을 경우 별도 명시 필요

---

### Appendix E. The Executable Jar Format

* `spring-boot-loader` 안에 실행가능한 Jar와 War filer을 만드는 것을 지원한다.

#### E.1.1 The Executable Jar File Structure

`spring-boot-loader`에 호환되는 jar 파일들의 구조는 아래의 형상을 따라야 한다.

```
example.jar
 |
 +-META-INF
 |  +-MANIFEST.MF
 +-org
 |  +-springframework
 |     +-boot
 |        +-loader
 |           +-<spring boot loader classes>
 +-BOOT-INF
    +-classes
    |  +-mycompany
    |     +-project
    |        +-YourClasses.class
    +-lib
       +-dependency1.jar
       +-dependency2.jar
```

* 어플리케이션 클래스들은 `BOOT-INF/classes` 경로에, 의존성들은 `BOOT-INF/lib`에 있어야 한다.

#### E.1.2 The Executable War File Structure

```
example.war
 |
 +-META-INF
 |  +-MANIFEST.MF
 +-org
 |  +-springframework
 |     +-boot
 |        +-loader
 |           +-<spring boot loader classes>
 +-WEB-INF
    +-classes
    |  +-com
    |     +-mycompany
    |        +-project
    |           +-YourClasses.class
    +-lib
    |  +-dependency1.jar
    |  +-dependency2.jar
    +-lib-provided
       +-servlet-api.jar
       +-dependency3.jar
```

* Runtime시에 제공되는 의존성들은, `WEB-INF/lib-provided`에 넣어준다.

### E.2 Spring Boot’s “JarFile” Class

* `org.springframework.boot.loader.jar.JarFile`를 사용해 이러한 nested jar파일들을 읽음
  * 초기 로딩시, 각각의 `Jarentry`들이 아래의 형태로, jar파일들의 현재 위치를 가지고 온다.

```
myapp.jar
+-------------------+-------------------------+
| /BOOT-INF/classes | /BOOT-INF/lib/mylib.jar |
|+-----------------+||+-----------+----------+|
||     A.class      |||  B.class  |  C.class ||
|+-----------------+||+-----------+----------+|
+-------------------+-------------------------+
 ^                    ^           ^
 0063                 3452        3980
```

* 개발자가 만든 `A.class` 는,  `myapp.jar` 의 `0063` 의 위치
* nested Jar의 `B.class`와 `C.class`는 `myapp.jar`의 `3452`와 `3990`에 각각 위치
* 이러한 매핑 정보를 통해, 실제 외부에 존재하는 jar파일에서 필요한 특정 정보를 찾는다.
  * 모든 데이터를 읽을 필요도 없고, unpacking 할 필요도 없다.

#### E.2.1 Compatibility with the Standard Java “JarFile”

스프링 부트 로더에서 지원하는 `JarFile`은 `java.util.jar.JarFile`을 상속하고 있기 때문에, 기존의 자바와 호환이 가능하다.

### E.3 Launching Executable Jars

* 실행가능한 jar가 기동되는 시작 클래스는 `org.springframework.boot.loader.Launcher` 이다.
* 이를 통해 적절한 `URLClassLoader` 를 만들고, `main()`를 호출한다.

* 세 가지의 종류의 subclass가 존재하며, nested Jar나 War에서 리소스를 읽는 것이 목적이다.
  * `JarLauncher` & `WarLauncher`: 위에서 설명한 고정된 경로에서 읽는다.
  * `PropertiesLauncher` : 별도의 프로퍼티 설정을 통해 경로를 변경 & 추가할 수 있다.

#### E.3.1 Launcher Manifest

Jar파일 로딩을 위한 런쳐에 대한 방법으로, `META-INF/MANIFEST.MF`에 정의하며 maven 빌드시 자동으로 생성된다.

**실행가능한 Jar의 기본 값**

```
ain-Class: org.springframework.boot.loader.JarLauncher
Start-Class: com.mycompany.project.MyApplication
```

**실행가능한 War의 기본 값**

```
Main-Class: org.springframework.boot.loader.WarLauncher
Start-Class: com.mycompany.project.MyApplication
```

#### E.3.2 Exploded Archives

일부 특수한 PaaS 환경에서는 실행전에 unpack이 필요할 수 있다. 예를들어 [Cloud Foundry](https://www.cloudfoundry.org/)의 경우이다.

```
$ unzip -q myapp.jar
$ java org.springframework.boot.loader.JarLauncher
```

### E.5 Executable Jar Restrictions

* **압축에 관련된 부분:** Jar의 `ZipEntry` 는 `ZipEntry.STORED`메소드를 통해 저장 되어야 한다.
* **System cassloader**: `Thread.getContextClassLoader()`를 사용해야 한다.
  * `ClassLoader.getSystemClassLoader()`를 사용하지 말아야 한다.
  * `java.util.Logging`도 같은 이유에서 사용하지 말아야 한다.

---





