## 레퍼런스로 스터디하는 스프링부트 9강

#### 24.7 Using YAML Instead of Properties

**[YAML](https://yaml.org/)**은 간편하게 계층구조의 설정 정보를 표현할 수 있는 JSON구조의 언어로, `SpringApplication` 클래스는 [SnakeYAML](https://bitbucket.org/asomov/snakeyaml)을 클래스패스에 포함하면, 자동적으로 프로퍼티 파일을 대체할 수 있도록 지원한다. 

##### 24.7.1 Loading YAML

스프링 프레임워크는 YAML 문서를 읽기 위해서 두개의 편의 클래스를 제공한다

* `YamlPropertiesFactoryBean` : `Properties` 형태로 읽는다
* `YamlMapFactoryBean`: `Map` 형태로 읽는다.

아래와 같은 YAML 문서는,

```yaml
environments:
	dev:
		url: http://dev.example.com
		name: Developer Setup
	prod:
		url: http://another.example.com
		name: My Cool App
```

애래의 프로퍼티로 변환이 된다.

```properties
environments.dev.url=http://dev.example.com
environments.dev.name=Developer Setup
environments.prod.url=http://another.example.com
environments.prod.name=My Cool App
```

YAML이 지원하는 **list**는 `[index]`를 가진 프로퍼티로 바뀐다.

```yaml
my:
servers:
	- dev.example.com
	- another.example.com
```

```properties
my.servers[0]=dev.example.com
my.servers[1]=another.example.com
```

스프링부트의 `Binder` 유틸리티들을 통해서, 

##### 24.7.2 Exposing YAML as Properties in the Spring Environment

`YamlPropertySourceLoader` 를 사용하면, 스프링 `Environment`의 YAML을 `PropertySource` 로 노출 시킬 수 있으며, 이를통해 `@Value` 어노테이션을 통해 YAML 설정에 접속된다.

##### 24.7.3 Multi-profile YAML Documents

`properties` 파일과 유사하게, YAML도 "*profile-speicific*"한 설정을 하나의 파일에 `spring.profiles`를 키로 사용하는 방식으로 아래의 예제와 같이 지원한다.

```yaml
server:
	address: 192.168.1.100
---
spring:
	profiles: development
server:
	address: 127.0.0.1
---
spring:
	profiles: production & eu-central
server:
	address: 192.168.1.120
```

위의 예제는 실행시에 어떠한 profile이 **활성화**되어 있는지에 따라서, `server.address` 프로퍼티가 달라지는 결과를 낳는다. 가장 위의 값은 어떠한 프로파일도 활성화 되지 않았을 때에 사용될 것이고, `production`**과**`eu-central`이 **동시에** 활성화 되었을 때에는, `192.168.1.120`값이 사용될 것이다.

만약에 어플리케이션 어떠한 profile도 명시적으로 활성화 되어있지 않다면, "default" profile들이 활성화된다. default profile에 대한 값도 아래의 예제처럼 설정할 수 있다.

```yaml
server:
  port: 8000
---
spring:
  profiles: default
  security:
    user:
      password: weak
```

"default" 프로파일로 설정하는 것과, 아무런 프로파일로 설정하지 않은 YAML 문서 값의 차이는, 전자는 아무런 프로파일이 없는 경우에만 설정된 다는 것이고, **후자는 어떠한 프로파일에도 설정된 뒤 변경된다는 것이다.**

`!` 문자를 사용해서, profile의 부정상태에 사용되도록 설정할 수 있다. `  profiles: !test`

##### 24.7.4 YAML Shortcomings

YAML파일은 `@PropertySource` 어노테이션으로 읽을 수 없기 때문에, 이경우에는 properties 파일을 사용해야 한다. 또한 단일 파일에 

#### 24.8 Type-safe Configuration Properties

`@Value("${property}")`의 어노테이션을 통해 프로퍼티들을 읽고 주입하는 작업은 번거로울 수 있기 때문에, 스프링부트는  타입화된 Bean들이 어플리케이션의 설정을 제어하고 검증할 수 있도록 하는 방법을 제공한다.

###### `@ConfigurationProperties`을 사용하는 예제 및 설명

```java
@ConfigurationProperties("acme")
public class AcmeProperties {

	private boolean enabled;
	private InetAddress remoteAddress;
	private final Security security = new Security();
	public boolean isEnabled() { ... }
	public void setEnabled(boolean enabled) { ... }
	public InetAddress getRemoteAddress() { ... }
	public void setRemoteAddress(InetAddress remoteAddress) { ... }
	public Security getSecurity() { ... }
    
	public static class Security {
		private String username;
		private String password;
		private List<String> roles = new ArrayList<>(Collections.singleton("USER"));
		public String getUsername() { ... }
		public void setUsername(String username) { ... }
		public String getPassword() { ... }
		public void setPassword(String password) { ... }
		public List<String> getRoles() { ... }
		public void setRoles(List<String> roles) { ... }
	}
}
```



