## 레퍼런스로 스터디하는 스프링부트 5강

### 20. Developer Tools

* `spring-boot-devtools` 모듈을 프로젝트에 포함시키면, 개발시에 특수한 추가기능을 사용할 수 있다.

###### Maven 설정

```xml
<dependencies>
	<dependency>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-devtools</artifactId>
		<optional>true</optional>
	</dependency>
</dependencies>
```

#### 20.1 Property Defaults

스프링부트의 **개발자도구**는 아래와 같은 기본 설정을 가지고 있다. 

* 스프링부트 및 관련 모듈들이 제공하는 **캐시 기능 끄기**
  * **[template engines](https://docs.spring.io/spring-boot/docs/2.1.3.RELEASE/reference/htmlsingle/#boot-features-spring-mvc-template-engines)** 와 같은 캐시기능은 개발환경에서 변경을 확인하는데 방해가 될 수 있기 때문
  * Thymeleaf와 같은 모듈의 캐시기능도 자동으로 오프설정으로 변경한다.
* `web` 로깅 그룹의 설정을 `DEBUG`로 활성화 시킨다.
  *  Spring MVC나 Spring WebFlux 어플리케이션 request에 대한 정보를 볼 수 있게 해줌
* 이러한 기본설정들은 `spring.devtools.add-properties`를 `false`로 세팅하면 된다.
* [`DevToolsPropertyDefaultsPostProcessor`](https://github.com/spring-projects/spring-boot/blob/v2.1.3.RELEASE/spring-boot-project/spring-boot-devtools/src/main/java/org/springframework/boot/devtools/env/DevToolsPropertyDefaultsPostProcessor.java) 에서 다른 기본설정을 확인 가능

```java
	static {
		Map<String, Object> properties = new HashMap<>();
		properties.put("spring.thymeleaf.cache", "false");
		properties.put("spring.freemarker.cache", "false");
		properties.put("spring.groovy.template.cache", "false");
		properties.put("spring.mustache.cache", "false");
		properties.put("server.servlet.session.persistent", "true");
		properties.put("spring.h2.console.enabled", "true");
		properties.put("spring.resources.cache.period", "0");
		properties.put("spring.resources.chain.cache", "false");
		properties.put("spring.template.provider.cache", "false");
		properties.put("spring.mvc.log-resolved-exception", "true");
		properties.put("server.error.include-stacktrace", "ALWAYS");
		properties.put("server.servlet.jsp.init-parameters.development", "true");
		properties.put("spring.reactor.stacktrace-mode.enabled", "true");
		PROPERTIES = Collections.unmodifiableMap(properties);
}
```

##### 20.2 Automatic Restart

스프링부트 **개발자도구**(`spring-boot-devtools`)를 사용하는 어플리케이션은, 클래스패스내의 파일에 변경이 일어나면 자동으로 어플리케이션 재기동한다. **기본 설정**은 클래스패스의 모든 엔트리들은 변경사항 모니터링 대상이고, 뷰나 static resource들은 어플리케이션 재시작을 하지않아도 된다.

* 자동 재시작기능은 LiveReload(20.3) 와 궁합이 잘 맞는다. **JRebel**을 사용하면 해당 기능은 비활성화 되지만, 개발자도구의 나머지 기능은 작동한다.
* 자동 재시작기능은 application cotext의 `ShutdownHook`을 사용하기 때문에 해당 기능을 비활성화 시키면 안된다.
* `spring-boot`, `spring-boot-devtools`, `spring-boot-autoconfigure`, `spring-boot-acurator`, `spring-boot-starter`에 대한 변경은 무시된다.
* `ResourceLoader`를 사용하기 때문에, `ApplicationContext`의 `getResource`메소드의 오버라이딩은 지원하지 않는다.

###### Restart vs. Reload

* 스프링 부트가 사용하는 재기동(**Restart**) 기술은 **두 개의 classloader**를 사용하는 기법이다.
  * ***base* classloader**: 변경이 일어나지 않는 Class들(예. 서드파티 jar)이 올라간다.
  * ***restart* classloader**:활발하게 변경이 일어나는 개발대상들
* 변경감지로 인한 재기동이 일어나면, 기존 restart classloader는 폐기되고, 새롭게 만들어진다.
* **Reload**기술은 JRebel이 제공하는데, 클래스단위로 바이트 코드를 변경시키기는 방식이다.

###### 자동 재시작 예시(클래스 내용을 변경한 경우)

```powershell
13:54:02.439[      Thread-13] o.s.s.concurrent.ThreadPoolTaskExecutor  : Shutting down ExecutorService 'applicationTaskExecutor'

  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::        (v2.1.3.RELEASE)

13:54:02.632[  restartedMain] me.joshua.Exmaple                        : Starting Exmaple on joshua-Lenovo-ideapad-330S-14IKB with PID 12061 (/home/joshua/git/StudyBootWithRef/target/classes started by joshua in /home/joshua/git/StudyBootWithRef)
13:54:02.632[  restartedMain] me.joshua.Exmaple                        : No active profile set, falling back to default profiles: default
13:54:02.991[  restartedMain] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat initialized with port(s): 8080 (http)
13:54:02.994[  restartedMain] o.apache.catalina.core.StandardService   : Starting service [Tomcat]
13:54:02.994[  restartedMain] org.apache.catalina.core.StandardEngine  : Starting Servlet engine: [Apache Tomcat/9.0.16]
13:54:03.019[  restartedMain] o.a.c.c.C.[Tomcat].[localhost].[/]       : Initializing Spring embedded WebApplicationContext
13:54:03.020[  restartedMain] o.s.web.context.ContextLoader            : Root WebApplicationContext: initialization completed in 385 ms
13:54:03.124[  restartedMain] o.s.s.concurrent.ThreadPoolTaskExecutor  : Initializing ExecutorService 'applicationTaskExecutor'
13:54:03.157[  restartedMain] o.s.b.a.w.s.WelcomePageHandlerMapping    : Adding welcome page: class path resource [static/index.html]
13:54:03.186[  restartedMain] o.s.b.d.a.OptionalLiveReloadServer       : LiveReload server is running on port 35729
13:54:03.283[  restartedMain] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat started on port(s): 8080 (http) with context path ''
13:54:03.284[  restartedMain] me.joshua.Exmaple                        : Started Exmaple in 0.681 seconds (JVM running for 71.53)
13:54:03.296[  restartedMain] .ConditionEvaluationDeltaLoggingListener : Condition evaluation unchanged
13:54:09.358[nio-8080-exec-1] o.a.c.c.C.[Tomcat].[localhost].[/]       : Initializing Spring DispatcherServlet 'dispatcherServlet'
13:54:09.359[nio-8080-exec-1] o.s.web.servlet.DispatcherServlet        : Initializing Servlet 'dispatcherServlet'
13:54:09.371[nio-8080-exec-1] o.s.web.servlet.DispatcherServlet        : Completed initialization in 11 ms
```

##### 20.2.1 Logging changes in condition evaluation

자동 재시작이 일어나면, 상세 정보를 Log로 남기게 되며, 이런 설정은 아래의 프로퍼티로 비활성화 할 수 있다.

```properties
spring.devtools.restart.log-condition-evaluation-delta=false
```

##### 20.2.2 Excluding Resources

* 자동재시작이 필요하지 않은 자원들이 존재할 경우 제외할 수 있다.
  * 예를들면, Thymeleaf 템플릿들 중 `/META-INF/maven`, `/META-INF/resources`, `/resources`, `/static`, `/public`, `/templates`이하의 자원들은 재시작을 야기하지 않는다.
* 이러한 기본 설정을 변경하여 커스터마이즈 할 수 있는데 아래처럼 프로퍼티를 선언하면 된다.

```properties
# 이경우엔 기본설정이 무시되고, 아래가 기본설정이 된다
spring.devtools.restart.exclude=static/**,public/**
# 기본설정에 추가적으로 경로를 추가하고 싶을 경우
spring.devtools.restart.additional-exclude
```

##### 20.2.3 Watching Additional Paths

* 클래스패스에 없는 파일에 변경이 일어나도 자동재시작을 사용할 수 있다!
* `spring.devtools.restart.additional-paths`를 사용한 프로퍼티 설정을 사용하면 된다.
  * 위의 Excluding Resources 기법과 병행해 full restart 와 live reload 사이에서 조정할 수 있다.

##### 20.2.4 Disabling Restart

* `spring.devtools.restart.enable` 를 `application.properties`에 선언해 재시작 기능 비활성화 한다.
  * 위의 경우 restart classloader가 초기화 되지만 변경사항을 감지하지 않는다.
* `System` 설정에 해당 내용을 `false`로 설정하면 *완전하게* 재시작 기능을 비활성화 시킬 수 있다.
  * `SpringApplication.run(...)`이 호출되기 전에 선언되어야 한다.

```java
ublic static void main(String[] args) {
	System.setProperty("spring.devtools.restart.enabled", "false");
	SpringApplication.run(MyApp.class, args);
}
```

##### 20.2.5 Using a Trigger File

* IDE 기본 값이나 설정에 따라, 파일변경 감시를 통한 자동재시작이 오히려 불편할 수 있다.
* **Trigger File**을 지정하여 해당 파일의 변경을 통해 자동재시작 시킬 수 있다.

```properties
spring.devtools.restart.trigger-file=파일경로
```

##### 20.2.6 Customizing the Restart Classloader

* 언급한대로 두 개의 Classloader를 사용하는 자동재시작 기법은 문제를 일으키는 경우가 있다.

  * 사용중인 IDE에 열려있는 프로젝트는 **"restart"**, 다른 일반 `.jar`파일들은 **"base"**

* 다중 모듈 구조의 프로젝트의 경우, 각 classloader가 어떤 파일을 통해 동작할지를 명시할 수 있다.

  * `META-INF/spring-devtools.properties` 파일을 만든다.
  * `restart.exclude`접두사를 사용해 설정하면 **base** classloader로 할당된다.
  * `restart.include`를 접두사로 사용해 설정하면 **restart** classloader로 할당 된다.
  * 설정값의 value 값은 정규표현식을 사용하여 지정할 수 있다.

  ```properties
  restart.exclude.companycommonlibs=/mycorp-common-[\\w-]+\.jar
  restart.include.projectcommon=/mycorp-myproj-[\\w-]+\.jar
  ```

  * key 값은 위의 접두사를 사용하되 겹치지 말아야 한다.
  * 헤당 프로퍼티 파일은 클래스패스 경로에 어떠한 형태로도 있으면 로딩이 되는데, 다른 jar파일안에 있어도 로딩된다.

##### 20.2.7 Known Limitations

* `ObjectInputStream` 을 사용해서 **역직렬화** 된 객체에서는 자동재시작이 제대로 동작 하지 않을 수 있다.
* 이에 대한 대응 법은 아래의 두 방법을 같이 사용하는 것이다.
  * `ConfigurableObjectInputStream`
  * `Thread.currentThread().getContextClassLoader()`
* 또한 간혹 context classloader 를 사용하는 라이브러리들은 동일한 문제를 일으킬 수 있다.

#### 20.3 LiveReload

* 개발자도구에는 resource 변경이 일어나면 **브라우저를 새로고침** 하는 **LiveReload 서버**를 내장하고 있다.
* 이 기능을 활용하기 위해선 각 브라우저에 플러그인을 설치해야 한다. (http://livereload.com/)

* `spring.devtools.livereload.enabled`를 `false`로 하여서 비활성화 할 수 있다.
* **LiveReload** 서버는 다중 기동이 불가하여, 여러개 어플리케이션을 실행 시, 첫 번째만 기능을 지원한다.

#### 20.4 Global Settings

자신의 홈 디렉토리에, `.spring-boot-devtools.properties`(**파일이름 주의**) 라는 파일을 만들어 Global 설정을 만들 수 있다. 해당 파일안에 존재하는 모든 설정들은 개발자도구(devtools)를 사용하는 모든 스프링 부트 어플리케이션에 공통적으로 적용된다.

### 20.5 Remote Applications

* 스프링 부트 개발자도구는 Local 개발뿐 아니라, 원격으로 어플리케이션을 기동하는 경우의 기능을 포함한다.
* 원격기능은 opt-in이기 때문에, 사용을 위해서는 `devtools`가 패키지에 포함되도록 아래와 같이 설정한다.

```xml
<!-- Package as an executable jar -->
<build>
    <plugins>
        <plugin>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-maven-plugin</artifactId>
            <configuration> <!--빌드 추가 설정-->
                <excludeDevtools>false</excludeDevtools> 
            </configuration>
        </plugin>
    </plugins>
</build>
```

* 또한, 프로퍼티 파일에 `spring.devtools.remote.secret`을 사용해야 한다.
* 위 기능은 보안 위험성이 존재함으로, **상용환경에서는 절대 사용하지 말아야 한다.**
* 원격 도구는 아래의 두 가지 부분으로 제공 된다.
  * **서버사이드 Endpoint**: `spring.devtools.remote.secret`로 자동활성화 됨
  * **클라이언트 어플리케이션**: 수동으로 IDE에서 기동해 줘야 한다.

##### 20.5.1 Running the Remote Client Application

원격 클라이언트 어플리케이션은 IDE를 통해 실행되어야 하며, `org.springframework.boot.devtools` 패키지의 `RemoteSpringApplication`을 연결하려는 원격 프로젝트와 동일한 클래스패스에서 실행해야 한다. 레퍼런스 문서에서는 Eclipse를 통한 실행법을 예시로 보여주는데 아래와 같다.

* `Run Confiugrations...`메뉴를 실행한다.
* 새로운 `Java Application` "launch configuration"을 생성한다.
* 해당 프로젝트를 찾는다.
* `org.springframework.boot.devtools.RemoteSpringApplication`을 메인 클래스로 한다.
* `Program argument`에 원격 URL을 입력한다.

원격프로젝트와 동일한 클래스패스에서 실행되기 때문에, `spring.devtools.remote.secret`와 같은 설정 정보들도 읽어서 서버에 전달할 수 있다. 프록시등을 사용하는 경우엔 `spring.devtools.remote.proxy.host` 나`spring.devtools.remote.proxy.port`등을 고려할 수 있다.

##### 20.5.2 Remote Update

* 원격 프로젝트에 push된 변경 정보들은 로컬환경에서와 동일하게 자동 재기동을 동작하게 한다.
  * 위 기능은 원격 클라이언트가 실행될때만 작동한다.

### 21. Packaging Your Application for Production

개발환경을 위해 만들었던 실행가능한 Jar는 상용환경에 배포에도 적합하며, 클라우드 환경에도 매우 적합하다. 추가적인 상용환경을 위한 설정에 대해서는, [*Part V, “Spring Boot Actuator: Production-ready features”*](https://docs.spring.io/spring-boot/docs/2.0.0.RC2/reference/htmlsingle/#production-ready)참고하면 된다.

### 22. What to Read Next

스프링 부트를 어떻게 최적으로 사용하는가에 대한 기본적인 이해에 대해서 살펴보았고, 이어서 각각의 기능들에 대한 세부정보를 살펴볼 수 있다. 꼭 순서대로 살펴보지 않고 상용환경을 위한 기능들로 먼저 넘어가도 된다.