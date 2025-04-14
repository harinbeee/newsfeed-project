![header](https://capsule-render.vercel.app/api?type=Waving&color=auto&height=300&section=header&text=5it-newsfeed&fontSize=70&desc=Spring%20Boot를%20활용한%20뉴스%20피드%20프로젝트입니다.&descAlignY=30)

<br/>

# 🔗 목차

1. [📆 프로젝트 소개](#-프로젝트-소개)
2. [🕰️ 개발 기간](#%EF%B8%8F-개발-기간)
3. [📚 개발 환경](#-개발-환경)
4. [🌳 디렉토리 구조](#-디렉토리-구조)
5. [📝 프로젝트 명세](#-프로젝트-명세)

   5-1. [ERD](#erd)

   5-2. [API 명세서](#api-명세서)

6. [💭 마무리](#-마무리)

<br/>

# 🧮 프로젝트 소개

1️⃣ 프로필 관리

- 프로필 조회 기능
- 프로필 수정 기능

2️⃣ 뉴스피드 게시물 관리

- 게시물 작성, 조회, 수정, 삭제 기능
- 뉴스피드 조회 기능

3️⃣ 사용자 인증

- 회원가입 기능
- 회원탈퇴 기능

4️⃣ 친구 관리

- 특정 사용자 친구 추가/삭제 기능
- 상대방의 수락 기능

🆙 댓글 기능

- 댓글 작성, 조회, 수정, 삭제 기능

🆙 좋아요 기능

- 댓글/게시글 좋아요 기능

<br/>

Spring Boot와 JPA를 활용해서 뉴스 피드 프로젝트의 백엔드 API를 구현한 프로젝트입니다.

트위터 (현 X) 를 모티브로 한 간단한 SNS 플랫폼입니다.

Front-end는 구현하지 않고 데이터 통신과 DB와의 연동 위주로 작성되었습니다.

<br/>

# 🕰️ 개발 기간

- 2025.04.07 ~ 2025.04.14 (총 6일)

<br/>

# 📚 기술 스택

### Language

[![My Skills](https://skillicons.dev/icons?i=java)](https://skillicons.dev)

### Backend

[![My Skills](https://skillicons.dev/icons?i=spring)](https://skillicons.dev)

### Database

[![My Skills](https://skillicons.dev/icons?i=mysql)](https://skillicons.dev)

### Development Tools

[![My Skills](https://skillicons.dev/icons?i=idea,postman)](https://skillicons.dev)

### SCM

[![My Skills](https://skillicons.dev/icons?i=git,github)](https://skillicons.dev)

### Communication

[![My Skills](https://skillicons.dev/icons?i=notion)](https://skillicons.dev)

<br/>

# 🌳 디렉토리 구조

```
newsfeed-project/
    ├──src/
    │   ├──main/java/com/example/newsfeed/
    │   │   ├──auth/
    │   │   │   ├──controller/
    │   │   │   ├──dto/
    │   │   │   └──service/
    │   │   ├──boards/
    │   │   │   ├──controller/
    │   │   │   ├──dto/
    │   │   │   ├──entity/
    │   │   │   ├──repository/
    │   │   │   └──service/
    │   │   ├──common/
    │   │   │   ├──config/
    │   │   │   ├──entity/
    │   │   │   ├──exception/
    │   │   │   ├──filter/
    │   │   │   ├──handler/
    │   │   │   ├──response/
    │   │   │   └──util/
    │   │   ├──friends/
    │   │   │   ├──controller/
    │   │   │   ├──dto/
    │   │   │   ├──entity/
    │   │   │   ├──repository/
    │   │   │   └──service/
    │   │   ├──likes/
    │   │   │   ├──controller/
    │   │   │   ├──dto/
    │   │   │   ├──entity/
    │   │   │   ├──repository/
    │   │   │   └──service/
    │   │   ├──users/
    │   │   │   ├──controller/
    │   │   │   ├──dto/
    │   │   │   ├──entity/
    │   │   │   ├──repository/
    │   │   │   └──service/
    │   │   └──NewsfeedApplication.java
    │   ├──main/resources
    │   │   ├──static/
    │   │   │   └──application.properties
    ├── .gitignore
    ├──schedule.sql
    └──README.md
```

<br/>

# 📝 프로젝트 명세

### [ERD](https://github.com/harinbeee/newsfeed-project/wiki/ERD)

### [API 명세서](https://github.com/harinbeee/newsfeed-project/wiki)

<br/>

# 💭 마무리

### 느낀 점

### 아쉬운 점

