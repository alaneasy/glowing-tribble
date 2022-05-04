package com.example.site_reservation_system;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableCaching
@EnableAsync
@EnableTransactionManagement
@EnableScheduling
@EnableWebMvc
public class SiteReservationSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(SiteReservationSystemApplication.class, args);
    }

}