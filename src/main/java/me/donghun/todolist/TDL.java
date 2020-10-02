package me.donghun.todolist;

import org.hibernate.annotations.Fetch;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
public class TDL implements Serializable {

    @Id @GeneratedValue
    private Long id;

    private LocalDate date = LocalDate.now();

    private String content;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<ToDo> todos = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<ToDo> getTodos() {
        return todos;
    }

    public void setTodos(List<ToDo> todos) {
        this.todos = todos;
    }

    @Override
    public String toString() {
        return "TDL{" +
                "id=" + id +
                ", date=" + date +
                ", content='" + content + '\'' +
                ", todos=" + todos +
                '}';
    }
}