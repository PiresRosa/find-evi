# 소통의 간격을 줄이다, SPACE D 🪐
SPACE D는 여백, 간격, 우주를 의미하는 **SPACE** 와 ‘Designer, Developer’의 **D** 를 합친 합성어로,  
**‘알아듣지 못하는 용어로 생긴 소통의 간격을 줄인다'** 는 의미를 담고 있습니다.  

### 기술 스택
`Spring Boot`  `Java`  `Spring Security`  `Spring Security OAuth2 Client`  `jjwt` `H2 database` `MySQL` `JPA` `Querydsl` `Logback` `Lombok` `Swagger` `Docker` `AWS EC2` `AWS RDS` `AWS Route 53`  `Nginx`

### 개발 규칙
#### Git Branch 
- 기본적으로 [Github-Flow](https://docs.github.com/en/get-started/using-github/github-flow) 의 양식을 따르고 그 기능을 적극 활용
- 이슈 단위로 개발을 진행하며 브랜치 네이밍은 {type}/#{이슈 번호}
- 브랜치 보호 전략으로는 main & develop 브랜치 사용 
  - force push & force merge 금지
- merge 전략으로는 모두 일반 merge 사용
    - feature → develop
    - develop → main

#### Code & Commit convention
- 코드 컨벤션으로는 [Google Java Style](https://github.com/google/styleguide) 사용
- 커밋 양식은 Angularjs Commit Convention 기반
    - Body 와 Footer 는 필요시에만 작성

#### Package Structure
```
- com
  - dnd
    - spaced
        - domain
            - account
                - application
                    - dto
                    - exception
                - domain
                    - exception
                    - repository
                - presentation
                    - dto
                        - request
                        - response
                    - exception
                - infrastructure
	  - global
	    - config
	    - docs
	    - entity
	    - exception
	    - resolver
	    - interceptor
	    - repository
	    - security
```
