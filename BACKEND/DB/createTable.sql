-- create database helome;
use helome;
#[회원 테이블] idx | 이름 | 학교 | 이메일 | 비밀번호 | 등급idx
#[등급 테이블] idx | 이름
#[과목 테이블] idx | 과목명 | 담당자(회원idx)
#[과제 테이블] idx | 과목idx | 과제명
#[점수 테이블] idx | 회원idx | 과제idx | 점수
#[성실 테이블] idx | 회원idx | 과제idx | 점수

-- member table
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
CREATE TABLE IF NOT EXISTS `homeworktb`(
	idx int auto_increment primary key,
    subidx int,
    name varchar(100),
    foreign key (subidx) references subjecttb(idx)
    on delete cascade
);
desc homeworktb;

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
