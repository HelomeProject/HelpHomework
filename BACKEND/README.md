# API 문서

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



### /auth

| Method | URI    | Description | Name        |
| ------ | ------ | ----------- | ----------- |
| POST   | /login | 로그인      | LoginMember |



### /member

| Method | URI       | description    | Name            |
| ------ | --------- | -------------- | --------------- |
| POST   | /user     | 회원가입       | CreateMember    |
| GET    | /users    | 회원조회(list) | GetMemberList   |
| GET    | /user/:id | 회원조회(one)  | GetMember       |
| PUT    | /user/:id | 회원수정(one)  | UpdateMember    |
| DELETE | /user/:id | 회원삭제(one)  | DeleteMember    |
| DELETE | /users    | 회원삭제(list) | DeleteMemberAll |



### /board

| Method      | URI                      | Description             | Name            |
| ----------- | ------------------------ | ----------------------- | --------------- |
| GET         | /notice/:id              | 공지사항 조회(one)      | GetNotice       |
| GET         | /notice/:grade/:classnum | 공지사항 반별조회(list) | GetNoticeFilter |
| GET         | /notices                 | 공지사항 조회(list)     | GetNoticeList   |
| POST        | /notice                  | 공지사항 입력           | CreateNotice    |
| PUT         | /notice/:id              | 공지사항 수정           | UpdateNotice    |
| DELETE      | /notice/:id              | 공지사항 삭제           | DeleteNotice    |
| ----------- | -----------              | -----------             | -----------     |
| GET         | /homework/:id            | 숙제조회(one)           | GetHomework     |
| GET         | /homeworks               | 숙제조회(list)          | GetHomeworkList |
| POST        | /homework                | 숙제입력                | CreateHomework  |
| DELETE      | /homework                | 숙제삭제                | DeleteHomework  |



----



## DATABASE TABLE

| Name       | Description |
| ---------- | ----------- |
| membertb   | 회원 테이블 |
| homeworktb | 과제 테이블 |
| noticetb   | 공지 테이블 |



### membertb

| Field     | Type         | Null | Key  | Default | Extra          |
| --------- | ------------ | ---- | ---- | ------- | -------------- |
| idx       | int          | NO   | PRI  |         | auto_increment |
| username  | vharchar(20) | YES  |      |         |                |
| password  | varchar(100) | YES  |      |         |                |
| email     | varchar(50)  | YES  | UNI  |         |                |
| school    | varchar(20)  | YES  |      |         |                |
| grade     | int          | YES  |      |         |                |
| classnum  | int          | YES  |      |         |                |
| isteacher | int          | YES  |      |         |                |



### homeworktb

| Field          | Type         | Null | Key     | Default | Extra          |
| -------------- | ------------ | ---- | ------- | ------- | -------------- |
| idx            | int          | NO   | PRI     |         | auto_increment |
| memberIdx      | int          | YES  | MUL(FK) |         |                |
| homeworkTitle  | varchar(100) | YES  |         |         |                |
| startDate      | date         | YES  |         |         |                |
| endDate        | date         | YES  |         |         |                |
| homeworkDetail | varchar(200) | YES  |         |         |                |



### noticetb

| Field          | Type         | Null | Key     | Default | Extra          |
| -------------- | ------------ | ---- | ------- | ------- | -------------- |
| idx            | int          | NO   | PRI     |         | auto_increment |
| noticeTitle    | varchar(50)  | NO   |         |         |                |
| noticeImgUrl   | varchar(100) | YES  |         |         |                |
| memberIdx      | int          | NO   | MUL(FK) |         |                |
| memberGrade    | int          | NO   |         |         |                |
| memberClassNum | int          | NO   |         |         |                |

