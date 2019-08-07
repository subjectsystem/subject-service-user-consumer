package com.miyako.subject.service.user.consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.context.annotation.ImportResource;

/**
 * ClassName SubjectServiceCourseConsumerApplication
 * Description //TODO
 * Author weila
 * Date 2019-08-07-0007 11:29
 */
@EnableHystrix
@EnableHystrixDashboard
@SpringBootApplication(scanBasePackages = "com.miyako.subject")
@ImportResource(value = {"classpath:consumer.xml"})
public class SubjectServiceUserConsumerApplication{
    public static void main(String[] args) {
        SpringApplication.run(SubjectServiceUserConsumerApplication.class, args);
    }
}
