package com.yujing.crash;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class CrashApplication {

    public static void main(String[] args) {
        SpringApplication.run(CrashApplication.class, args);
        System.out.println("=================YuJing=================");
        System.out.println("====　　　　　　　　　　　　　　　　　　　====");
        System.out.println("====　　　　　　　服务启动成功！　　　　　====");
        System.out.println("====　　　　　　　　　　　　　　　　　　　====");
        System.out.println("========================================");
    }
}
