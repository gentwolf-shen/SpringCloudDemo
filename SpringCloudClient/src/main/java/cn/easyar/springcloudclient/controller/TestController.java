package cn.easyar.springcloudclient.controller;

import cn.easyar.springcloudclient.feignclient.TestFeignClient;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RequestMapping("/test")
@RestController
public class TestController {

    @Autowired
    private TestFeignClient testFeignClient;

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/app/ip")
    public String ipFromFeign() {
        return this.testFeignClient.appIp();
    }

    @HystrixCommand(fallbackMethod = "fallback")
    @GetMapping("/app/ip/a")
    public String ipFromFeginA() {
        return this.restTemplate.getForObject("http://spring-cloud-consul/test/app/ip", String.class);
    }

    public String fallback() {
        return "ERROR FALLBACK";
    }
}
