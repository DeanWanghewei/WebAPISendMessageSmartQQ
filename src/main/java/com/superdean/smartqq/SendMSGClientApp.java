package com.superdean.smartqq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Author DeanWanghewei@gmail.com
 * Time: 2018/11/23
 */

@SpringBootApplication
public class SendMSGClientApp {

    private static final Logger LOG = LoggerFactory.getLogger(SendMSGClientApp.class);

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(SendMSGClientApp.class);
        app.run(args);
        LOG.info("SpiderNodePlus start ...");
        LOG.info("let`t go ahead !!!");
    }
}
