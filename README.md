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
    .andReturn();
TDL tdl = (TDL)mvcResult.getFlashMap().get("tdl");
```

####20.9.17. <br>
- index 페이지에서 TDL의 목록 출력

TIL

- String을 LocalDate로 변환하는 Formatter를 만들어
bean으로 등록을 했는데 테스트 코드에서 동작하지 않았다.
스프링부트에서는 Formatter를 bean으로만 등록해도
자동으로 찾아서 등록해주는 것으로 알고 있었는데(맞다)
안돼서 당황했다. 침착하게 다시 보니 테스트가 @WebMvcTest로
웹 계층만 테스트하는 slicing test인걸 깜빡하고 테스트 환경에
Formatter 빈을 따로 추가해주지 않아서 생긴 문제였다.
Formatter 빈을 제공해주자 테스트에 성공하였다.<br>
[@WebMvcTest 테스트에서 Formatter가 동작하지 않는 이유](https://live-everyday.tistory.com/205)
- thymeleaf expression: preprocessing<br>
앞뒤로 __가 붙으면 그건 preprocessing 대상이다.
<br>`<a th:href="@{/tdl/${tdl.date}}"></a>`
<br> 위와 같이 적으면 아래의 주소로 이동한다.
<br> `http://localhost:8080/tdl/$%7Btdl.date%7D`
<br> 즉, 앞의 @만 보고 처리하여 뒤의 $가 무시되는 것이다.
이럴 때 preprocessing을 이용할 수 있다. $의 앞 뒤로 __를 붙여주면
$부터 처리(preprocessing)된다.
<br> `<a th:href="@{/tdl/__${tdl.date}__}</a>"`
<br> 아래와 같이 의도한 대로 이동한다.
<br> `http://localhost:8080/tdl/2020-09-17`
<br>[참고: thymeleaf tutorial document](https://www.thymeleaf.org/doc/tutorials/3.0/usingthymeleaf.html#standard-expression-syntax)