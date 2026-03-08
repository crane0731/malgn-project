# README

# [(주)맑은 기술 ] 코딩 과제

이메일 :  dlwnsgkr8318@naver.com

## 주제 :  CMS **(Content Management System) REST API 개발**


## 프로젝트 기간

- 2026-03-08 ~ 2026-03-09

## 기술 스택

- Java 21
- Spring Boot 4
- Spring Security
- JPA
- H2 Database
- Lombok
- JWT
- QueryDsl
- Caffeine(단일 메모리 캐시)

## AI
- chat gpt 를 활용하여 에러 메시지 부분 질문

## 실행 방법

- 깃허브 에서 zip파일 다운을 받거나 clone 한다
- intelij 에서 프로젝트를 열고 실행시킨다.
- h2 데이터베이스에 다음 밑의 sql문을 복사 붙혀 넣은 후 실행 한다.

```sql
create table member(
 member_id bigint auto_increment primary key,
 email varchar(50) not null,
 password varchar(100) not null,
 name varchar(50) not null,
 role varchar(10) not null,
 created_date timestamp default current_timestamp not null,
 last_modified_date timestamp default current_timestamp not null,
 delete_status varchar(10) not null, 
 deleted_date timestamp 
);

create table token(
  token_id bigint auto_increment primary key,
  member_id bigint not null,
  refresh_token varchar(255) not null,
  created_date timestamp default current_timestamp not null,
  last_modified_date timestamp default current_timestamp not null,

  constraint fk_token_member foreign key (member_id) references member(member_id)
);

create table content(
  content_id bigint auto_increment primary key,
  member_id bigint,
  title varchar(100) not null,
  description text,
  view_count bigint not null,
  created_date timestamp default current_timestamp not null,
  last_modified_date timestamp default current_timestamp not null,
  created_by varchar(50) not null,
  last_modified_by varchar(50) not null,
  delete_status varchar(10) not null,
  
  constraint fk_content_member foreign key (member_id) references member(member_id)
  
);

insert into member (email,password,name,role,delete_status) values ('admin@naver.com','$2a$10$gDHN103kfylm4v8bvCKGUuy7.nke9aB.szc2NG5c9WnyBlzvLDj.W','관리자','ADMIN','UNDELETED');

insert into member (email,password,name,role,delete_status) values ('tester1@naver.com','$2a$10$gDHN103kfylm4v8bvCKGUuy7.nke9aB.szc2NG5c9WnyBlzvLDj.W','테스터1','MEMBER','UNDELETED');

insert into member (email,password,name,role,delete_status) values ('tester2@naver.com','$2a$10$gDHN103kfylm4v8bvCKGUuy7.nke9aB.szc2NG5c9WnyBlzvLDj.W','테스터2','MEMBER','UNDELETED');

insert into member (email,password,name,role,delete_status) values ('tester3@naver.com','$2a$10$gDHN103kfylm4v8bvCKGUuy7.nke9aB.szc2NG5c9WnyBlzvLDj.W','테스터3','MEMBER','UNDELETED');

insert into member (email,password,name,role,delete_status) values ('tester4@naver.com','$2a$10$gDHN103kfylm4v8bvCKGUuy7.nke9aB.szc2NG5c9WnyBlzvLDj.W','테스터4','MEMBER','UNDELETED');

insert into member (email,password,name,role,delete_status) values ('tester5@naver.com','$2a$10$gDHN103kfylm4v8bvCKGUuy7.nke9aB.szc2NG5c9WnyBlzvLDj.W','테스터5','MEMBER','UNDELETED');

insert into member (email,password,name,role,delete_status) values ('tester6@naver.com','$2a$10$gDHN103kfylm4v8bvCKGUuy7.nke9aB.szc2NG5c9WnyBlzvLDj.W','테스터6','MEMBER','UNDELETED');

insert into member (email,password,name,role,delete_status) values ('tester7@naver.com','$2a$10$gDHN103kfylm4v8bvCKGUuy7.nke9aB.szc2NG5c9WnyBlzvLDj.W','테스터7','MEMBER','UNDELETED');

insert into member (email,password,name,role,delete_status) values ('tester8@naver.com','$2a$10$gDHN103kfylm4v8bvCKGUuy7.nke9aB.szc2NG5c9WnyBlzvLDj.W','테스터8','MEMBER','UNDELETED');

insert into member (email,password,name,role,delete_status) values ('tester9@naver.com','$2a$10$gDHN103kfylm4v8bvCKGUuy7.nke9aB.szc2NG5c9WnyBlzvLDj.W','테스터9','MEMBER','UNDELETED');

insert into member (email,password,name,role,delete_status) values ('tester10@naver.com','$2a$10$gDHN103kfylm4v8bvCKGUuy7.nke9aB.szc2NG5c9WnyBlzvLDj.W','테스터10','MEMBER','UNDELETED');

insert into content (member_id,title,description,view_count,created_by,last_modified_by,delete_status)
values (1,'테스트 게시글 1','테스트 내용입니다 1',10,'1','1','UNDELETED');

insert into content (member_id,title,description,view_count,created_by,last_modified_by,delete_status)
values (2,'테스트 게시글 2','테스트 내용입니다 2',5,'2','2','UNDELETED');

insert into content (member_id,title,description,view_count,created_by,last_modified_by,delete_status)
values (3,'테스트 게시글 3','테스트 내용입니다 3',7,'3','3','UNDELETED');

insert into content (member_id,title,description,view_count,created_by,last_modified_by,delete_status)
values (4,'테스트 게시글 4','테스트 내용입니다 4',15,'4','4','UNDELETED');

insert into content (member_id,title,description,view_count,created_by,last_modified_by,delete_status)
values (5,'테스트 게시글 5','테스트 내용입니다 5',20,'5','5','UNDELETED');

insert into content (member_id,title,description,view_count,created_by,last_modified_by,delete_status)
values (6,'테스트 게시글 6','테스트 내용입니다 6',3,'6','6','UNDELETED');

insert into content (member_id,title,description,view_count,created_by,last_modified_by,delete_status)
values (7,'테스트 게시글 7','테스트 내용입니다 7',11,'7','7','UNDELETED');

insert into content (member_id,title,description,view_count,created_by,last_modified_by,delete_status)
values (8,'테스트 게시글 8','테스트 내용입니다 8',6,'8','8','UNDELETED');

insert into content (member_id,title,description,view_count,created_by,last_modified_by,delete_status)
values (9,'테스트 게시글 9','테스트 내용입니다 9',4,'9','9','UNDELETED');

insert into content (member_id,title,description,view_count,created_by,last_modified_by,delete_status)
values (10,'테스트 게시글 10','테스트 내용입니다 10',12,'10','10','UNDELETED');

insert into content (member_id,title,description,view_count,created_by,last_modified_by,delete_status)
values (1,'테스트 게시글 11','테스트 내용입니다 11',9,'1','1','UNDELETED');

insert into content (member_id,title,description,view_count,created_by,last_modified_by,delete_status)
values (2,'테스트 게시글 12','테스트 내용입니다 12',8,'2','2','UNDELETED');

insert into content (member_id,title,description,view_count,created_by,last_modified_by,delete_status)
values (3,'테스트 게시글 13','테스트 내용입니다 13',13,'3','3','UNDELETED');

insert into content (member_id,title,description,view_count,created_by,last_modified_by,delete_status)
values (4,'테스트 게시글 14','테스트 내용입니다 14',17,'4','4','UNDELETED');

insert into content (member_id,title,description,view_count,created_by,last_modified_by,delete_status)
values (5,'테스트 게시글 15','테스트 내용입니다 15',2,'5','5','UNDELETED');

insert into content (member_id,title,description,view_count,created_by,last_modified_by,delete_status)
values (6,'테스트 게시글 16','테스트 내용입니다 16',19,'6','6','UNDELETED');

insert into content (member_id,title,description,view_count,created_by,last_modified_by,delete_status)
values (7,'테스트 게시글 17','테스트 내용입니다 17',14,'7','7','UNDELETED');

insert into content (member_id,title,description,view_count,created_by,last_modified_by,delete_status)
values (8,'테스트 게시글 18','테스트 내용입니다 18',16,'8','8','UNDELETED');

insert into content (member_id,title,description,view_count,created_by,last_modified_by,delete_status)
values (9,'테스트 게시글 19','테스트 내용입니다 19',21,'9','9','UNDELETED');

insert into content (member_id,title,description,view_count,created_by,last_modified_by,delete_status)
values (10,'테스트 게시글 20','테스트 내용입니다 20',18,'10','10','UNDELETED');

```

