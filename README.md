## 레퍼런스로 스터디하는 스프링 부트

#### <u>바람개비 개요 및 목표</u>

Spring Boot를 익히기 위해 Spring Boot Reference Guide 및 해당 문서 해설 강의를 각자 공부하고, 예제 소스코드를 작성한다. 또한 매 주 오프라인 모임을 통하여 학습한 내용, 질문사항을 공유하고, 각자가 속한 개발환경에서 적용 가능한 사례를 연구한다. 총 3회(39주)간의 과정을 통해 아래의 사항을 학습 할 수 있다.

* Spring Boot에 대한 소개 및 SpringApplication의 기초
* Spring Boot의 각종 설정 및 로깅
* Spring Boot를 통한 웹 어플리케이션 및 MVC
* Spring Boot Test
* Spring WebFlux
* Spring Thymeleaf
* Spring Security
* Spring JDBC & in-memory DB
* Spring Data & JPA
* MongoDB
* Neo4J
* Spring Boot Actuator
* Spring Boot Application 배포 및 서비스 등록

#### <u>기간</u>

기간은 3개월을 1회로 하여 총 3회간 진행한다.

* **1회차**: 13주 Course (2019.3 ~ 2019.5)
* **2회차**: 13주 Course (2019.6 ~ 2019.8)
* **3회차**: 13주 Course (2019.9 ~ 2019.11)


#### <u>사용자료</u>

