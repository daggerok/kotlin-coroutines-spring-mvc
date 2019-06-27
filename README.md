# SpringMVC Coroutines
Using konrad-kaminski integration

```bash
./mvnw
java -jar target/*-SNAPSHOT.jar
http :8080
# see logs / Travis CI output
```

## details

_pom.xml_

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project>
    <properties>
        <kotlinx-coroutines.version>1.3.0-M1</kotlinx-coroutines.version>
    </properties>
    <dependencies>
        <dependency>
            <groupId>org.jetbrains.kotlinx</groupId>
            <artifactId>kotlinx-coroutines-core</artifactId>
            <version>${kotlinx-coroutines.version}</version>
        </dependency>
        <dependency>
            <groupId>org.jetbrains.kotlinx</groupId>
            <artifactId>kotlinx-coroutines-reactor</artifactId>
            <version>${kotlinx-coroutines.version}</version>
        </dependency>
        <dependency>
            <groupId>org.springframework.kotlin</groupId>
            <artifactId>spring-webmvc-kotlin-coroutine</artifactId>
            <version>0.3.7</version>
        </dependency>
        <dependency>
            <groupId>org.springframework.kotlin</groupId>
            <artifactId>spring-kotlin-coroutine</artifactId>
            <version>0.3.7</version>
        </dependency>
    </dependencies>
    <repositories>
        <!-- https://www.kotlinresources.com/library/spring-kotlin-coroutine/#web-methods -->
        <repository>
            <id>spring-webmvc-kotlin-coroutine</id>
            <name>spring-webmvc-kotlin-coroutine</name>
            <url>https://dl.bintray.com/konrad-kaminski/maven</url>
            <releases>
                <enabled>true</enabled>
                <updatePolicy>never</updatePolicy>
                <checksumPolicy>ignore</checksumPolicy>
            </releases>
            <snapshots>
                <enabled>true</enabled>
                <updatePolicy>never</updatePolicy>
                <checksumPolicy>ignore</checksumPolicy>
            </snapshots>
        </repository>
    </repositories>
</project>
```

_App.kt_

```kotlin
@EnableCoroutine
@SpringBootApplication
class App

fun main(args: Array<String>) {
  runApplication<App>(*args)
}
```

_MyCoroutinesResource.kt_

```kotlin
@RestController
class MyCoroutinesResource(private val myCoroputinesService: MyCoroutinesService) {

  private val log = LogManager.getLogger()

  @GetMapping("/**")
  suspend fun defaultFallback(): Map<String, Any> {
    log.info("before")
    return myCoroputinesService
        .suspendedMethod()
        .also { log.info("after") }
  }
}
```

* [konrad-kaminski / spring-kotlin-coroutine](https://www.kotlinresources.com/library/spring-kotlin-coroutine/#web-methods)
* [YouTube: Asynchronous Programming with Kotlin Coroutines in Spring](https://www.youtube.com/watch?v=aG4ArTL8KHY)