## ERD

 [https://www.erdcloud.com/d/c57sLbeQd5T3qw4jp](https://www.erdcloud.com/d/c57sLbeQd5T3qw4jp)

<img width="1275" height="632" alt="image" src="https://github.com/user-attachments/assets/4290847c-dc47-4b16-a55a-1c8437b25284" />


- 회원 과 콘텐츠 테이블은 SOFT DELETE 방식을 사용.

## 기능

### MEMBER

회원가입

- [post] localhost:8081/api/malgn/auth/signup
- 인증헤더  : x
- body

```json
{
    "email" : "test1@naver.com",
    "password" : "1234",
    "passwordCheck" : "1234",
    "name" : "테스터1"
}
```

- 응답

```json
{
    "success": true,
    "message": "요청이 성공했습니다.",
    "data": {
        "message": "회원가입 성공"
    }
}
```

로그인 (Spring Security + JWT)  (MEMBER , ADMIN)

- [post] localhost:8081/api/malgn/auth/login
- 인증헤더  : x
- body

```json
{
    "email":"tester1@naver.com",
    "password":"1234"
}
```

- 응답

```json
{
    "success": true,
    "message": "요청이 성공했습니다.",
    "data": {
        "accessToken": "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJkbHduc2drcjgzMThAbmF2ZXIuY29tIiwiaWF0IjoxNzcyOTgxNDgwLCJleHAiOjE3NzI5ODMyODAsInN1YiI6InRlc3RlcjFAbmF2ZXIuY29tIiwiaWQiOjIsInJvbGUiOiJNRU1CRVIifQ.l46mZPVlD69WVJnPcUCI1oV1Usd4NXM_SNjm02NepHk",
        "refreshToken": "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJkbHduc2drcjgzMThAbmF2ZXIuY29tIiwiaWF0IjoxNzcyOTgxNDgwLCJleHAiOjE3NzI5ODg2ODAsInN1YiI6InRlc3RlcjFAbmF2ZXIuY29tIiwiaWQiOjIsInJvbGUiOiJNRU1CRVIifQ.ir47w_o18E7uSGn-D83Dz09ZcUlm0iKJGzrci1MYTCI"
    }
}
```

로그아웃

- [post] localhost:8081/api/malgn/auth/logout
- 인증헤더  :  Bearer [access_token]
- 권한 : ADMIN, MEMBER
- 응답

```json
{
    "success": true,
    "message": "요청이 성공했습니다.",
    "data": {
        "message": "로그아웃 성공"
    }
}
```

회원 자신의 정보 상세 조회

- [GET] localhost:8081/api/malgn/members/me
- 인증헤더  :  Bearer [access_token]
- 권한 : ADMIN, MEMBER
- 응답

```json
{
    "success": true,
    "message": "요청이 성공했습니다.",
    "data": {
        "email": "test1@naver.com",
        "name": "테스터1",
        "createdDate": "2026.03.08 20:35",
        "lastModifiedDate": "2026.03.08 20:35",
        "contentsCount": 2
    }
}
```

회원 탈퇴(SOFT DELETE)
- 회원 탈퇴는 soft delete 를 사용하고 삭제시 email , name 을 "deleted_" + 난수+ "_" +email , "deleted_" +난수+ "_" + name 과 같은식으로 변경
- [post] localhost:8081/api/malgn/members/me/withdraw
- 인증헤더  :  Bearer [access_token]
- 권한 : ADMIN, MEMBER
- 응답

```json
{
    "success": true,
    "message": "요청이 성공했습니다.",
    "data": {
        "message": "회원 탈퇴 성공"
    }
}
```

### TOKEN

리프레쉬 토큰을 통한 엑세스 토큰 재발급

- [post] localhost:8081/api/malgn/auth/token
- 인증헤더  :  Bearer [refresh_token]
- 권한 : ADMIN, MEMBER
- 응답

```json
{
    "success": true,
    "message": "요청이 성공했습니다.",
    "data": {
        "accessToken": "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJkbHduc2drcjgzMThAbmF2ZXIuY29tIiwiaWF0IjoxNzcyOTY5MTgyLCJleHAiOjE3NzI5NzA5ODIsInN1YiI6InRlc3QxQG5hdmVyLmNvbSIsImlkIjozLCJyb2xlIjoiTUVNQkVSIn0.WF8QuUBYHnmArE7qP-8jrV8W5DsntHE8_SzRU9-xLgk",
        "refreshToken": "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJkbHduc2drcjgzMThAbmF2ZXIuY29tIiwiaWF0IjoxNzcyOTY4NTkyLCJleHAiOjE3NzI5NzU3OTIsInN1YiI6InRlc3QxQG5hdmVyLmNvbSIsImlkIjozLCJyb2xlIjoiTUVNQkVSIn0.Z3Lq6X7eIaY7YBOb_mNI2PxMFHxV0w1JasqQTCEKDN4"
    }
}
```

### CONTENT

콘텐츠 생성

- [post] localhost:8081/api/malgn/contents
- 인증헤더  :  Bearer [access_token]
- 권한 : ADMIN, MEMBER
- body

```json
{
    "title":"테스트 콘텐츠입니다",
    "description" : "테스트 콘텐츠 내용 입니다."
}
```

- 응답

```json
{
    "success": true,
    "message": "요청이 성공했습니다.",
    "data": {
        "message": "콘텐츠 생성 성공"
    }
}
```

콘텐츠 수정(본인의 콘텐츠만)

- [put] localhost:8081/api/malgn/contents/{id}
- 인증헤더  :  Bearer [access_token]
- 권한 : ADMIN, MEMBER
- body

```json
{
    "title":"수정된 테스트 콘텐츠",
    "description":"수정된 테스트 콘텐츠 내용"
}
```

- 응답

```json
{
    "success": true,
    "message": "요청이 성공했습니다.",
    "data": {
        "message": "콘텐츠 수정 성공"
    }
}
```

콘텐츠 삭제(본인의 콘텐츠만 , SOFT DELETE)

- [delete] localhost:8081/api/malgn/contents/{id}
- 인증헤더  :  Bearer [access_token]
- 권한 : ADMIN, MEMBER
- 응답

```json
{
    "success": true,
    "message": "요청이 성공했습니다.",
    "data": {
        "message": "콘텐츠 삭제 성공"
    }
}
```

콘텐츠 상세 조회(조회수 증가는 버퍼 +스케줄러 사용)

- 조회 할 때 마다 조회수를 증가시키는 update 쿼리는 서버에 큰 부하를 줄 수 있음 → 따라서 조회수는 따로 버퍼에 모은 후 스케줄러를 사용해 특정 시간마다 update
- [get] localhost:8081/api/malgn/contents/{id}
- 인증헤더  :  Bearer [access_token]
- 권한 : ADMIN, MEMBER
- 응답

```json
{
    "success": true,
    "message": "요청이 성공했습니다.",
    "data": {
        "contentId": 3,
        "createdDate": "2026.03.08 21:00",
        "description": "수정텐 테스트 콘텐츠 내용",
        "lastModifiedDate": "2026.03.08 21:08",
        "memberEmail": "test1@naver.com",
        "memberId": 4,
        "memberName": "테스터1",
        "title": "수정된 테스트 콘텐츠",
        "viewCount": 3
    }
}
```

콘텐츠 목록 조회 (검색 조건 , 정렬 조건 , 페이징 , 캐싱)

- 서비스의 특성상 무검색, 무정렬 첫 페이지 목록 조회가  가장 많은 트래픽이 발생하는 api라고 판단 하여 첫페이지 조회부분 caffeine 캐시 사용 (서비스가 커지고 트래픽이 많아지면 redis 도입 고려)
- [get] localhost:8081/api/malgn/contents
- 인증헤더  :  Bearer [access_token]
- 권한 : ADMIN, MEMBER
- request parameter
    - memberName → 회원 이름 (필수 x)
    - title → 콘텐츠 제목 (필수 x)
    - sortType → 정렬 타입 (`LATEST//최신순 , OLDEST//오래된순, HIGH_VIEW //조회수 높은 순)`(필수 x)
    - page →페이지 번호 (필수 x)
- 응답

```json
{
    "success": true,
    "message": "요청이 성공했습니다.",
    "data": {
        "content": [
            {
                "contentId": 4,
                "createdDate": "2026.03.08 21:04",
                "description": null,
                "lastModifiedDate": "2026.03.08 21:04",
                "memberEmail": "test1@naver.com",
                "memberId": 4,
                "memberName": "테스터1",
                "title": "테스트 콘텐츠2",
                "viewCount": 0
            },
            {
                "contentId": 3,
                "createdDate": "2026.03.08 21:00",
                "description": "수정텐 테스트 콘텐츠 내용",
                "lastModifiedDate": "2026.03.08 21:08",
                "memberEmail": "test1@naver.com",
                "memberId": 4,
                "memberName": "테스터1",
                "title": "수정된 테스트 콘텐츠",
                "viewCount": 5
            },
            {
                "contentId": 2,
                "createdDate": "2026.03.08 21:00",
                "description": "테스트 콘텐츠 1입니다.",
                "lastModifiedDate": "2026.03.08 21:00",
                "memberEmail": "test1@naver.com",
                "memberId": 4,
                "memberName": "테스터1",
                "title": "테스트 콘텐츠1",
                "viewCount": 0
            }
        ],
        "page": 0,
        "size": 10,
        "totalPages": 1,
        "totalElements": 3,
        "first": true,
        "last": true
    }
}
```

### ADMIN

콘텐츠 수정(모든 콘텐츠 가능)

- [put]  localhost:8081/api/malgn/admin/contents/{id}
- 인증헤더  :  Bearer [access_token]
- 권한 : ADMIN
- body

```json
{
    "title":"관리자가 수정함",
    "description":"관리자가 수정한 내용"
}
```

- 응답

```json
{
    "success": true,
    "message": "요청이 성공했습니다.",
    "data": {
        "message": "콘텐츠 수정 성공"
    }
}
```

콘텐츠 삭제(모든 콘텐츠 가능 , SOFT DELETE)

- [delete] localhost:8081/api/malgn/admin/contents/{id}
- 인증헤더  :  Bearer [access_token]
- 권한 : ADMIN
- 응답

```json
{
    "success": true,
    "message": "요청이 성공했습니다.",
    "data": {
        "message": "콘텐츠 삭제 성공"
    }
}
```

## API 명세

### MEMBER

| 기능 | URL | 방식 | 설명 |
| --- | --- | --- | --- |
| 회원 가입 | `/api/malgn/auth/signup` | POST | 회원 가입을 진행한다. |
| 로그인 | `/api/malgn/auth/login` | POST | 로그인을 진행한다. |
| 로그아웃 | `/api/malgn/auth/logout` | POST | 로그아웃을 진행한다. |
| 자신의 정보 조회 | `/api/malgn/members/me` | GET | 자신의 정보를 상세 조회한다. |
| 회원 탈퇴 | `/api/malgn/members/me/withdraw` | POST | 회원 탈퇴를 진행한다. |

### TOKEN

| 기능 | URL | 방식 | 설명 |
| --- | --- | --- | --- |
| 엑세스 토큰 재발급 | `/api/malgn/auth/token` | POST | 리프레쉬 토큰을 기반으로 엑세스 토큰을 재발급 한다. |

### CONTENT

| 기능 | URL | 방식 | 설명 |
| --- | --- | --- | --- |
| 콘텐츠 생성 | `/api/malgn/contents`  | POST | 콘텐츠를 생성한다. |
| 콘텐츠 수정 | `/api/malgn/contents/{id}`  | PUT | 자신의 콘텐츠를 수정한다. |
| 콘텐츠 삭제 | `/api/malgn/contents/{id}` | DELETE | 자신의 콘텐츠를 삭제한다. |
| 콘텐츠 상세 조회 | `/api/malgn/contents/{id}`  | GET | 콘텐츠를 상세 조회 한다. |
| 콘텐츠 목록 조회 | `/api/malgn/contents`  | GET | 콘텐츠 목록을 조회한다. |

### ADMIN

| 기능 | URL | 방식 | 설명 |
| --- | --- | --- | --- |
| 콘텐츠 수정 | `/api/malgn/admin/contents/{id}`  | PUT | 콘텐츠를 수정한다. |
| 콘텐츠 삭제 | `/api/malgn/admin/contents/{id}` | DELETE | 콘텐츠를 삭제한다. |
