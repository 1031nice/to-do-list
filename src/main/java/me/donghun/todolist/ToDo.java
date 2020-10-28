package me.donghun.todolist;

import javax.persistence.*;

@Embeddable
public class ToDo {

    private String name;

    private boolean isDone = false;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }

    public ToDo() {
    }

    public ToDo(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "ToDo{" +
                "name='" + name + '\'' +
                ", isDone=" + isDone +
                '}';
    }
}
