# Profile & Tech Stack API

## 프로젝트 개요

개발자 프로필과 기술 스택을 관리하는 REST API 서비스입니다. Spring Boot 기초 학습 내용을 종합하여 Layered Architecture와 DAO 패턴을 활용한 CRUD API를 구현합니다.

### 주요 기능
- 개발자 프로필 관리 (생성, 조회, 수정, 삭제)
- 기술 스택 관리 (프로필별 기술 스택 추가, 조회, 수정, 삭제)
- 페이징 처리 및 검색 기능
- 공통 응답 형식과 예외 처리

## 기술 스택

- **Language**: Java 17
- **Framework**: Spring Boot 4.0.3
- **Database**: H2 (개발), MySQL (운영)
- **Data Access**: JdbcTemplate
- **Build Tool**: Gradle
- **Others**: Lombok

## 프로젝트 구조

```
src/main/java/com/study/profile_stack_api/
├── ProfileStackApiApplication.java
├── domain/
│   ├── profile/
│   │   ├── controller/
│   │   │   └── ProfileController.java
│   │   ├── service/
│   │   │   └── ProfileService.java
│   │   ├── repository/
│   │   │   └── ProfileRepository.java
│   │   ├── dao/
│   │   │   ├── ProfileDao.java              (인터페이스)
│   │   │   └── ProfileDaoImpl.java          (JdbcTemplate 구현체)
│   │   ├── entity/
│   │   │   ├── Profile.java
│   │   │   └── Position.java                (Enum)
│   │   └── dto/
│   │       ├── request/
│   │       │   ├── ProfileCreateRequest.java
│   │       │   └── ProfileUpdateRequest.java
│   │       └── response/
│   │           └── ProfileResponse.java
│   └── techstack/
│       ├── controller/
│       │   └── TechStackController.java
│       ├── service/
│       │   └── TechStackService.java
│       ├── repository/
│       │   └── TechStackRepository.java
│       ├── dao/
│       │   ├── TechStackDao.java            (인터페이스)
│       │   └── TechStackDaoImpl.java        (JdbcTemplate 구현체)
│       ├── entity/
│       │   ├── TechStack.java
│       │   ├── TechCategory.java            (Enum)
│       │   └── Proficiency.java             (Enum)
│       └── dto/
│           ├── request/
│           │   ├── TechStackCreateRequest.java
│           │   └── TechStackUpdateRequest.java
│           └── response/
│               └── TechStackResponse.java
└── global/
    ├── common/
    │   ├── ApiResponse.java                 (공통 응답 객체)
    │   └── Page.java                        (페이징 응답 객체)
    ├── exception/
    │   ├── BusinessException.java
    │   ├── ProfileNotFoundException.java
    │   ├── TechStackNotFoundException.java
    │   ├── DuplicateEmailException.java
    │   └── GlobalExceptionHandler.java
    └── config/
        └── AppConfig.java
```

## 데이터베이스 스키마

### Profile 테이블
| 컬럼명 | 타입 | 설명 | 제약조건 |
|--------|------|------|----------|
| id | BIGINT | 프로필 ID | PK, AUTO_INCREMENT |
| name | VARCHAR(50) | 이름 | NOT NULL |
| email | VARCHAR(100) | 이메일 | NOT NULL, UNIQUE |
| bio | VARCHAR(500) | 자기소개 | - |
| position | VARCHAR(20) | 직무 | NOT NULL |
| career_years | INT | 경력 연차 | NOT NULL, DEFAULT 0 |
| github_url | VARCHAR(200) | GitHub URL | - |
| blog_url | VARCHAR(200) | 블로그 URL | - |
| created_at | TIMESTAMP | 생성일시 | DEFAULT CURRENT_TIMESTAMP |
| updated_at | TIMESTAMP | 수정일시 | DEFAULT CURRENT_TIMESTAMP |

