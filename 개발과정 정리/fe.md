[vue3] 프로젝트 생성
=======
vue 초기화
-----
- 공식홈페이지에서 node.js 설치

- 폴더 생성 후 Visual Studio Code에서 해당 폴더 선택 후 [Ctrl + ~]

- 터미널에서 뷰 설치 : npm install -g @vue/cli

- 프로젝트 생성
    - vue create aniserver-fe
    - vue3 선택
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

 - 생성 후 터미널에서 생성된 폴더로 이동 후 npm run serve

 - 종료하고 싶으면 터미널에서 [Ctrl + c]

 - package.json에서 원하는 포트로 변경[scripts.serve뒤에 --port 9999 추가]
 ~~~
scripts": {
    "serve": "vue-cli-service serve --port 9999",
}
 ~~~

 - node_modules 폴더는 ignore 처리(vue프로젝트 생성 시 자동으로 등록됨)

 - http://localhost:9999 접속해서 Welcome화면이 보이면 성공