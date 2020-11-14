package me.donghun.todolist.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {

    private static final String VIEWS_CREATE_OR_UPDATE_FORM = "userCreateOrUpdateForm";

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/login")
    public String processLogin(User user, HttpSession session){
        List<User> all = userRepository.findAll();
        for(User dbUser : all){
            if(dbUser.equals(user)) {
                session.setAttribute("user", user);
                return "redirect:/tdls";
            }
        }
        return "index";
    }

}
