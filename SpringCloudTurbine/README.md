断路器聚合监控(Hystrix Turbine)
----------------------------

1. pom.xml

    ```
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-netflix-turbine</artifactId>
        <exclusions>
            <exclusion>
                <groupId>org.springframework.cloud</groupId>
                <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
            </exclusion>
        </exclusions>
    </dependency>
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-netflix-hystrix-dashboard</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-consul-discovery</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-actuator</artifactId>
    </dependency>
    ```
    
2. application.yml
    
    ```
    turbine:
      aggregator:
        cluster-config: default
      app-config: spring-cloud-client-a,spring-cloud-client-b
      cluster-name-expression: new String("default")
      combine-host-port: true
    ```

3. dashboard

    http://127.0.0.1:{PORT}/hystrix
    
    输入：  http://127.0.0.1:{PORT}/turbine.stream
    
    控制台中查看主机数： curl http://127.0.0.1:{PORT}/turbine.stream
    
4. 调试时发现问题

    1. 检测不到虚拟机的服务