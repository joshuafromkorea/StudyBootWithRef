## 레퍼런스로 스터디하는 스프링부트 18강

#### 29.1.13 CORS Support

* [Cross-origin resource sharing](https://en.wikipedia.org/wiki/Cross-origin_resource_sharing)(CORS)란, 서비스 도메인 밖의 다른 도메인으로 부터 리소스를 요청할 수 있도록 허용한 구조로, 대부분의 브라우져에서 사용가능한 W3C 스펙의 구현이다.
* 스프링 부트에선 특별한 설정 없이 CORS를 사용할 수 있고, `WebMvcConfigurer` Bean을 통해 전역적으로 커스터마이징 해서 사용할 수 있다.

```java
@Configuration
public class MyConfiguration {

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/api/**");
			}
		};
	}
}
```

##### CORS 관련 실습

###### `BangsongController.java`

```java
@GetMapping("/bs")
public Bangsong bangsong (){
    Bangsong bangsong = new Bangsong();
    bangsong.setStreamer("kiwon");
    bangsong.setTitle("레스링부");
    return bangsong;
}
```

* 위와 같은 컨트롤러가 `localhost:8080`에 띄워져 있을때, `localhost:9000`을 사용하는 클라이언트에서 요청을 보내면 아래와 같은 에러가 발생한다.

```
Access to XMLHttpRequest at 'http://localhost:8080/bs' from origin 'http://localhost:9000' has been blocked by CORS policy: No 'Access-Control-Allow-Origin' header is present on the requested resource.
```

* 기본 CORS 정책에 따라, 다른 도메인에 있는 서비스를 호출할 수 없다는 에러이다.

* `@CrossOrigin`어노테이션만 컨트롤러에 붙이면, 모든 도메인에 대해서 허용하는 정책을 바로 붙일 수 있다.

	```java
@CrossOrigin
@GetMapping("/bs")
public Bangsong bangsong (){
    Bangsong bangsong = new Bangsong();
    bangsong.setStreamer("kiwon");
    bangsong.setTitle("레스링부");
    return bangsong;
}
	```

###### `WebMvcConfigurer` 구현체에 전역설정 적용하기

```java
@Configuration
public class  WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/bs/**").allowedOrigins("*");
    }
}
```