### TechStack 테이블
| 컬럼명 | 타입 | 설명 | 제약조건 |
|--------|------|------|----------|
| id | BIGINT | 기술스택 ID | PK, AUTO_INCREMENT |
| profile_id | BIGINT | 프로필 ID | NOT NULL, FK |
| name | VARCHAR(50) | 기술명 | NOT NULL |
| category | VARCHAR(20) | 카테고리 | NOT NULL |
| proficiency | VARCHAR(20) | 숙련도 | NOT NULL |
| years_of_exp | INT | 경험 연수 | NOT NULL, DEFAULT 0 |
| created_at | TIMESTAMP | 생성일시 | DEFAULT CURRENT_TIMESTAMP |
| updated_at | TIMESTAMP | 수정일시 | DEFAULT CURRENT_TIMESTAMP |

## API 엔드포인트

### Profile API

| Method | Endpoint | 설명 |
|--------|----------|------|
| POST | `/api/v1/profiles` | 프로필 생성 |
| GET | `/api/v1/profiles` | 프로필 목록 조회 (페이징) |
| GET | `/api/v1/profiles/{id}` | 프로필 단건 조회 |
| GET | `/api/v1/profiles/position/{position}` | 직무별 프로필 조회 |
| PUT | `/api/v1/profiles/{id}` | 프로필 수정 |
| DELETE | `/api/v1/profiles/{id}` | 프로필 삭제 |

### TechStack API

| Method | Endpoint | 설명 |
|--------|----------|------|
| POST | `/api/v1/profiles/{profileId}/tech-stacks` | 기술 스택 추가 |
| GET | `/api/v1/profiles/{profileId}/tech-stacks` | 기술 스택 목록 조회 (페이징) |
| GET | `/api/v1/profiles/{profileId}/tech-stacks/{id}` | 기술 스택 단건 조회 |
| PUT | `/api/v1/profiles/{profileId}/tech-stacks/{id}` | 기술 스택 수정 |
| DELETE | `/api/v1/profiles/{profileId}/tech-stacks/{id}` | 기술 스택 삭제 |

## Enum 정의

### Position (직무)
- `BACKEND` - 백엔드 개발자 ⚙️
- `FRONTEND` - 프론트엔드 개발자 🎨
- `FULLSTACK` - 풀스택 개발자 🔄
- `MOBILE` - 모바일 개발자 📱
- `DEVOPS` - DevOps 엔지니어 🚀
- `DATA` - 데이터 엔지니어 📊
- `AI` - AI/ML 엔지니어 🤖
- `ETC` - 기타 💻

### TechCategory (기술 카테고리)
- `LANGUAGE` - 프로그래밍 언어 📝
- `FRAMEWORK` - 프레임워크 🏗️
- `DATABASE` - 데이터베이스 💾
- `DEVOPS` - DevOps/인프라 ☁️
- `TOOL` - 개발 도구 🔧
- `ETC` - 기타 📦

### Proficiency (숙련도)
- `BEGINNER` - 입문 🌱
- `INTERMEDIATE` - 중급 🌿
- `ADVANCED` - 고급 🌳
- `EXPERT` - 전문가 🏆

## 실행 방법

### 1. 프로젝트 클론
```bash
git clone https://github.com/jinho-yoo-jack/groom-spring-boot-starter-1.git
cd groom-spring-boot-starter-1
```

### 2. 애플리케이션 실행
```bash
./gradlew bootRun
```

### 3. H2 Console 접속
- URL: http://localhost:8080/h2-console
- JDBC URL: `jdbc:h2:mem:testdb`
- Username: `sa`
- Password: (빈 값)

### 4. API 테스트
```bash
# 프로필 생성
curl -X POST http://localhost:8080/api/v1/profiles \
  -H "Content-Type: application/json" \
  -d '{
    "name": "김자바",
    "email": "java.kim@example.com",
    "bio": "Spring Boot를 사랑하는 백엔드 개발자입니다.",
    "position": "BACKEND",
    "careerYears": 3,
    "githubUrl": "https://github.com/javakim"
  }'

# 프로필 목록 조회
curl "http://localhost:8080/api/v1/profiles?page=0&size=10"

# 기술 스택 추가
curl -X POST http://localhost:8080/api/v1/profiles/1/tech-stacks \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Java",
    "category": "LANGUAGE",
    "proficiency": "ADVANCED",
    "yearsOfExp": 3
  }'
```

