[spring boot] 프로젝트 생성
=======
초기화
-----
- IntelliJ comunity 설치 : https://www.jetbrains.com/ko-kr/idea/download/#section=windows

- maven과 gardle 2개의 선택지가 있는데 gradle로 프로젝트를 생성한다.
    - New Project -> Gradle -> 프로젝트명과 path를 입력 -> finish

- gradle 프로젝트를 생성했으니 spring boot에 필요한 설정을 해준다.
    - gradle 버전은 7, JDK는 14다.
    - build.gardle 파일을 열어 아래의 내용을 넣는다. 
    - 에러가 난다면 implementation -> compile, testImplementataion -> testCompile로 변경해본다.(버전에 따라 다르다함)
        - https://bluayer.com/13
    - 설정값에 대한 내용 참고 https://earth-95.tistory.com/78
~~~
buildscript{
    ext{
        springBootVersion = '2.1.7.RELEASE'
    }
    repositories {
        mavenCentral()
        jcenter()
    }
    dependencies{
        classpath("org.springframework.boot:spring-boot-" +
                "gradle-plugin:${springBootVersion}")
    }
}

apply plugin: 'java'
apply plugin: 'eclipse'
apply plugin: 'org.springframework.boot'
apply plugin: 'io.spring.dependency-management'

group 'com.aniserver.api'
version '1.0-SNAPSHOT'

repositories {
    mavenCentral()
}

dependencies {
    implementation('org.springframework.boot:spring-boot-starter-web')
    testImplementation('org.springframework.boot:spring-boot-starter-test')
}
~~~

- 입력 후 코끼리(load gradle changes) 클릭

- java폴더 아래 패키지 생성 후 프로젝트 실행을 위한 class를 및 테스트 controller를 생성한다.
~~~
//RunApplication.java

package com.aniserver.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RunApplication {
    public static void main(String[] args) {
        SpringApplication.run(RunApplication.class, args);
    }
}
~~~
~~~
//MainController.java

package com.aniserver.api;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {

    @RequestMapping(value = "/")
    public String check() {
        return "server works";
    }
}
~~~

- terminal에서 ./gradlew bootRun 입력하면 실행

- localhost:8080로 쐈을때 check()함수에서 "server workds"가 리턴되는것까지 확인

- git에 push하기 전에 ignore에 아래 내용을 추가해준다.
~~~
### Java template
*.class
 
# Package Files #
*.jar
*.war
*.ear
 
### macOS template
*.DS_Store
.AppleDouble
.LSOverride
 
# IntelliJ project files
.idea
.idea/*.xml
*.iml
out
gen
build
rebel.xml
 
# Compliled files
/target/
**/target
 
/example/
 
# Gradle
.gradle
/build/
.gradletasknamecache
~~~

- db 설정(sqlite)
    - 처음엔 mysql을 사용하려 했는데 다른곳에서 설정하려면 매우 귀찮아진다. 그래서 sqlite로 변경했다.
    - 그래들에 아래 2개를 추가하고 빌드한다.
        - implementation group: 'org.xerial', name: 'sqlite-jdbc', version: '3.8.11.2'
        - implementation 'com.github.gwenn:sqlite-dialect:0.1.2'
    - gui가 짱이다. sqlite를 보기 위해 sqlitebrowser를 다운받는다.
        - https://sqlitebrowser.org/blog/version-3-12-2-released/
    - 프로젝트 폴더 안에 sqlitesample.db라는 놈이 생기는데 날려버리고 sqlitebrowser를 열어 새로 생성해준다.
    - 들어진 db를 application.properties에 넣어준다.
        - spring.datasource.url=jdbc:sqlite:aniserver.db
    - 정상적으로 동작되는지 확인을 위해 TEST란 테이블을 만들고 컬럼 생성후 쿼리를 날려본다.
        - 컨트롤러에 해당 내용을 넣고 postman으로 쏴서 결과물이 나오면 설정은 끝.
~~~
    @GetMapping(value = "/dbTest")
    public List<Map<String, Object>> dbTest() throws Exception {
        return jdbcTemplate.queryForList("SELECT * FROM TEST");
    }
~~~