package com.example.kotlincoroutinesspringmvc

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.apache.logging.log4j.LogManager
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kotlin.coroutine.EnableCoroutine
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.getForEntity

@RestController
class MyDataResource {

  private val log = LogManager.getLogger()

  @GetMapping("/get-my-data")
  fun getMyData(): Map<String, Any> {
    log.info("before result")
    return mapOf("ololo" to "trololo")
        .also {
          log.info("after result")
        }
  }
}

@Configuration
class MyRestClientConfig {

  @Bean
  fun restTemplate(): RestTemplate = RestTemplateBuilder().build()
}

@Service
class MyCoroutinesRestClient(private val restTemplate: RestTemplate) {

  private val log = LogManager.getLogger()

  suspend fun getMyData(): Map<String, Any> = withContext(Dispatchers.Default) {
    log.info("before restTemplate")
    return@withContext restTemplate.getForEntity<Map<String, Any>>("http://127.0.0.1:8080/get-my-data")
        .body
        .also {
          log.info("after restTemplate")
        } ?: mapOf()
  }
}

@RestController
class MyCoroutinesResource(private val restClinet: MyCoroutinesRestClient) {

  private val log = LogManager.getLogger()

  @GetMapping("/**")
  suspend fun defaultFallback(): Map<String, Any> {
    log.info("before restClinet")
    return restClinet.getMyData()
        .also {
          log.info("after restClinet")
        }
  }
}

@EnableCoroutine
@SpringBootApplication
class KotlinCoroutinesSpringMvcApplication

fun main(args: Array<String>) {
  runApplication<KotlinCoroutinesSpringMvcApplication>(*args)
}