## 구현 체크리스트

### Phase 1: 기본 설정
- [x] Spring Boot 프로젝트 설정
- [x] application.yml 작성
- [x] schema.sql, data.sql 작성
- [x] MySQL 연결 확인

### Phase 2: 공통 모듈
- [x] ApiResponse 클래스 구현
- [x] Page 클래스 구현
- [x] BusinessException 및 하위 예외 클래스 구현
- [x] GlobalExceptionHandler 구현

### Phase 3: Profile CRUD
- [x] Profile Entity 및 Enum 구현
- [x] Profile DTO 구현
- [x] ProfileDao 인터페이스 및 구현체
- [x] ProfileRepository 구현 -> 현재는 DAO가 대체 중
- [x] ProfileService 구현
- [x] ProfileController 구현

### Phase 4: TechStack CRUD
- [x] TechStack Entity 및 Enum 구현
- [x] TechStack DTO 구현
- [x] TechStackDao 인터페이스 및 구현체
- [x] TechStackRepository 구현 -> TechStackDao로 대체
- [x] TechStackService 구현
- [x] TechStackController 구현

### Phase 5: 페이징 & 검색
- [x] 프로필 페이징 구현
- [x] 프로필 검색 기능 구현
- [x] 기술 스택 페이징 구현
- [ ] 기술 스택 필터링 구현 -> 특정 Profile에서 TeckStack을 필터링 해야하는건지..?

### Phase 6: 어노테이션 기반 코드 최적화 (Lombok)
- [ ] build.gradle에 Lombok 및 MapStruct 의존성 추가
- [ ] Profile, TechStack 엔티티의 Getter/Setter, 생성자 제거 및 @Getter, @NoArgsConstructor(access = AccessLevel.PROTECTED) 등 적용
- [ ] 모든 DTO에 @Getter, @Builder, @AllArgsConstructor 적용으로 보일러플레이트 코드 제거
- [ ] 컨트롤러 및 서비스 레이어에 @Slf4j 적용하여 로그 시스템 통합

### Phase 7: 선언적 유효성 검증 (Bean Validation)
- [ ] spring-boot-starter-validation 추가
- [ ] Request DTO 필드에 @NotBlank, @NotNull, @Size, @Email 등 적절한 어노테이션 선언
- [ ] @RequestBody 앞에 @Valid 어노테이션 추가하여 자동 검증 활성화
- [ ] MethodArgumentNotValidException을 GlobalExceptionHandler에서 잡아 ApiResponse 형식으로 반환하도록 수정

### Phase 8: 객체 매핑 자동화 (MapStruct)
- [ ] ProfileMapper, TechStackMapper 인터페이스 생성
- [ ] @Mapper(componentModel = "spring")를 사용하여 Entity ↔ DTO 간 변환 로직 자동 생성
- [ ] 수동으로 new 생성자나 빌더를 호출하던 코드를 mapper.toEntity() 또는 mapper.toResponse() 호출로 변경
- [ ] List 형태의 객체 변환 로직 통합 및 테스트

### Phase 9: 보안 및 인증/인가 (Spring Security)
- [ ] SecurityFilterChain 빈 등록 및 기본적인 허용 경로(PermitAll) 설정
- [ ] 로그인 기능 및 JWT(또는 Session) 기반의 인증 시스템 구축
- [ ] GET 메서드(조회) 누구나 접근 가능하도록 설정
- [ ] POST, PUT, DELETE: Authenticated 사용자만 접근 가능하도록 @PreAuthorize 또는 설정 파일 기반 권한 제어 적용
- [ ] 생성/수정 시 현재 로그인한 사용자의 정보를 자동으로 주입하도록 로직 수정

## 아키텍처

### Layered Architecture
```
Controller (REST API 엔드포인트)
    ↓
Service (비즈니스 로직)
    ↓
Repository (데이터 접근 추상화)
    ↓
DAO (JdbcTemplate 구현)
    ↓
Database (H2/MySQL)
```

