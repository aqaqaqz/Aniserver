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
