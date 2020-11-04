package me.donghun.todolist.user;

import me.donghun.todolist.tdl.TDL;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "_user")
public class User {
    @Id
    @GeneratedValue
    private Long id;

    private String userId;
    private String password;
    private Integer successInARow;
    @OneToMany
    private List<TDL> tdls = new ArrayList<>();

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
}
