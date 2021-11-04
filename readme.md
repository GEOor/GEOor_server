# Black ice (도로 표면의 얇은 얼음막) 예측 프로그램

## 서비스 설명

- Algorithms.Hillshade 알고리즘과 팀원들이 특수 제작한 알고리즘을 결합해 특정 도로의 Black ice 발생을 예측해주는 서비스입니다.


## 1. Algorithms.Hillshade Algorithm
[공식 참조](https://pro.arcgis.com/en/pro-app/latest/tool-reference/3d-analyst/how-hillshade-works.htm)

- 널리쓰이고있는 Algorithms.Hillshade(음영기복도) 알고리즘입니다.<br>

### 대략적인 구성
<br><img src="img/readme_hillshade.png?raw=true"/><br>

### 1. 지형 높이를 알려주는 DEM 데이터
<br><img src="img/readme_dem.png?raw=true"/>
<br>
- Black ice에 많은 영향을 미치는 "그늘"에 대한 예측을 도와줍니다. 

### 2. 태양고도각, 태양방위각 데이터
[api 참조](https://www.data.go.kr/data/15012692/openapi.do)
<br>

### 3. 도로 shape 데이터
<br><img src="img/readme_road.png?raw=true"/>
<br>
<b>알고리즘 원리</b>
- 동쪽에 높은 산이 있는 지형을 생각해봅시다. 만약 현재 동쪽에 해가 떠 있다면, 이 지형에는 그늘이 뜰 것이라고 짐작할 수 있습니다.
<br><br>

## 2. GeoOr Algorithm (개발예정)
