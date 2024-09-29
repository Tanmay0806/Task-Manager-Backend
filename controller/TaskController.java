package com.codewithtanmay.fullstack_backend.controller;

import com.codewithtanmay.fullstack_backend.exception.TaskNotFoundException;
import com.codewithtanmay.fullstack_backend.model.Task;
import com.codewithtanmay.fullstack_backend.repo.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("http://localhost:5178")
public class TaskController {

    @Autowired
    private TaskRepository taskRepository;
    @PostMapping("/task")
    Task newTask(@RequestBody Task newTask) {
        return taskRepository.save(newTask);
    }


    @GetMapping("/tasks")
    public Page<Task> getAllTasks(@PageableDefault(size = 5) Pageable pageable) {
        return taskRepository.findAll(pageable);
    }

    @GetMapping("/task/{id}")
    Task getTaskById(@PathVariable Long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));
    }
    @PutMapping("/task/{id}")
    Task updateTask(@RequestBody Task updatedTask, @PathVariable Long id) {
        return taskRepository.findById(id)
                .map(task -> {
                    task.setAssignedTo(updatedTask.getAssignedTo());
                    task.setStatus(updatedTask.getStatus());
                    task.setPriority(updatedTask.getPriority());
                    task.setDueDate(updatedTask.getDueDate());
                    task.setComments(updatedTask.getComments());
                    return taskRepository.save(task);
                }).orElseThrow(() -> new TaskNotFoundException(id));
    }
    @DeleteMapping("/task/{id}")
    String deleteTask(@PathVariable Long id) {
        if (!taskRepository.existsById(id)) {
            throw new TaskNotFoundException(id);
        }
        taskRepository.deleteById(id);
        return "Task with id " + id + " has been deleted successfully.";
    }
}
