package me.donghun.todolist.tdl;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@org.springframework.stereotype.Controller
@RequestMapping("/tdls")
public class ToDoListController {

    private final ToDoListRepository tdlRepository;

    private static final String VIEWS_CREATE_OR_UPDATE_FORM = "createOrUpdateForm";

    public ToDoListController(ToDoListRepository tdlRepository) {
        this.tdlRepository = tdlRepository;
    }

    // 인덱스 페이지에 이렇게 하면 데이터를 전송할 수 있을까? yes!
    @RequestMapping
    public ModelAndView index(){
        List<ToDoList> tdlList = tdlRepository.findAll();
        ModelAndView mav = new ModelAndView();
        mav.setViewName("tdlList");
        mav.addObject("tdlList", tdlList);
        return mav;
    }

    @GetMapping("/create")
    public String createForm(Model model) {
        ToDoList tdl = (ToDoList) model.getAttribute("TDL");
        if(tdl == null) {
            tdl = new ToDoList();
            tdl.getTodos().add(new ToDo("what to do"));
        }
        model.addAttribute(tdl); // 따로 지정안하면 이름은 클래스명으로 들어가는 것 같다
        return VIEWS_CREATE_OR_UPDATE_FORM;
    }

    @PostMapping(value = "/create", params = "add")
    public String addCreateForm(@ModelAttribute ToDoList tdl) {
        tdl.getTodos().add(new ToDo("what to do"));
//        model.addAttribute("tdl", tdl); Model에 자동으로 추가
        return VIEWS_CREATE_OR_UPDATE_FORM;
    }

    @PostMapping(value = "/create", params = "submit")
    public String processCreateForm(@ModelAttribute ToDoList tdl,
                                    BindingResult bindingResult) {
        List<ToDo> todos = tdl.getTodos();
        // VALIDATE
        for(ToDo todo : todos){
            if(todo == null){
                bindingResult.addError(new FieldError("tdl", "todos", "there is blank"));
                return VIEWS_CREATE_OR_UPDATE_FORM;
            }
        }
        if(bindingResult.hasErrors()){
            return VIEWS_CREATE_OR_UPDATE_FORM;
        }
        tdlRepository.save(tdl);
        return "redirect:/tdls/" + tdl.getId();
    }

    @GetMapping("/{id}")
    public String getTdl(@PathVariable Long id, Model model) {
        ToDoList tdl = (ToDoList) model.asMap().get("tdl");
        // model에 tdl 객체가 있으면 redirect 요청
        // model에 tdl 객체가 있으면 index 뷰에서 온 요청
        if(tdl == null) {
            Optional<ToDoList> byId = tdlRepository.findById(id);
            if (byId.isPresent())
                tdl = byId.get();
            else {
            } // TODO what to do?
        }
        model.addAttribute(tdl);
        return "tdlDetails";
    }

    @PostMapping("/update/{id}")
    public String updateTdl(@ModelAttribute ToDoList tdl,
                            @RequestParam(required = false) List<Integer> checkedToDos,
                            RedirectAttributes redirectAttributes) {
        // hidden input으로 tdl에 todos 바인딩 받는것보다 repo에서 id로 tdl 가져오는 게 나을 수도
        System.out.println(checkedToDos);
        List<ToDo> todos = tdl.getTodos();
        if(checkedToDos != null) {
            for (int i = 0; i < checkedToDos.size(); i++) {
                todos.get(checkedToDos.get(i)).setDone(true);
            }
        }
        tdlRepository.save(tdl);
        redirectAttributes.addFlashAttribute(tdl);
//        return "redirect:/tdls/" + tdl.getId();
        return "redirect:/tdls/{id}";
    }

}