package me.donghun.todolist.tdl;

import lombok.*;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotBlank;

@Embeddable
@Getter @Setter @NoArgsConstructor
@EqualsAndHashCode(of = {"name", "done"})
@ToString(of = {"name", "done"})
public class ToDo {

    @NotBlank
    private String name;

    private boolean done = false;

    public ToDo(String name) {
        this.name = name;
    }

}
