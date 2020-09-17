package me.donghun.todolist;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
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
    @GetMapping("/tdl/{date}")
    public String showTdl(@PathVariable LocalDate date) {
        // TODO 저장소에서 해당 날짜의 to-do-list 가져오기
        TDL tdl = new TDL();
        tdl.setDate(date);
        return tdl.toString();
    }

}
