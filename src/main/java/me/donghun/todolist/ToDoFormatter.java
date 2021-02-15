package me.donghun.todolist;

import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.Locale;

@Component
public class ToDoFormatter implements Formatter<ToDo> {
    @Override
    public ToDo parse(String s, Locale locale) throws ParseException {
        return new ToDo(s);
    }

    @Override
    public String print(ToDo toDo, Locale locale) {
        return toDo.getName();
    }
}
