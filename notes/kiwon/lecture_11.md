## 레퍼런스로 스터디하는 스프링부트 11강

### 25. Profile

* **Spring Profile**의 역할은 어플리케이션의 설정을 **분리**하고 특정환경에서만 **활성화**시키는 것이다. 

* `@Component` 혹은 `@Configuration` 은 `@Profile`로 표시되면, 제약에 대해 명시하는 것이다.

```java
@Configuration
@Profile("production")
public class ProductionConfiguration {
}
```

* `spring.profiles.active` `Environment`를 통해 어떤 Profile을 활성화 시킬지 결정할 수 있다.

```properties
spring.profiles.active=dev, hsqldb
```

* 커매드 라인 실행을 통해 활성화 시킬 수도 있다. `--spring.profiles.active=dev,hsqldb`

#### 25.1 Adding Active Profiles

* `PropertySource`의 우선순위에 따라, 프로파일의 활성화도 우선순위가 정해진다.
  * 즉 프로퍼티 파일에 활성화 시킨 프로파일 정보를 Command Line에서 **교체**가능 하다는 것이다.
  * **교체**하는 것 보다, **추가**하는게 효율적인 상황도 있다

* `spring.profiles.include` 프로퍼티를 통해 특정프로파일에 포함되는 다른 프로파일들을 설정할 수 있다.

```properties
---
my.property: fromyamlfile
---
spring.profiles: prod
spring.profiles.include:
  - proddb
  - prodmq
```

* 위의 프로퍼티 파일을 포함하는 어플리케이션 실행시, `--spring.profiles.active=prod`와 함께 실행하면 `proddb`와 `prodmq`프로파일도 같이 활성화 된다.

#### 25.2 Programatically Setting Profiles

* 어플리케이션 실행전에 `SpringApplication.setAdditionalProfiles(...)`를 실행하면 소스코드를 통한 프로파일 활성화가 가능하다.
* `ConfigurableEnvironment` 인터페이스를 사용한 활성화도 가능하다.

#### 25.3 Profile-specific Configuration Files

* 특정 프로파일로 명시한 모든 리소스들은 

### 26. Logging

