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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
                .param("userId", savedUser.getUsername())
                .param("password", savedUser.getPassword()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/tdls"))
                .andExpect(request().sessionAttribute("user", user))
                .andDo(print());
    }

    @Test
    @DisplayName("로그인 실패")
    void loginFailure() throws Exception {
        mockMvc.perform(post("/users/login")
                .param("userId", "wrongId")
                .param("password", "wrongPassword"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"))
                .andDo(print());
    }
}