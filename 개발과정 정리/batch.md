[spring batch] 배치
=======
-----
1. Spring Scheluder
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

- 어노테이션 방식이기 때문에 동적으로 변화가 안된다.
	- 동적으로 변경만 필요하다면 2번이나 3번을 활용해야 한다.

-----
2. Spring Quartz
- build.gradle에 implementation group: 'org.quartz-scheduler', name: 'quartz', version: '2.3.1' 추가 후 빌드
- 버전별로 사용법이 조금씩 다르다. -> http://www.quartz-scheduler.org/documentation/2.3.1-SNAPSHOT/
	- 처음에 2.1.7버전으로 했는데, 사용법이 아예 다르다.
- 메모리기반으로 사용할거라면 상관없지만 클러스트기능이 필요하다면 db설정을 해줘야 한다.
	- application.properties에 db에 관한 정보들을 추가시켜준다.
	- db에 붙을수 있게 gradle 해당 내용을 추가시킨다.(사용 db : postgresql)
~~~
#quartz.properties(http://www.quartz-scheduler.org/documentation/2.3.1-SNAPSHOT/configuration.html)
#StdSchedulerFactory load a properties file named quartz.properties from the "current working directory"

org.quartz.scheduler.instanceName = MyClusteredScheduler
org.quartz.scheduler.instanceId = AUTO

#============================================================================
# Configure ThreadPool
#============================================================================

org.quartz.threadPool.class = org.quartz.simpl.SimpleThreadPool
org.quartz.threadPool.threadCount = 25
org.quartz.threadPool.threadPriority = 5

#============================================================================
# Configure JobStore
#============================================================================

org.quartz.jobStore.misfireThreshold = 60000

org.quartz.jobStore.class = org.quartz.impl.jdbcjobstore.JobStoreTX
org.quartz.jobStore.driverDelegateClass = org.quartz.impl.jdbcjobstore.PostgreSQLDelegate
org.quartz.jobStore.useProperties = <span class="code-keyword">false</span>
org.quartz.jobStore.dataSource = myDS
org.quartz.jobStore.tablePrefix = QRTZ_

org.quartz.jobStore.isClustered = <span class="code-keyword">true</span>
org.quartz.jobStore.clusterCheckinInterval = 20000

#============================================================================
# Configure Datasources
#============================================================================
org.quartz.dataSource.myDS.driver = org.postgresql.Driver
org.quartz.dataSource.myDS.URL = jdbc:postgresql://10.74.102.213:5432/hamonica3
org.quartz.dataSource.myDS.user = hamonica3
org.quartz.dataSource.myDS.password = g2crZqbZHHjvoOK52YdKRg==
org.quartz.dataSource.myDS.maxConnections = 5
org.quartz.dataSource.myDS.validationQuery = select 0
org.quartz.dataSource.myDS.provider = hikaricp
~~~
~~~
	//build.gradle

    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-jdbc")
    implementation group: 'org.postgresql', name: 'postgresql', version: '42.2.23'
~~~

- db설정이 끝나고 정상적으로 실행이 되었다면 접속을 확인한다.
	- 작성한 MainController에 아래의 내용을 추가시켜주고 postman으로 호출 시 조회결과가 맞는지 확인해보자.
~~~
@RestController
public class MainController {
    @Autowired
    JdbcTemplate jdbcTemplate;

    @GetMapping(value = "/dbTest")
    public List<Map<String, Object>> dbTest() throws Exception {
        return jdbcTemplate.queryForList("SELECT * FROM METRICS");
    }
}
~~~

- 사용하는 db에 맞춰서 테이블을 생성해준다. quartz버전별로 스키마가 다를 수 있으니 맞춰서 해준다.
	- https://github.com/quartz-scheduler/quartz/tree/quartz-2.3.x/quartz-core/src/main/resources/org/quartz/impl/jdbcjobstore
	- 공식 문서에는 org.quartz.dataSource.myDS.provider = hikaricp 이 내용이 빠져잇는데 없으면 db에 못붙는다. 스프링 부트인경우 넣어줘야 하는듯한데 자세한 내용은 찾아봐야 함.

- 실행시킬 각 클래스들은 Job interfact를 implements 시켜주고 execute함수를 override해준다.
~~~
public class Test implements Job {
    public Test() {
        System.out.println("TestJob created");
    }

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        System.out.println("TestJob execute~");
    }
}
~~~


-----
3. ThreadPoolTaskScheduler, ThreadPoolTaskExecutor
-----
