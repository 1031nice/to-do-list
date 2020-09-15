package me.donghun.todolist;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@org.springframework.stereotype.Controller
public class Controller {

    @GetMapping("/create")
    public String initCreateForm() {
        return "createForm";
    }

    @ResponseBody
    @PostMapping("/create")
    public String processCreateForm(String title, String content) {
        return title + " " + content;
    }


}
