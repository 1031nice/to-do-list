package me.donghun.todolist.tdl;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Objects;

@Embeddable
public class ToDo {

    @NotBlank
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ToDo toDo = (ToDo) o;
        return isDone == toDo.isDone &&
                Objects.equals(name, toDo.name);
    }

}