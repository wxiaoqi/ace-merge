package com.github.wxiaoqi.ace.merge.demo;

import com.github.wxiaoqi.merge.EnableAceMerge;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;

/**
 *
 * @author ace
 * @date 2017/7/28
 */
@EnableEurekaClient
@EnableFeignClients("com.github.wxiaoqi.ace.merge.demo.feign")
@EnableAceMerge
@SpringBootApplication
public class DemoBootstrap {
    public static void main(String[] args) {
        SpringApplication.run(DemoBootstrap.class, args);
    }
}
