## 레퍼런스로 스터디하는 스프링부트 16강

##### 29.1.9 ConfigurableWebBindingInitializer

* 스프링 MVC는 `WebBindingInitializer`를 통해서 `WebDataBinder`를 초기화해서, 특정 요청을 위해서 사용한다.
  * 스프링 MVC에서 데이터를 바인딩할 때 사용하는 핵심적인 모듈이다.
  * 직접 커스터마이징 하려면 `ConfigurableWebBindingInitializer`라는 `@Bean`을 만들면 된다.
  
* **`Converter`를 구현하여 사용하는 예**

    ###### `BangsongController.java`

    ```java
    @RestController
    public class BangsongController {
    	@GetMapping("/bs/{id}")
    	public Bangsong getBangsong(@PathVariable("id") Bangsong bangsong){
        	return bangsong;
    	}
    }
    ```

    * 위와 같은 경우에, `String`을 `Bangsong` 타입으로 변환해주는 컨버터를 등록해야 하는데, 이때 `ConfigurableWebBindingInitializer`를 사용해야 한다.

    ###### `BangsongConverter` 구현

    ```java
    public class BangsongConverter implements Converter<String, Bangsong> {
        @Override
        public Bangsong convert(String s) {
            Bangsong bangsong = new Bangsong();
            bangsong.setId(Integer.valueOf(s));
            return bangsong;
        }
    }
    
    ```

    ###### `ConfigurableWebBindingInitializer` 를 통한 `BangsongConverter`등록

    ```java
    @Bean
    public ConfigurableWebBindingInitializer initializer(){
        ConfigurableWebBindingInitializer initializer 
            = new ConfigurableWebBindingInitializer();
        ConfigurableConversionService conversionService 
            = new FormattingConversionService();
        conversionService.addConverter(new BangsongConverter());
        initializer.setConversionService(conversionService);
        return initializer;
    }
    ```

* 다만 앞서서 살펴본, **`WebMvcConfigurer` 구현체**를 사용하면 이보다 더 쉽게 할 수 있다.

  ```java
  @Configuration
  public class  WebConfig implements WebMvcConfigurer {
      @Override
      public void addFormatters(FormatterRegistry registry) {
          registry.addConverter(new BangsongConverter());
      }
  }
  ```

* 마지막으로, **`BangsongConverter`를 Bean**으로 등록해주기만 해도 이 모든일을 처리해준다.

  ```java
  @Component
  public class BangsongConverter implements Converter<String, Bangsong> {
      @Override
      public Bangsong convert(String s) {
          Bangsong bangsong = new Bangsong();
          bangsong.setId(Integer.valueOf(s));
          return bangsong;
      }
  }
  ```
* 결국 `ConfigurableWebBindingInitializer` 를 사용할 일이 없을 것이고, 마지막 3번째 방법을 사용하는게 가장 편리하고 현명하다.
#### 29.1.10 Template Engines

* Spring MVC를 통한 동적인 HTML 컨텐츠 제공을 할 수 있다.
  * [Thymeleaf](http://www.thymeleaf.org/),[Freemarker](https://freemarker.apache.org/docs/) 등과 같은 다양한 템플릿 기술을 제공한다
  * JSP의 경우엔 내장된 서블릿 컨테이너에서 제약사항이 존재해 지양하는 것이 좋다.
* 이러한 템플릿들은 기본설정상 `src/main/resources/templates`에서 자동으로 사용된다.
  * Tomcat, Jetty에서는 executable War는 사용할 수 있지만, executable Jar는 사용할 수 있다.
  * 커스터마이징된 `error.jsp` 페이지를 만들어서, 에러 처리를 오버라이딩하는제 제약이 있음

##### Freemarker 사용법

###### 컨트롤러

```java
@Controller
public class FreemarkerController {

    @GetMapping("/hello/freemarker")
    public String hello(Model model, @RequestParam String name){
        model.addAttribute("name",name);
        return "hello";
    }
}
```

###### `hello.ftl`

```html
<html>
<head>
    <title>Welcome!</title>
</head>
	<body>
		<h1>Welcome ${name}!</h1>
	</body>
</html>
```

##### Thymeleaf 사용법

###### 컨트롤러

```java
@Controller
public class ThymeleafController {

    @GetMapping("/hello/thymeleaf")
    public String hello(Model model, @RequestParam String name){
        model.addAttribute("name",name);
        return "helloThymeleaf";
    }
}
```

###### `hello.html`

```html
<html xmlns:th="http://www.thymeleaf.org">
	<body>
		<p th:text="${name}">default name</p>
	</body>
</html>
```

* IDE에서 어플리케이션을 실행할 때와, Maven 패키징해서 실행하는 것 사이에서, 클래스패스를 찾는 순서가 다를 수 있다.
  * 이러한 문제를 겪게 될 경우, `classpath*:/templates/`를 사용해서 모든 `template`디렉토리를 찰 수 있도록 할 수 있다.

#### 29.1.11 Error Handling

* 기본적으로, 스프링부트는 `/error`라는 매핑을 제공해서, 모든 에러를 클라이언트의 요청에 알맞은 형태로 처리하며, 해당 페이지가 전역 에러페이지로 설정되어 있다.

  * Machine client: 에러 상세를, HTTP status, 예외메시지를 Json으로 응답
  * 브라우저 client: 상기한 데이터를 포함하는 하얀 바탕의 view를 제공한다
    * 이를 오버라이딩하기 위해선, `error`를 표현하는 `View`를 추가하면 된다.

* 이런 기본 설정을 완전히 오버라이딩 하기 위해서는,  `ErrorController` 를 구현하고 Bean으로 등록한다

  * 혹은 `ErrorAttributes` 타입을 기존에 존재하는 설정에 입력해주면 된다.

* `BasicErrorController`를 커스터마이징한 `ErrorController`의 부모 클래스로 사용할 수 있다.

  * 이경우, ` BasicErrorController`를 상속받아서, 새로운 `@RequestMapping`을 사용한 메서드를 추가하여 `produces` 어트리뷰트를 추가하면 된다.

  ```java
   @RequestMapping(
          produces = {"text/html"}
      )
      public ModelAndView errorHtml(HttpServletRequest request, HttpServletResponse response) {
          ...
   }
  ```
