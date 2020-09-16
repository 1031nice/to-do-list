package me.donghun.todolist;

import java.time.LocalDate;

// to-do-list
public class TDL {

    private Long id;

    private LocalDate date;

    private String content;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "TDL{" +
                "id=" + id +
                ", date=" + date +
                ", content='" + content + '\'' +
                '}';
    }
}
