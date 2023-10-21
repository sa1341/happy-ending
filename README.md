# 멀티모듈 기반에서 다양한 프로덕트 서비스 개발하기

이 프로젝트는 멀티모듈 기반의 서비스에서 각 모듈별 관심사별로 의존성을 분리하고
외부 미들웨어 및 서비스와 빠르게 연동하고 어떻게 확장성 있게 애플리케이션을 개발할 수 있을지
고민하기 위해 만든 개인학습을 위한 프로젝트입니다.

## 프로젝트 구조

- happy-ending
  - app
    - api 서비스
    - admin 서비스
    - batch 서비스

  - domain
    - 서비스별 도메인

  - infra
    - mysql
    - kafka
    - redis

  - support
    - logging
    - utils
    - testkit
    - model
    - metric
    - yaml-importer


## 프로젝트 목적

해당 프로젝트는 실제 회사에서 운영중인 MSA 프로젝트에서 자주 사용하는 기술들을 습득하고 발전시키기 위해서
다양한 학습을 위한 프로젝트로 구축을 하였습니다.

## 공부하고 싶은 과제목록

- Redis 분산락 구현(Redisson Client 사용)
- WebClinet API를 이용한 재처리 및 circuitbreaker 적용
- Kafka streams를 이용한 다양한 처리 토폴로지 구현해보기
- 모니터링 툴을 위한 Metric 설정(프로메테우스, 그라파나 연동)
- open vault 서비스를 사용하여 민감한 데이터정보 관리하기 등



