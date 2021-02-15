package me.donghun.user;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import me.donghun.todolist.ToDoList;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "users")
@Getter @Setter @EqualsAndHashCode(of = {"userId", "password"})
public class User {
    @Id
    @GeneratedValue
    private Long id;

    private String userId;
    private String password;
    private Integer successInARow;
    @OneToMany
    private List<ToDoList> toDoLists = new ArrayList<>();

}
