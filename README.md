# NBE3-4-1-6

> 프로그래머스 데브코스: 팀 프로젝트 #1

## 설명

고객들에게 커피콩 상품을 판매하는 웹 사이트 입니다.  
매일 전날 오후 2시부터 오늘 오후 2시까지의 주문을 모아서 처리합니다.  
별도의 회원관리는 하지 않습니다.

## 기술

Backend: Spring Boot  
Frontend: React  
Database: H2 Database

## 디렉터리 구조

```
└───org
    └───team6
        └───coffeebeanery
            │   CoffeeBeaneryApplication.java
            │
            ├───common
            │   ├───constant
            │   │       OrderStatus.java
            │   │
            │   ├───exception
            │   │       ErrorDetails.java
            │   │       GlobalExceptionHandler.java
            │   │       InvalidInputException.java
            │   │       ResourceNotFoundException.java
            │   │
            │   ├───model
            │   │       Address.java
            │   │
            │   └───validation
            │           Url.java
            │           UrlValidator.java
            │
            ├───config
            │   └───WebConfig.java
            │
            ├───delivery
            │   ├───controller
            │   │       DeliveryController.java
            │   │
            │   ├───dto
            │   │       DeliveryDTO.java
            │   │
            │   ├───mapper
            │   │       DeliveryMapper.java
            │   │
            │   ├───model
            │   │       Delivery.java
            │   │
            │   ├───repository
            │   │       DeliveryRepository.java
            │   │
            │   └───service
            │           DeliveryService.java
            │
            ├───order
            │   ├───controller
            │   │       BuyerOrderController.java
            │   │       SellerOrderController.java
            │   │
            │   ├───dto
            │   │       OrderDetailDTO.java
            │   │       OrderDTO.java
            │   │
            │   ├───mapper
            │   │       OrderDetailMapper.java
            │   │       OrderMapper.java
            │   │
            │   ├───model
            │   │       Order.java
            │   │       OrderDetail.java
            │   │
            │   ├───repository
            │   │       OrderRepository.java
            │   │
            │   └───service
            │           BuyerOrderService.java
            │           SellerOrderService.java
            │
            └───product
                ├───controller
                │       BuyerProductController.java
                │       SellerProductController.java
                │
                ├───dto
                │       ProductDTO.java
                │
                ├───mapper
                │       ProductMapper.java
                │
                ├───model
                │       Product.java
                │
                ├───repository
                │       ProductRepository.java
                │
                └───service
                        BuyerProductService.java
                        SellerProductService.java
```

*(console `free /F` 입력)

## 프로젝트 설정

```
plugins {
	id 'java'
	id 'org.springframework.boot' version '3.4.1'
	id 'io.spring.dependency-management' version '1.1.7'
}

group = 'org.team6'
version = '0.0.1-SNAPSHOT'

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(23)
	}
}

configurations {
	compileOnly {
		extendsFrom annotationProcessor
	}
}

repositories {
	mavenCentral()
}

dependencies {
	implementation 'org.springframework.boot:spring-boot-starter-data-jpa'
	implementation 'org.springframework.boot:spring-boot-starter-validation'
	implementation 'org.springframework.boot:spring-boot-starter-web'
	implementation 'org.mapstruct:mapstruct:1.5.5.Final'
	compileOnly 'org.projectlombok:lombok'
	developmentOnly 'org.springframework.boot:spring-boot-devtools'
	runtimeOnly 'com.h2database:h2'
	annotationProcessor 'org.projectlombok:lombok'
	testImplementation 'org.springframework.boot:spring-boot-starter-test'
	testRuntimeOnly 'org.junit.platform:junit-platform-launcher'
}

tasks.named('test') {
	useJUnitPlatform()
}
```

