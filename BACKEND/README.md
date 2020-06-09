# API 문서

## HTTP Req Method

| method | description |
| ------ | ----------- |
| POST   | 리소스 추가 |
| GET    | 리소스 조회 |
| PUT    | 리소스 변경 |
| DELETE | 리소스 삭제 |



## API Tree

| resource  | description        |
| --------- | ------------------ |
| /auth     | 인증               |
| /member   | 회원               |
| /board    | 공지(일반 및 과제) |
| /homework | 과제               |



### /auth

| Method | URI    | Description | Name        | Done                    |
| ------ | ------ | ----------- | ----------- | ----------------------- |
| POST   | /login | 로그인      | LoginMember | :ballot_box_with_check: |



### /member

| Method | URI       | description    | Name          | Done                    |
| ------ | --------- | -------------- | ------------- | ----------------------- |
| POST   | /user     | 회원가입       | CreateMember  | :ballot_box_with_check: |
| GET    | /users    | 회원조회(list) | GetMemberList | :ballot_box_with_check: |
| GET    | /user/:id | 회원조회(one)  | GetMember     | :ballot_box_with_check: |
| PUT    | /user/:id | 회원수정(one)  | UpdateMember  | :ballot_box_with_check: |
| DELETE | /user/:id | 회원삭제(one)  | DeleteMember  | :ballot_box_with_check: |



### /board

| Method      | URI           | Description         | Name                  | Done                    |
| ----------- | ------------- | ------------------- | --------------------- | ----------------------- |
| GET         | /notice/:id   | 공지사항 조회(one)  | GetNotice             | :ballot_box_with_check: |
| GET         | /notices      | 공지사항 조회(list) | GetNoticeList         | :ballot_box_with_check: |
| POST        | /notice       | 공지사항 생성       | CreateNotice          | :ballot_box_with_check: |
| DELETE      | /notice/:id   | 공지사항 삭제       | DeleteNotice          | :ballot_box_with_check: |
| ----------- | -----------   | -----------         | -----------           |                         |
| GET         | /homeworks    | 숙제공지 조회(list) | GetHomeworkNoticeList | :ballot_box_with_check: |
| POST        | /homework     | 숙제공지 생성       | CreateHomeworkNotice  | :ballot_box_with_check: |
| PUT         | /homework/:id | 숙제공지 수정       | UpdateHomeworkNotice  | :ballot_box_with_check: |
| DELETE      | /homework/:id | 숙제공지 삭제       | DeleteHomeworkNotice  | :ballot_box_with_check: |



### /homework

| Method | URI           | Description     | Name            | Done                    |
| ------ | ------------- | --------------- | --------------- | ----------------------- |
| GET    | /homework/:id | 숙제 조회(one)  | GetHomework     | :ballot_box_with_check: |
| GET    | /homeworks    | 숙제 조회(list) | GetHomeworkList | :ballot_box_with_check: |
| POST   | /homework     | 숙제 생성       | CreateHomework  | :ballot_box_with_check: |
| DELETE | /homework/:id | 숙제 삭제       | DeleteHomework  | :ballot_box_with_check: |



----



## DATABASE TABLE

| Name             | Description     |
| ---------------- | --------------- |
| membertb         | 회원 테이블     |
| noticetb         | 공지 테이블     |
| homeworkNoticetb | 과제공지 테이블 |
| homeworktb       | 과제 테이블     |



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



### noticetb

| Field          | Type         | Null | Key     | Default | Extra          |
| -------------- | ------------ | ---- | ------- | ------- | -------------- |
| idx            | int          | NO   | PRI     |         | auto_increment |
| noticeTitle    | varchar(50)  | NO   |         |         |                |
| noticeImgUrl   | varchar(100) | YES  |         |         |                |
| memberIdx      | int          | NO   | MUL(FK) |         |                |
| memberGrade    | int          | NO   |         |         |                |
| memberClassNum | int          | NO   |         |         |                |



### homeworkNoticetb

| Field                    | Type         | Null | Key  | Default | Extra          |
| ------------------------ | ------------ | ---- | ---- | ------- | -------------- |
| HomeworkNotice_idx       | int          | NO   | PRI  |         | auto_increment |
| HomeworkNotice_memberIdx | int          | YES  |      |         |                |
| HomeworkNotice_title     | varchar(100) | YES  |      |         |                |
| HomeworkNotice_startDate | date         | YES  |      |         |                |
| HomeworkNotice_endDate   | date         | YES  |      |         |                |
| HomeworkNotice_detail    | varchar(200) | YES  |      |         |                |



### homeworktb

| Field               | Type         | Null | Key  | Default | Extra          |
| ------------------- | ------------ | ---- | ---- | ------- | -------------- |
| Homework_idx        | int          | NO   | PRI  |         | auto_increment |
| Homework_memberIdx  | int          | YES  |      |         |                |
| Homework_noticeIdx  | int          | YES  |      |         |                |
| Homework_url        | varchar(100) | YES  |      |         |                |
| Homework_score      | float        | YES  |      |         |                |
| Homework_submitDate | date         | YES  |      |         |                |

