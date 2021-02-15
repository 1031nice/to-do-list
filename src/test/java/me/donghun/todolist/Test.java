package me.donghun.todolist;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.time.LocalDate;
import java.util.Map;

@RunWith(SpringRunner.class)
@Import(ObjectMapper.class)
public class Test {

    @Autowired
    ObjectMapper objectMapper;

    @org.junit.Test
    public void multiValueMap() {
        ToDoList tdl = new ToDoList();
        tdl.setDate(LocalDate.now());
        ToDo toDo = new ToDo();
        toDo.setName("coding");

        Map<String, Object> map = objectMapper.convertValue(tdl, new TypeReference<Map<String, Object>>() {
        });

        MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
        params.setAll(map);

        params.forEach((k, v) -> {
            System.out.println(k);
            v.forEach(System.out::println);
        });
    }

}
