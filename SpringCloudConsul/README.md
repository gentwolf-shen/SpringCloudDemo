### Spring Cloud Consul 简单注册配置

1. POM.XML

    ```
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-actuator</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-consul-discovery</artifactId>
    </dependency>
    ```
2. application.yml

    ```    
    server:
      port: 8080
    spring:
      application:
        name: spring-cloud-consul
      cloud:
        consul:
          host: 127.0.0.1
          port: 8500
          discovery:
            enabled: true
            instance-id: ${spring.application.name}-${spring.cloud.client.ip-address}:${server.port}
            health-check-path: /actuator/health
            service-name: ${spring.application.name}
            prefer-ip-address: true

    ```
    
3. 启动 consul 查看