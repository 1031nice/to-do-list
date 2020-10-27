package me.donghun.todolist;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.util.Iterator;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
//@WebMvcTest({Controller.class, LocalDateFormatter.class})
@SpringBootTest
@AutoConfigureMockMvc
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
        assertThat(tdl.getDate()).isEqualTo(LocalDate.now().toString());
    }

    @Test
    public void showTdl() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/tdl/1"))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();
        ModelAndView mav = result.getModelAndView();
        assertThat(mav.getModel().get("tdl")).isNotNull();
        assertThat(mav.getViewName()).isEqualTo("tdlDetails");
    }

    @Test
    public void addCreateFormWithBlank() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/create")
                .param("add", "")
                .param("todos", ""))
                    .andExpect(status().isOk())
                    .andDo(print());
    }

    @Test
    public void processCreateFormWithBlank() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/create")
                .param("submit", "")
                .param("todos", ""))
                    .andExpect(status().is3xxRedirection())
                    .andDo(print());
    }

}