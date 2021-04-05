package me.donghun;

import me.donghun.todolist.ToDoList;
import me.donghun.todolist.ToDoListRepository;
import me.donghun.todolist.ToDo;
import me.donghun.user.User;
import me.donghun.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Component
@Transactional
public class JpaRunner implements ApplicationRunner {

    // jpa 핵심 클래스
    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    ToDoListRepository tdlRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        ToDo toDo = new ToDo("running");
        ToDo toDo2 = new ToDo("coding");
        ToDo toDo3 = new ToDo("studying");
        toDo3.setDone(true);

        ToDoList tdl = new ToDoList();
        tdl.getToDos().add(toDo);
        tdl.getToDos().add(toDo2);
        tdl.getToDos().add(toDo3);

        tdlRepository.save(tdl);

        User user = new User();
        user.setUsername("qwer");
        user.setPassword("1234");
        userRepository.save(user);

//        entityManager.persist(tdl);
        // hibernate 핵심 클래스
//        Session session = entityManager.unwrap(Session.class);
//        session.save(tdl);
    }

}
