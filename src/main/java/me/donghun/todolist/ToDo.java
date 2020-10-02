package me.donghun.todolist;

import javax.persistence.*;

@Embeddable
public class ToDo {

    private String name;

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
