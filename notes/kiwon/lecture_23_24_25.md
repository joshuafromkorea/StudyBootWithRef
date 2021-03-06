## 레퍼런스로 스터디하는 스프링부트 23강

### 29.2 The "Spring WebFlux Framework"

* 스프링 웹플럭스는 새로운 리액티브 웹 프레임워크로, 스프링 프레임워크 5.0에서 소개되었다.
  * 스프링 MVC와 달리, 서블릿 API를 필요로 하지 않으며, 완전하게 비동기/Non-blocking이다
  * [the Reactor project](https://projectreactor.io/) 를 통해  [Reactive Streams](http://www.reactive-streams.org/)의 스펙을 구현한다.
* 스프링 웹플럭스는 두 가지 형태로 사용할 수 있다.
  * 어노테이션 기반: 기존의 Spring MVC와 유사한 형태이다.
  * 함수 기반: 라우팅 설정을 실제 요청처리에서 분리한 형태이다.
* 웹플럭스를 사용하기 위해선, `spring-boot-starter-webflux`를 추가하면 된다.

##### 어노테이션 사용

```java
@RestController
@RequestMapping("/users")
public class MyRestController {

	@GetMapping("/{user}")
	public Mono<User> getUser(@PathVariable Long user) {
		// ...
	}

	@GetMapping("/{user}/customers")
	public Flux<Customer> getUserCustomers(@PathVariable Long user) {
		// ...
	}

	@DeleteMapping("/{user}")
	public Mono<User> deleteUser(@PathVariable Long user) {
		// ...
	}

}
```

##### 함수 사용

```java
@Configuration
public class RoutingConfiguration {

	@Bean
	public RouterFunction<ServerResponse> monoRouterFunction(UserHandler userHandler) {
		return route(GET("/{user}").and(accept(APPLICATION_JSON)), userHandler::getUser)
				.andRoute(GET("/{user}/customers").and(accept(APPLICATION_JSON)), userHandler::getUserCustomers)
				.andRoute(DELETE("/{user}").and(accept(APPLICATION_JSON)), userHandler::deleteUser);
	}

}

@Component
public class UserHandler {

	public Mono<ServerResponse> getUser(ServerRequest request) {
		// ...
	}

	public Mono<ServerResponse> getUserCustomers(ServerRequest request) {
		// ...
	}

	public Mono<ServerResponse> deleteUser(ServerRequest request) {
		// ...
	}
}
```

#### 29.2.1 Spring WebFlux Auto-configuration

* 스프링 부트는 스프링 웹플럭스를 위한 자동설정을 제공하며 기본 추가기능은 다음과 같다
  * `HttpMessageReader`와 `HttpMessageWriter`인스턴스를 위한 코덱
  * Webjar를 위한 지원을 포함한 정적 리소스를 위한 지원

#### 29.2.2 HTTP Codecs with `HttpMessageReaders` and `HttpMessageWriters`

* 스프링 웹플럭스는 HTTP 요청과 응답에 대한 변환을 위해서 `HttpMessageReader`와 `HttpMessageWriter`를 사용한다.
* `CodecConfigurer`를 통해서 클래스패스에 존제하는 라이브러리를 검색해 적절한 기본설정을 제공한다.

#### 29.2.3 Static Content

* 기본적으로 스프링부트는 정적 컨텐츠 제공을 위해서 클래스패스의 `/static` (혹은 `/public` 혹은, `/resource` 혹은 `/META-INF/resources`)를 사용한다. 
  * `WebFluxConfigurer`의 `addResourceHandlers` 메서드를 오버라이딩해서 변경할 수 있다.
* 기본적으로 리소스들은  `/**`로 매핑되지만, `spring.webflux.static-path-pattern`프로퍼티를 통해 변경할 수 있다.

* 스프링 웹플럭스는 서블릿 API에 의존하지 않기 때문에, war 파일로 배포할 수 없으며, `src/main/webapp` 디렉토리를 사용하면 안된다.

#### 29.2.4 Template Engines

* 스프링 웹플럭스도 동일하게, FreeMarker, Thymeleaf, Mustache를 지원한다.
* 만약에 위의 템플릿엔진을 사용한다면, `src/main/resources/templates` 경로에서 템플릿을 찾게된다.

#### 29.2.5 Error Handling

* 스프링부트는 `WebExceptionHandler`를 통해, 어플리케이션이 적절하게 모든 에러를 핸들링할 수 있게 한다.
  * 머신 클라이언트에게는 JSON으로 응답을 보내며, Browser에게는 HTML페이지를 보여준다.
* 만약 웹플럭스의 에러 처리 방식에 대한 변경을 하고 싶으면, `ErrorWebExceptionHandler`를 구현하면 된다.

```java
public class CustomErrorWebExceptionHandler extends AbstractErrorWebExceptionHandler {

	// Define constructor here

	@Override
	protected RouterFunction<ServerResponse> getRoutingFunction(ErrorAttributes errorAttributes) {

		return RouterFunctions
				.route(aPredicate, aHandler)
				.andRoute(anotherPredicate, anotherHandler);
	}

}
```

#### 29.2.6 Web Filters

* 웹플럭스는 `WebFilter`를 제공하여, HTTP 요청 응답에서 작동하는 필터로 구현되게 할 수 있다.

  * 어플리케이션 컨텍스트에서 발견되는 `WebFilter` Bean들은 자동적으로 사용되게 된다.
  * `Webfilter`간의 순서는 `@Order`를 붙이거나 `Ordered`를 붙이면 된다.

  ```java
  @Component
  public class BookFilter implements WebFilter {
      @Override
      public Mono<Void> filter(ServerWebExchange serverWebExchange,
                               WebFilterChain webFilterChain) {
          System.out.println("book filter");
          return webFilterChain.filter(serverWebExchange);
      }
  }
  ```

* 스프링이 자동적으로 설정해주는 `WebFilter`들의 우선순위는 다음과 같다.

| Web Filter                             | Order                            |
| -------------------------------------- | -------------------------------- |
| `MetricsWebFilter`                     | `Ordered.HIGHEST_PRECEDENCE + 1` |
| `WebFilterChainProxy`(Spring Security) | `-100`                           |
| `HttpTraceWebFilter`                   | `Ordered.LOWEST_PRECEDENCE - 10` |

### 29.3 JAX-RS and Jersey

* REST endpoint를 만들기 위해 JAX-RS 프로그래밍 모델을 사용하는 걸 선호한다면, 구현체중 하나를 사용하면 된다.
  * [Jersey](https://jersey.github.io/) and [Apache CXF](https://cxf.apache.org/)
* Jersey의 경우엔 네이티브하게 스프링을 지원하고, 따라서 스프링 부트에서도 자동설정을 제공한다.
  * `spring-boot-starter-jersey` 를 의존성에 추가한 뒤, `ResourceConfig`를 상속받는 Bean을 하나 만들어 모든 endopoint를 등록하면 된다.

```java
@Component
public class JerseyConfig extends ResourceConfig {

	public JerseyConfig() {
		register(Endpoint.class);
	}

}
```

### 29.4 Embedded Servlet Container Support

* 스프링 부트는 Tomcat, Jetty, 그리고 Undertow와 같은 내장 컨테이너 서버를 포함하여 제공한다.
  * 기본적으로 내장된 서버는 `8080` 포트를 사용한다.

#### 29.4.1 Servlets, Filters, and listeners

* 내장 서블릿 컨테이너를 사용하면, 서블릿, 필터, 그리고 모든 리스너를 스프링 Bean 혹은 서블릿 컴포넌트를 스캔하여 등록할 수 있다.

##### Registering Servlets, Filters, and Listners as Spring Bean

* 모든 `Servlet`, `Filter` 혹은 서블릿 `*Listner`들은 스프링 Bean일 경우엔 모두 내장컨테이너에 등록된다.
* 기본적으로, 만약 컨텍스트가 하나의 서블릿만 있을 때에는, `/` 서블릿에 매핑된다.
  * 만약 여러개가 있으면 해당 bean 이름이 `/*`에 매핑된다.
* 상기한 전통적인 매핑방법을 수정하고 싶으면, `ServletRegistrationBean`, `FilterRegistrationBean` 그리고 `ServletListnerRegistrationBean` 클래스를 사용하여 제어할 수 있다.
* 스프링 부트는 여러가지 자동설정을 제공하기 때문에, 이로 인해 정의된 필터들이 있으며 해당 필터들의 순서는 다음과 같다.

| Servlet Filter                   | Order                            |
| -------------------------------- | -------------------------------- |
| `OrderedCharacterEncodingFilter` | `Ordered.HIGHEST_PRECEDENCE`     |
| `WebMvcMetricsFilter`            | `Ordered.HIGHEST_PRECEDENCE + 1` |
| `ErrorPageFilter`                | `Ordered.HIGHEST_PRECEDENCE + 1` |
| `HttpTraceFilter`                | `Ordered.LOWEST_PRECEDENCE - 10` |

* 필터 Bean들에 순서를 주지 않는 경우가 대부분의 경우 안전하지만 만약 순서를 주어야 할 경우엔
  * Request Body를 읽는 필터의 경우에는 `Ordered.HIGHEST_PRECEDENCE`를 주지 말아야 한다.
  * 서블릿 필터가 요청을 Wrapping 하는 경우 `OrderedFilter.REQUEST_WRAPPER_FILTER_MAX_ORDER`와 같거나 낮은 우선순위를 주어야 한다.

#### 29.4.4 Customizing Embedded Servlet Containers

* 일반적인 서블릿 컨테이너 세팅은 스프링 `Environment` properties 를 통해서 할 수 있다.
  * 보통의 경우엔 `application.properties` 파일을 통해서 하게 된다.
* 일반 적인 서버 세팅은 다음과 같다
  * 네트워크: HTTP 요청에 대한 포트설정 (`server.port`), 바인드할 인터페이스 주소 `server.address` 등
  * 세션:  세션이 영속적인지 여부 (`server.servlet.session.persistence`), 세션 타임아웃 정보 (`server.servlet.session.timeout`), 세션 데이터 위치 (`server.servlet.session.store-dir`), 세션 쿠키 설정 (`server.servlet.session.cookie.*`)
  * 에러 관리: 에러페이지 = `server.error.path`
  * [SSL](https://docs.spring.io/spring-boot/docs/2.1.3.RELEASE/reference/htmlsingle/#howto-configure-ssl)
  * [HTTP compression](https://docs.spring.io/spring-boot/docs/2.1.3.RELEASE/reference/htmlsingle/#how-to-enable-http-response-compression)

