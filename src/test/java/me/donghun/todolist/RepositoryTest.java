package me.donghun.todolist;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@DataJpaTest // slicing test & h2 의존성이 있는 경우 h2를 test db로 사용
public class RepositoryTest {

    @Autowired
    TDLRepository repository;

    @Test
    public void save() {
        TDL tdl = new TDL();
        ToDo[] toDos = new ToDo[3];
        toDos[0] = new ToDo();
        toDos[0].setName("1. running");
        toDos[1] = new ToDo();
        toDos[1].setName("2. coding");
        toDos[2] = new ToDo();
        toDos[2].setName("3. reading");
        List<ToDo> list = Arrays.asList(toDos);
        tdl.setTodos(list);

        repository.save(tdl);
    }

    @Test
    public void lazyLoading() {
        Optional<TDL> tdl = repository.findById(1L);
    }

}
