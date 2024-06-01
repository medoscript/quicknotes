package com.example.quicknotes.controller;

import com.example.quicknotes.domain.entity.TaskManager;
import com.example.quicknotes.domain.dto.TaskDto;
import com.example.quicknotes.service.TaskManagerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
// @RestController указывает, что это контроллер,
// и его методы будут возвращать данные непосредственно в HTTP-ответах.
@RequestMapping("/tasks")
//@RequestMapping("/tasks") указывает базовый путь для всех методов в этом контроллере.
public class TaskManagerController {


    private TaskManagerService service;

    public TaskManagerController(TaskManagerService service) {
        this.service = service;
    }

    @GetMapping("/all")
    public List<TaskDto> viewAllTasks() {
        return service.getAllTasks();
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<TaskDto> updateTask(@PathVariable Long id, @RequestBody TaskDto taskDto) {
        try {
            taskDto.setId(id); // Устанавливаем id из URL в DTO
            TaskDto updatedTask = service.updateTask(taskDto);
            return ResponseEntity.ok(updatedTask);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }


    @PostMapping("/save")
    public TaskDto saveTask(@RequestBody TaskDto taskDto) {
        return service.saveTask(taskDto);

    }

    @GetMapping("/{id}")
    public TaskManager getTaskById(@PathVariable Long id) {
        return service.getTaskById(id);
    }


    @DeleteMapping("/delete/{id}")
    public String deleteTask(@PathVariable Long id) {
        if (service.deleteTask(id)) {
            return "Delete Success";
        } else {
            return "Delete Failure";
        }
    }
}