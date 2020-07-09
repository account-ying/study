package com.example.demo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * https://spring.io/guides/gs/gateway/
 */
@SpringBootApplication
@RestController
@EnableConfigurationProperties(UriConfiguration.class)

public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}


//	@Bean
//	public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
//		return builder.routes()
//				.route(r -> r.path("/course").uri("http://httpbin.org:80"))
//				.build();
//	}

	/**
	 * 创建一个简单的路由
	 * @param builder
	 * @return
	 */
//	@Bean
//	public RouteLocator myRoutes2(RouteLocatorBuilder builder) {
//		return builder.routes()
//				.route(p -> p
//						.path("/get")
//						.filters(f -> f.addRequestHeader("Hello", "World"))
//						.uri("http://httpbin.org:80"))
//				.build();
//	}

	/**
	 * 使用断路器 hystrix
	 */
//	@Bean
//	public RouteLocator myRoutes(RouteLocatorBuilder builder) {
//		return builder.routes()
//				.route(p -> p
//						.path("/get")
//						.filters(f -> f.addRequestHeader("Hello", "World"))
//						.uri("http://httpbin.org:80"))
//				.route(p -> p
//						.host("*.hystrix.com")
//						.filters(f -> f.hystrix(config -> config.setName("mycmd")))
//						.uri("http://httpbin.org:80")).
//						build();
//	}

//	@Bean
//	public RouteLocator myRoutes3(RouteLocatorBuilder builder) {
//		return builder.routes()
//				.route(p -> p
//						.path("/get")
//						.filters(f -> f.addRequestHeader("Hello", "World"))
//						.uri("http://httpbin.org:80"))
//				.route(p -> p
//						.host("*.hystrix.com")
//						.filters(f -> f.hystrix(config -> config
//								.setName("mycmd")
//								.setFallbackUri("forward:/fallback")))
//						.uri("http://httpbin.org:80"))
//				.build();
//	}

	@RequestMapping("/fallback")
	public Mono<String> fallback() {
		return Mono.just("fallback");
	}

	/**
	 * I can't understand this ...
	 * curl --dump-header - --header Host: www.hystrix.com http://localhost:8084/delay/3
	 * @param builder
	 * @param uriConfiguration
	 * @return
	 */
	@Bean
	public RouteLocator myRoutes(RouteLocatorBuilder builder, UriConfiguration uriConfiguration) {
		String httpUri = uriConfiguration.getHttpbin();
		return builder.routes()
				.route(p -> p
						.path("/get")
						.filters(f -> f.addRequestHeader("Hello", "World"))
						.uri(httpUri))
				.route(p -> p
						.host("*.hystrix.com")
						.filters(f -> f
								.hystrix(config -> config
										.setName("mycmd")
										.setFallbackUri("forward:/fallback")))
						.uri(httpUri))
				.build();
	}


}

/**
 * 这个注解从配置文件里面取值
 */
@ConfigurationProperties(prefix = "config")

class UriConfiguration {

	//private String httpbin = "http://httpbin.org:80";
	private String httpbin;

	public String getHttpbin() {
		return httpbin;
	}

	public void setHttpbin(String httpbin) {
		this.httpbin = httpbin;
	}

}


