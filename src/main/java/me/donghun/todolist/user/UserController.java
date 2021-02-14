package me.donghun.todolist.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/login")
    public String processLogin(User user, HttpSession session){
        Optional<User> byUserId = userRepository.findByUserId(user.getUserId());
        if(byUserId.isPresent()) {
            session.setAttribute("user", user);
            return "redirect:/tdls";
        }
        else
            return "index";
    }

}
