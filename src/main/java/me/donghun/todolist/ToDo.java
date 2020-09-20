package me.donghun.todolist;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

public class ToDo {

//    @NotBlank binding을 TDL로 받아서 ToDo에 이런거 붙여도 소용이 없나봐 custom을 만들어야하나?
//    @NotEmpty
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
