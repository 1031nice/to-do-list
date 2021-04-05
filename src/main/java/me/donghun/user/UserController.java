package me.donghun.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;
    private final UserService userService;

    @PostMapping(value = "/login", params = "login")
    public String processLogin(User user, HttpSession session){
        Optional<User> byUserId = userRepository.findByUsername(user.getUsername());
        if(byUserId.isPresent()) {
            session.setAttribute("user", user);
            return "redirect:/to-do-lists";
        }
        else
            return "index";
    }

    @PostMapping(value = "/login", params = "signUp")
    public String signUpHandler(){
        return "redirect:/sign-up";
    }

    @GetMapping("/sign-up")
    public String initSignUp(Model model) {
        model.addAttribute(new SignUpForm());
        return "sign-up";
    }

    @PostMapping("/sign-up")
    public String processSignUp(@Valid SignUpForm signUpForm, Errors errors,
                                HttpSession session) {
        if (errors.hasErrors()) // validation 과정을 여기에 같이 묶어보자
            return "sign-up";
        User user = userService.processSignUp(signUpForm);
        if(user == null)
            return "sign-up";
        session.setAttribute("user", user);
        return "redirect:/to-do-lists";
    }

}
