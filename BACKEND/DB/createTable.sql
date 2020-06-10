-- create database helome;
use helome;
#[회원 테이블] idx | 이름 | 학교 | 이메일 | 비밀번호 | 등급idx
#[등급 테이블] idx | 이름
#[과목 테이블] idx | 과목명 | 담당자(회원idx)
#[과제 테이블] idx | 과목idx | 과제명
#[점수 테이블] idx | 회원idx | 과제idx | 점수
#[성실 테이블] idx | 회원idx | 과제idx | 점수

-- member table
drop table membertb;
CREATE TABLE IF NOT EXISTS `membertb` (
	 idx int auto_increment primary key,
     username varchar(20),
     password varchar(100),
     email varchar(50) unique,
     school varchar(20),
     grade int,
     classnum int,
     isteacher int
);
desc membertb;

-- grade table
CREATE TABLE IF NOT EXISTS `gradetb` (
	 idx int primary key,
     name varchar(20) unique
);
desc gradetb;

-- 과목 table
CREATE TABLE IF NOT EXISTS `subjecttb` (
	 idx int auto_increment  primary key,
     name varchar(30) UNIQUE,
     manager int default 0,
     foreign key (manager) references membertb(idx)
);
desc subjecttb;

-- 과제 table
drop table homeworktb;
CREATE TABLE IF NOT EXISTS `homeworktb`(
	Homework_idx int auto_increment primary key,
    Homework_memberIdx int,
    Homework_noticeIdx int,
    Homework_url varchar(100),
    Homework_score float,
    Homework_submitDate date
);
desc homeworktb;

drop table homeworkNoticetb;
CREATE TABLE IF NOT EXISTS `homeworkNoticetb`(
	HomeworkNotice_idx int auto_increment primary key,
    HomeworkNotice_memberIdx int,
    HomeworkNotice_title varchar(100),
    HomeworkNotice_startDate date,
    HomeworkNotice_endDate date,
    HomeworkNotice_detail varchar(200),
    foreign key (HomeworkNotice_idx) references membertb(idx)
);
desc homeworkNoticetb;

-- 점수 table
CREATE TABLE IF NOT EXISTS `scoretb` (
	idx int auto_increment primary key,
	memidx int,
    homeidx int,
    score int,
    foreign key (memidx) references membertb(idx),
    foreign key (homeidx) references homeworktb(idx)
);
desc scoretb;

-- 성실점수 테이블
CREATE TABLE IF NOT EXISTS `score2tb` (
	idx int auto_increment primary key,
	memidx int,
    homeidx int,
    score int,
    foreign key (memidx) references membertb(idx),
    foreign key (homeidx) references homeworktb(idx)
);
desc score2tb;

-- 공지사항 테이블
drop table noticetb;
CREATE TABLE IF NOT EXISTS `noticetb` (
	idx int auto_increment primary key,
	memberIdx int,
    memberGrade int,
    memberClassNum int,
    noticeTitle varchar(50),
    noticeImgUrl varchar(100),
    foreign key (memberIdx) references membertb(idx)
);
desc noticetb;
