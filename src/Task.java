
import java.awt.Checkbox;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author KTECH
 */
public class Task {

    String task;
    Checkbox check;
    Task next, prev;
    int priority;

    private boolean isDone;

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }

    public Task(String task, int periority, Checkbox ch) {
        this.task = task;
        this.check = ch;
        this.next = null;
        this.prev = null;
        this.priority = periority;

    }
}
