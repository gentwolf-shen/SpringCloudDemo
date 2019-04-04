package cn.easyar.springcloudclient.feignclient;

import feign.hystrix.FallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "spring-cloud-consul", fallbackFactory = FeignFallback.class)
public interface TestFeignClient {
    @GetMapping("/test/app/ip")
    String appIp();
}

@Component
class FeignFallback implements FallbackFactory<TestFeignClient> {
    @Override
    public TestFeignClient create(Throwable throwable) {

        return () -> String.format("ERROR %s", throwable.getCause());
    }
}
