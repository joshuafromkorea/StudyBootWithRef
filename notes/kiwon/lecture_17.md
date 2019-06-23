## 레퍼런스로 스터디하는 스프링부트 17강

#### 29.1.11 Error Handling 이어서

* `@ControllerAdvice`라는 어노테이션을 가진 클래스를 통해, `JSON` 문서를 커스터마이징 할 수 있다.
  * 아래와 같이 특정한 컨트롤러나 특정한 예외 타입용으로 사용할 수 있다.

```java
@ControllerAdvice(basePackageClasses = AcmeController.class)
public class AcmeControllerAdvice extends ResponseEntityExceptionHandler {

	@ExceptionHandler(YourException.class)
	@ResponseBody
	ResponseEntity<?> handleControllerException(HttpServletRequest request, 
                                                Throwable ex) {
		HttpStatus status = getStatus(request);
		return new ResponseEntity<>(
            new CustomErrorType(status.value(), ex.getMessage()), status);
	}

	private HttpStatus getStatus(HttpServletRequest request) {
		Integer statusCode = 
            (Integer) request.getAttribute("javax.servlet.error.status_code");
		if (statusCode == null) {
			return HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return HttpStatus.valueOf(statusCode);
	}
}
```

##### Custom Error Pages

* 만약, 커스터마이징된 HTML 에러 페이지를 보여주고싶다면, `/error` 폴더에 해당 파일을 집어넣으면 된다.

  * static HTML파일과 템플릿 파일을 모두 쓸 수 있다. 
  * 파일 이름을 정확한 HTTP 에러코드를 사용하거나, masking 을 활용할 수 있다.

  ###### 404와 매핑하는 경우

  ```
  src/
   +- main/
       +- java/
       |   + <source code>
       +- resources/
           +- public/
               +- error/
               |   +- 404.html
               +- <other public assets>
  ```
  
  ###### 5로 시작하는 에러와 매핑하는 경우

  	src/
  	 +- main/
  	     +- java/
  	     |   + <source code>
  	     +- resources/
  	         +- templates/
  	             +- error/
  	             |   +- 5xx.ftl
  	             +- <other templates>

* 좀더 복잡한 매핑을 원한다면, `ErrorViewResolver` 인터페이스를 구현하여서 다음과 같이 할 수 있다.

```java
public class MyErrorViewResolver implements ErrorViewResolver {

	@Override
	public ModelAndView resolveErrorView(HttpServletRequest request,
			HttpStatus status, Map<String, Object> model) {
		// Use the request or status to optionally return a ModelAndView
		return ...
	}

}
```

* 위에서 설명하는 스프링 부트의 에러 핸들링 방법이외에, 당연히 Spring MVC 가 제공해주는 기능들을 모두 사용할 수 있다.

  * `@ExceptionHandler` 메소드나, `@ControllerAdvice` 를 사용할 수 있다.

    ###### `@ExceptionHandler` 메소드 사용예 

    ```java
    @ExceptionHandler(BangsongException.class)
    public ResponseEntity<String> handlerException(BangsongException e){
        return new ResponseEntity<>(HttpStatus.I_AM_A_TEAPOT);
    }
    ```

    ###### `@ControllerAdvice` 클래스 사용예

    ```java
    @ControllerAdvice
    public class ExceptionHandleController {
        @ExceptionHandler(BangsongException.class)
        public ResponseEntity<String> handlerException(BangsongException e){
            return new ResponseEntity<>(HttpStatus.I_AM_A_TEAPOT);
        }
    }
    ```

  * 위의 스프링 MVC에서 처리되지 않는 것들이, 부트가 제공하는 `ErroController` 가 처리가 된다.

##### Mapping Error Pages outside of Spring MVC

* 스프링 MVC를 사용하지 않는 어플리케이션의 경우, `ErrorPageRegistrar` 인터페이스를 구현해서, `ErrorPage`를 등록할 수 있다. 

  * 스프링 MVC의 `DispatcherServlet`이 없어도 동작하는데, 이는 내장된 서블릿 컨테이너를 직접 통해서 사용하기 때문이다. `web.xml`에 직접 등록하는 것과 유사한 방법

  ```java
  @Bean
  public ErrorPageRegistrar errorPageRegistrar(){
  	return new MyErrorPageRegistrar();
  }
  
  // ...
  
  private static class MyErrorPageRegistrar implements ErrorPageRegistrar {
  
  	@Override
  	public void registerErrorPages(ErrorPageRegistry registry) {
  		registry.addErrorPages(new ErrorPage(HttpStatus.BAD_REQUEST, "/400"));
  	}
  
  }
  ```

#### 29.1.12 Spring HATEOAS

* 하이퍼미디어를 사용하는, RESTful API를 사용한다면, 스프링부트는 대부분의 어플리케이션에서 잘 동작하는 Spring HATEOAS를 위한 **자동설정**을 제공한다.
  * 이러한 자동설정은 `@EnableHypermediaSupport` 를 사용하지 않아도 동작할 수 있게 해주고, 하이퍼미디어 기반의 어플리케이션을 만들기 위해 등록해야하는 여러가지 Bean들을 자동으로 등록해준다.
    * 클라이언트 측 지원을 위한 `LinkDiscoverers` 
    * 오브젝트를 JSON화 할 수 있게 해주는`ObjectMapper`

* 앞서서 언급한 `@EnableHypermediaSupport`를 통해서 Spring HATEOAS를 설정할 수 있고, 이 경우에는 앞서서 언급한 `ObjectMapper`에 대한 설정은 비활성화 된다.