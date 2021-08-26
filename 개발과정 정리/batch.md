[spring batch] 배치
=======
-----
1. Spring Scheluder
-----
- RunApplication.java에 @EnableScheduling를 달아준다.
- batch를 돌릴 클래스를 작성하고 크론탭과 동일한 형태로 어노테이션을 달아준다.
- 작성한 클래스는 @Component를 달아 컴포넌트로 등록한다.
~~~
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
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

~~~
- 크론탭 표현
    - 초 | 분 | 시 | 일 | 월 | 요일 | 연도(생략가능)
    - 10초마다 실행하고 싶으면 [0/10 * * * * ?]

-----
2. Spring Quartz
-----