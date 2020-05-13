# commit message convention

### JIRA-StoryIDX [상태] 명사형-설명

``S02P31C110-4 [*] Readme file modification for commit message convention`` 혹은

``S02P31C110-4 [*] 커밋 메시지 컨벤션을 위한 리드미 파일 수정``



### 상태

[+] add 추가

[-]  remove 삭제

[*] edit 수정



### 브랜치 branch : feature/api명

default branch는 develop으로 변경되어 있으므로 이후 feature/api명으로 브랜치를 바꿔서 사용

``feature/readme`` ``feature/login`` ``feature/tensorflow`` ``feature/ui``



### git 초기 명령어

``git clone https://lab.ssafy.com/s02-final/s02p31c110``

``git branch feature/api명``

``git checkout feature/api명``

작업

``git add .``

위 규칙대로 commit 메시지 작성

``git push``

``git checkout develop``

``git pull``

``git branch -D feature/api명``

