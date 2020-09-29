package me.donghun.todolist;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class ToDo {

    @Id @GeneratedValue
    private Long Id;
//    @NotBlank binding을 TDL로 받아서 ToDo에 이런거 붙여도 소용이 없나봐 custom을 만들어야하나?
//    @NotEmpty
    private String name;

    private int number;

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

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
