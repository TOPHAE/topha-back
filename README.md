# 내가 사용하고싶은 언어들과 만들어보고싶은 프로젝트를 같이하기위한 동료 모집하기
#### 목표
- 프론트 코드 https://github.com/woojin126/topha-front
- 프론트 실행은
- npm i
- npm start

### 기본 사양
- spring boot 2.4.5
- Spring Data JPA
- Oracle 11g XE
- h2Database

### Utils dependencies
- lombok
- spring-boot-starter-web
- spring-boot-starter-data-jpa
- querydsl-jpa
- devtools
- junit-jupiter-api:5.7.0
- unit-jupiter-engine:5.7.2
- spring-boot-starter-oauth2-client', version: '2.4.2
- spring-boot-starter-mustache
- security:spring-security-test
- spring-boot-starter-validation'
- spring-boot-configuration-processor', version: '2.5.2'
-'com.querydsl', name: 'querydsl-jpa', version: '4.4.0'
-'io.jsonwebtoken', name: 'jjwt', version: '0.9.1'
-'org.springframework.boot:spring-boot-starter-mustache'
-'org.springframework.boot:spring-boot-devtools'


### IDEL
- IntelliJ로 구축

### Version
## 1.0 구현

- 1.1.0 Oauth2-Client로 네이버,페이스북,구글 ReSource Server에서 AccessToken을받아 Profile정보 구성
- 1.1.0.1 Oauth2-Client 토큰 교환방식은 JWT 토큰방식의 HS512 암호화방식을 사용
- 1.1.1 소셜 로그인시 회원가입까지 자동으로 진행
- 1.1.2 일반회원이 로그인하게되면 ROLE_USER 권한 부여, 관리자 계정은 ROLE_ADMIN 계정부여
- 1.1.3 로그인하지 않은 사용자는 프로젝트모집 글작성이 불가능 (ROLE_USER) 권한이 있어야 가능
- 1.1.4 프로젝트 모집 글등록시 SPEC 필드에 백엔드, 프론트엔드 , 백엔드+ 프론트엔드 선택가능
- 1.1.5 프로젝트 모집 글등록시 TECH 필드에 프로젝트에 필요한 여러가지 언어 선택가능
- 1.1.6 프로젝트 등록할때 SPEC, TECH 기반으로 메인페이지에서 사용자들이 사용하고자하는 언어가 포함된
 프로젝트를 (메인에있는 프로그래밍언어들과, TECH 기반으로 선택하여 프로젝트들을 필터링가능) 
- 1.1.7 동적쿼리를 QueryDsl을 사용해서 검색기능, 필터기능을 구현
- 1.1.8 좋아요 기능, 조회수 기능 구현 (메인페이지에서 등록한 프로젝트들을 좋아요 많은순서대로 정렬)

### IntelliJ 콘솔 로그 한글 깨짐 해결 방법
- IntelliJ File Encodings 변경

1. 컨트롤 + 알트 + S
2. Editor > File Encodings 선택
3. 셋팅

- Global Encoding:UTF-8
- Project Encoding:UTF-8
- Default encoding for properties files:UTF-8

### lombok 설정
1. Setting
2. Annotation Processors
3. Enable annotaion processing 체크

### gradle 동작 방식 설정
1. setting
2. 검색창에 gradle
3. Build and run using , Run tests using : 모두 IntelliJ로 변경
