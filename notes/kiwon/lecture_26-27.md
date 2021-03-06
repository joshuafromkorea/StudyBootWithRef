## 레퍼런스로 스터디하는 스프링부트 26-27강

### 30. Security

* Spring Security가 클래스패스에 존재하면 기본적으로 어플리케이션은 Secure한 상태가 된다.

* 스프링 부트는 Spring Security의 컨텐츠 협상 전략을 사용해 `httpBasic`과 `formLogin`중 어떤 것을 사용할지 결정한다.

* 메서드 레벨의 security를 웹 어플리케이션에 적용하기 위해서, `@EnableGlobalMethodSecurity`를 원하는 설정과 함꼐 사용할 수 있다.

* 기본 `UserDetailsService`는 `user`라는 이름의 하나의 사용자를 가지고 있으며, 해당 유저의 암호는 INFO레벨 로그에 무작위로 생성되어서 기록된다.

  ```she
  Using generated security password: 0c8703c5-3414-4f45-8268-9ab591d8a073
  ```

  * `spring.security.user.name`과 `spring.security.user.password`에 값들을 변경하면 값을 변경할 수 있다.

* 기본적으로 제공되는 기능은 다음과 같다.
  * `UserDetailsService` bean이 등록되는데, `user`객체 하나와 인메모리 기능을 사용한다.
  * Form기반의 로그인이나 HTTP Basic 시큐리티 기능을 어플리케이션 전반에 제공
  * 인증 이벤트를 퍼블리싱 하기위한 `DefaultAuthenticationEventPublisher`

### 30.1 MVC Security

* 기본 시큐리티 설정은 `SecurityAutoConfiguration`과 `UserDetailsServiceConfiguration`에 구현되어있다.
* `SecurityAutoConfiguration`은 `SpringBootWebSecurityConfiguration`에서 웹 시큐리티를 임포트하고, `UserDetailsServiceAutoConfiguration`은 인증 설정을 한다.
* 기본 설정을 완전하게 끄고 싶다면, `WebSecurityConfigurerAdabter`를 Bean으로 추가하면 된다.
  * 이경우에도 `UserDetailsService` 설정이나 Actuator's Security는 비활성화 되지 않는다.
* `UserDetailsService` 설정을 끄고 싶은 경우엔, 아래의 세 가지중 하나의 Bean 을 만들면 된다.
  * `UserDetailsService`, `AuthenticationProvider` `AuthenticationManager`
* 접근 규칙은 `WebSecurityConfigurerAdapter`를 추가하고 커스터마이징해서 오버라이드할 수 있다.
  * 스프링 부트는 actuator endpoint와 정적 리소스에 대한 접근 규칙을 손쉽게 오버라이드할 수 있게 해준`다.
  * `EndopointRequest`는 `management.endpoints.web.base-path` 프로퍼티를 기준으로해서, `RequestMaptcher`를 만들 수 있도록 사용된다.
  * `PathRequest`는 일반적으로 사용되는 위치에 있는 리소스들을 위한 `RequestMatcher`를 만들 수 있게 해준다.

```java
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // @formatter:off
        http.authorizeRequests()
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                .requestMatchers(new AntPathRequestMatcher("/hello.html")).permitAll()
                .anyRequest().fullyAuthenticated()
                .and()
                .formLogin().permitAll()
                .and()
                .logout().permitAll();
        // @formatter:on
    }
}
```

* [Spring Boot samples](https://github.com/spring-projects/spring-boot/tree/v2.1.3.RELEASE/spring-boot-samples/) 링크를 통해 일반적으로 MVC 시큐리티를 사용하는 예제들을 볼 수 있다.

#### 30.2 WebFlux Security

* 스프링 MVC 어플리켕션과 유사하게, `spirng-boot-starter-security` 의존성을 추가하여 WebFlux 어플리케이션에도 시큐리티를 추가할 수 있다.
* `ReactiveSecurityAutoConfiguration`와 `UserDetailsServiceAutoConfiguration`에 기본 시큐리티 설정들이 구현되어있다.
* `ReactiveSecurityAutoConfiguration`은 `WebFluxSecurityConfiguration` 임포트하여 웹 시큐리티를 제공하고, `UserDetailsServiceAutoConfiguration`을 통해 인증 설정을 한다.
  * 해당 기능을 끄기 위해서는 `WebFilterChainProxy`를 Bean으로 추가하면 된다.
  * `UserDetailsService`를 끄기 위해선, `ReactiveUserDetailsService`나 `ReactiveAuthenticationManager`를 bean으로 추가하면 된다.
* `SecurityWebFilterChain`을 커스터마이징하여서 접근 규칙을 제어할 수 있다.
  * `EndpointRequest`를 사용해서 `ServerWebExchangeMatchewr`를 만들 수 있다.
  * `PathRequest` 또한 `ServerWebExchangeMatcher`를 만드는데 사용해서 리소스에 대한 제어할 수 있다.

```java
@Bean
public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
	return http
		.authorizeExchange()
			.matchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
			.pathMatchers("/foo", "/bar")
				.authenticated().and()
			.formLogin().and()
		.build();
}
```

### 30.3 OAuth2

#### 30.3.1 Client

* 만약 `spring-security-oath2-client`가 클래스패스에 존재하면, 자동설정을 통해 쉽게 OAuth2/Open ID Connect 클라이언트를 셋업할 수 있다.
* 이러한 자동설정은 `OAuth2ClientPropeirties`이하의 설정을 따르는데, 아래와 같이 `spring.security.oauth2.client` prefix를 사용해서 다수의 설정도 할 수 있다.

```properties

