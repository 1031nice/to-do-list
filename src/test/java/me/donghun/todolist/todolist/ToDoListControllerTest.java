package me.donghun.todolist.todolist;

import me.donghun.todolist.ToDo;
import me.donghun.todolist.ToDoList;
import me.donghun.todolist.ToDoListRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.servlet.ModelAndView;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

//@WebMvcTest({Controller.class, LocalDateFormatter.class})
@SpringBootTest
@AutoConfigureMockMvc
class ToDoListControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ToDoListRepository toDoListRepository;

    @Test
    @DisplayName("ToDo 입력폼 정상 제출")
    void submitToDoForm() throws Exception {
        long before = toDoListRepository.count();

        mockMvc.perform(post("/to-do-lists/create")
                .param("toDos", "coding")
                .param("toDos", "running")
                .param("toDos", "reading")
                .param("toDos", "") // 빈칸을 예외로 보고 다시 입력하라고 하지 않고 그냥 없는셈치고 진행
                .param("submit", "submit"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("/to-do-lists/?"))
                .andDo(print())
                .andReturn();

        long after = toDoListRepository.count();

        // ToDoList 객체를 확인할 좋은 방법이 없을까. ToDo들 모두 잘 들어갔는지 보고싶은데
        assertThat(after).isSameAs(before + 1);
    }

    @Test
    @DisplayName("ToDo 입력폼 추가")
    void addToDoForm() throws Exception {
        MvcResult mvcResult = mockMvc.perform(post("/to-do-lists/create")
                .param("toDos", "what to do")
                .param("toDos", "what to do")
                .param("toDos", "what to do")
                .param("add", "add"))
                .andExpect(status().isOk())
                .andExpect(view().name("createOrUpdateForm"))
                .andDo(print())
                .andReturn();
        String[] split = mvcResult.getResponse().getContentAsString().split("input name=\"toDos\"");
        int numOfInputTag = split.length - 1;
        assertThat(numOfInputTag).isEqualTo(4);
    }

    @Test
    @DisplayName("ToDoList 가져오기")
    void getToDoList() throws Exception {
        ToDoList newToDoList = new ToDoList();
        newToDoList.setToDos(Arrays.asList(new ToDo("coding"), new ToDo("running"), new ToDo("reading")));
        ToDoList savedToDoList = toDoListRepository.save(newToDoList);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/to-do-lists/" + savedToDoList.getId()))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();
        ModelAndView mav = result.getModelAndView();
        ToDoList toDoList = (ToDoList) mav.getModel().get("toDoList");
        assertThat(mav.getViewName()).isEqualTo("toDoListDetails");
        assertThat(toDoList).isNotNull();
//        assertThat(toDoList.equals(savedToDoList)).isTrue();
        assertThat(checkSameTdl(toDoList, savedToDoList)).isTrue(); // PersistentBag의 equals는 뭔가 다른가보다
    }

    private boolean checkSameTdl(ToDoList toDoList1, ToDoList toDoList2) {
        if(! toDoList1.getDate().equals(toDoList2.getDate()))
            return false;
        List<ToDo> toDos1 = toDoList1.getToDos();
        List<ToDo> toDos2 = toDoList2.getToDos();
        if(toDos1.size() != toDos2.size())
            return false;
        for(int i = 0; i< toDos1.size(); i++){
            if(! toDos1.get(i).equals(toDos2.get(i)))
                return false;
        }
        return true;
    }


    @Test
    @DisplayName("ToDoLists 가져오기")
    void getToDoLists() throws Exception {
        ToDoList newToDoList = new ToDoList();
        newToDoList.setToDos(Arrays.asList(new ToDo("coding"), new ToDo("running"), new ToDo("reading")));
        ToDoList savedToDoList = toDoListRepository.save(newToDoList);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/to-do-lists/" + savedToDoList.getId()))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        ModelAndView mav = result.getModelAndView();
        ToDoList toDoList = (ToDoList) mav.getModel().get("toDoList");
        assertThat(mav.getViewName()).isEqualTo("toDoListDetails");
        assertThat(toDoList).isNotNull();
    }

    @Test
    @DisplayName("ToDoList 업데이트")
    void updateToDoList() throws Exception {
        ToDoList toDoList = new ToDoList();
        toDoList.setToDos(Arrays.asList(new ToDo("coding"), new ToDo("running"), new ToDo("reading")));
        toDoList = toDoListRepository.save(toDoList);

        // 0번째 ToDo와 2번째 ToDo가 체크되었을 때
        mockMvc.perform(post("/to-do-lists/update/" +toDoList.getId())
                .param("checkedToDos", "0")
                .param("checkedToDos", "2")
                .param("toDos", "coding")
                .param("toDos", "running")
                .param("toDos", "reading"))
                .andExpect(status().is3xxRedirection())
                .andDo(print())
                .andReturn();

        Optional<ToDoList> byId = toDoListRepository.findById(toDoList.getId());
        List<ToDo> toDos = byId.get().getToDos();
        // 0번째 ToDo와 2번째 ToDo만 isDone이 true여야 한다
        assertThat(toDos.get(0).isDone()).isTrue();
        assertThat(toDos.get(1).isDone()).isFalse();
        assertThat(toDos.get(2).isDone()).isTrue();
    }

}