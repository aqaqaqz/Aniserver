[node.js] npm 초기화 및 express 설치
=======
npm 초기화
-----
- npm init
~~~
    package name: (apitest)
    version: (1.0.0)
    description: aniserver api
    entry point: (index.js) server.js
    test command: api
    git repository:
    keywords:
    author:
    license: (ISC)
~~~

- npm install express

- server.js
~~~
    const port = 12345;
    const express = require('express');
    const app = express();

    app.get('/test', (req, res) => {
        res.send('server works');
    });

    app.use((req, res, next) => {
        res.status(404).send('404 not found');
    });

    app.listen(port, () => {
        console.log('animation api server start!');
    })
~~~

- node server.js & (&를 붙여주면 백그라운드 실행)

- 돌고있는 서버를 죽이기 위해 프로세스 번호를 가져오자
~~~
     ps -ef | grep node

     root      7574  7362  2 12:41 pts/8    00:00:00 node server.js
     -> kill 7574
~~~

- 서버가 돌아가는지 확인
    - postman에서 [ip]:[port]/test -> rst : server works