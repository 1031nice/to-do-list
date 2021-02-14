package me.donghun.todolist.user;

import lombok.Getter;
import lombok.Setter;
import me.donghun.todolist.tdl.ToDoList;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity(name = "users")
@Getter @Setter
public class User {
    @Id
    @GeneratedValue
    private Long id;

    private String userId;
    private String password;
    private Integer successInARow;
    @OneToMany
    private List<ToDoList> toDoLists = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(userId, user.userId) &&
                Objects.equals(password, user.password);
    }
}
