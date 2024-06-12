# 🖥️ Spring-project-market
- 예약구매 쇼핑몰

#### 개발 기간
- 24.04.17 ~ 24.06.10

<br>

## ✅ 프로젝트 목표
- 데이터 수집
  - 상품 DB 정보 수집
 
- 대용량 트래픽 환경에서 상품 주문에 대한 동시성 제어
  - 10000개의 요청에 대해 재고 변경에 따른 에러 상황 대응
  - 사용자에 따른 정확한 주문 처리


## ⚙️ 개발 환경
- **Language** : `Java`  `JDK 17`
- **IDE** : `IntelliJ`
- **Framework** : `Spring boot` `Lombok` `OpenFeign` `API Gateway` `OpenFeign`
- **Database** : `MySQL` `Redis`
- **ORM** : `JPA`
- **Architecture** : `Microservices (MSA)` `Spring Cloud`
- **CI/CD** : `GitHub Actions` `Docker`
- **Infra** : `EC2` `RDS` `Kafka` `Eureka`
- **Test** : `nGrinder` `TDD`

<br>

![marketERD](https://github.com/already-cow/market_msa/assets/97944505/ec4b082f-988f-41d8-985a-9e8ebe391773)

## ❓ 기술적 의사결정
- 세션 쿠키 vs JWT
- nGrinder vs Jmeter
- 메세지 큐 중에 kafka 결정
- 2PC  vs TCC vs Saga Pattern

<br>

## ⭐️ 트러블 슈팅
- 동시성 이슈 해결 (synchronized keywword, 낙관적, 비관적, 분산락) -> 분산락 결정
왜 쓰지 않았는가 ? 인스턴스가 여러개면 안된다

## 성능 개선
- Openfeign에서 시간 이슈 해결 -> kafka 사용
<br>
<br>
