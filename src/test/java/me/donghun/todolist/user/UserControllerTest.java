package me.donghun.todolist.user;

import me.donghun.user.User;
import me.donghun.user.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    UserRepository userRepository;

    @Test
    @DisplayName("로그인 성공")
    void loginSuccess() throws Exception {
        User user = new User();
        user.setUsername("1031nice");
        user.setPassword("1031");
        user = userRepository.save(user);
        Optional<User> byId = userRepository.findById(user.getId());
        User savedUser = byId.get();

        mockMvc.perform(post("/users/login")
                .param("username", savedUser.getUsername())
                .param("password", savedUser.getPassword())
                .param("login", "login"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/to-do-lists"))
                .andExpect(request().sessionAttribute("user", user))
                .andDo(print());
    }

    @Test
    @DisplayName("로그인 실패")
    void loginFailure() throws Exception {
        mockMvc.perform(post("/users/login")
                .param("username", "wrongId")
                .param("password", "wrongPassword")
                .param("login", "login"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"))
                .andDo(print());
    }

    @Test
    @DisplayName("회원가입 요청")
    void handleSignUpRequest() throws Exception {
        mockMvc.perform(post("/users/login")
                .param("signUp", "signUp"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/sign-up"))
                .andDo(print());
    }

    @Test
    @DisplayName("회원가입 뷰")
    void initSignUp() throws Exception {
        mockMvc.perform(post("/users/sign-up"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("signUpForm"))
                .andExpect(view().name("sign-up"))
                .andDo(print());
    }

    @Test
    @DisplayName("회원가입 성공")
    void signUpSuccess() throws Exception {
        User user = new User();
        user.setUsername("donghun");
        user.setPassword("1234");

        mockMvc.perform(post("/users/sign-up")
                .param("username", "donghun")
                .param("password", "12341234")
                .param("email", "donghun@gmail.com"))
                .andExpect(status().is3xxRedirection()) // 세션에 save된 객체가 들어있는지 어떻게 테스트하지?
                .andExpect(redirectedUrl("/to-do-lists"))
                .andDo(print());

        Optional<User> donghun = userRepository.findByUsername("donghun");
        assertThat(donghun).isNotEmpty();
    }

    @Test
    @DisplayName("회원가입 실패 - 중복된 아이디")
    void signUpFailure() throws Exception {
        User user = new User();
        user.setUsername("donghun2"); // 왜 얘만 실행시킬 땐 통과하다가 전체 다 테스트하면 충돌이 일어나지? donghun을 이전에 저장했어도 이 테스트는 독립적이니까 상관없지 않나 테스트 전체에 롤백 붙여도 안되네
        user.setPassword("1234");
        userRepository.save(user);

        mockMvc.perform(post("/users/sign-up")
                .param("username", "donghun2")
                .param("password", "12341234")
                .param("email", "donghun@gmail.com"))
                .andExpect(view().name("sign-up"))
                .andDo(print());
    }
}