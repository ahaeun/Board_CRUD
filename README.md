# Daogift_Popup_CRUD_Test



## 다오기프트 팝업 기능 CRUD

# 기능
1. 팝업 추가 기능
    * 이미지 10개까지 업로드 가능
    * 이미지 업로드 후 팝업 저장
    * 파일 이름 Seed128로 암호화
2. 팝업 리스트 조회
    * 팝업 리스트 페이징 기능
3. 팝업 상세 조회 기능
4. 팝업 수정 기능
5. 팝업 삭제 기능

* xss : security에 필터를 적용하여 스크립트를 허용하지 못하도록 block한다.
* sql injection : mybatis에서 #{}를 사용하여 String으로 치환, 실행 하도록 한다.

# 경로
/dao/popup/controller - 팝업 컨드롤러<br/>
/dao/popup/service - 팝업 service, serviceImpl 비지니스 로직<br/>
/dao/popup/dto - request, response 데이터 모델<br/>
/dao/popup/mapper - mapper interface 모음<br/>
/dao/popup/security - seed128 관련 파일<br/>

/dao/popup/config - xss, 웹 설정 파일 <br/>
/dao/popup/enums - enum 모음<br/>
/dao/popup/util - 경로 관련, 공통 코드<br/>