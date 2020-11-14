package me.donghun.todolist;

import me.donghun.todolist.tdl.TDL;
import me.donghun.todolist.tdl.TDLRepository;
import me.donghun.todolist.tdl.ToDo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.servlet.ModelAndView;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
//@WebMvcTest({Controller.class, LocalDateFormatter.class})
@SpringBootTest
@AutoConfigureMockMvc
public class TDLControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    TDLRepository tdlRepository;

    @Test
    public void processCreateForm() throws Exception {
        TDL tdl = new TDL();
        tdl.getTodos().add(new ToDo("coding"));
        tdl.getTodos().add(new ToDo("running"));

        mockMvc.perform(post("/tdls/create")
                .param("todos", tdl.getTodos().get(0).getName())
                .param("todos", tdl.getTodos().get(1).getName())
                .param("submit", "submit"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("/tdls/?"))
                .andDo(print())
                .andReturn();

        TDL lastTdl = tdlRepository.findTopByOrderByIdDesc();
        assertThat(same(lastTdl, tdl)).isTrue();
    }

    private boolean same(TDL tdl1, TDL tdl2) {
        List<ToDo> todos1 = tdl1.getTodos();
        List<ToDo> todos2 = tdl2.getTodos();
        return todos1.containsAll(todos2);
    }

    @Test
    public void processCreateFormWithBlank() throws Exception {
        mockMvc.perform(post("/tdls/create")
                .param("submit", "")
                .param("todos", "running")
                .param("todos", "")
                .param("todos", "coding"))
                .andExpect(model().hasErrors())
                .andExpect(view().name("createOrUpdateForm"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Autowired
    EntityManager entityManager;

    @Test
    public void getTdl() throws Exception {
        TDL newTdl = new TDL();
        newTdl.setDate(LocalDate.now());
        newTdl.getTodos().add(new ToDo("coding"));
        newTdl.getTodos().add(new ToDo("running"));
        newTdl.getTodos().add(new ToDo("reading"));
        TDL savedTdl = tdlRepository.saveAndFlush(newTdl);
        entityManager.clear();
//        assertThat(entityManager.contains(savedTdl)); // 클리어 했는데 왜 성공하냐
        assertThat(savedTdl).isEqualTo(newTdl);
        TDL tdl2 = tdlRepository.findById(savedTdl.getId()).orElse(new TDL());
//        assertThat(savedTdl).isEqualTo(tdl2); // 왜실패 .. persistent에 있는거 그대로 가져왔는데 왜!
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/tdls/" + savedTdl.getId()))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();
        ModelAndView mav = result.getModelAndView();
        TDL tdl = (TDL) mav.getModel().get("TDL");
        assertThat(tdl).isNotNull();
        TDL tdl3 = tdlRepository.findById(savedTdl.getId()).orElse(new TDL());
        assertThat(savedTdl).isNotEqualTo(tdl3);
//        assertThat(tdl).isNotEqualTo(tdl2);
        assertThat(savedTdl).isNotEqualTo(tdl);
        checkSameTdl(savedTdl, tdl);
        assertThat(mav.getViewName()).isEqualTo("tdlDetails");
    }

    private void checkSameTdl(TDL tdl1, TDL tdl2) {
        assertThat(tdl1.getId()).isEqualTo(tdl2.getId());
        assertThat(tdl1.getDate()).isEqualTo(tdl2.getDate());
        List<ToDo> tdl1Todos = tdl1.getTodos();
        List<ToDo> tdl2Todos = tdl2.getTodos();
        assertThat(tdl1Todos.size()).isEqualTo(tdl2Todos.size());
        for(int i = 0; i< tdl1Todos.size(); i++){
            assertThat(tdl1Todos.get(i)).isEqualTo(tdl2Todos.get(i));
        }
    }

    @Test
    public void updateTdl() throws Exception {
        TDL tdl = makeTdl();

        // 0번째 ToDo와 2번째 ToDo가 체크되었을 때
        MvcResult result = mockMvc.perform(post("/tdls/update/"+tdl.getId())
                .param("checkedToDos", "0")
                .param("checkedToDos", "2")
                .param("todos", "coding")
                .param("todos", "running")
                .param("todos", "reading"))
                .andExpect(flash().attributeExists("TDL"))
                .andExpect(status().is3xxRedirection())
                .andDo(print())
                .andReturn();
        TDL returnTdl = (TDL) result.getFlashMap().get("TDL");
        List<ToDo> returnTdlTodos = returnTdl.getTodos();

        // 0번째 ToDo와 2번째 ToDo만 isDone이 true여야 한다
        assertThat(returnTdlTodos.get(0).isDone()).isTrue();
        assertThat(returnTdlTodos.get(1).isDone()).isFalse();
        assertThat(returnTdlTodos.get(2).isDone()).isTrue();
    }

    private TDL makeTdl() {
        TDL tdl = new TDL();
        tdl.setId(1L);
        ToDo toDo1 = new ToDo("coding");
        ToDo toDo2 = new ToDo("running");
        ToDo toDo3 = new ToDo("reading");
        List<ToDo> todos = tdl.getTodos();
        todos.add(toDo1);
        todos.add(toDo2);
        todos.add(toDo3);
        return tdl;
    }
}