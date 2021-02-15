package me.donghun.todolist;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@org.springframework.stereotype.Controller
@RequestMapping("/to-do-lists")
public class ToDoListController {

    private final ToDoListRepository toDoListRepository;

    private static final String VIEWS_CREATE_OR_UPDATE_FORM = "createOrUpdateForm";

    public ToDoListController(ToDoListRepository tdlRepository) {
        this.toDoListRepository = tdlRepository;
    }

    @RequestMapping
    public ModelAndView getToDoLists(){
        List<ToDoList> toDoLists = toDoListRepository.findAll();
        return new ModelAndView("toDoLists", "toDoLists", toDoLists);
    }

    @GetMapping("/{id}")
    public String getToDoList(@PathVariable("id") ToDoList toDoList,
                              Model model) {
        model.addAttribute(toDoList);
        return "toDoListDetails";
    }

    @PostMapping("/update/{id}")
    public String updateToDoList(@ModelAttribute ToDoList toDoList,
                                 @RequestParam(required = false) List<Integer> checkedToDos) {
        // repository에서 가져오는 것보다는 hidden input으로 ToDoList 객체에 toDos 바인딩 받는 게 나을 것 같다
        List<ToDo> toDos = toDoList.getToDos();
        if(checkedToDos != null) {
            for (Integer checkedToDo : checkedToDos) {
                toDos.get(checkedToDo).setDone(true);
            }
        }
        toDoListRepository.save(toDoList);
        return "redirect:/to-do-lists/{id}";
    }

    @GetMapping("/create")
    public String createToDoForm(Model model) {
        ToDoList toDoList = new ToDoList();
        toDoList.getToDos().add(new ToDo("what to do"));
        model.addAttribute(toDoList); // 따로 지정안하면 이름은 클래스명으로 들어가는 것 같다
        return VIEWS_CREATE_OR_UPDATE_FORM;
    }

    @PostMapping(value = "/create", params = "add")
    public String addToDoForm(@ModelAttribute ToDoList toDoList) {
        toDoList.getToDos().add(new ToDo("what to do"));
//        model.addAttribute("tdl", tdl); Model에 자동으로 추가
        return VIEWS_CREATE_OR_UPDATE_FORM;
    }

    @PostMapping(value = "/create", params = "submit")
    public String submitToDoForm(@ModelAttribute ToDoList toDoList) {
        // binding 중에 에러 일어날 일 없을 것 같아서 BindingResult 없앴는데 항상 해주는 게 좋나?
        toDoListRepository.save(toDoList);
        return "redirect:/to-do-lists/" + toDoList.getId();
    }

}