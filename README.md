# to-do-list

###1. CRUD
####20.9.15.<br>
- 글쓰기에 대한 get/post 요청 처리를 완료

####20.9.16. <br>
- TDL(to-do-list) 클래스 추가(이제 더이상 글쓰기가 아니라 TDL 만들기)
- 사용자가 TDL를 만들면 redirect를 이용하여 생성된 TDL 출력

TIL
- 요청이 redirect 되는지 테스트하는 방법 `andExpect(status().is3xxRedirection())`
- 어떤 url로 redirect 되는지 테스트하는 방법 `.andExpect(redirectedUrl("/tdl"))`
- flash attributes 값 확인하는 방법
```java        
MvcResult mvcResult = 
...
    .andReturn();
TDL tdl = (TDL)mvcResult.getFlashMap().get("tdl");