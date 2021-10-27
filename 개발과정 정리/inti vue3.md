[vue3] 프로젝트 생성
=======
vue 초기화
-----
- 공식홈페이지에서 node.js 설치

- 폴더 생성 후 Visual Studio Code에서 해당 폴더 선택 후 [Ctrl + ~]

- 터미널에서 뷰 설치 : npm install -g @vue/cli

-----------
- 프로젝트 생성
    - vue create front
    - Manually select features 선택
        - Babel, TypeScript, Router, Vuex, Linter 선택
    - Vue의 버전은 3.x선택
    - Use class-style component syntax? : N
        - 클래스 컴포넌트 스타일로 개발하기 쉽게 도와주는 데코레이터 사용 여부
    - Use Babel alongside TypeScript (required for modern mode, auto-detected polyfills, transpiling JSX) : Y
        - 타입스크립트에 babel 설정을 할지 여부
    - Use history mode for router? (Requires proper server setup for index fallback in production) : n
        - 히스토리 모드인지 해시 모드인지 
        - https://happy-coding-day.tistory.com/128 참조
    - Pick a linter / formatter config: (Use arrow keys) : ESLint + Prettier
        - fomatter 선택. Prettier가 인기가 제일 많은거같음
    - Pick additional lint features : Lint on save 선택
        - lint 검사 시점 선택
    - Where do you prefer placing config for Babel, ESLint, etc.? : In package.json
        - 설정값을 config에서 할지, package.json에서 할지 선택
    - Save this as a preset for future projects? : y
        - 현재 설정을 다음 프로젝트에서 사용하기위해 저장할지 선택
    - Save preset as : default preset
        - 현재 설정을 저장해둘 preset의 이름
    - VScode에서 권한이 없는 경우
        - [powershell(관리자 권한) -> get-help Set-ExecutionPolicy(권한 목록확인) -> Set-ExecutionPolicy RemoteSigned(변경)]
        - 참조 : https://singa-korean.tistory.com/21
~~~
vue : 이 시스템에서 스크립트를 실행할 수 없으므로 C:\Users\lsh\AppData\Roaming\npm\vue.ps1 파일을 로드할 수 없습니다. 자세한 내용은 about_Execution_Policies(https://go.microsoft.com/fwlink/?LinkID=135170)를 참조하십시오.
위치 줄:1 문자:1
vue create Aniserver-fe
    + CategoryInfo          : 보안 오류: (:) [], PSSecurityException
    + FullyQualifiedErrorId : UnauthorizedAccess
~~~

 - 생성 후 터미널에서 생성된 폴더로 이동 후 실행
    - cd front
    - npm run serve

 - 종료하고 싶으면 터미널에서 [Ctrl + c]

 - package.json에서 원하는 포트로 변경[scripts.serve뒤에 --port 9999 추가]
 ~~~
scripts": {
    "serve": "vue-cli-service serve --port 9999",
}
 ~~~

 - node_modules 폴더는 ignore 처리(vue프로젝트 생성 시 자동으로 등록됨)

 - http://localhost:9999 접속해서 Welcome화면이 보이면 성공