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

| Method | URI    | Description | Name        | Done                    |
| ------ | ------ | ----------- | ----------- | ----------------------- |
| POST   | /login | 로그인      | LoginMember | :ballot_box_with_check: |



### /member

| Method | URI       | description    | Name            | Done                    |
| ------ | --------- | -------------- | --------------- | ----------------------- |
| POST   | /user     | 회원가입       | CreateMember    | :ballot_box_with_check: |
| GET    | /users    | 회원조회(list) | GetMemberList   | :ballot_box_with_check: |
| GET    | /user/:id | 회원조회(one)  | GetMember       | :ballot_box_with_check: |
| PUT    | /user/:id | 회원수정(one)  | UpdateMember    | (free)                  |
| DELETE | /user/:id | 회원삭제(one)  | DeleteMember    | (free)                  |
| DELETE | /users    | 회원삭제(list) | DeleteMemberAll | (free)                  |



### /board

| Method      | URI                      | Description             | Name            | Done                    |
| ----------- | ------------------------ | ----------------------- | --------------- | ----------------------- |
| GET         | /notice/:id              | 공지사항 조회(one)      | GetNotice       | :ballot_box_with_check: |
| GET         | /notice/:grade/:classnum | 공지사항 반별조회(list) | GetNoticeFilter |                         |
| GET         | /notices                 | 공지사항 조회(list)     | GetNoticeList   | :ballot_box_with_check: |
| POST        | /notice                  | 공지사항 입력           | CreateNotice    | :ballot_box_with_check: |
| PUT         | /notice/:id              | 공지사항 수정           | UpdateNotice    | (free)                  |
| DELETE      | /notice/:id              | 공지사항 삭제           | DeleteNotice    | (free)                  |
| ----------- | -----------              | -----------             | -----------     |                         |
| GET         | /homework/:id            | 숙제조회(one)           | GetHomework     |                         |
| GET         | /homeworks               | 숙제조회(list)          | GetHomeworkList | :ballot_box_with_check: |
| POST        | /homework                | 숙제입력                | CreateHomework  | :ballot_box_with_check: |
| DELETE      | /homework                | 숙제삭제                | DeleteHomework  | (free)                  |



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

