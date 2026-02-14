### Phase 1: 기본 설정

- [x]  Spring Boot 프로젝트 생성 (spring-boot-starter-web, spring-boot-starter-jdbc, h2, lombok)
- [x]  application.yml 작성 (H2 설정, SQL 초기화 설정)
- [x]  schema.sql, data.sql 작성
- [x]  애플리케이션 실행 후 H2 Console에서 테이블 확인

### Phase 2: 공통 모듈

- [x]  ApiResponse 클래스 구현
- [ ]  Page 제네릭 클래스 구현
- [x]  ErrorCode Enum 정의
- [x]  BusinessException 및 하위 예외 클래스 구현
- [x]  GlobalExceptionHandler 구현

### Phase 3: Profile CRUD

- [x]  Entity 클래스 작성 (Profile, Position Enum)
- [x]  DTO 클래스 작성 (CreateRequest, UpdateRequest, Response)
- [ ]  ProfileDao 인터페이스 정의
- [ ]  ProfileDaoImpl 구현 (JdbcTemplate + RowMapper)
- [x]  ProfileRepository 구현
- [x]  ProfileService 구현
- [x]  ProfileController 구현
- [x]  cURL 또는 Postman으로 CRUD 테스트

- ### Phase 4: TechStack CRUD

- [ ]  Entity 클래스 작성 (TechStack, TechCategory Enum, Proficiency Enum)
- [ ]  DTO 클래스 작성 (CreateRequest, UpdateRequest, Response)
- [ ]  TechStackDao 인터페이스 정의
- [ ]  TechStackDaoImpl 구현 (profileId를 활용한 쿼리 주의)
- [ ]  TechStackRepository 구현
- [ ]  TechStackService 구현 (프로필 존재 여부 검증 포함)
- [ ]  TechStackController 구현
- [ ]  cURL 또는 Postman으로 CRUD 테스트

### Phase 5: 페이징 & 검색

- [ ]  프로필 목록 페이징 구현 (findAllWithPaging)
- [ ]  프로필 검색 구현 (이름 검색, 직무 필터링)
- [ ]  기술 스택 목록 페이징 구현 (findByProfileIdWithPaging)
- [ ]  기술 스택 검색 구현 (카테고리, 숙련도 필터링)

### Phase 6: 테스트 코드 (보너스)

- [ ]  ProfileDao 통합 테스트
- [ ]  TechStackDao 통합 테스트
- [ ]  페이징 경계 조건 테스트 (첫 페이지, 마지막 페이지, 빈 페이지)