package com.sentinel.track;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling; // CHECK THIS LINE

@SpringBootApplication
@EnableScheduling // CHECK THIS LINE - IT IS THE MASTER SWITCH
public class TrackApplication {
    public static void main(String[] args) {
        SpringApplication.run(TrackApplication.class, args);
    }
}