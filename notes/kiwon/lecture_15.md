## 레퍼런스로 스터디하는 스프링부트 15강

##### 29.1.5 Static Content ...이어서

* 추가적으로, [Webjars 컨텐트](https://www.webjars.org/)은 특수한 케이스로 취급되어서, `/webjars/**` 형태로 제공되는 파일에 접근할 수 있다.
  * 어플리케이션을 Jar로 패키지할 경우엔 `src/main/webapp` 경로를 사용하지 말아야 한다.
* `webjars-locator-core`를 의존성 추가하고, `/webjars/jqeury/jquery.min.js`와 같이 추가하면 된다.
  * Jboss의 경우엔, 위 의존성 대신에  `webjars-locator-jboss-vfs` 를 추가해야 한다.
* cache busting목적으로 아래와 같이 설정해서, content hash를 리소스 url에 추가하게 할 수 있다.

```properties
spring.resources.chain.strategy.content.enabled=true
spring.resources.chain.strategy.content.paths=/**
```

#### 29.1.6 Welcome Page

* 스프링부트는 설정된 static 컨텐트 경로에서 `index.html`을 찾고, 없으면, `index` 템플릿을 찾는다. 그리고 이 자원들은 어플리케이션의 Welcome 페이지로 자동으로 사용된다.

#### 29.1.7 Custom Favicon

* static 경로와 루트 경로에서 `favicon.ico`를 찾아서, 어플리케이션의 favicon으로 사용한다.

#### 29.1.8 Path Matching and Content Negotiation

* 스프링 MVC는 HTTP 요청 인입에 대해서 경로를 보고 어플리케이션에 정의된 핸들러로 매핑해준다.

* 스프링 부트는 suffix 패턴 매칭을 **비활성화**한 상태이기 때문에, `"GET /projects/spring-boot-.json"`를 `@GetMapping("/project/spring-boot")`와 매핑해주지 않는다.

  * 위의 방식은 스프링 MVC의 best practice로 간주되어왔지만, 이는 과거에 HTTP 클라이언트가 적합한 `Accept` request header를 제공하지 않았기 때문이었다.

* 현재는 컨텐츠 협상이 좀더 신뢰할 수 있지만, 아직도 `Accept` request header을 사용하지 않는 요청에 대해서 query 파라미터를 활용하는 방법이 있다.

  * `"GET /projects/spring-boot?format=json"`이 `GetMapping("/projects/psringboot")`에 매핑

  ```properties
  spring.mvc.contentnegotiation.favor-parameter=true
  
  # "format"이 기본 파라미터 이름으로 되어있지만 아래처럼 바꿀 수 있다.
  # spring.mvc.contentnegotiation.parameter-name=myparam
  
  # 추가적인 file 확장자나 미디어타입을 아래처럼 등록할 수 있다.
  spring.mvc.contentnegotiation.media-types.markdown=text/markdown
  ```

  * 쿼리 파라미터를 통한 요청은 **`Accept` 헤더보다 우선한다.**

* 만약에 suffix 패턴 매칭을 끝까지 지원하고 싶다면 아래의 설정을 해주면 된다.

```properties
spring.mvc.contentnegotiation.favor-path-extension=true
spring.mvc.pathmatch.use-suffix-pattern=true
```

* 파일 확장자 및 미디어타입 추가를 아래와 같이 할 수 있다.

```properties
spring.mvc.contentnegotiation.media-types.adoc=text/asciidoc
```

