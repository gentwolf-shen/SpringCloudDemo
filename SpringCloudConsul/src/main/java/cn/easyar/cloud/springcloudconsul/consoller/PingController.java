package cn.easyar.cloud.springcloudconsul.consoller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
public class PingController {

    @GetMapping("/ping")
    public Object ping() {
        Map<String, Object> map = new HashMap<>();
        map.put("message", "pong");
        map.put("date", new Date());
        return map;
    }
}