spring.security.oauth2.client.registration.my-client-1.client-id=abcd
spring.security.oauth2.client.registration.my-client-1.client-secret=password
spring.security.oauth2.client.registration.my-client-1.client-name=Client for user scope
spring.security.oauth2.client.registration.my-client-1.provider=my-oauth-provider
spring.security.oauth2.client.registration.my-client-1.scope=user
spring.security.oauth2.client.registration.my-client-1.redirect-uri-template=http://my-redirect-uri.com
spring.security.oauth2.client.registration.my-client-1.client-authentication-method=basic
spring.security.oauth2.client.registration.my-client-1.authorization-grant-type=authorization_code

spring.security.oauth2.client.registration.my-client-2.client-id=abcd
spring.security.oauth2.client.registration.my-client-2.client-secret=password
spring.security.oauth2.client.registration.my-client-2.client-name=Client for email scope
spring.security.oauth2.client.registration.my-client-2.provider=my-oauth-provider
spring.security.oauth2.client.registration.my-client-2.scope=email
spring.security.oauth2.client.registration.my-client-2.redirect-uri-template=http://my-redirect-uri.com
spring.security.oauth2.client.registration.my-client-2.client-authentication-method=basic
spring.security.oauth2.client.registration.my-client-2.authorization-grant-type=authorization_code

spring.security.oauth2.client.provider.my-oauth-provider.authorization-uri=http://my-auth-server/oauth/authorize
spring.security.oauth2.client.provider.my-oauth-provider.token-uri=http://my-auth-server/oauth/token
spring.security.oauth2.client.provider.my-oauth-provider.user-info-uri=http://my-auth-server/userinfo
spring.security.oauth2.client.provider.my-oauth-provider.user-info-authentication-method=header
spring.security.oauth2.client.provider.my-oauth-provider.jwk-set-uri=http://my-auth-server/token_keys
spring.security.oauth2.client.provider.my-oauth-provider.user-name-attribute=name
```

* 기본적으로, 스프링 시큐리티의 `OAuth2LoginAuthenticationFilter` 는 `/login/oauth2/code/*` 와 매칭되는 URL만 처리한다.
  * 만약에 `redirect-uri`를 다른 패턴으로 커스터마이징 하고 싶으면, 설정에 제공하여야 한다.
  * 예를 들어, 서블릿 어플리케이션에서는 `WebSecurityConfigurerAdapter`에 아래와 같이 작성하면된다.

```java
ublic class OAuth2LoginSecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.authorizeRequests()
				.anyRequest().authenticated()
				.and()
			.oauth2Login()
				.redirectionEndpoint()
					.baseUri("/custom-callback");
	}
}
```

##### OAuth2 client registration for common providers

* 대중적인 OAuth2와 OpenID 제공사(Google, Github, Facebook, Ockta)를 사용하기 위해, 기본 설정을 제공한다.
  * 위의 제공사들을 커스터마이징된 설정없이 사용하려면 `provider` 어트리뷰트에 적어주면된다.
  * 혹은 key값에 적어줘도 동일하게 동작한다, 아래의 두 예제는 모두 같은 의미이다.

```properties
spring.security.oauth2.client.registration.my-client.client-id=abcd
spring.security.oauth2.client.registration.my-client.client-secret=password
spring.security.oauth2.client.registration.my-client.provider=google

spring.security.oauth2.client.registration.google.client-id=abcd
spring.security.oauth2.client.registration.google.client-secret=password
```

#### 30.3.2 Resource Server

* 클래스패스에 `spring-security-oauth2-resource-server`가 클래스패스에 있고, 이하와 같이 JWK Set URI 혹은 OIDC Issuer URI가 명시되어 있을때, 스프링 부트는 OAuth2 Resource Server를 활성화해준다.

```properties
spring.security.oauth2.resourceserver.jwt.jwk-set-uri=https://example.com/oauth2/default/v1/keys

spring.security.oauth2.resourceserver.jwt.issuer-uri=https://dev-123456.oktapreview.com/oauth2/default/
```

### 30.4 Actuator Security

* 기본적으로 `/health` 와 `/info`를 제외한 다른 Actuator들은 모두 비활성화 되어있다.
  * 활성화가 필요할 경우엔 `management.endpoints.web.exposure.include`에 명시해야 한다.
* 만약에 스프링 시큐리티가 클래스 패스에 있고, 다른 `WebSecurityConfigurerAdapter`가 없을 경우, `/health`와 `/info`를 제외한 모든 actuator들은 스프링 부트의 자동설정을 따른다.
  * `WebSecurityConfigurerAdapter`가 정의된다면, 스프링 기본설정은 사용되지 않는다.

#### 30.4.1 Cross Site Request Forgery Protection

* 스프링 부트는 스프링 시큐리티의 기본값을 사용하기 때문에, CSRF 보호기능은 자동으로 활성화 된다.
  * `POST`, `PUT`, `DELETE`를 필요로 하는 actuator endpoint들은 403 forbidden error 를 응답받을 것이다.