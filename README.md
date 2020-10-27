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

#### <br> 20.9.20. <br>

- @ModelAttribute 사용(parameter 직접 받지 않고 바인딩 받음)

- 'processCreateForm' handler에서 TDL 검증
(List<ToDo>에서 이름 없는 ToDo가 있을 경우
error message를 담아 createForm.html으로 redirect)

- createForm.html에서 List\<ToDo>를 순회하며 ToDo를 출력하는데
List의 null element인 경우 ${todo.name}을 하면 예외가 발생하므로
조건문을 이용하여 null element가 아닐 때만 ${todo.name}를 value로 갖도록 수정

TIL
- @ModelAttribute
<br> request param, query param, form data, path variable의 정보 중에
이 어노테이션이 붙은 핸들러 매개변수 타입의 필드와
이름이 일치하는 것을 binding 

- BindingResult를 이용한 error 정보 전달 방법
```java
/*
...
if(todo == null) {
    bindingResult.addError(new FieldError("tdl", "todos", "there is blank"));
    return "createForm";
}
...
*/
```
```html
<p th:if="${#fields.hasErrors('todos')}" th:errors="*{todos}">Incorrect todos</p>
```
- th:if와 th:unless
```html
<input th:if="${todo != null}" th:name="todos" th:value="${todo.name}" type="text">
<input th:unless="${todo != null}" th:name="todos" type="text">
```

#### <br> 20.9.27. <br>

- h2 환경설정

오늘은 TDL을 DB에 저장해보려고 했다.
인메모리 데이터베이스인 H2로 시작하는 게
좋을 것 같다고 생각했다. 연결도 잘되고
테스트 삼아 쿼리를 만들어 실행했는데 역시 잘됐다.
하지만 TDL를 저장하려고 하니 TDL의 필드 중 하나인
List 타입 'todos'에서 막혔다. 얘를 어떻게
저장하는 게 좋을까.

TIL
- h2 console 이용 방법
<br> application.properties에 'spring.h2.console.enabled=true'로 설정하고
애플리케이션을 실행시킨 뒤 다음의 주소로 이동하면
h2 console을 이용할 수 있다.
<br>`http://localhost:{port}/h2-console`

#### <br> 20.9.29. <br>

- spring data jpa + postgres(docker) 환경 구축 및 테스트

객체를 DB 테이블에 매핑하는 문제를 해결하기 위해 jpa를 사용했다.
sql은 postgres이고 도커를 이용하여 구축했다.

TIL
- spring boot data jpa
<br> spring boot는 data jpa 의존성이 추가되었을 때
application.properties에서 db 정보를 읽는다.
따라서 application.properties에 db 정보를 써줘야 한다.

- docker 명령어
<br>`docker exec -i -t {image_name} bash`
<br>`psql --username {user_name} --dbname {db_name}`

- postgres 명령어
<br> `\list` list of databases
<br> `\dt` list of relations

#### <br> 20.9.30. <br>

- ToDo를 독립적인 entity가 아닌 TDL의 embedded 프로퍼티로 설정

ToDo 클래스가 독립적인 entity일 필요가 있을까?
ToDo는 항상 TDL 안에 존재하므로
TDL table 안에 두고
독립적인 entity로 만들지는 않는 게 좋을 것 같다.

TIL
- @Embedded && @Embeddable
<br> Entity 속 객체(독립적인 entity가 아닌
entity 속에서만 존재하는 객체)를 db table에 매핑할 때
사용되는 어노테이션

- @ElementCollection && @Embeddable
<br> Entity 속 객체가 Collections(마찬가지로
독립적인 entity가 아니라 이 entity 속에서만 존재하는
객체일 때) db table에 매핑하는 방법
<br>[참고: jpa/embedded-element-collection](https://www.logicbig.com/tutorials/java-ee-tutorial/jpa/embedded-element-collection.html)

- `spring.jpa.hibernate.ddl-auto=create`
<br> DB를 다 날리고 다시 만드는 게 아니라
DB에 A, B 라는 테이블이 있는데 코드에서 A에 대한 DDL이 있을 경우
B는 안 건드리고, A를 날리고 다시 만든다.

- `spring.jpa.show-sql=true`
<br> 생성되는 query 출력

- `spring.jpa.properties.hibernate.format_sql=true`
<br> query 가독성 더 좋게 출력

#### <br> 20.10.2. <br>

- TDLRepository(extends JpaRepository) 인터페이스 추가

JpaRepository를 상속받는 TDLRepository를 만들었다.

embedded 관계에서도 lazy loading이 가능한지 궁금해서
이를 확인해보았다.

TIL
- Embedded 프로퍼티도 lazy/eager loading 설정 가능(lazy가 default)
<br> one to many 관계일 때 one 입장에서
many를 lazy하게 가져올 수 있다는 것은 알고 있었다.
현재 프로젝트는 entity끼리의 관계가 아니라
하나의 entity와 그 안에 embedded 프로퍼티 관계인데
이런 상황에서도 lazy loading이 되는지 알아보고 싶었다.
확인 결과 lazy/eager loading이 설정 가능하다(default lazy).
설정 방법은 다음과 같다.
<br> [참고: JPA @Embedded, @Embeddable, @ElementCollection 그리고 FetchType](https://live-everyday.tistory.com/209)
```java
    @ElementCollection(fetch = FetchType.EAGER)
    private List<ToDo> todos = new ArrayList<>();
```

- `spring.jpa.hibernate.ddl-auto=create`
<br> create로 값을 두면 프로그램에서 다루는 테이블을
매번 drop하고 새로 만든다. 따라서 데이터베이스에
저장되어 있는 값을 바탕으로 테스트를 할 생각이라면
이때는 옵션을 create가 아니라 update로 주어야 한다.

- @DataJpaTest
<br>@WebMvcTest와 마찬가지로 슬라이싱 테스트에 해당한다.
데이터와 관련된 설정만 적용하고 데이터와 관련된 컴포넌트만
빈으로 등록한다. 또한 @Transactional 어노테이션을
메타 어노테이션으로 담고 있으며, h2 의존성이 추가되어 있는 경우
테스트용 DB로 h2 데이터베이스를 사용한다.
다음에 @WebMvcTest와 함께 더 자세히 살펴보는 게 좋을 것 같다.

#### <br> 20.10.27. <br>

- Controller의 showTdl 메소드 수정

날짜가 아니라 id를 path variable로 받도록 수정하였다.
`http://localhost:8080/tdl/2020-10-27` =>
`http://localhost:8080/tdl/1`

TIL
- spring data jpa의 도움을 받아 domain class converter를 이용해서
repository로부터 가져온 TDL에는 todos 정보가 비어있다.
FetchType.EAGER 임에도 말이다. 그래서 path variable로 id를 받아 직접 repository의
findById 메소드를 통해 가져오자 todos 정보까지 잘 가져왔다.

내일은 인덱스 페이지를 수정해보자. 지금은 임의의 데이터를 컨트롤러에서 만든 뒤
이를 인덱스 페이지에 전달하고 있는데, 인덱스 페이지는 repository에서 tdl을
가져와 출력해야 한다.