### spring boot client

从注册中心访问后端API

1. application.yml

    ```
    spring:
      application:
        name: spring-cloud-client
      cloud:
        consul:
          host: 10.10.20.212
          port: 8500
          discovery:
            register: false # 不需要注册到consul
    ```

2. 使用RestTemplate负载均衡

    ```
    @Configuration
    public class AppConfiguration {
        @Bean
        @LoadBalanced
        public RestTemplate restTemplate() {
            return new RestTemplate();
        }
    }
    ```
    
    使用：
    ```
    @Autowired
    private RestTemplate restTemplate;
    
    // ...
    
    this.restTemplate.getForObject(String.format("http://%s/test/app/ip", "spring-cloud-consul"), String.class)
    ```
    
3. 使用 feign

    使用feign fallback, 在application.yml增加：
    ```
    feign:
      hystrix:
        enabled: true
    ```

    ```
    // 仅使用name, 则有负载均衡， 如果添加了url,则name失效
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

    ```    

4. HystrixDashboard

    1. 启动类增加 @EnableHystrixDashboard
    
    2. 增加 @Bean
        ```
        @Bean
        public ServletRegistrationBean servletRegistrationBean() {
            HystrixMetricsStreamServlet streamServlet = new HystrixMetricsStreamServlet();
            ServletRegistrationBean registrationBean = new ServletRegistrationBean(streamServlet);
            registrationBean.addUrlMappings("/hystrix.stream");
    
            return registrationBean;
        }
        ```
        
    3. controller
        ```
            @Autowired
            private TestFeignClient testFeignClient;
            
            // ...
            
            @GetMapping("/app/ip")
            public String ipFromFeign() {
                return this.testFeignClient.appIp();
            }
        ```
    
    4. 访问地址
        
        http://127.0.0.1:8082/hystrix, 输入 http://127.0.0.1:8082/hystrix.stream