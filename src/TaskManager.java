
import java.awt.Checkbox;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author KTECH
 */
public class TaskManager {

    public Task front = null;
    public Task rear = null;

    public void AddTask(String task, int priority, Checkbox check) {
        Task newTask = new Task(task, priority, check);
        if (front == null || priority < front.priority) {
            newTask.next = front;
            if (front != null) {
                front.prev = newTask;  
            }
            front = newTask;
        } else {
            Task current = front;
            while (current.next != null && priority >= current.next.priority) {
                current = current.next;
            }
            newTask.next = current.next;
            newTask.prev = current;  
            if (current.next != null) {
                current.next.prev = newTask;  
            }
            current.next = newTask;
        }
        check.addItemListener(e
                -> {
            if (e.getStateChange() == 1) {
                removeTask(check);

            }

        });
    }

    public void removeTask(Checkbox check) {
        Task t = front;

        if (front.check == check) {
            if (front.next != null) {
                front = front.next;
            } else {
                front = null;
            }

            check.setForeground(Color.GRAY);
            check.setEnabled(false);

            saveCompletedTasksToNewFile("completed_tasks.txt", t);
            saveTasksToFile("tasks.txt");
            return;
        }

        while (t != null) {
            if (t.next != null && t.next.task.equalsIgnoreCase(check.getLabel())) {
                Task nextTask = t.next;
                t.next = nextTask.next;
                if (nextTask.next != null) {
                    nextTask.next.prev = t;
                }

                check.setForeground(Color.GRAY);
                check.setEnabled(false);

                saveCompletedTasksToNewFile("completed_tasks.txt", nextTask);
                saveTasksToFile("tasks.txt");
                break;
            }
            t = t.next;
        }

    }

    private void saveCompletedTasksToNewFile(String filename, Task task) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename, true))) {
            
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

            String formattedDateTime = LocalDateTime.now().format(formatter);
            
            writer.println(task.task + ","+ formattedDateTime);
        } catch (IOException e) {
        }
    }

    public Task getFrontTask() {
        return front;
    }

    public void saveTasksToFile(String filename) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            Task current = front;
            while (current != null) {
                writer.println(current.task + "," + current.priority);
                current = current.next;
            }
        } catch (IOException e) {
        }
    }

    public void retrieveTasksFromFile(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    String task = parts[0];
                    int priority = Integer.parseInt(parts[1]);
                    Checkbox check = new Checkbox(task);
                    AddTask(task, priority, check);
                    check.setFont(new Font("Times New Roman", Font.PLAIN, 15));
                    check.setSize(new Dimension(30, 30));
                }
            }
        } catch (IOException e) {
        }
    }
    
    
}
