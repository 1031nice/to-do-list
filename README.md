# to-do-list

### 1. CRUD
#### <br> 20.9.15.<br>
- 글쓰기에 대한 get/post 요청 처리를 완료

#### <br> 20.9.16. <br>
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

#### <br> 20.9.17. <br>
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
[@WebMvcTest 테스트에서 Formatter가 동작하지 않는 이유(blog)](https://live-everyday.tistory.com/205)

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
<br>[참고: standard expression syntax(thymeleaf)](https://www.thymeleaf.org/doc/tutorials/3.0/usingthymeleaf.html#standard-expression-syntax)

#### <br> 20.9.19. <br>

- ToDo 클래스 추가(TDL 클래스는 List\<ToDo>를 가짐)
- TDL 필드 이름 변경 tasks -> todos
- createForm.html 수정(dynamic fields)
- tdlDetails.html 추가(할일 목록 출력)

TIL

- dynamic fields
<br>[참고: dynamic fields(thymeleaf)](https://www.thymeleaf.org/doc/tutorials/2.1/thymeleafspring.html#dynamic-fields)

- 생성자에서 String 타입의 매개변수를 하나 받는다면 Formatter가 없어도
String과 Composite Object간에 변환이 이루어진다.
예를 들어 ToDo 클래스는 name이라는 String 타입 필드를 갖는데,
name을 인자로 받는 생성자가 있기 때문에 Formatter
없이도 변환이 이루어진다.
<br>[참고: Formatter 없이도 변환이 되는 경우(blog)](https://live-everyday.tistory.com/206)

- @RequestParam 리스트로 받는 방법
<br>parameter의 이름을 같게 한 뒤 List로 받으면 된다. 

- html의 checkbox와 label 사용 방법
```html
<input type="checkbox" th:id="todo + ${iter.index}" th:name="todo + ${iter.index}">
<label th:for="todo + ${iter.index}" th:text="${todo.name}"></label>
```

- th:each에서 반복자 사용하는 방법
```html
        <tr th:each="todo, iter : *{todos}">
            <th th:text="${iter.index + 1} + ."></th>
```

- thymeleaf 표현식 *와 $의 차이
<br> *는 object가 선택된 경우 selection할 때 사용
object가 선택되지 않은 경우라면 *는 $와 동일하다.
아래의 두 코드는 완전히 동일하다.
<br>[참고: expressions on selections asterisk syntax(thymeleaf)](https://www.thymeleaf.org/doc/tutorials/3.0/usingthymeleaf.html#expressions-on-selections-asterisk-syntax)
```html
  <div th:object="${session.user}">
    <p>Name: <span th:text="*{firstName}">Sebastian</span>.</p>
    <p>Surname: <span th:text="*{lastName}">Pepper</span>.</p>
    <p>Nationality: <span th:text="*{nationality}">Saturn</span>.</p>
  </div>
```
```html
<div>
  <p>Name: <span th:text="${session.user.firstName}">Sebastian</span>.</p>
  <p>Surname: <span th:text="${session.user.lastName}">Pepper</span>.</p>
  <p>Nationality: <span th:text="${session.user.nationality}">Saturn</span>.</p>
</div>
```