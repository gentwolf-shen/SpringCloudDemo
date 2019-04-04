package cn.easyar.cloud.springcloudgateway.filter;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Mono;

public class TokenGatewayFilterFactory extends AbstractGatewayFilterFactory<TokenGatewayFilterFactory.Config> {

    public TokenGatewayFilterFactory() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            HttpHeaders headers = request.getHeaders();

            String token = headers.getFirst("token");
            if (StringUtils.isEmpty(token)) {
                token = request.getQueryParams().getFirst("token");
            }

            if (StringUtils.isEmpty(token)) {
                // 检测 token
                // ...

                ServerHttpResponse response = exchange.getResponse();
                HttpHeaders headers1 = response.getHeaders();
                headers1.add("Content-Type", "application/json");

                response.setStatusCode(HttpStatus.UNAUTHORIZED);
                DataBuffer buffer = response.bufferFactory().wrap("{\"statusCode\": 401, \"message\": \"Unauthorized\"}".getBytes());
                return response.writeWith(Mono.just(buffer));
            }

            // 将token解析出的用户信息写的header中
            // ...

            ServerHttpRequest.Builder builder = exchange.getRequest().mutate();
            builder.header("userId", "1");
            builder.header("username", "gentwolf");

            return chain.filter(exchange.mutate().request(builder.build()).build());
        });
    }

    public static class Config {

    }
}
