package me.donghun.todolist;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.collection.internal.PersistentBag;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Entity
@Getter @Setter
public class ToDoList implements Serializable {

    @Id @GeneratedValue
    private Long id;

    private LocalDate date = LocalDate.now();

    @ElementCollection(fetch = FetchType.EAGER)
    @NotNull
    private List<ToDo> toDos = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ToDoList toDoList = (ToDoList) o;
        return Objects.equals(date, toDoList.date) && Objects.equals(toDos, toDoList.toDos);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date, toDos);
    }

    public static void main(String[] args) {
        ToDoList list1 = new ToDoList();
        list1.setToDos(Arrays.asList(new ToDo("hello"), new ToDo("can you hear me")));
        ToDoList list2 = new ToDoList();
        list2.setToDos(Arrays.asList(new ToDo("hello"), new ToDo("can you hear me")));
        ToDoList list3 = new ToDoList();
        list3.setToDos(Arrays.asList(new ToDo("hello"), new ToDo("can you hear me?")));
        System.out.println(list1.equals(list2));
        System.out.println(list2.equals(list3));
    }

}