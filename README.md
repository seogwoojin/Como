<h2>프로젝트 설명</h2>

Como는 여러 기업들의 기존 출제 경향, 문제 수준, 문제 수를 참고해 이에 적합한
코딩테스트 모의고사를 제공합니다

<h2>사이트 바로가기</h2>
<a href="https://cocomo.site">Como</a>

<h2>프로젝트 기능 소개</h2>

- 본 사이트에서는 여러 블로그, 기출 문제를 참고해 각 기업 별 출제 난이도와 알고리즘 문제 경향을 분석했습니다.
- 이 분석 결과에 맞춰 유저 요청 시 기존에 스크랩해놓은 백준의 문제들 중 유사한 문제들을 랜덤하게 선정해 제공합니다.

<h2>프로젝트 사용 기술</h2>

- **jsoup** : Jsoup 라이브러리를 이용해 백준 내의 모든 문제들 중,
  난이도 브론즈5 ~ 플레티넘 5, 빈출 알고리즘 (DP, DFS_BFS, Greedy, Str, Simulation, BruteForce, DataStructure, BinarySearch, Graph, Sort, Else),
  다국어 문제나 문제의 안정성을 고려해 시도 횟수 200회 이상 500회 이하인 문제는 한글 문제만, 200회 이하 문제X
  이를 만족하는 문제들만 파싱해 DB에 저장했습니다.

- **GitAction** : CI/CD 구현을 위해 깃 액션을 이용했습니다.

- **Docker&Docker-Compose** : 마찬가지로 더 빠른 수정 반영을 위해 사용한 기술입니다.


<h2>아키텍쳐</h2>

![image](https://github.com/user-attachments/assets/595159ea-777a-4b02-9fc8-79eacc59b77c)

(in EC2)
![image](https://github.com/user-attachments/assets/9ba26e11-a42e-4049-812b-6b648e47a480)


<h2>CI/CD</h2>

![image](https://github.com/user-attachments/assets/993f14b8-5a39-4126-bc26-0fbacb03fab1)
