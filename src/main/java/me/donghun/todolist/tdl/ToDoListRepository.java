package me.donghun.todolist.tdl;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ToDoListRepository extends JpaRepository<ToDoList, Long> {
    ToDoList findTopByOrderByIdDesc();
}