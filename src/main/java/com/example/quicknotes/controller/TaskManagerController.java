package com.example.quicknotes.controller;

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

    @PutMapping("/{id}/update")
    public String updateTask(@RequestBody TaskDto dto) {
        service.updateTask(dto);
        return "Task update is a success";
        }


    @PostMapping("/{id}/Status_update")
    public ResponseEntity<String> updateTaskStatus(@PathVariable Long id, @RequestBody TaskDto statusUpdateDto) {
        try {
            service.updateStatus(id);
            return ResponseEntity.ok("Task status updated successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Task not found");
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while updating the task status");
//        }
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

//    @GetMapping("/{id}")
//    public TaskManager getTaskById(@PathVariable Long id) {
//        return service.getTaskById(id);
//    }
//
//    @PostMapping("/{id}/edit")
//    public String editSaveTask(@RequestBody TaskManager todo) {
//        if (service.saveOrUpdateTask(todo)) {
//            return "Edit Success";
//        } else {
//            return "Edit Failure";
//        }
//    }
//
//    @DeleteMapping("/delete{id}")
//    public String deleteTask(@PathVariable Long id) {
//        if (service.deleteTask(id)) {
//            return "Delete Success";
//        } else {
//            return "Delete Failure";
//        }

}