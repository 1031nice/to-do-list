package me.donghun.todolist;

import org.hibernate.Session;
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

    @Override
    public void run(ApplicationArguments args) throws Exception {
        ToDo toDo = new ToDo();
        entityManager.persist(toDo);
        TDL tdl = new TDL();
        tdl.getTodos().add(toDo);
        entityManager.persist(tdl);

        // hibernate 핵심 클래스
//        Session session = entityManager.unwrap(Session.class);
//        session.save(tdl);
    }
}
