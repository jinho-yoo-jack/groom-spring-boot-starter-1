-- 데이터베이스 생성
CREATE DATABASE IF NOT EXISTS profile_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- 데이터베이스 사용
use profile_db;

-- 테이블 삭제
DROP TABLE IF EXISTS tech_stack;
DROP TABLE IF EXISTS profile;

-- profile 테이블 생성
CREATE TABLE IF NOT EXISTS profile
(
  id BIGINT auto_increment PRIMARY KEY COMMENT '프로필 고유 ID',
  name VARCHAR(50) NOT NULL COMMENT '이름',
  email VARCHAR(100) NOT NULL unique COMMENT '이메일',
  bio VARCHAR(500) COMMENT '자기소개',
  position VARCHAR(20) NOT NULL COMMENT '직무',
  career_years int NOT NULL default 0 COMMENT '경력 연차',
  github_url VARCHAR(200) COMMENT 'GitHub 주소',
  blog_url VARCHAR(200) COMMENT '블로그 주소',
  created_at timestamp default current_timestamp COMMENT '생성 일시',
  updated_at timestamp default current_timestamp ON UPDATE current_timestamp  COMMENT '수정 일시'
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT '프로필 테이블';

-- tech_stack 테이블 생성
CREATE TABLE IF NOT EXISTS tech_stack
(
  id bigint auto_increment PRIMARY KEY COMMENT '기술 스택 고유 ID',
  profile_id bigint NOT NULL COMMENT '프로필 ID (FK)',
  name VARCHAR(50) NOT NULL COMMENT '기술명',
  category VARCHAR(20) NOT NULL COMMENT '기술 카테고리',
  proficiency VARCHAR(20) NOT NULL COMMENT '숙련도',
  years_of_exp int NOT NULL default 0 COMMENT '사용 경험 (년)',
  created_at timestamp default current_timestamp COMMENT '생성 일시',
  updated_at timestamp default current_timestamp ON UPDATE current_timestamp COMMENT '수정 일시',
  FOREIGN KEY(profile_id) references profile(id) ON DELETE cascade
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT '기술 스택 테이블';

-- 인덱스 생성
CREATE INDEX idx_profile_position ON profile(position) ;
CREATE INDEX idx_tech_stack_profile_id ON tech_stack(profile_id) ;
CREATE INDEX idx_tech_stack_category ON tech_stack(category);
