create database if not exists starter_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

use starter_db;

DROP TABLE IF EXISTS tech_stack;
DROP TABLE IF EXISTS profile;

create table if not exists profile (
    id bigint PRIMARY KEY AUTO_INCREMENT comment '프로필 ID',
    name varchar(50) not null comment '이름',
    email varchar(100) not null unique comment '이메일',
    bio varchar(500) comment '자기소개',
    position varchar(20) not null comment '직무',
    career_years int not null default 0 comment '경력 연차',
    github_url varchar(200) comment 'GitHub URL',
    blog_url varchar(200) comment '블로그 URL',
    created_at timestamp default current_timestamp comment '생성일시',
    updated_at timestamp default current_timestamp comment '수정일시'
) ENGINE = InnoDB default charset=utf8mb4 collate=utf8mb4_unicode_ci comment = '프로필 테이블';

create table if not exists tech_stack (
    id bigint primary key auto_increment comment '기술 스택 ID',
    profile_id bigint not null comment '프로필 ID',
    name varchar(50) not null comment '기술명',
    category varchar(20) not null comment '카테고리',
    proficiency varchar(20) not null comment '숙련도',
    years_of_exp int not null default 0 comment '경험 연수',
    created_at timestamp default current_timestamp comment '생성 일시',
    updated_at timestamp default current_timestamp comment '수정 일시',
    foreign key (profile_id) references profile(id) on delete cascade
) ENGINE = InnoDB default charset=utf8mb4 collate=utf8mb4_unicode_ci comment = '테크 스택 테이블';

-- Index 세팅은 하지 않았음