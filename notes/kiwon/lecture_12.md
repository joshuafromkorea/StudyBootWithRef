## 레퍼런스로 스터디하는 스프링부트 12강

#### 26.6 Custom Log Configuration

* 다양한 로깅 시스템을 라이브러리를 추가하여 활성화 시킬 수 있고, 또한 `Environment` 프로퍼티에 `logging.config`값을 통해서 커스터마이즈 할 수 있다.
*  `org.springframework.boot.logging.LoggingSystem`에 시스템 프로퍼티를 주어서, 스프링 부트가 특정한 로깅 시스템을 사용하도록 강제할 수 있다.
  * 해당 프로퍼티의 값은 `LoggingSystem` 구현체의 "fully qualified class" 이름이어야 한다.
  * 또한 `none`을 값으로 주면, 스프링 부트의 로깅 설정을 전부 **비활성화**할 수 있다.
* 스프링 로깅은 `ApplicationContext`가 **생성되기 전**에 초기화 된다. 즉 로깅 시스템을 바꾸거나 비활성화하는 **유일한**방법은 System 프로퍼티로만 가능하다.

* 사용하는 로깅 시스템과 커스터마이징을 위해 로딩되는 파일 정보이다.

| Logging System          | Customization                                                |
| ----------------------- | ------------------------------------------------------------ |
| Logback                 | `logback-spring.xml`, `logback-spring.groovy`, `logback.xml`, or `logback.groovy` |
| Log4j2                  | `log4j2-spring.xml` or `log4j2.xml`                          |
| JDK (Java Util Logging) | `logging.properties`                                         |

* `-spring`이 포함된 값을 사용하기를 권하는데, 이래야지 스프링이 완전하게 로깅 초기화를 할 수 있다.

* 아래와 같이, 스프링 `Environment`의 값들 중 다른 몇개의 값들은 시스템 프로퍼티로 전달되어서, 로깅 커스터마이징을 지원한다.

| Spring Environment                  | System Property                 | Comments                                                |
| ----------------------------------- | ------------------------------- | ------------------------------------------------------- |
| `logging.exception-conversion-word` | `LOG_EXCEPTION_CONVERSION_WORD` | 로그 익셉션이 발생하면, conversion-word가 사용됨        |
| `logging.file`                      | `LOG_FILE`                      | 로그파일 이름으로 사용됨                                |
| `logging.file.max-size`             | `LOG_FILE_MAX_SIZE`             | 최대 파일 사이즈                                        |
| `logging.file.max-history`          | `LOG_FILE_MAX_HISTORY`          | 아카이빙 최대 갯수                                      |
| `logging.path`                      | `LOG_PATH`                      | 로그 파일 경로 설정                                     |
| `logging.pattern.console`           | `CONSOLE_LOG_PATTERN`           | 기본 Logback 설정에서만 사용되는 로깅 패턴 설정(stdout) |
| `logging.pattern.dateformat`        | `LOG_DATEFORMAT_PATTERN`        | 기본 Logback 설정에서만 사용되는 날짜 형식 설정         |
| `logging.pattern.file`              | `FILE_LOG_PATTERN`              | 로그파일을 위한 패턴 설정                               |
| `logging.pattern.level`             | `LOG_LEVEL_PATTERN`             | 로그레벨 표현을 위한 설정 기본 값은 %5p                 |
| `PID`                               | `PID`                           | 현재 프로세스 ID                                        |

* 지원되는 모든 로깅 시스템은 시스템 프로퍼티를 조회할 수 있다.

#### 26.7 Logback Extensions

* 스프링 부트는 고급 설정을 위해 Logback Extension을 제공하며, `logback-spring.xml`파일에서 사용할 수 있다.
  * `logback.xml`이 로딩되는 사이클이 너무 이르기 때문에 extension 사용을 지원하지 않는다.

##### 26.7.1 Profile-specific Configuration

* `<springProfile>` 태그를 통해 로그설정파일의 특정 부분을 포함하거나 제외하기 위해 활성화된 스프링 프로파일을 사용할 수 있다.
  * `name` 어트리뷰트를 사용해 프로파일을 지정한다.
  * 이름 뿐만 아니라 프로파일 표현식을 사용할 수 있다. `product & (eu-central | eu-west)` 등

```xml
<springProfile name="staging">
	<!-- "staging"이 활성화 되면 동작하는 설정 -->
</springProfile>

<springProfile name="dev | staging">
	<!-- "dev" 혹은 "staging"이 활성화 되면 동작하는 설정 -->
</springProfile>

<springProfile name="!production">
	<!-- "production"이 비활성화 되었을 때만 동작하는 설정 -->
</springProfile>
```

##### 26.7.2 Environment Properties

* `<springProperty>` 태그를 통해 스프링 `Environment`의 값들을 Logback 설정에서 사용할 수 있다.
  * `application.properties`에 담은 값들을 사용할 수 있다.
  * Logback의 기본 `<property>`와 유사하게 동작하지만,`value` 어트리 뷰트 대신 `source`를 사용하는 차이점이 있다.
  * `Environment`에 값이 없는 경우를 대비해서 `defaultValue` 어트리뷰트를 사용하여 기본 값을 지정할 수 있다.
  * `scope` 어트리뷰트로 `local` 밖에서도 사용할 수 있게 지정할 수 있다.

```xml
<springProperty scope="context" name="fluentHost" source="myapp.fluent.host"
		defaultValue="localhost"/>
<appender name="FLUENT" class="ch.qos.logback.more.appenders.DataFluentAppender">
	<remoteHost>${fluentHost}</remoteHost> <!-- 위에서 등록한 값을 사용 -->
	...
</appender>
```

* `source` 어트리뷰트는 반드시 kebab 형식(예. `my.property-name`)을 따라야 한다.