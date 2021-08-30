package com.aniserver.api.batch;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

public class Scheduler {
    @Scheduled(fixedDelay = 1000) // scheduler 끝나는 시간 기준으로 1000 간격으로 실행
    public void fixedDelay() {
        System.out.println("fixedDelay - " + System.currentTimeMillis() / 1000);
    }

    @Scheduled(fixedRate = 1000) // scheduler 시작하는 시간 기준으로 1000 간격으로 실행
    public void fixedRate() {
        System.out.println("fixedRate - " + System.currentTimeMillis() / 1000);
    }

    @Scheduled(cron = "0/10 * * * * ?") // crontab과 동일(10초마다 실행)
    public void crontab() {
        System.out.println("crontab - " + System.currentTimeMillis() / 1000);
    }
}
