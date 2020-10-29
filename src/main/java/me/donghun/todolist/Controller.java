package me.donghun.todolist;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@org.springframework.stereotype.Controller
public class Controller {

    @Autowired
    TDLRepository tdlRepository;

    // 인덱스 페이지에 이렇게 하면 데이터를 전송할 수 있을까? yes!
    @GetMapping("/")
    public ModelAndView index(){
        List<TDL> tdlList = tdlRepository.findAll();
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
        redirectAttributes.addFlashAttribute("tdl", tdl); // flash를 받는 하나의 handler를 만드는 게 나으려나?
        tdlRepository.save(tdl);
        return "redirect:/tdl/" + tdl.getId();
    }

    @GetMapping("/tdl/{id}")
    public ModelAndView showTdl(@PathVariable Long id, HttpSession session) {
        Optional<TDL> byId = tdlRepository.findById(id);
        TDL tdl = null;
        if(byId.isPresent())
            tdl = byId.get();
        else {} // TODO what to do?
        ModelAndView mav = new ModelAndView();
        mav.setViewName("tdlDetails");
        mav.addObject("tdl", tdl);
        session.setAttribute("tdl", tdl);
        return mav;
    }

    @PostMapping("/update")
    public String update(HttpSession session, @RequestParam("todo") List<Integer> checked) {
        TDL tdl = (TDL) session.getAttribute("tdl");
        for(ToDo toDo : tdl.getTodos()){ // 모두 체크를 푼 뒤
            toDo.setDone(false);
        }
        for(int i=0; i<checked.size(); i++) { // 제출했을 때 체크 되어있는 것만 닷 ㅣ체크
            tdl.getTodos().get(checked.get(i)).setDone(true);
        }
        tdlRepository.save(tdl);
        session.removeAttribute("tdl");
        return "redirect:/";
    }

}