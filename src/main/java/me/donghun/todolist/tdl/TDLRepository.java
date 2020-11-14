package me.donghun.todolist.tdl;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TDLRepository extends JpaRepository<TDL, Long> {
    TDL findTopByOrderByIdDesc();
}