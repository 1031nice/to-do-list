package me.donghun.todolist.user;

import me.donghun.todolist.tdl.TDL;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity(name = "users")
public class User {
    @Id
    @GeneratedValue
    private Long id;

    private String userId;
    private String password;
    private Integer successInARow;
    @OneToMany
    private List<TDL> tdls = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public Integer getSuccessInARow() {
        return successInARow;
    }
    public void setSuccessInARow(Integer successInARow) {
        this.successInARow = successInARow;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(userId, user.userId) &&
                Objects.equals(password, user.password);
    }
}
