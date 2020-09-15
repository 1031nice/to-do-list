package me.donghun.todolist;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(Controller.class)
public class ControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    public void initCreateForm() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/create"))
                .andExpect(status().isOk())
                .andExpect(view().name("createForm"))
                .andDo(print());
    }

    @Test
    public void processCreateForm() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/create")
                .param("title", "mytitle")
                .param("content", "mycontent"))
                    .andExpect(status().isOk())
                    .andExpect(content().string("mytitle mycontent"))
                    .andDo(print());
    }

}