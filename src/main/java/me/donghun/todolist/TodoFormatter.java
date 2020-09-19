package me.donghun.todolist;

import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.Locale;

@Component
//생성자에서 String type 매개변수를 하나 받는 경우 Formatter없어도 변환되네
public class TodoFormatter implements Formatter<ToDo> {
    @Override
    public ToDo parse(String s, Locale locale) throws ParseException {
        ToDo toDo = new ToDo(s);
        return toDo;
    }

    @Override
    public String print(ToDo toDo, Locale locale) {
        return toDo.getName();
    }
}
