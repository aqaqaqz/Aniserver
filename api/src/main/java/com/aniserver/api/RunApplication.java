package com.aniserver.api;

import com.aniserver.api.batch.Batch;
import com.aniserver.api.batch.Job.OhysDownload;
import com.aniserver.api.model.QuartzInfo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;


@EnableScheduling
@SpringBootApplication
public class RunApplication {
    public static void main(String[] args) {
        SpringApplication.run(RunApplication.class, args);


    }
}