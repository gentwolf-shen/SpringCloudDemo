package cn.easyar.cloud.springcloudconfigclient.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RefreshScope
@RequestMapping("/test")
@RestController
public class TestController {

    @Value("${spring.application.name:NONE}")
    private String appName;

    @Value("${app.pwd:NONE}")
    private String pwd;

    @Value("${app.msg:NONE}")
    private String msg;

    @GetMapping("/name")
    public String name() {
        return this.appName;
    }

    @GetMapping("/pwd")
    public String pwd() {
        return this.pwd;
    }

    @GetMapping("/msg")
    public String msg() {
        return this.msg;
    }
}
