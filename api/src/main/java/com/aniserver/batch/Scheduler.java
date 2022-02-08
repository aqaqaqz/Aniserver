package com.aniserver.batch;

import org.springframework.scheduling.annotation.Scheduled;

public class Scheduler {
    @Scheduled(cron = "0/10 * * * * ?") // crontab과 동일(10초마다 실행)
    public void crontab() {
        System.out.println("crontab - " + System.currentTimeMillis() / 1000);
    }
}
