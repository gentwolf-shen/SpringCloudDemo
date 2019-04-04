package cn.easyar.cloud.springcloudconsul.consoller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RequestMapping("/test")
@RestController
public class TestController {

    @Value("${spring.cloud.consul.discovery.instance-id}")
    private String instanceId;

    @GetMapping("/app/instanceid")
    public String instanceId() {
        return this.instanceId;
    }

    @GetMapping("/app/ip")
    public String clientIp(HttpServletRequest request) {
        return String.format("%s:%s", request.getLocalAddr(), request.getLocalPort());
    }

    @GetMapping("/app/token")
    public Map<String, Object> token(HttpServletRequest request,
                        @RequestParam(name = "token", required = false, defaultValue = "") String token) {
        String userId = request.getHeader("userId");
        String username = request.getHeader("username");

        Map<String, Object> map = new HashMap<>();
        map.put("token", token);
        map.put("userId", userId);
        map.put("username", username);

        return map;
    }

    @GetMapping("/app/header")
    public String header(HttpServletRequest request) {
        return String.format("X-Request-Gateway = %s", request.getHeader("X-Request-Gateway"));
    }

    @GetMapping("/app/status/{status}")
    public ResponseEntity<String> httpStatus(@PathVariable(value = "status") int status) {
        return new ResponseEntity<>("dsf", HttpStatus.BAD_GATEWAY);
    }
}
