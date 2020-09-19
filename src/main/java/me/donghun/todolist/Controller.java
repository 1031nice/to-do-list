package me.donghun.todolist;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@org.springframework.stereotype.Controller
public class Controller {

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
        tdl.getTodos().add(new ToDo("reading"));
        tdl.getTodos().add(new ToDo("coding"));
        tdl.getTodos().add(new ToDo("running"));
        model.addAttribute(tdl); // 따로 지정안하면 이름은 클래스명으로 들어가는 것 같다
        return "createForm";
    }

    @PostMapping(value = "/create", params = "add")
    public String addCreateForm(@ModelAttribute TDL tdl) {
        // initCreateForm에서 전달한 TDL 객체째로 받는 방법은 없나? 즉, html에서 객체째로 던질 순 없나?
        // 있다 그게 @ModelAttribute다
        tdl.getTodos().add(new ToDo("what to do"));
//        model.addAttribute("tdl", tdl); Model에 자동으로 추가
        return "createForm";
    }

//    @PostMapping(value = "/create", params = "submit")
//    public String processCreateForm(@RequestParam List<ToDo> todos, RedirectAttributes redirectAttributes) {
//        TDL tdl = new TDL();
//        tdl.setTodos(todos);
//        tdl.setDate(LocalDate.now());
//        redirectAttributes.addFlashAttribute("tdl", tdl);
//        return "redirect:/tdl";
//    }

//    @PostMapping(value = "/create", params = "submit")
//    public String processCreateForm(@RequestParam List<ToDo> todos, RedirectAttributes redirectAttributes) {
//        TDL tdl = new TDL();
//        tdl.setTodos(todos);
//        tdl.setDate(LocalDate.now());
//        redirectAttributes.addFlashAttribute("tdl", tdl);
//        return "redirect:/tdl";
//    }

    @PostMapping(value = "/create", params = "submit")
    public String processCreateForm(@ModelAttribute TDL tdl, RedirectAttributes redirectAttributes) {
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

    @ResponseBody
    @GetMapping("/tdl/{date}")
    public String showTdl(@PathVariable LocalDate date) {
        // TODO 저장소에서 해당 날짜의 to-do-list 가져오기
        TDL tdl = new TDL();
        tdl.setDate(date);
        return tdl.toString();
    }

    // Formatter 없이도 변환이 되네 생성자에서 String 매개변수를 받는 경우

    @ResponseBody
    @GetMapping("/test/{todo}")
    public String test(@PathVariable ToDo todo){
        return todo.getName();
    }

}