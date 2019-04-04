package cn.easyar.springcloudclient.controller;

import cn.easyar.springcloudclient.feignclient.TestFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/test")
@RestController
public class TestController {

    @Autowired
    private TestFeignClient testFeignClient;

    @GetMapping("/app/ip")
    public String ipFromFeign() {
        return this.testFeignClient.appIp();
    }

}
