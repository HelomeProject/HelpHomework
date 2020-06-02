# API 명명 규칙

## HTTP Req Method

| method | description |
| ------ | ----------- |
| POST   | 리소스 추가 |
| GET    | 리소스 조회 |
| PUT    | 리소스 변경 |
| DELETE | 리소스 삭제 |



## API Tree

| resource | description      |
| -------- | ---------------- |
| /auth    | 인증             |
| /member  | 회원             |
| /board   | 공지 및 과제보드 |



### /member

| Method | URI       | description    | Name            |
| ------ | --------- | -------------- | --------------- |
| POST   | /user     | 회원가입       | CreateMember    |
| GET    | /users    | 회원조회(list) | GetMemberList   |
| GET    | /user/:id | 회원조회(one)  | GetMember       |
| PUT    | /user/:id | 회원수정(one)  | UpdateMember    |
| DELETE | /user/:id | 회원삭제(one)  | DeleteMember    |
| DELETE | /users    | 회원삭제(list) | DeleteMemberAll |



### /auth

| Method | URI    | Description | Name        |
| ------ | ------ | ----------- | ----------- |
| POST   | /login | 로그인      | LoginMember |



### /board

| Method      | URI         | Description         | Name            |
| ----------- | ----------- | ------------------- | --------------- |
| GET         | /notice     | 공지사항 조회(one)  | GetNotice       |
| GET         | /notices    | 공지사항 조회(list) | GetNoticeList   |
| POST        | /notice     | 공지사항 입력       | CreateNotice    |
| PUT         | /notice/:id | 공지사항 수정       | UpdateNotice    |
| DELETE      | /notice/:id | 공지사항 삭제       | DeleteNotice    |
| ----------- | ----------- | -----------         | -----------     |
| GET         | /homework   | 숙제조회(one)       | GetHomework     |
| GET         | /homeworks  | 숙제조회(list)      | GetHomeworkList |
| POST        | /homework   | 숙제입력            | CreateHomework  |
| DELETE      | /homework   | 숙제삭제            | DeleteHomework  |



