package me.donghun.todolist;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@org.springframework.stereotype.Controller
public class Controller {

    @Autowired
    JdbcTemplate jdbcTemplate;

    // 인덱스 페이지에 이렇게 하면 데이터를 전송할 수 있을까? yes!
    @GetMapping("/")
    public ModelAndView index(){
        // create test-data
        TDL[] tdls = new TDL[3];
        tdls[0] = new TDL();
        tdls[0].setDate(LocalDate.of(2020, 10, 31));
        tdls[0].setContent("이번 생일");
        tdls[1] = new TDL();
        tdls[1].setDate(LocalDate.of(2019, 10, 31));
        tdls[1].setContent("이전 생일");
        tdls[2] = new TDL();
        tdls[2].setContent("이전이전 생일");
        tdls[2].setDate(LocalDate.of(2018, 10, 31));
        List<TDL> tdlList = Arrays.asList(tdls);
        ModelAndView mav = new ModelAndView();
        mav.setViewName("index");
        mav.addObject("tdlList", tdlList);
        return mav;
    }

    @GetMapping("/create")
    public String initCreateForm(Model model) {
        TDL tdl = new TDL();
        tdl.getTodos().add(new ToDo());
        model.addAttribute(tdl); // 따로 지정안하면 이름은 클래스명으로 들어가는 것 같다
        return "createForm";
    }

    // 여기선 binding 검사를 할 필요가 없다. 아직 제출하는 게 아니라 입력칸을 늘리기 위한 로직이기 때문에
    @PostMapping(value = "/create", params = "add")
    public String addCreateForm(@ModelAttribute TDL tdl) {
        // initCreateForm에서 전달한 TDL 객체째로 받는 방법은 없나? 즉, html에서 객체째로 던질 순 없나?
        // 객체째로 던질 순 없고 객체로 binding 받을 수는 있다 -> @ModelAttribute다
        tdl.getTodos().add(new ToDo("new"));
//        model.addAttribute("tdl", tdl); Model에 자동으로 추가
        return "createForm";
    }

    @PostMapping(value = "/create", params = "submit")
    public String processCreateForm(@ModelAttribute TDL tdl,
                                    BindingResult bindingResult,
                                    RedirectAttributes redirectAttributes) {
        List<ToDo> todos = tdl.getTodos();
        // VALIDATE
        // List에 null element가 존재한다 -> 이름이 없는 ToDo가 존재한다 -> 입력 form으로 돌려보내기
        for(ToDo todo : todos){
            if(todo == null){
                bindingResult.addError(new FieldError("tdl", "todos", "there is blank"));
                return "createForm";
            }
        }
        tdl.setDate(LocalDate.now());
        redirectAttributes.addFlashAttribute("tdl", tdl);
        return "redirect:/tdl";
    }

    @GetMapping("/tdl")
    public String showTdl(Model model) {
        TDL tdl = (TDL) model.asMap().get("tdl");
        // TODO repository.save(tdl)
        model.addAttribute("tdl", tdl);
        return "tdlDetails";
    }

    @GetMapping("/tdl/{date}")
    public String showTdl(@PathVariable LocalDate date) {
        // TODO 저장소에서 해당 날짜의 to-do-list 가져오기
        TDL tdl = new TDL();
        tdl.setDate(date);
        return "tdlDetails";
    }

}