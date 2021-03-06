## 레퍼런스로 스터디하는 스프링부트 34강

### 35. Calling Rest Services with `RestTemplate`

* 원격지의 REST 서비스를 어플리케이션에서 호출할 필요가 있다면, 스프링 프레임워크의 `RestTemplate`을 사용할 수 있다.
  * 일반적인 경우 `RestTemplate`은 사용전에 설정을 해야하지만, 스프링 부트에서는 `RestTemplateBuilder`라는  빌더를 통해서 자동설정을 해준다.

```java
@Service
public class MyService {

	private final RestTemplate restTemplate;

	public MyService(RestTemplateBuilder restTemplateBuilder) {
		this.restTemplate = restTemplateBuilder.build();
	}

	public Details someRestCall(String name) {
		return this.restTemplate.getForObject("/{name}/details", Details.class, name);
	}

}
```

* `RestTemplateBuilder`에는 몇가지의 유용한 메서드들이 있다. 예를들어 BASIC 인증 지원을 위해 `builder.basicAuthentication("user", "password").build()` 등을 지원한다.

#### 35.1 `RestTemplate` Customization

* `RestTemplate`의 커스터마이징을 위해 총 3가지의 접근법이 제공되는데 이는 얼마나 커스터마이징 할 영역에 따라 달라진다.
* 가장 적게 커스터마이징 하고 싶다면, `RestTemplateBuilder`를 주입받고, 필요한 메서드를 호출하여서 체인 메서드 방식으로 커스터마이징 하는 것이다.
* `RestTemplateCustomizer` Bean을 사용하면 어플리케이션 전역적으로 설정할 수 있다. 이렇게 되면 `RestTemplateBuilder`로 만들어지는 모든 템플릿에 적용된다.
* 아래의 예제는 `192.168.0.5`를 제외한 모든 호스트에 프록시를 사용하는 설정을 하는 커스터마이저 이다.

```java
static class ProxyCustomizer implements RestTemplateCustomizer {

	@Override
	public void customize(RestTemplate restTemplate) {
		HttpHost proxy = new HttpHost("proxy.example.com");
		HttpClient httpClient = HttpClientBuilder.create()
				.setRoutePlanner(new DefaultProxyRoutePlanner(proxy) {

					@Override
					public HttpHost determineProxy(HttpHost target,
							HttpRequest request, HttpContext context)
							throws HttpException {
						if (target.getHostName().equals("192.168.0.5")) {
							return null;
						}
						return super.determineProxy(target, request, context);
					}

				}).build();
		restTemplate.setRequestFactory(
				new HttpComponentsClientHttpRequestFactory(httpClient));
	}

}
```

* 마지막 방법은, `RestTemplateBuilder`라는 Bean을 직접적으로 선언하여서 등록하는 방법으로, 스프링부트가 제공하는 빌더를 대체하고, `RestTemplateCustomizer` bean이 사용되는 것을 막는다.

### 36. Calling Rest Services with `WebClient`

* 스프링 웹플럭스가 클래스패스에 있으면, REST 서비스를 호출할 때 `WebClient`를 사용해서 호출할 수 있다.
  * `RestTemplate`와 비교해서 functional 하고 완전히 reactive하다.
* `WebClient` 또한 `Webclient.Builder`를 사용해서 만들 수 있으며, 사용하는것이 강력하게 권고된다.
* 아래의 일반적인 예제를 보자

```java
@Service
public class MyService {

	private final WebClient webClient;

	public MyService(WebClient.Builder webClientBuilder) {
		this.webClient = webClientBuilder.baseUrl("http://example.org").build();
	}

	public Mono<Details> someRestCall(String name) {
		return this.webClient.get().uri("/{name}/details", name)
						.retrieve().bodyToMono(Details.class);
	}

}
```

#### 36.2 `WebClient` Customization

* `WebClient`의 경우에도 3 가지의 커스터마이징 할 수 있는 방법이 제공된다.
* 가장 적은 범위에서 커스터마이징을 할 때에는 `WebClient.Builder`를 bean으로 사용하고, 메소드를 호출하는 것이다. 
  * 만약 동일한 빌더를 사용해서 여러가지의 클라이언트를 만들 경우 빌더를 클론하는 방법을 고려하라
    * `Webclient.Builder other = builder.cloen();`
* 전역 적인 커스터마이징은 `WeblientCustomizer`를 bean으로 선언하는 것이다
* 마지막으로 `WebCleint.create()`를 사용할 경우, 어떠한 자동설정도 적용되지 않는다.

