# 빌드 및 배포

## Android

minSdk : 18 ( 홈캠 기능 용 : 21 )   
targetSdk : 33   
jvmTarget : 1.8


## Spring Server
JVM : openjdk:8-jdk   
server : ubuntu 20.04 LTS (GNU/Linux 5.4.0-1018-aws x86_64)   
IDE : IntelliJ IDEA 20201.2.4   
Spring boot : 2.7.14   

프로젝트에서 사용한 라이브러리 버전
![spring classpath](/exec/resources/classpath.PNG)


### VM option
-Djasypt.encryptor.password=s1s2a3f4y@

#### build 방법
```
cd phonesin
./gradlew clean build -x test
```

## 주요 계정 및 프로퍼티가 정의된 파일 목록

phonesin/src/main/resources/application.yml (MailSender 설정)

```
spring:
  mail:
    host: {사용하는 SMTP 서버 주소}
    port: {사용하는 SMTP 서버 포트번호}
    username: {사용하는 SMTP 서버 아이디}
    password: {사용하는 SMTP 서버 앱 비밀번호}
```

phonesin/src/main/resources/application-db.yml (DB 설정)

```
spring:
  datasource:
    driver-class-name: {DB에 맞는 driver}
    url: {DB URL}
    username: {계정명}
    password: {비밀번호}
```


# 외부 서비스

### 카카오 MAP

Kakao 지도 Android API 는 앱 키 발급 및 키 해시를 등록해야만 사용 가능합니다.
이를 위해서는 카카오 계정이 필요합니다.

키 해시 등록을 위해서는 아래 과정이 필요합니다.
1. 카카오 개발자사이트 (https://developers.kakao.com) 접속
2. 개발자 등록 및 앱 생성
3. Android 플랫폼 추가: 앱 선택 – [플랫폼] – [Android 플랫폼 등록] – 패키지명(필수) 등록
4. 키 해시 등록: [Android 플랫폼 등록] – 키 해시를 등록합니다.
5. 페이지 상단의 [네이티브 앱 키], 등록한 [패키지명], [키 해시]를 사용합니다.

### 구글 메일

폰고지신 프로젝트에서는 SMTP를 사용하기위해 구글을 이용하였습니다.

1. 구글 계정 보안 설정 (https://myaccount.google.com/u/1/security) 접속
2. 2단계 인증이 되어 있지 않은경우 2단계 인증 설정
3. Google에 로그인하는 방법 - [2단계 인증] - [앱 비밀번호] 설정
4. 앱설정 -> 메일, 기타 설정 ->  기타(맞춤 이름) 입력
5. 사용하는 앱 이름을 입력하고 생성을 누르면 앱 비밀번호가 제공된다.

application.yml 해야하는 설정
```
spring:
  mail:
    host: stmp.gmail.com
    port: 587
    username: {google 이메일}
    password: {위에서 설정한 앱 비밀번호}
```



# 시연 시나리오

