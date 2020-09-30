package me.donghun.todolist;

import javax.persistence.Embeddable;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Embeddable
public class ToDo {

//    @Id @GeneratedValue
//    private Long Id;

    private String name;

//    public Long getId() {
//        return Id;
//    }
//
//    public void setId(Long id) {
//        Id = id;
//    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ToDo() {
    }

    public ToDo(String name) {
        this.name = name;
    }
}
