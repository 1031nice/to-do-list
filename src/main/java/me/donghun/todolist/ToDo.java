package me.donghun.todolist;

public class ToDo {
    private String name;

    private int number;

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