* Spring Boot는 [**Commons Logging**](https://commons.apache.org/proper/commons-logging/)을 사용하지만, 로깅 구현에 대해서 열려있다.
* [Java Util Logging](https://docs.oracle.com/javase/8/docs/api//java/util/logging/package-summary.html), [Log4j2](https://logging.apache.org/log4j/2.x/), [Logback](http://logback.qos.ch/)를 위한 기본 설정을 제공한다.
  * 각각의 경우 Logger가 콘솔 출력을 기본으로 하고 있고, 파일출력도 지원한다.
* "Starters" 를 사용하는 경우 **Logback**이 로깅에 사용된다.

#### 26.1 Log Format

스프링 부트의 기본 로그 형식의 샘플과 그 상세를 살펴보자

```powershell
2014-03-05 10:57:51.253  INFO 45469 --- [ost-startStop-1] o.s.web.context.ContextLoader : Root WebApplicationContext: initialization completed in 1358 ms
```

* **날짜와 시간**: 밀리세컨드 단위까지 표현되며, 쉽게 정렬 가능하다

```powershell
2014-03-05 10:57:51.112
```

* **로그 수준(Log Level)**: `ERROR`, `WARN`, `INFO`, `DEBUG`, `TRACE`

```powershell
INFO
```

* **프로세스 ID**

```powershell
45469
```

* **`---` 구분자**: 실제 로그 메시지의 시작을 나타내는 구분자
* **스레드 이름**: `[]`로 감싸져 있으며, 콘솔 출력에서는 축약될 수 있다.

```powershell
[ost-startStop-1]
```

* **Logger 이름**: 기본적으로 해당 클래스 이름이 사용되며, 축약된다

```powershell
o.s.web.context.ContextLoader
```

* **Log 메시지**

```powershell
Root WebApplicationContext: initialization completed in 1358 ms
```

#### 26.2 Console Output

* 기본적으로 `ERROR`, `WARN`, `INFO` 레벨의 로그가 출력되고, `--debug` 플래그를 통해 **"debug 모드"**를 활성화 할 수 있다.

```powershell
$ java -jar myapp.jar --debug
```

* **"debug 모드"**가 활성화 되면, 내장된 컨테이너(tomcat), Hibernate, Spring Boot등의 로거들이 더 많은 정보를 출력하게 된다.
  * 하지만, **"debug 모드"**의 활성화가  `DEBUG` 수준의 모든 로그를 출력하도록 **하지는 않는다.**
* `--trace`로 활성화시키는 **"trace 모드"**도, 위에서 말한 핵심 로거들의 `TRACE`로그를 출력하게 한다.

##### 26.2.1 Color-coded Output

* ANSI를 지원하는 터미널에서는 색표현을 통해 가독성을 높일 수 있다.
* `spring.output.ansi.enabled` 프로퍼티 값에 `ALWAYS`, `DETECT`, `NEVER` 값을 주어 조정가능 하다
* 기본 값은 다음과 같다

| Level   | Color  |
| ------- | ------ |
| `FATAL` | Red    |
| `ERROR` | Red    |
| `WARN`  | Yellow |
| `INFO`  | Green  |
| `DEBUG` | Green  |
| `TRACE` | Green  |

* 로그의 색 설정은 `$clr` 변환자를 사용하여 할 수 있다.

```java
"%clr(%d{yyyy-MM-dd HH:mm:ss.SSS}){yellow}" //날짜와 시간을 노란색으로
```

* 이외에 지원하는 색상 변환자는 `blue` ,`cyan`, `faint`, `green` ,`magenta`, `red`, `yellow`가 있다.

#### 26.3 File Output

* `logging.file` 혹은 `logging.path` 프로퍼티에 값을 주어서, 파일 출력을 **추가로 활성화**시킬 수 있다.
* 아래의 표는 해당 프로퍼티에 값을 설정할 때 로깅되는 경우의 수이다.

| `logging.file` | `logging.path`     | 값 예시    | 설명                                                         |
| -------------- | ------------------ | ---------- | ------------------------------------------------------------ |
| *(none)*       | *(none)*           |            | Console 출력만 됨                                            |
| Specific file  | *(none)*           | `my.log`   | 지정한 이름의 파일이 생성됨, 이름은 절대 혹은 상대 경로 가능 |
| *(none)*       | Specific directory | `/var/log` | 지정한 디렉토리에 `spring.log`라는 이름의 파일로 생성됨, 경로는 절대 및 상대 경로 가능 |

* 로그파일의 기본 사이즈 제한은 10MB이며, `logging.file.max-size`프로퍼티 값으로 변경된다.
* `logging.file.max-history` 프로퍼티에 값이 없으면, 무기한으로 아카이빙 된다.

#### 26.4 Log Levels

* 스프링 `Environment` 에 `logging.level.<logger-name>=<level>`형식으로 값을 주면 지원하는 모든 로깅 시스템의 수준을 지정할 수 있다.
  * `root` 로거의 경우 `logging.level.root`로 된다.
* `application.properties`에 명시한 예

```properties
logging.level.root=WARN
logging.level.org.springframework.web=DEBUG
logging.level.org.hibernate=ERROR
```

#### 26.5 Log Groups

* 연관된 로거들을 하나의 그룹으로 묶어서 설정하는 것이 효과적일 수 있다.
  * 예) Tomcat과 관련된 모든 로그레벨을 설정하는 경우
* 이를 위해, `Environment`에 로깅 그룹을 지정하여서 설정할 수 있다.

```properties
logging.group.tomcat=org.apache.catalina, org.apache.coyote, org.apache.tomcat
```

* 이렇게 지정된 로그 그룹은, 기본적으로 로거의 레벨을 정하는 것과 동일하게 설정할 수 있다.

```properties
logging.level.tomcat=TRACE
```

* 스프링 부트는 특별히 두개의 그룹을 미리 정의한 상태로 제공한다

| Name | Loggers                                                      |
| ---- | ------------------------------------------------------------ |
| web  | `org.springframework.core.codec`, `org.springframework.http`, `org.springframework.web` |
| sql  | `org.springframework.jdbc.core`, `org.hibernate.SQL`         |