### 패키지 구조
- `domain`: 도메인별 기능 구현
    - `profile`: 프로필 관련 기능
    - `techStack`: 기술 스택 관련 기능
- `global`: 공통 기능
    - `common`: 공통 응답 객체
    - `exception`: 예외 처리
    - `config`: 설정

## 주요 구현 포인트

### 1. DAO 패턴
- 인터페이스와 구현체를 분리하여 데이터 접근 계층 추상화
- JdbcTemplate을 활용한 SQL 쿼리 직접 작성
- RowMapper를 통한 객체 매핑

### 2. 페이징 처리
- Page 객체를 활용한 페이징 응답 구현
- LIMIT, OFFSET을 활용한 쿼리 최적화

### 3. 예외 처리
- BusinessException을 상속한 커스텀 예외 정의
- GlobalExceptionHandler를 통한 전역 예외 처리
- 일관된 에러 응답 형식

### 4. FK 관계 처리
- TechStack의 profileId를 통한 연관 관계 관리
- CASCADE DELETE를 통한 참조 무결성 유지
- 프로필 존재 여부 검증 로직

## 테스트

### 단위 테스트
```bash
./gradlew test
```

### 통합 테스트
- H2 인메모리 데이터베이스를 활용한 통합 테스트
- @DataJdbcTest를 활용한 DAO 계층 테스트

## 참고 자료

- [Spring Boot 공식 문서](https://spring.io/projects/spring-boot)
- [Spring Data JDBC](https://spring.io/projects/spring-data-jdbc)
- [H2 Database](http://www.h2database.com)

## 라이센스

MIT License

## 기여하기

### Fork를 통한 협업 방법

#### 1. Fork 및 Clone
```bash
# 1. GitHub에서 프로젝트를 Fork (웹에서 Fork 버튼 클릭)

# 2. Fork한 저장소를 로컬에 Clone
git clone https://github.com/YOUR_GITHUB_USERNAME/groom-spring-boot-starter-1.git
cd groom-spring-boot-starter-1
```

#### 2. Upstream 저장소 설정
```bash
# 원본 저장소를 upstream으로 추가
git remote add upstream https://github.com/jinho-yoo-jack/groom-spring-boot-starter-1.git

# remote 저장소 확인
git remote -v
# origin: 본인의 Fork 저장소
# upstream: 원본 저장소
```

#### 3. 브랜치 생성 및 작업
```bash
# main 브랜치에서 새 브랜치 생성 (본인 이름 사용)
git checkout -b spring1/YOUR_NAME

# 예시
git checkout -b spring1/jinho
```

#### 4. 변경사항 Commit
```bash
# 변경사항 확인
git status

# 변경사항 추가
git add .

# Commit 작성
git commit -m "feat: Profile API 구현 완료"
```

#### 5. Upstream 동기화 (최신 상태 유지)
```bash
# upstream의 최신 변경사항 가져오기
git fetch upstream

# 현재 브랜치에 upstream/master 병합
git merge upstream/master

# 충돌이 있다면 해결 후 commit
```

#### 6. Fork 저장소에 Push
```bash
# 본인의 Fork 저장소에 Push
git push origin spring1/YOUR_NAME
```

#### 7. Pull Request 생성
1. GitHub에서 본인의 Fork 저장소로 이동
2. "Compare & pull request" 버튼 클릭
3. PR 제목과 설명 작성
4. Create Pull Request 클릭

### 주의사항
- 작업 전 항상 upstream과 동기화
- 의미있는 commit 메시지 작성
- 하나의 PR에는 하나의 기능만 포함
- 코드 리뷰 피드백은 적극적으로 반영

## 문의사항

- GitHub Issues: [https://github.com/jinho-yoo-jack/groom-spring-boot-starter-1/issues](https://github.com/jinho-yoo-jack/groom-spring-boot-starter-1/issues)
- Email: jhy7342@gmail.com

---

**Made with ❤️ by Profile & Stack API Team**