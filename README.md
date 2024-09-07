### Server

- ![IntelliJ IDEA](https://img.shields.io/badge/IntelliJ%20IDEA-000000.svg?style=flat-square&logo=intellij-idea&logoColor=white)
  ![Java](https://img.shields.io/badge/Java-FF9900?style=flat-square&logo=JAVA&logoColor=FFFFFF)
  ![Springboot](https://img.shields.io/badge/Springboot-6DB33F?style=flat-square&logo=springboot&logoColor=white)
  ![Gradle](https://img.shields.io/badge/Gradle-02303A.svg?style=flat-square&logo=Gradle&logoColor=white)
  ![Spring Data JPA](https://img.shields.io/badge/Spring%20Data%20JPA-6DB33F?style=flat-square&logo=spring&logoColor=white)
- ![MySQL](https://img.shields.io/badge/MySQL-%2300f.svg?style=flat-square&logo=mysql&logoColor=white)
  ![Hibernate](https://img.shields.io/badge/Hibernate-59666C?style=flat-square&logo=Hibernate&logoColor=white)
- ![GitHub Actions](https://img.shields.io/badge/Github%20Actions-%232671E5.svg?style=flat-square&logo=githubactions&logoColor=white)
  ![Docker](https://img.shields.io/badge/Docker-%230db7ed.svg?style=flat-square&logo=docker&logoColor=white)
  ![Nginx](https://img.shields.io/badge/Nginx-%23009639.svg?style=flat-square&logo=nginx&logoColor=white)
  ![Redis](https://img.shields.io/badge/Redis-DC382D?style=flat-square&logo=Redis&logoColor=FFFFFF)

### Co-working Tool

- ![Slack](https://img.shields.io/badge/Slack-4A154B.svg?style=flat-square&logo=slack&logoColor=white)
  ![Notion](https://img.shields.io/badge/Notion-000000.svg?style=flat-square&logo=notion&logoColor=white)

<br>

## **🐾 기술 스택 선정 이유**

### **📗 Server 스택 선정 이유**

- spring boot(Java)

프레임 워크로 spring boot를 사용

다양한 라이브러리와 설정을 자동으로 제공하므로 개발자가 애플리케이션을 더 빠르게 만들 수 있음

특정 환경이나 서버, 기술에 종속되지 않으며 유연한 애플리케이션을 개발할 수 있음

기존 코드와의 호환성을 고려해 java 언어 선정

- MySql

RDBMS 로 테이블 형태로 연관관계를 형성해서 데이터를 관리하는 목적으로 사용

- JPA

데이터베이스 쿼리를 간단하게 작성할 수 있어 쿼리 작성에 필요한 노력과 시간을 크게 줄일 수 있음

ORM(Object-Relational Mapping) 기능을 자동으로 제공하므로 객체와 데이터베이스 간의 매핑을 자동으로 처리할 수 있으며, 데이터베이스의 스키마 변경에도 유연하게 대응할 수 있음


- QueryDsl

QueryDsl을 사용하여 타입 안전한 쿼리를 작성함

즉, 쿼리를 작성할 때 컴파일 타임에 오류를 잡을 수 있어 실행 중에 발생할 수 있는 쿼리 관련 오류를 방지할 수 있습니다.

- docker

컨테이너 기반의 오픈소스 가상화 플랫폼으로, 환경에 구애받지 않고 애플리케이션을 신속하게 구축, 테스트 및 배포

다양한 프로그램들과 실행환경을 컨테이너로 규격화시켜 프로그램의 배포 및 관리를 단순화할 수 있어 편리

- github action

CI와 CD를 자동화하여 복잡성을 줄이고 효율적이고 간편한 CI/CD 파이프 라인 구축

- Redis

Redis를 활용하여 데이터 캐싱, 빠른 데이터 접근, 실시간 데이터 처리 및 유연한 데이터 구조 지원을 합니다.

Redis는 인메모리 데이터 저장소로서 높은 성능과 다양한 기능을 제공하기 때문에 다양한 애플리케이션에서 사용됩니다. 

- K6

성능 테스트 및 부하 테스트를 수행하기 위한 오픈 소스 도구로, 주로 웹 애플리케이션, API의 성능을 검증하는 데 사용하였습니다. 
 
이는 간편한 설정, 직관적인 스크립트 작성, 경량화된 성능 및 자동화 파이프라인과의 통합 용이성이 있습니다.

- DDD(도메인 주도 설계)

도메인은 서로 분리되고, 높은 응집력과 낮은 결합도로 변경과 확장에 용이한 설계

<br>

## 🔖 Naming Rules

### 💻Backend

- **Packages**
  -  언더스코어(_)나 대문자를 섞지 않고 소문자를 사용하여 작성합니다.
- **Classes**
  - 클래스 이름은 명사나 명사절이어야 한다.
  - 대문자 카멜표기법(Upper camel case)을 사용합니다.
- **Interfaces**
  - 인터페이스 이름도 클래스 이름과 같은 대문자 규칙을 적용한다.
- **Methods**
  - 메서드 이름은 동사/전치사로 시작하여야 한다.
  - 소문자 카멜표기법(Lower camel case)를 사용합니다.
  - 의도가 전달되도록 최대한 간결하게 표현합니다.
- **Constants**
  - 클래스 상수로 선언된 변수들과 상수들의 이름은 모두 대문자로 쓰고 각 단어는 언더바 ("\_")로 분리한다.
- **Variables**
  - 소문자 카멜표기법(Lower camel case)를 사용합니다.
  - 언더바 또는 달러 표시 문자로 시작하는 것이 허용 되기는 하지만, 사용하지 말자.
  - 짧지만 의미있게 짓는다.
  - 변수의 사용 의도를 알 수 있도록 의미적으로 짓는다.
  - 한문자로만 이루어진 변수는 암시적으로만 사용하고 버릴 변수를 제외하고는 피한다.
- **ETC**
  - DB 테이블: 소문자와 언더스코어로(lower_snake_case) 구성합니다.
  - ENUM, 상수: 상태를 가지지 않는 자료형이면서 static final로 선언되어 있는 필드일 때를 상수로 간주하며, 대문자와 언더스코어(UPPER_SNAKE_CASE)로 구성합니다.
  - 컬렉션(Collection): **복수형**을 사용하거나 **컬렉션**을 명시합니다. (Ex. userList, users, userMap)
  - LocalDateTime: 접미사에 **Date**를 붙인다.

<br>

## **🗂️ Commit Convention**

Ex) `[feat] #8 구글 로그인 API 기능 구현 `

- **feat** : 새로운 기능 구현 `[feat] #11 구글 로그인 API 기능 구현`
- **fix** : 코드 오류 수정 `[fix] #10 회원가입 비즈니스 로직 오류 수정`
- **del** : 쓸모없는 코드 삭제 `[del] #12 불필요한 import 제거`
- **docs** : README나 wiki 등의 문서 개정 `[docs] #14 리드미 수정`
- **refactor** : 내부 로직은 변경 하지 않고 기존의 코드를 개선하는 리팩터링 `[refactor] #15 코드 로직 개선`
- **chore** : 의존성 추가, yml 추가와 수정, 패키지 구조 변경, 파일 이동 `[chore] #21 yml 수정`, `[chore] #22 lombok 의존성 추가`
- **test**: 테스트 코드 작성, 수정 `[test] #20 로그인 API 테스트 코드 작성`
- **style** : 코드에 관련 없는 주석 달기, 줄바꿈
- **rename** : 파일 및 폴더명 수정

<br>

## **🐬 Git Flow**

작업 시작 시 선행되어야 할 작업은 다음과 같습니다.

> 1. issue를 생성합니다.
> 2. feature branch를 생성합니다.
> 3. add → commit → push → pull request 를 진행합니다.
> 4. pull request를 develop branch로 merge 합니다.
> 5. 이전에 merge된 작업이 있을 경우 다른 branch에서 진행하던 작업에 merge된 작업을 pull 받아옵니다.
> 6. 종료된 issue와 pull request의 label을 관리합니다.

## **💡Branch**

branch는 작업 단위 & 기능 단위로 생성된 issue를 기반으로 합니다.

branch를 생성하기 전 issue를 먼저 작성합니다. issue 작성 후 생성되는 번호와 domain 명을 조합하여 branch의 이름을 결정합니다. `<Prefix>/<Issue_Number>-<Domain>` 의 양식을 준수합니다.

## **🖥️Branch Naming Rule**

branch를 생성하기 전 issue를 먼저 작성합니다. issue 작성 후 생성되는 번호와 domain 명을 조합하여 branch의 이름을 결정합니다. `<Prefix>/<Issue_Number>-<Domain>` 의 양식을 준수합니다.

## **📑Prefix**

- `main` : 개발이 완료된 산출물이 저장될 공간입니다.
- `develop`: feature branch에서 구현된 기능들이 merge될 default branch 입니다.
- `feature`: 기능을 개발하는 branch 입니다. 이슈 별 & 작업 별로 branch를 생성 후 기능을 개발하며 naming은 소문자를 사용합니다.

<br>


# Back-end
## 공통 사항
- 단위 테스트 작성(service 메소드 별로) : Junit 사용
- 다른 사람이 알아보기 쉽도록 주석처리해야 합니다.
    - javadoc 형식 https://jake-seo-dev.tistory.com/59
- issue 생성 및 PR을 통해 본인이 구현한 부분에 대한 기록을 남겨야 합니다.
- 테스트 및 프론트와 연결 과정을 위한 로그을 작성해야 합니다.
- 개발 기간 : 8/24 ~ 9/8
- 스프린트 4회(3일간격) 진행
    - 수요일, 토요일

<br>

## 개발규칙

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