* **Spring Boot Reference Guide** [<span style="background-color:#6db33f; border-radius:3px; padding: 2px 4px; color:#fff;font-size:13px;font-weight:700; ">2.1.3 RELEASE</span>](https://docs.spring.io/spring-boot/docs/2.1.3.RELEASE/reference/htmlsingle/)
* **백기선님의 스프링 부트 강좌** [<img src="https://www.freeiconspng.com/minicovers/red-youtube-logo-icon-8.png" width="30px">](https://www.youtube.com/playlist?list=PLfI752FpVCS8tDT1QEYwcXmkKDz-_6nm3)
  * 해당 강좌에서는 [<span style="background-color:#898989; border-radius:3px; padding: 2px 4px; color:#fff;font-size:13px;font-weight:700; ">2.0.0 RC2</span>](https://docs.spring.io/autorepo/docs/spring-boot/2.0.0.RC2/reference/html/) 사용
* 그외에 각자 바람개비에서 사용하려고 정한 자료 및 개발도구

#### <u>바람개비 진행 방법</u>

##### 레퍼런스 자습

정해진 주차에 맞는 레퍼런스 내용을 유튜브 강의 및 예제 소스코드 작성을 통해 학습한다.

##### Online Study Board 참여

온라인 Study Board에 해당 주차 스터디 한 내용에 대하여 오프라인 모임전에 소스코드 및 간단한 내용을 업로드 한다.

##### Offline Study 참여 (필수 아님)

오프라인 모임을 통해서 온라인 Study Board를 통해 해결하지 못한 궁금증이나, 공유하고 싶은 정보 및 사례를 토의한다.

---

### 레퍼런스로 스터디하는 스프링 부트 1 of 3 회차


#### <u>일정</u> (2019.03~2019.05)

| 주차  |학습 내용|Offline 모임일|
| ----- | -------------------- | -------------------- |
| 01 |바람개비 Orientation|skip|
| 02 |[**스프링 부트 시작하기**](https://youtu.be/CnmTCMRTbxo)|2019-03-15|
|03|[**Executable JAR 어떻게 만들고 어떻게 동작하는가**](https://youtu.be/PicKx3lDGLk)|**2019-03-21**|
|04|[**스프링 부트 스타터**](https://youtu.be/w9wqpnLHnkY)|2019-03-26|
|05|[**@SpringBootApplication과 XML 빈 설정 파일 사용하기**](https://youtu.be/jftcS1BQ_0g)|2019-04-04|
|06|[**spring-boot-devtools 그리고 릴로딩**](https://youtu.be/5BhWpx7RW-w)|2019-04-11|
|07|[**배너 그리고 SpringApplication**](https://youtu.be/38UK7BRJf1o)|2019-04-18|
|08|[**SpringApplication 커스터마이징과 Admin 기능 (MBeans)**](https://youtu.be/8fK1tA7C6Ss)|2019-04-25|
|09|[**프로퍼티와 각종 외부 설정의 우선 순위**](https://youtu.be/jv50m3yOemU)|2019-05-02|
|10|[**YAML 사용하기**](https://youtu.be/m7j6ysAW5rc)|2019-05-09|
|11|[**@ConfigurationProperties의 여러 장점과 유일한 단점**](https://youtu.be/0QUNXpRHVVM)|2019-05-16|
|12|[**스프링 프로파일과 스프링 부트 기본 로깅**](https://youtu.be/h_VoxXhhNH0)|2019-05-23|
|13|[**커스텀 로그 설정 제공하기와 logback에서 스프링프로파일 사용하기**](https://youtu.be/uVR2iBEb474)|2019-05-30|

#### <u>학습 자료 링크</u>

|강의 링크| [<span style="background-color:#6db33f; border-radius:3px; padding: 2px 4px; color:#fff;font-size:13px;font-weight:700; ">2.1.3 RELEASE</span>](https://docs.spring.io/spring-boot/docs/2.1.3.RELEASE/reference/htmlsingle/)|[<span style="background-color:#898989; border-radius:3px; padding: 2px 4px; color:#fff;font-size:13px;font-weight:700; ">2.0.0 RC2</span>](https://docs.spring.io/autorepo/docs/spring-boot/2.0.0.RC2/reference/html/)|
| -------------------- | -------------------- | -------------------- |
|[1강: **스프링 부트 시작하기**](https://youtu.be/CnmTCMRTbxo)|**[1 ~ 11.4](https://docs.spring.io/spring-boot/docs/2.1.3.RELEASE/reference/htmlsingle/#boot-documentation-about)**|**[1 ~ 11.4](https://docs.spring.io/spring-boot/docs/2.0.0.RC2/reference/htmlsingle/#boot-documentation)**|
|[2강: **Executable JAR 어떻게 만들고 어떻게 동작하는가**](https://youtu.be/PicKx3lDGLk)|**[11.2 ~ 13.4](https://docs.spring.io/spring-boot/docs/2.1.3.RELEASE/reference/htmlsingle/#getting-started-first-application-run)**|**[11.2 ~ 13.4](https://docs.spring.io/spring-boot/docs/2.0.0.RC2/reference/htmlsingle/#getting-started-first-application-dependencies)**|
|[3강: **스프링 부트 스타터**](https://youtu.be/w9wqpnLHnkY)|**[13.5 ~ 15.1](https://docs.spring.io/spring-boot/docs/2.1.3.RELEASE/reference/htmlsingle/#using-boot-starter)**|**[13.5 ~ 15.1](https://docs.spring.io/spring-boot/docs/2.0.0.RC2/reference/htmlsingle/#using-boot-starter)**|
|[4강: **@SpringBootApplication과 XML 빈 설정 파일 사용하기**](https://youtu.be/jftcS1BQ_0g)|**[15.2 ~ 19.5](https://docs.spring.io/spring-boot/docs/2.1.3.RELEASE/reference/htmlsingle/#using-boot-importing-xml-configuration)**|**[15.2 ~ 19.5](https://docs.spring.io/spring-boot/docs/2.0.0.RC2/reference/htmlsingle/#using-boot-importing-xml-configuration)**|
|[5강: **spring-boot-devtools 그리고 릴로딩**](https://youtu.be/5BhWpx7RW-w)|**[20 ~ 22](https://docs.spring.io/spring-boot/docs/2.1.3.RELEASE/reference/htmlsingle/#using-boot-devtools)**|**[20 ~ 22](https://docs.spring.io/spring-boot/docs/2.0.0.RC2/reference/htmlsingle/#using-boot-devtools)**|
|[6강: **배너 그리고 SpringApplication**](https://youtu.be/38UK7BRJf1o)|**[23 ~ 23.5](https://docs.spring.io/spring-boot/docs/2.1.3.RELEASE/reference/htmlsingle/#boot-features)**|**[23 ~ 23.5](https://docs.spring.io/spring-boot/docs/2.0.0.RC2/reference/htmlsingle/#boot-features)**|
|[7강: **SpringApplication 커스터마이징과 Admin 기능 (MBeans)**](https://youtu.be/8fK1tA7C6Ss)|**[23.6 ~ 23.9](https://docs.spring.io/spring-boot/docs/2.1.3.RELEASE/reference/htmlsingle/#boot-features-web-environment)**|**[23.6 ~ 23.9](https://docs.spring.io/spring-boot/docs/2.0.0.RC2/reference/htmlsingle/#boot-features-web-environment)**|
|[8강: **프로퍼티와 각종 외부 설정의 우선 순위**](https://youtu.be/jv50m3yOemU)|**[23.10 ~ 24.6](https://docs.spring.io/spring-boot/docs/2.1.3.RELEASE/reference/htmlsingle/#boot-features-application-admin)**|**[23.10 ~ 24.5](https://docs.spring.io/spring-boot/docs/2.0.0.RC2/reference/htmlsingle/#boot-features-application-admin)**|
|[9강: **YAML 사용하기**](https://youtu.be/m7j6ysAW5rc)|**[24.7](https://docs.spring.io/spring-boot/docs/2.1.3.RELEASE/reference/htmlsingle/#boot-features-external-config-yaml)**|**[24.6](https://docs.spring.io/spring-boot/docs/2.0.0.RC2/reference/htmlsingle/#boot-features-external-config-yaml)**|
|[10강: **@ConfigurationProperties의 여러 장점과 유일한 단점**](https://youtu.be/0QUNXpRHVVM)|**[24.8](https://docs.spring.io/spring-boot/docs/2.1.3.RELEASE/reference/htmlsingle/#boot-features-external-config-typesafe-configuration-properties)**|**[24.7](https://docs.spring.io/spring-boot/docs/2.0.0.RC2/reference/htmlsingle/#boot-features-external-config-typesafe-configuration-properties)**|
|[11강: **스프링 프로파일과 스프링 부트 기본 로깅**](https://youtu.be/h_VoxXhhNH0)|**[25 ~ 26.5](https://docs.spring.io/spring-boot/docs/2.1.3.RELEASE/reference/htmlsingle/#boot-features-profiles)**|**[25 ~ 26.4](https://docs.spring.io/spring-boot/docs/2.0.0.RC2/reference/htmlsingle/#boot-features-profiles)**|
|[12강: **커스텀 로그 설정 제공하기와 logback에서 스프링프로파일 사용하기**](https://youtu.be/uVR2iBEb474)|**[26.6 26.7](https://docs.spring.io/spring-boot/docs/2.1.3.RELEASE/reference/htmlsingle/#boot-features-custom-log-configuration)**|**[26.5 ~ 26.6](https://docs.spring.io/spring-boot/docs/2.0.0.RC2/reference/htmlsingle/#boot-features-custom-log-configuration)**|

