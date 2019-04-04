package cn.easyar.cloud.springcloudgateway.configuration;

import cn.easyar.cloud.springcloudgateway.filter.TokenGatewayFilterFactory;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpResponse;
import reactor.core.publisher.Mono;

@Configuration
public class AppConfiguration {

    /**
     * 自定义过滤器，需要在配置文件中设置，"- Token"
     * @return
     */
    @Bean
    public TokenGatewayFilterFactory tokenGatewayFilter() {
        return new TokenGatewayFilterFactory();
    }


    /**
     * 全局过滤器，不需要在配置文件中设置
     * @return
     */
    @Bean
    public GlobalFilter headerGlobalFilter() {
        return ((exchange, chain) -> {
            ServerHttpResponse response = exchange.getResponse();
            HttpHeaders headers = response.getHeaders();
            headers.add("global-filter", "true");

            return chain.filter(exchange);
        });
    }

    /**
     * 设置RequestRateLimiter (redis)
     * @return
     */
    @Bean
    public KeyResolver ipKeyResolver() {
        return exchange -> Mono.just(exchange.getRequest().getRemoteAddress().getAddress().getHostAddress());
    }
}
