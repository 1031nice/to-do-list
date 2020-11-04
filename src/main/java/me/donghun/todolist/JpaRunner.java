package me.donghun.todolist;

import me.donghun.todolist.tdl.TDL;
import me.donghun.todolist.tdl.TDLRepository;
import me.donghun.todolist.tdl.ToDo;
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
    TDLRepository repository;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        ToDo toDo = new ToDo("running");
        ToDo toDo2 = new ToDo("coding");
        ToDo toDo3 = new ToDo("studying");
        toDo3.setDone(true);

        TDL tdl = new TDL();
        tdl.getTodos().add(toDo);
        tdl.getTodos().add(toDo2);
        tdl.getTodos().add(toDo3);

        repository.save(tdl);

//        entityManager.persist(tdl);
        // hibernate 핵심 클래스
//        Session session = entityManager.unwrap(Session.class);
//        session.save(tdl);
    }

    class Account {

    }

}
