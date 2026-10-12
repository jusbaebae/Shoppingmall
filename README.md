# MY SHOP 🛍️

Spring Boot와 React를 활용한 온라인 쇼핑몰 프로젝트입니다.

회원 인증부터 상품 조회, 장바구니, 주문, 결제 및 환불까지 온라인 쇼핑몰의 핵심 기능을 구현했습니다.

## 📌 프로젝트 소개

- **프로젝트명:** MY SHOP
- **개발 목적:** Spring Boot와 React를 활용한 풀스택 웹 개발 학습 및 포트폴리오
- **주요 목표:** 사용자 인증, 상품 및 주문 관리, 외부 결제 서비스 연동 경험

## 🛠️ 기술 스택

### Backend
- Java
- Spring Boot
- Spring Security
- Spring Data JPA
- JWT
- MySQL
- Gradle

### Frontend
- React
- JavaScript
- Vite
- Axios
- React Router

### Payment
- PortOne
- KG이니시스

### Development Tools
- IntelliJ IDEA
- Visual Studio Code
- Postman
- Git / GitHub

## ✨ 주요 기능

### 1. 회원 인증 및 권한 관리
- 회원가입 및 로그인
- JWT 기반 인증
- 사용자 권한에 따른 접근 제어
- 관리자 전용 기능 접근 제어

### 2. 상품 관리
- 상품 목록 및 상세 정보 조회
- 상품 가격 및 할인 가격 표시
- 상품 이미지 업로드 및 조회
- 품절 상품 처리
- 관리자 상품 관리

### 3. 장바구니
- 상품 장바구니 추가 및 수량 관리
- 장바구니 상품별 가격 및 총액 계산
- 구매 완료 후 장바구니 상품 정리

### 4. 주문 및 배송 정보
- 주문 생성 및 주문 내역 조회
- 수령인 및 배송지 정보 입력
- 주문 상태 관리
- 주문 취소 및 환불 처리

### 5. 결제
- PortOne과 KG이니시스를 이용한 결제 연동
- 서버에서 결제 정보를 확인하는 검증 로직
- 결제 결과에 따른 주문 상태 관리

### 6. 재고 관리
- 주문 시 상품 재고 확인 및 차감
- 재고 부족 상품에 대한 구매 제한
- 환불 시 상품 재고 복구

## 🔐 보안 및 데이터 처리

- Spring Security를 이용한 인증 및 권한 관리
- JWT를 이용한 Stateless 인증 방식 적용
- 비밀번호 및 API Secret 등 민감정보의 환경변수 관리
- 서버에서 결제 결과를 검증한 후 주문 상태 처리

## 🏗️ 프로젝트 구조

### Backend

```text
src/main/java/com/example/shoppingmall/
├── member/       # 회원 및 인증
├── product/      # 상품 관리
├── cart/         # 장바구니
├── order/        # 주문 및 주문 상품
└── global/       # 공통 설정 및 보안
```

### Frontend

```text
src/
├── api/          # Axios 및 API 통신
├── components/   # 공통 컴포넌트
├── pages/        # 페이지 컴포넌트
└── assets/       # 이미지 및 정적 리소스
```

※ 실제 프로젝트의 패키지 및 폴더 구조에 맞게 수정합니다.

## ⚙️ 실행 방법

### 1. 사전 준비
- Java 및 Gradle 실행 환경
- Node.js 및 npm
- MySQL

### 2. 데이터베이스 설정

MySQL에 데이터베이스를 생성합니다.

```sql
CREATE DATABASE ShoppingMall;
```

데이터베이스 접속 정보는 환경변수 또는 로컬 설정 파일을 통해 지정합니다.

### 3. Backend 실행

1. 프로젝트를 IntelliJ IDEA에서 엽니다.
2. 데이터베이스 접속 정보와 JWT 및 결제 관련 환경변수를 설정합니다.
3. Spring Boot 애플리케이션을 실행합니다.

기본 API 주소: `http://localhost:8080`

### 4. Frontend 실행

React 프로젝트 폴더에서 다음 명령어를 실행합니다.

```bash
npm install
npm run dev
```

기본 개발 서버 주소: `http://localhost:5173`

### 5. 환경변수 설정

실행에 필요한 환경변수 예시:

- `DB_USERNAME`
- `DB_PASSWORD`
- `JWT_SECRET`
- `PORTONE_API_SECRET`

실제 프로젝트에서 사용하는 설정 키와 일치하도록 구성해야 합니다.

민감정보가 포함된 설정 파일과 실제 API Secret은 GitHub에 업로드하지 않습니다.

## 📸 화면 및 시연

실제 구현한 화면을 추가할 예정입니다.

- 메인 페이지 및 상품 목록
- 상품 상세 페이지
- 장바구니
- 주문 및 결제
- 주문 내역 및 환불

## 🚀 향후 개선 계획

- 예외 처리 및 API 응답 구조 개선
- 테스트 코드 작성
- 배포 환경 구성
- UI/UX 개선
- 주문 및 결제 처리 안정성 강화

## 👨‍💻 개발자

MY SHOP 개인 프로젝트
