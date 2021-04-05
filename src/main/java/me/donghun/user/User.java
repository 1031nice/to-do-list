package me.donghun.user;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import me.donghun.todolist.ToDoList;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "users")
@Getter @Setter @EqualsAndHashCode(of = {"username", "password"})
public class User {
    @Id
    @GeneratedValue
    private Long id;
    @Column(unique = true)
    private String username;
    private String password;
    private Integer successInARow;
    @Column(unique = true)
    private String email;
    @OneToMany
    private List<ToDoList> toDoLists = new ArrayList<>();

}
