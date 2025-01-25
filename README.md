<div align="center">

# 모아가이드 - STO 큐레이션 플랫폼 모아가이드

<p>
  <img width="671" alt="Image" src="https://github.com/user-attachments/assets/7bfdd0ef-7aa2-4f29-af6e-0f93b1088fca" />
</p>

</div>

<br />

## 📝프로젝트 소개

**제작기간**: 2024.03.02 ~ 개발 진행 중

**제작인원**: 8명 (PM 2명, UI/UX 외주 제작, FE 3명, BE 3명)

**서비스 소개**: 해당 프로젝트는 분산된 STO(토큰증권) 시장 정보를 통합해 투자자들에게 신뢰도 높은 최신 데이터와 분석 리포트를 제공하는 서비스입니다. 자체 플랫폼 개발과
마케팅을 통해 개발 초기 67여 명의 유저를 모집했으며, 2025년까지 2,000명의 유저를 확보할 계획입니다. MVP 개발 이후 플랫폼 확장과 B2B 모델을 통해 시장 점유율을
확대할 예정입니다. 조각투자 상품 정보 제공, 최신 뉴스를 제공하며 자체 프리미엄 구독권 결제 시 투자 가이드 서비스를 함께 제공합니다.

<br />

## 🧑‍🤝‍🧑 프로젝트 팀원

### **Backend Team**

|                    이준호                     |                     김찬호                      |                      박소미                       |
|:------------------------------------------:|:--------------------------------------------:|:----------------------------------------------:|
| ![](https://github.com/LJHCS.png?size=120) | ![](https://github.com/kch2234.png?size=120) | ![](https://github.com/parksomii.png?size=120) |
|      [@LJH](https://github.com/LJHCS)      |    [@kch2234](https://github.com/kch2234)    |   [@parksomii](https://github.com/parksomii)   |

<br />

## 🛠 백엔드 기술 스택

<p align="center">
  <img src="https://github.com/user-attachments/assets/e3ed42ca-427f-4955-b3da-524aaf7a9359" alt="Image" />
</p>

- **개발**: `Spring boot`, `Spring JPA`

- **배포 및 호스팅**: `EC2`, `NginX`

- **DB**: `RDS`, `MySQL`, `Redis`

- **ETC**: `elasticsearch`, `S3`, `AWS CloudFront`, `Certbot`, `Git`, `GitHub Actions`, `Docker Hub`

<br />

## 💡 프로젝트 주요 기능

> ### **조각 투자 상품 실시간 정보 제공**
>
> - **상품의 시가총액, 현재가, 등락 폭, 수익률 데이터를 실시간 그래프를 통해 시각적으로 제공**
> - **관심 상품 저장 기능을 통해 저장한 상품에 접근**
> - **검색 기능을 통해 투자 상품에 쉽고 빠르게 접근**


> ### **투자 상품 관련 기사 모아보기**
>
> - **부동산, 음악 저작권, 미술품 등 카테고리별 최신 기사로 바로 이동할 수 있는 링크제공**
> - **가장 많이 본 기사, 최신순, 인기순 등 다양한 정렬 방식으로 뉴스 정보 제공**


> ### **조각 투자 가이드**
>
> - **조각 투자에 익숙하지 않은 사용자를 위해, 투자 가이드 커리큘럼과 리포트를 PDF 형식으로 제공**


> ### **로그인/회원가입**
>
> - **카카오, 네이버, 구글 계정을 통해 소셜 로그인 기능 제공**
> - **휴대폰 인증이 포함된 회원가입**
> - **회원탈퇴 기능**

> ### **ETC**
>
> - **마이페이지, 회원정보 수정**
> - **카카오톡 1:1 문의**
> - **모바일 반응형 디자인**
> - **토스 자동 결제 기능**
> - **관리자 전용 페이지**

<br />

## 🛠️ 트러블 슈팅

> ### **1. 검색 시스템 구현**
>
> 모아가이드의 상품 데이터는 상품별로 고유 속성을 가지며, RDB의 기본 인덱싱 방식으로는 다양한 속성 검색 시 속도 저하 문제가 발생했습니다.
>
> 이를 해결하기 위해 **Elasticsearch**를 도입하여 **역색인(Inverted Index) 방식**으로 검색 성능을 최적화했습니다.
>
> - **도입 효과**: 검색 응답 시간이 RDB 기반 대비 **90% 이상 단축**되었습니다.


> ### **2. 상품 리스트 로딩 최적화**
>
> 상품 리스트 페이지는 **약 1,000만 건의 주가 데이터**를 처리하며 로딩 시간이 길어졌습니다(10초 이상)
>
>이를 해결하기 위해 **테이블 분리**와 **데이터 구조 최적화**를 적용하여 **1500개의 상품 데이터만 처리**하도록 설계했습니다.
>
>- **최종 성과**: 로딩 시간이 **0.6초**로 단축되었고, 사용자 경험이 크게 향상되었습니다.


> ### **3. 검색 순위 문제 해결**
>
> 검색 순위 생성 중 **의미 없는 검색어**(예: `ㅇ`, `ㅁ`)와 **의미 없는 검색어**가 포함되는 문제가 있었습니다.
>
> 이에, **검색어 저장 로직을 수정**하여 **검색 결과가 없는 검색어는 저장하지 않도록** 변경했습니다.
>
> - **결과**: 검색 시스템 성능 최적화 및 불필요한 검색어 저장 문제 해결


<br />

## 📌Commit Convention

| Commit Type        | Description                              |
|--------------------|------------------------------------------|
| `feat`             | 새로운 기능 추가                                |
| `fix`              | 버그 수정                                    |
| `docs`             | 문서 수정                                    |
| `style`            | 코드 formatting, 세미콜론 누락, 코드 자체의 변경이 없는 경우 |
| `refactor`         | 코드 리팩토링                                  |
| `test`             | 테스트 코드, 리팩토링 테스트 코드 추가                   |
| `chore`            | 패키지 매니저 수정, 그 외 기타 수정 ex) .gitignore     |
| `design`           | CSS 등 사용자 UI 디자인 변경                      |
| `comment`          | 필요한 주석 추가 및 변경                           |
| `rename`           | 파일 또는 폴더 명을 수정하거나 옮기는 작업만인 경우            |
| `remove`           | 파일을 삭제하는 작업만 수행한 경우                      |
| `!breaking change` | 커다란 API 변경의 경우                           |
| `!hotfix`          | 급하게 치명적인 버그를 고쳐야 하는 경우                   |
