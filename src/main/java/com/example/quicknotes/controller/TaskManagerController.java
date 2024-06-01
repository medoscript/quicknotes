package com.example.quicknotes.controller;

import com.example.quicknotes.model.TaskManager;
import com.example.quicknotes.model.dto.TaskDto;
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
        //Аннотация @PostMapping("/save") указывает, что метод контроллера
        // должен обрабатывать POST-запросы, поступающие на URL /tasks/save.
        // Метод помеченный этой аннотацией, принимает объект данных из тела
        // запроса с помощью аннотации @RequestBody, сохраняет его
        // и возвращает в ответе.
    }

    @GetMapping("/{id}")
    public TaskManager getTaskById(@PathVariable Long id) {
        return service.getTaskById(id);
    }

    //    @PostMapping("/{id}/edit")
//    public String editSaveTask(@RequestBody TaskManager todo) {
//        if (service.saveOrUpdateTask(todo)) {
//            return "Edit Success";
//        } else {
//            return "Edit Failure";
//        }
//    }
//
    @DeleteMapping("/delete/{id}")
    public String deleteTask(@PathVariable Long id) {
        if (service.deleteTask(id)) {
            return "Delete Success";
        } else {
            return "Delete Failure";
        }
    }
}