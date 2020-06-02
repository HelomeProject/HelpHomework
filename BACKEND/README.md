# API 명명 규칙

## HTTP Req Method

| method | description |
| ------ | ----------- |
| POST   | 리소스 추가 |
| GET    | 리소스 조회 |
| PUT    | 리소스 변경 |
| DELETE | 리소스 삭제 |



## API Tree

| resource | description |
| -------- | ----------- |
| /auth    | 인증        |
| /member  | 회원        |
|          |             |



### /member

| Method | URI       | description    | method name     |
| ------ | --------- | -------------- | --------------- |
| POST   | /user     | 회원가입       | CreateMember    |
| GET    | /users    | 회원조회(list) | GetMemberList   |
| GET    | /user/:id | 회원조회(one)  | GetMember       |
| PUT    | /user/:id | 회원수정(one)  | UpdateMember    |
| DELETE | /user/:id | 회원삭제(one)  | DeleteMember    |
| DELETE | /users    | 회원삭제(list) | DeleteMemberAll |

