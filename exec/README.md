# 빌드 및 배포

## Android

minSdk : 18 ( 홈캠 기능 용 : 21 )   
targetSdk : 33   
jvmTarget : 1.8   
IDE : Android Studio flamingo

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

### 카카오 MAP Android

Kakao 지도 Android API 는 앱 키 발급 및 키 해시를 등록해야만 사용 가능합니다.
이를 위해서는 카카오 계정이 필요합니다.

키 해시 등록을 위해서는 아래 과정이 필요합니다.
1. 카카오 개발자사이트 (https://developers.kakao.com) 접속
2. 개발자 등록 및 앱 생성
3. Android 플랫폼 추가: 앱 선택 – [플랫폼] – [Android 플랫폼 등록] – 패키지명(필수) 등록
4. 키 해시 등록: [Android 플랫폼 등록] – 키 해시를 등록합니다.
5. 페이지 상단의 [네이티브 앱 키], 등록한 [패키지명], [키 해시]를 사용합니다.

### 카카오 MAP Web

Kakao 지도 Javscript API 는 키 발급을 받아야 사용할 수 있습니다.
그리고 키를 발급받기 위해서는 카카오 계정이 필요합니다.

키 발급에는 아래 과정이 필요합니다.

1. 카카오 개발자사이트 (https://developers.kakao.com) 접속
2. 개발자 등록 및 앱 생성
3. 웹 플랫폼 추가: 앱 선택 – [플랫폼] – [Web 플랫폼 등록] – 사이트 도메인 등록
4. 사이트 도메인 등록: [웹] 플랫폼을 선택하고, [사이트 도메인] 을 등록합니다. (예: http://localhost:8080)
5. 페이지 상단의 [JavaScript 키]를 지도 API의 appkey로 사용합니다.
6. 앱을 실행합니다.

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

(웹사이트에서 시작한다.)

시연시작하겠습니다.    
앞서 설명햇던 것처럼 구글 플레이 스토어를 사용하지 못하는 휴대폰에서도 저희 어플을 사용할 수 있도록 이지를 만들어 apk를 다운 받을 수 있게 만들었습니다.   
다운이 오래 걸리기 때문에 시연에서는 미리 설치해둔 앱으로 사용을 하겠습니다.

![WebHomePage](/exec/resources/home_page.jpg)

(홈화면으로 넘어간다.)

![HomeImage](/exec/resources/home.jpg)

(기기 탭을 누른다.)

기기탭에서는 대여, 반납, 기증이 가능합니다.

3개중 기증을 시연해보겠습니다.

![기기탭page](/exec/resources/recycle_page.jpg)

(기증을 누른다.)

기증을 하기 위해서는 수거방법과 수거날짜를 선택해야합니다. 방문 택배는 사용자가 설정한 주소에 직접 방문해서 휴대폰을 수거하는 방법이고 삼성 대리점은 직접 사용자가 정해진 날짜에 삼성 대리점에 기증을 하는 방법입니다.

![donation_first](/exec/resources/donation_first_page.jpg)

저는 삼성 대리점을 선택하겠습니다.

날짜는 25일에 기증하러 가겠습니다.

![donation_second](/exec/resources/donation_second.jpg)

('다음'을 누른다)

대리점 선택을 하면 현재 위치에서 제일 가까운 대리점 순으로 대리점이 나옵니다. 

저는 제일 가까운 구미센터에 제출하겠습니다.

![donation_agent](/exec/resources/donation_agent_choice.jpg)

선택한 대리점의 상세정보가 나오면서 신청버튼을 누르면 기증신청이 완료가 됩니다.

![donation_agent_detail](/exec/resources/donation_agent_detail.jpg)

지금까지 신청한 기증을 확인하기 위해 마이페이지로 가겠습니다.

![donation_finish](/exec/resources/donation_finish.jpg)

(내 기증 기록을 누른다.)

![mypage](/exec/resources/mypage.jpg)

화면과 같이 내 기증 기록을 볼 수 있습니다.

![donation_list](/exec/resources/donation_list.jpg)

다음으로는 플러스 기능을 보겠습니다.

플러스 기능으로는 4,5주차 때 보였던 y2k 사진부스, 리모컨, 홈캠 기능, 실내 온습도계가 있습니다.

이 중 저는 홈캠 기능을 보겠습니다.

![plus](/exec/resources/plus_app.jpg)

보여질 카메라의 이름을 정해주고 연결하기를 누르면 연결되있는 캠들을 볼 수 있게 됩니다.

![homecam_name](/exec/resources/homecam_name.jpg)

화면처럼 멀리 있는 저희 팀원의 모습이 보입니다. (최주님 인사시작)

![homecam](/exec/resources/homecam.jpg)

이렇게 폰고지신 사용자는 여러 플러스 기능을 통해 빌린 휴대폰을 어떻게 사용하는지 가이드를 제공합니다. 

폰고지신 통해 죽어있는 휴대폰에게  생명을 부여할 수 있습니다.

이상으로 시연 마치겠습니다