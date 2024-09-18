# 👨‍👧‍👦 팀 소개

### **팀명 : 위대한 렛츠비**
![큐시즘 제출용 표지](https://github.com/user-attachments/assets/3bc71ac6-b5bf-4a63-ad6f-316061afd2f2)

<br>

# 👥 팀원 및 역할

| **분야** | **이름** | **포지션** | **내용** |
| :---: | :---: | --- | --- |
| 기획 | 오민지 | 📈 PM, 서비스 기획 | 전체 프로젝트 관리 및 유저 리서치, 와이어프레임 제작, <br /> 서비스 기능 명세서 제작  |
| 기획 | 신예진 | 📋 서비스 기획 | 데스크/유저 리서치, 와이어프레임 제작, <br /> 서비스 기능 명세서 제작 |
| 기획 | 최수영 | 📊 서비스 기획 | 데스크/유저 리서치, 서비스 기능 명세서 제작 |
| 디자인 | 설정원 | 🎨 디자인 리드 | 디자인시스템/그래픽 |
| 디자인 | 이어령 | 🎨 디자인 | gui/브랜딩 |
| 개발 | 박인애 | 📱  프론트엔드 리드 | 개발 환경 세팅, 컴포넌트 개발, API 연동 |
| 개발 | 김진희 | 📱 프론트엔드 | 개발 환경 세팅, 컴포넌트 개발, API 연동 |
| 개발 | 문희상 | 💻 백엔드 리드 | ERD 작성,  API 개발 , 인프라 구축 |
| 개발 | 박준형 | 💻 백엔드 | ERD 작성, API 개발 |

<br>

# 👍 공통 사항
- 단위 테스트 작성(service 메소드 별로) : Junit 사용
- 다른 사람이 알아보기 쉽도록 주석처리해야 합니다.
    - javadoc 형식 https://jake-seo-dev.tistory.com/59
- issue 생성 및 PR을 통해 본인이 구현한 부분에 대한 기록을 남겨야 합니다.
- 테스트 및 프론트와 연결 과정을 위한 로그을 작성해야 합니다.
- 개발 기간 : 8/24 ~ 9/8
- 스프린트 4회(3일간격) 진행
    - 수요일, 토요일

<br>

# ✏️ 서비스 기능 소개

![image](https://github.com/user-attachments/assets/1ccbf0d1-ed78-4775-98c9-da8c32af2089)



### 1) MY 홈
**1-1) 내 커리어 현황 한 눈에 확인해요**

![image](https://github.com/user-attachments/assets/8e254f2e-2e37-47ef-881a-63fe5f7991b0)



- `커리어 현황 확인`
    - 관심 기업 또는 최근 임박한 기업 순으로 6개까지 확인 가능
    - 나의 총 지원현황을 수치/리스트로 정리
    - 마감기한 정보(D-DAY) 자동 업데이트
- `날짜별 투두리스트`
    - 우측 화면에서 일자별/기업별 투두리스트 확인
- `월별 채용일정 캘린더`

<br>

**1-2) 새 채용일정 추가** 

![image](https://github.com/user-attachments/assets/973110ff-92a6-4b94-a613-18e16c5f0f1c)


- `준비할 채용 일정 추가`
    - 사용자가 관심 있는 채용일정만 간편하게 추가하고 관리
 
<br>

**1-3) 마감 공고별 리마인드 알림 기능** 

![image](https://github.com/user-attachments/assets/fe19ea50-0d8c-4a6d-b1f3-0bb4bdbd121c)


- `알림센터`
    - 등록한 기업의 전형 마감일 3일/1일 전에 리마인드 알림 제공
    - 임박한 마감 일정 더블 체크
 
<br>

### 2) 내 캘린더
**2-1) 전체 캘린더 일정 관리** 

![image](https://github.com/user-attachments/assets/09314885-39d2-4925-b33d-0de7799ae0eb)


- `오늘의 커리어 일정`
    - 등록된 채용 일정 노출
    - 개인 일정을 함께 추가하고 관리할 수 있는 캘린더 기능
- `캘린더 내 기업별 전형 chip`
    - 서류 - 시작, 끝
    - 면접
    - 기타 - 사용자가 입력한 이외의 전형
- `오늘의 투두리스트`
    - 우측 화면에서 일자별/기업별 투두리스트 확인

<br>

### 3) 내 지원현황 보기
**3-1) 준비 현황 페이지** 

![image](https://github.com/user-attachments/assets/ca124f61-8eee-4940-b58c-266615a80431)



- `기업 카드`
    - 기업명/직무/가장 최근 전형의 마감일 정보 확인
    - 정렬 순서 : 관심 기업 → 마감기한
    - 전형별로 분류하는 필터 제공 : 서류 / 면접 / 기타

<br>

**3-2) 준비중인 기업 관리**

![image](https://github.com/user-attachments/assets/b56ef139-dbe3-4b8c-a234-a37a80192772)


- `전형 카드`
    - 진행되는 일정에 맞춰 전체 전형 절차 및 일정 등록 + 관리
    - 전형 종류 : 서류, 면접, 기타(사용자가 직접 등록한 이외의 전형)
    - 상태 : 진행 중 / 합격 / 불합격 3가지로 상태 변경 가능
- `기업별 투두 리스트`
    - 투두 등록 : 해당 기업을 위한 매일의 투두를 등록
    - 루틴 등록 (매일/매주) : 반복해야하는 일정이나 루틴을 등록하기 위한 기능
    - 일정 미루기, 날짜 변경 기능
- `기업별 관련 자료 아카이빙`
    - 기업별 자기소개서 작성하기 페이지 → 자소서 작성 시, 커리어 관리 [필살기 경험] 탭에 있는 경험 불러오기 가능
    - 아카이빙 :  취업 준비에 필요한 각종 자료들을 아카이빙하는 공간 → 자유로운 게시글 작성 / 파일 업로드 기능
    
![image](https://github.com/user-attachments/assets/b8c64d5e-174f-4b8d-84c1-b2fc6c1ded75)

<br>
  
**3-3) 종료된 기업카드 관리** 

![image](https://github.com/user-attachments/assets/26291c03-8d05-49c5-ab90-3abcb966a129)
- 불합격 / 최종합격 현황 확인 가능

<br>

**3-4) 복기 노트** 

    
![image](https://github.com/user-attachments/assets/59c235f9-9b57-4cbd-98fd-dac753cf7577)

- 전형 준비가 완료된 기업에 대해서 복기 및 회고할 수 있는 기능
- 서류/면접/기타 전형별 간편 복기 기능



    **TypeA 서류**
  
    ![image](https://github.com/user-attachments/assets/6f530ebb-86ab-46a9-b050-9aa324698ad3)


    - 만족도
    - 핵심 키워드 선택(잘한점/아쉬운점)
    - 면접 질문&답변 리스트 작성(잘한/아쉬운 답변 기록)
    - 아쉬웠던 질문 다시보기 기능

    <br>

    **TypeB 면접**
  
    ![image](https://github.com/user-attachments/assets/b4aa10ea-ae2e-4941-9e0d-01a713ecc808)
    
    
    - 만족도
    - 핵심 키워드 선택(잘한점/아쉬운점)
    - 면접 질문&답변 리스트 작성(잘한/아쉬운 답변 기록)
    - 아쉬웠던 질문 다시보기 기능
    
    <br>
    
    **TypeC 기타**
  
    ![image](https://github.com/user-attachments/assets/4e7dcc05-59c3-4b66-bdc3-d5996775fd20)
    
    - 만족도
    - 난이도
    - 잘한점/아쉬운점 자유롭게 기록

<br>


### 4) 커리어 관리 

![image](https://github.com/user-attachments/assets/ca8180c4-7989-4819-ac21-490343549c84)


**4-1) 기본 이력서**

- `지원서 작성에 필요한 공통 항목 관리용 이력서 기능`
    - 기본 정보/학력
    - 자격증/어학
    - 경력
    - 경험/수상
    - 포트폴리오

**4-2) 필살기 경험** 

- `자기소개서 작성에 활용 가능한 핵심 경험 정리 페이지`
    - 소제목/내용 작성란
    - 항목별 토글 기능(접기/펼치기)
    - 저장된 항목은 **<자기소개서 작성하기>** 페이지에서 불러오기 가능
    - ①성공/도전 경험, ②직무 경험, ③협업 경험 3가지 파트 구성

<br>

# 🎮 시연 영상
- https://youtu.be/6Pw6TpnJlIs

<br>

# 📑 ERD 설계도
![image](https://github.com/user-attachments/assets/e24074cc-f647-4d78-9078-1d7c9fe2bab7)


<br>

# 💾 시스템 아키텍쳐
![image](https://github.com/user-attachments/assets/233aa52e-1c57-4f16-88cf-c237f5de79f4)

<br>


# 🛠️ 개발스택

- #### Language, Framework, Library
  ![IntelliJ IDEA](https://img.shields.io/badge/IntelliJ%20IDEA-000000.svg?style=flat-square&logo=intellij-idea&logoColor=white)
  ![Java](https://img.shields.io/badge/Java-FF9900?style=flat-square&logo=JAVA&logoColor=FFFFFF)
  ![Springboot](https://img.shields.io/badge/Springboot-6DB33F?style=flat-square&logo=springboot&logoColor=white)
  ![Gradle](https://img.shields.io/badge/Gradle-02303A.svg?style=flat-square&logo=Gradle&logoColor=white)
  ![Spring Data JPA](https://img.shields.io/badge/Spring%20Data%20JPA-6DB33F?style=flat-square&logo=spring&logoColor=white)
  ![QueryDSL](https://img.shields.io/badge/QueryDSL-4096EE?style=flat-square&logo=QueryDSL&logoColor=white)
  - 다양한 라이브러리와 설정을 자동으로 제공하므로 개발자가 애플리케이션을 더 빠르게 만들 수 있음
  - 특정 환경이나 서버, 기술에 종속되지 않으며 유연한 애플리케이션을 개발할 수 있음
  - QueryDSL은 타입 안전한 방식으로 SQL 쿼리를 작성할 수 있어, 컴파일 시점에 오류를 잡아내고 가독성을 높임

- #### Test
  ![JUnit](https://img.shields.io/badge/JUnit-25A162?style=flat-square&logo=junit5&logoColor=white)
  ![Mockito](https://img.shields.io/badge/Mockito-1E92DD?style=flat-square&logo=Mockito&logoColor=white)
  - JUnit은 자바 기반의 단위 테스트 프레임워크로, 테스트를 자동화하여 코드의 품질을 보장하며, 코드 변경 시 발생할 수 있는 버그를 신속히 탐지
  - Mockito는 Mock Object를 사용해 의존성을 분리하고 테스트 대상의 행위만을 집중적으로 검증할 수 있어, 유닛 테스트 작성이 더 쉬움


- #### Cloud
  ![AWS](https://img.shields.io/badge/AWS-232F3E?style=flat-square&logo=amazonaws&logoColor=white)
  ![EC2](https://img.shields.io/badge/EC2-FF9900?style=flat-square&logo=Amazon%20EC2&logoColor=white)
  ![S3](https://img.shields.io/badge/S3-569A31?style=flat-square&logo=amazons3&logoColor=white)
  ![RDS](https://img.shields.io/badge/RDS-527FFF?style=flat-square&logo=Amazon%20RDS&logoColor=white)
  ![ElastiCache](https://img.shields.io/badge/ElastiCache-527FFF?style=flat-square&logo=Amazon)
  - 탄력적인 서버 인프라를 제공하여 필요에 따라 컴퓨팅 자원을 자동으로 확장 및 축소할 수 있어 비용 절감과 성능 최적화가 가능
  - S3는 안정적이고 확장 가능한 스토리지를 제공하며, 파일 저장 및 정적 콘텐츠 제공에 적합해 대규모 파일 관리를 효율적으로 처리 가능
  - Elasticache는 빠른 캐싱을 통해 데이터베이스 성능을 향상시켜 응답 속도를 최적화
- #### CICD
  ![GitHub Actions](https://img.shields.io/badge/github%20actions-%232671E5.svg?style=flat-square&logo=githubactions&logoColor=white)
  ![Docker](https://img.shields.io/badge/docker-%230db7ed.svg?style=flat-square&logo=docker&logoColor=white)
  - 자동화된 워크플로우로 코드 변경 시 테스트, 빌드, 배포 과정을 신속하게 처리해 개발 주기를 단축
  - 애플리케이션을 컨테이너로 묶어 일관된 환경에서 실행되도록 보장해, 배포 시 환경 차이에 따른 오류를 최소화
- #### Database
  ![MySQL](https://img.shields.io/badge/mysql-%2300f.svg?style=flat-square&logo=mysql&logoColor=white)
  ![Redis](https://img.shields.io/badge/redis-%23DD0031.svg?style=flat-square&logo=redis&logoColor=white)

- #### API 테스트, 명세서
  ![Notion](https://img.shields.io/badge/Notion-%23000000.svg?style=flat-square&logo=notion&logoColor=white)
  ![Postman](https://img.shields.io/badge/Postman-FF6C37?style=flat-square&logo=postman&logoColor=white)
  ![Spring REST Docs](https://img.shields.io/badge/Spring%20REST%20Docs-6DB33F?style=flat-square&logo=spring&logoColor=white)
  ![Swagger](https://img.shields.io/badge/Swagger-85EA2D?style=flat-square&logo=swagger&logoColor=white)
  - RestDocs를 통해 생성된 문서를 Swagger UI로 시각화하여, 개발자와 비개발자 모두가 실시간으로 API를 테스트 가능
  - 테스트 코드 작성과 함께 API 문서가 자동으로 생성되어, 실제 코드와 문서의 동기화 문제가 발생하지 않음
  - 테스트 시에 문서를 검증할 수 있어 신뢰성을 높임

- #### 협업 툴
- ![Slack](https://img.shields.io/badge/Slack-4A154B.svg?style=flat-square&logo=slack&logoColor=white)
  ![Notion](https://img.shields.io/badge/Notion-000000.svg?style=flat-square&logo=notion&logoColor=white)


<br>

# 🤙 개발규칙

### ⭐ Code Convention

---

<details>
<summary style = " font-size:1.3em;">Naming</summary>
<div markdown="1">

- 패키지 : 언더스코어(`_`)나 대문자를 섞지 않고 소문자를 사용하여 작성합니다.
- 클래스 : 클래스 이름은 명사나 명사절로 지으며, 대문자 카멜표기법(Upper camel case)을 사용합니다.
- 메서드 : 메서드 이름은 동사/전치사로 시작하며, 소문자 카멜표기법(Lower camel case)를 사용합니다. 의도가 전달되도록 최대한 간결하게 표현합니다.
- 변수 : 소문자 카멜표기법(Lower camel case)를 사용합니다.
- ENUM, 상수 : 상태를 가지지 않는 자료형이면서 `static final`로 선언되어 있는 필드일 때를 상수로 간주하며, 대문자와 언더스코어(UPPER_SNAKE_CASE)로 구성합니다.
- DB 테이블: 소문자와 언더스코어로(lower_snake_case) 구성합니다.
- 컬렉션(Collection): **복수형**을 사용하거나 **컬렉션을 명시합니다**. (Ex. userList, users, userMap)
- LocalDateTime: 접미사에 *Time**를 붙입니다.


</div>
</details>
<details>
<summary style = " font-size:1.3em;">Comment</summary>
<div markdown="1">

### 1. 한줄 주석은 // 를 사용한다.

```java
// 하이~
```

### 2. 한줄 주석 외에 설명을 위한 주석은 JavaDoc을 사용한다.

```java
/**
 * 두 정수를 더합니다.
 * 
 * <p>이 메소드는 두 개의 정수를 입력받아 그 합계를 반환합니다.</p>
 * 
 * @param a 첫 번째 정수
 * @param b 두 번째 정수
 * @return 두 정수의 합
 * @throws ArithmeticException 만약 계산 중 오류가 발생하면
 */
```

</div>
</details>
<details>
<summary style = " font-size:1.3em;">Import</summary>
<div markdown="1">

### 1. 소스파일당 1개의 탑레벨 클래스를 담기

> 탑레벨 클래스(Top level class)는 소스 파일에 1개만 존재해야 한다. ( 탑레벨 클래스 선언의 컴파일타임 에러 체크에 대해서는 [Java Language Specification 7.6](http://docs.oracle.com/javase/specs/jls/se7/html/jls-7.html#jls-7.6) 참조 )

### 2. static import에만 와일드 카드 허용

> 클래스를 import할때는 와일드카드(`*`) 없이 모든 클래스명을 다 쓴다. static import에서는 와일드카드를 허용한다.

### 3. 애너테이션 선언 후 새줄 사용

> 클래스, 인터페이스, 메서드, 생성자에 붙는 애너테이션은 선언 후 새줄을 사용한다. 이 위치에서도 파라미터가 없는 애너테이션 1개는 같은 줄에 선언할 수 있다.


### 4. 배열에서 대괄호는 타입 뒤에 선언

> 배열 선언에 오는 대괄호(`[]`)는 타입의 바로 뒤에 붙인다. 변수명 뒤에 붙이지 않는다.

### 5. `long`형 값의 마지막에 `L`붙이기

> long형의 숫자에는 마지막에 대문자 'L’을 붙인다. 소문자 'l’보다 숫자 '1’과의 차이가 커서 가독성이 높아진다.

</div>
</details>
<details>
<summary style = " font-size:1.3em;">URL</summary>
<div markdown="1">

### URL

URL은 RESTful API 설계 가이드에 따라 작성합니다.

- HTTP Method로 구분할 수 있는 get, put 등의 행위는 url에 표현하지 않습니다.
- 마지막에 `/` 를 포함하지 않습니다.
- `_` 대신 `-`를 사용합니다.
- 소문자를 사용합니다.
- 확장자는 포함하지 않습니다.


</div>
</details>

<br>

### ☀️ Commit Convention

---

<details>
<summary style = " font-size:1.3em;">Rules</summary>
<div markdown="1">

### 1. Git Flow

작업 시작 시 선행되어야 할 작업은 다음과 같습니다.


> 1. issue를 생성합니다.
> 2. feature branch를 생성합니다.
> 3. add → commit → push → pull request 를 진행합니다.
> 4. pull request를 develop branch로 merge 합니다.
> 5. 이전에 merge된 작업이 있을 경우 다른 branch에서 진행하던 작업에 merge된 작업을 pull 받아옵니다.
> 6. 종료된 issue와 pull request의 label을 관리합니다.

### 2. IntelliJ

IntelliJ로 작업을 진행하는 경우, 작업 시작 시 선행되어야 할 작업은 다음과 같습니다.

> 1. 깃허브 프로젝트 저장소에서 issue를 생성합니다.
> 2. 생성한 issue 번호에 맞는 feature branch를 생성함과 동시에 feature branch로 checkout 합니다.
> 3. feature branch에서 issue 단위 작업을 진행합니다.
> 4. 작업 완료 후, add → commit을 진행합니다.
> 5. remote develop branch의 변경 사항을 확인하기 위해 pull 받은 이후 push를 진행합니다.
> 6. 만약 코드 충돌이 발생하였다면, IntelliJ에서 코드 충돌을 해결하고 add → commit을 진행합니다.
> 7. push → pull request (feature branch → develop branch) 를 진행합니다.
> 8. pull request가 작성되면 작성자 이외의 다른 팀원이 code review를 진행합니다.
> 9. 최소 한 명 이상의 팀원에게 code review와 approve를 받은 경우 pull request 생성자가 merge를 진행합니다.
> 10. 종료된 issue와 pull request의 label과 milestone을 관리합니다.


### 3. Etc

준수해야 할 규칙은 다음과 같습니다.

> 1. develop branch에서의 작업은 원칙적으로 금지합니다. 단, README 작성은 develop branch에서 수행합니다.
> 2. commit, push, merge, pull request 등 모든 작업은 오류 없이 정상적으로 실행되는 지 확인 후 수행합니다.

</div>
</details>

<details>
<summary style = " font-size:1.3em;">Branch</summary>
<div markdown="1">

### 1. Branch

branch는 작업 단위 & 기능 단위로 생성된 issue를 기반으로 합니다.

### 2. Branch Naming Rule

branch를 생성하기 전 issue를 먼저 작성합니다. issue 작성 후 생성되는 번호와 domain 명을 조합하여 branch의 이름을 결정합니다. `<Prefix>/<Issue_Number>-<Domain>` 의 양식을 준수합니다.

### 3. Prefix

- `main` : 개발이 완료된 산출물이 저장될 공간입니다.
- `develop`: feature branch에서 구현된 기능들이 merge될 default branch 입니다.
- `feature`: 기능을 개발하는 branch 입니다. 이슈 별 & 작업 별로 branch를 생성 후 기능을 개발하며 naming은 소문자를 사용합니다.

### 4. Domain

- `user`, `home`, `error`, `config`


### 5. Etc

- `feature/7-user`, `feature/5-config`


</div>
</details>

<details>
<summary style = " font-size:1.3em;">Issue</summary>
<div markdown="1">

### 1. Issue

작업 시작 전 issue 생성이 선행되어야 합니다. issue 는 작업 단위 & 기능 단위로 생성하며 생성 후 표시되는 issue number 를 참조하여 branch 이름과 commit message를 작성합니다.

issue 제목에는 기능의 대표적인 설명을 적고 내용에는 세부적인 내용 및 작업 진행 상황을 작성합니다.

issue 생성 시 github 오른편의 assignee, label을 적용합니다. assignee는 해당 issue 담당자, label은 작업 내용을 추가합니다.

### 2. Issue Naming Rule

`[<Prefix>] <Description>` 의 양식을 준수하되, prefix는 commit message convention을 따릅니다.

### 3. Etc

<aside>
[feat] 약속 잡기 API 구현
<br/>[chore] spring data JPA 의존성 추가

</aside>

---

</div>
</details>

<details>
<summary style = " font-size:1.3em;">Commit</summary>
<div markdown="1">

### 1. Commit Message Convention

`[<Prefix>] #<Issue_Number> <Description>` 의 양식을 준수합니다.

- **feat** : 새로운 기능 구현 `[feat] #11 구글 로그인 API 기능 구현`
- **fix** : 코드 오류 수정 `[fix] #10 회원가입 비즈니스 로직 오류 수정`
- **del** : 쓸모없는 코드 삭제 `[del] #12 불필요한 import 제거`
- **docs** : README나 wiki 등의 문서 개정 `[docs] #14 리드미 수정`
- **refactor** : 내부 로직은 변경 하지 않고 기존의 코드를 개선하는 리팩터링 `[refactor] #15 코드 로직 개선`
- **chore** : 의존성 추가, yml 추가와 수정, 패키지 구조 변경, 파일 이동 `[chore] #21 yml 수정`, `[chore] #22 lombok 의존성 추가`
- **test**: 테스트 코드 작성, 수정 `[test] #20 로그인 API 테스트 코드 작성`
- **style** : 코드에 관련 없는 주석 달기, 줄바꿈
- **rename** : 파일 및 폴더명 수정

</div>
</details>

<details>
<summary style = " font-size:1.3em;">Pull Request</summary>
<div markdown="1">

### 1. Pull Request

develop & main branch로 merge할 때에는 pull request가 필요합니다. pull request의 내용에는 변경된 사항에 대한 설명을 명시합니다.

### 2. Pull Request Naming Rule

`[<Prefix>] <Description>` 의 양식을 준수하되, prefix는 commit message convention을 따릅니다.

### 3. Etc

[feat] 약속 잡기 API 구현
<br/>[chore] spring data JPA 의존성 추가

</div>
</details>
