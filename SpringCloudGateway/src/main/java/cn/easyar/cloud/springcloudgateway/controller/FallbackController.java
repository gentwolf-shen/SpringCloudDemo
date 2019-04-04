package cn.easyar.cloud.springcloudgateway.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RequestMapping("/fallback")
@RestController
public class FallbackController {

    @RequestMapping("")
    public Mono<Object> fallback() {
        return Mono.just("fallback");
    }
}
