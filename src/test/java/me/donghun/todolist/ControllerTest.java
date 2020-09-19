package me.donghun.todolist;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.util.Iterator;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest({Controller.class, LocalDateFormatter.class})
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
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/create")
                .param("content", "mycontent"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/tdl"))
                .andDo(print())
                .andReturn();
        TDL tdl = (TDL)mvcResult.getFlashMap().get("tdl");
        assertThat(tdl.getContent()).isEqualTo("mycontent");
        assertThat(tdl.getDate()).isEqualTo(LocalDate.now().toString());
    }

    @Test
    public void showTdl() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/tdl/2020-09-16"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void formatter() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/test/test"))
                .andExpect(status().isOk())
                .andExpect(content().string("test"))
                .andDo(print());
    }

}