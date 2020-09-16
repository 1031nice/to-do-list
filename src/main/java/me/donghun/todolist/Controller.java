package me.donghun.todolist;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.time.LocalDateTime;

@org.springframework.stereotype.Controller
public class Controller {

    @GetMapping("/create")
    public String initCreateForm() {
        return "createForm";
    }

    @PostMapping("/create")
    public String processCreateForm(String content, RedirectAttributes redirectAttributes) {
        TDL tdl = new TDL();
        tdl.setContent(content);
        tdl.setDate(LocalDate.now());
        redirectAttributes.addFlashAttribute("tdl", tdl);
        return "redirect:/tdl";
    }

    @ResponseBody
    @GetMapping("/tdl")
    public String showTdl(Model model) {
        return model.asMap().get("tdl").toString();
    }

    @ResponseBody
    @GetMapping("/tdl/{year}/{month}/{day}")
    public String showTdl(Model model,
                          @PathVariable("year") int year,
                          @PathVariable("month") int month,
                          @PathVariable("day") int day) {
        // TODO 저장소에서 해당 날짜의 to-do-list 가져오기
        return new TDL().toString();
    }

}
