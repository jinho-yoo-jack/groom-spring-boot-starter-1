DROP TABLE IF EXISTS profile;
DROP TABLE IF EXISTS tech_stack;

CREATE TABLE profile (
                         id BIGINT AUTO_INCREMENT PRIMARY KEY,
                         name VARCHAR(50) NOT NULL,
                         email VARCHAR(100) NOT NULL UNIQUE,
                         bio VARCHAR(500), -- 자기소개
                         position VARCHAR(20) NOT NULL, -- 직무
                         career_years INT NOT NULL DEFAULT 0, -- 경력 연차
                         github_url VARCHAR(200),
                         blog_url VARCHAR(200),
                         created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                         updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE tech_stack (
                            id BIGINT AUTO_INCREMENT PRIMARY KEY,
                            profile_id BIGINT NOT NULL,
                            name VARCHAR(50) NOT NULL,
                            category VARCHAR(20) NOT NULL,
                            proficiency VARCHAR(20) NOT NULL, -- 숙련도
                            years_of_exp INT NOT NULL DEFAULT 0, -- 경험 연수
                            created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                            updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                            FOREIGN KEY (profile_id) REFERENCES profile(id) ON DELETE CASCADE
);

CREATE INDEX idx_profile_position ON profile(position);
CREATE INDEX idx_tech_stack_profile_id ON tech_stack(profile_id);
CREATE INDEX idx_tech_stack_category ON tech_stack(category);