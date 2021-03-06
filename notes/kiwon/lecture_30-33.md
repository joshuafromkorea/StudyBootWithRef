## 레퍼런스로 스터디하는 스프링부트 30-33강

#### 31.1 JPA and Spring Data JPA

* JPA는 객체를 RDB에 매핑할 수 있게 해주며, `spring-boot-starter-date-jpa`를 등록함으로서 사용할 수 있다.
  * Hibernate, Spring Data JPA, Spring ORMs 을 추가해준다.

#### 31.3.1 Entity Classes

* 스프링 부트에서는 Entity Class를 어노테이션 스캐닝을 통해서 등록한다.
  * `@Entity`, `@Embeddable`, `@MappedSupperClass`의 경우 등록 된다.
* 일반적인 Entity Class는 아래의 형태를 가진다.

```java
package com.example.myapp.domain;

import java.io.Serializable;
import javax.persistence.*;

@Entity
public class City implements Serializable {

	@Id
	@GeneratedValue
	private Long id;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	private String state;

	// ... additional members, often include @OneToMany mappings

	protected City() {
		// no-args constructor required by JPA spec
		// this one is protected since it shouldn't be used directly
	}

	public City(String name, String state) {
		this.name = name;
		this.state = state;
	}

	public String getName() {
		return this.name;
	}

	public String getState() {
		return this.state;
	}

	// ... etc

}

```

* `@EntityScan` 어노테이션을 사용해서 엔티티 검색 위치를 바꿀 수 있다.

#### 31.3.2 Spring Data Jpa Repositories

* Spring Data Jpa Repository는 데이터에 접근(access data)하기 위해서 사용할 수 있는 인터페이스이다.
  * Spring Data JPA 메소드로부터 JPA 쿼리를 사용할 수 있다.
* 복잡한 쿼리같은 경우에는 [Query](https://docs.spring.io/spring-data/jpa/docs/current/api/org/springframework/data/jpa/repository/Query.html) 어노테이션을 사용해서 메소드에 표현할 수 있다.
* Spring Data repository는 일반적으로 `Repository` 혹은 `CrudRepository` 인터페이스를 상속한다.
  * 만약 자동설정을 사용하면, 해당 상속된 인터페이스들은 Repository로 등록된다.

```java
package com.example.myapp.domain;

import org.springframework.data.domain.*;
import org.springframework.data.repository.*;

public interface CityRepository extends Repository<City, Long> {

	Page<City> findAll(Pageable pageable);

	City findByNameAndStateAllIgnoringCase(String name, String state);

}
```

#### 31.3.3 Creating and Dropping JPA Databases

* 기본적으로 내장 DB(H2, HSQL, Derby)를 사용할 경우에만 JPA DB를 자동생성 해준다. 

  * 테이블을 매번 만들고 지우는 설정은 `application.properties`에 등록할 수 있다.

  ```properties
  spring.jpa.hibernate.ddl-auto=create-drop
  ```
  * 기본적으로 DDL은 `ApplicationContext` 생성까지 지연된다

#### 31.3.4 Open EntityManager in View

* 웹 어플리케이션을 기동하는 경우, 스프링부트는 `OpenEntityManagerINViewIntercepter`를 등록해서, `Open EntityManger in View` 패턴을 적용시킨다.
* 해당 기능을 끄고 싶으면 `spring.jpa.open-in-view`를 `false`로 설정하면 된다.

#### 31.5 Using H2's Web Console

* H2 database가 제공하는 브라우저 기반의 콘솔을 사용할 수 있는데, 아래의 조건을 만족하면 활성화된다.
  * 서블릿 기반의 웹 어플리케이션을 개발
  * `com.h2database:h2`가 클래스패스에 있음
  * 스프링부트의 dev-tools 사용

### 32. Working with NoSQL Technologies

* 스프링 데이터는 NoSQL기술을 지원하기 위해서 별도의 프로젝트를 제공한다.
  * 스프링 부트는 Redis, MongoDB, Neof4j, Elasticsearch, Solr Cassandra, Couchbase, and LDAP을 위한 자동설정을 제공한다.

#### 32.1 Redis

* Redis는 캐시, 메시지 전달자, 다양한 기능을 가진 키-밸류 저장소이다.
* `spring-boot-starter-data-redis` 스타터를 사용해 의존성을 추가하고 있고, 기본적으로 `Lettuce`를 사용한다.

#### 32.2 MongoDB

* Spring Boot는 `spring-boot-starter-data-mongodb`를 이용해 의존성을 등록하면, MongoDB활용을 위한 몇가지의 편리한 설정을 제공한다.

#### 32.2.1 Connecting to a MongoDB Database

* MongoDB에 접속하기 위해서는, 자동설정된 `MongoDBFactory` Bean을 주입받아서 사용하면 된다.

  * 기본적으로 `mongodb://localhost/test`에 접속하려고 한다.
  * `spring.data.mongodb.uri` 값 설정을 통해서 접속하고자 하는 서버를 바꿀 수 있다.

  ```properties
  spring.data.mongodb.uri=mongodb://user:secret@mongo1.example.com:12345,mongo2.example.com:23456/test
  ```

  * Mongo 2.x 버젼을 사용할 경우엔 호스트와 포트 정보를 아래와같이 주면 된다.

  ```properties
  spring.data.mongodb.host=mongoserver
  spring.data.mongodb.port=27017
  ```

#### 32.2.3 Spring Data MongoDB Repositories

* 스프링 데이터는 MongoDB를 위한 Repository설정을 지원한다. 

```java
package com.example.myapp.domain;

import org.springframework.data.domain.*;
import org.springframework.data.repository.*;

public interface CityRepository extends Repository<City, Long> {

	Page<City> findAll(Pageable pageable);

	City findByNameAndStateAllIgnoringCase(String name, String state);

}
```

#### 32.2.24 Embedded Mongo

* Spring Boot는 자동설정된 내장 MongoDB를 제공한다.
  *  `de.flapdoodle.embed:de.flapdoodle.embed.mongo`를 의존성에 추가하면 된다.

* `spring.data.mongodb.port` 프로퍼티로 내장 DB의 포트를 설정할 수 있다.
  * 0을 값으로 제공하면 랜덤 포트가 제공된다.

#### 32.3 Neo4j

* `spring-boot-starter-data-neo4j`를 의존성 추가하면 스프링부트가 제공하는 Neo4J 자동설정을 사용할 수 있다.

#### 32.3.1 Connecting to a Neo4j Database

* `org.neo4j.ogm.session.Session`를 사용하면, Neo4J 서버에 접속할 수 있다. 기본적으로 `localhost:7687`에 접속한다.
* 아래와 같은 `application.properties`설정을 할 수 있다.

```java
spring.data.neo4j.uri=bolt://my-server:7687
spring.data.neo4j.username=neo4j
spring.data.neo4j.password=secret
```

#### 32.3.2 Using the Emebedded Mode

* `org.neo4j:neo4j-ogm-embedded-driver`를 의존성 추가하면 스프링 부트는 자동 내장 Neo4J DB를 추가해준다.

#### 32.3.4 Spring Data Neo4j Repositories

* 모델의 경우엔, Spring Data의 `@Entity`를 `@NodeEntity`로 대체하면 된다.
* Repository의 예제는 다음과 같다.

```java
package com.example.myapp.domain;

import java.util.Optional;

import org.springframework.data.neo4j.repository.*;

public interface CityRepository extends Neo4jRepository<City, Long> {

	Optional<City> findOneByNameAndState(String name, String state);

}
```

