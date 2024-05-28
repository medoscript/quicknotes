package com.example.quicknotes.controller;

import com.example.quicknotes.model.TaskManager;
import com.example.quicknotes.service.TaskManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskManagerController {

    @Autowired
    private TaskManagerService service;

    @GetMapping
    public List<TaskManager> viewAllTasks() {
        return service.getAllTasks();
    }

    @PostMapping("/{id}/update")
    public String updateTaskStatus(@PathVariable Long id) {
        if (service.updateStatus(id)) {
            return "Update Success";
        } else {
            return "Update Failure";
        }
    }

    @PostMapping("/save")
    public String saveTask(@RequestBody TaskManager todo) {
        if (service.saveOrUpdateTask(todo)) {
            return "Save Success";
        } else {
            return "Save Failure";
        }
    }

    @GetMapping("/{id}")
    public TaskManager getTaskById(@PathVariable Long id) {
        return service.getTaskById(id);
    }

    @PostMapping("/{id}/edit")
    public String editSaveTask(@RequestBody TaskManager todo) {
        if (service.saveOrUpdateTask(todo)) {
            return "Edit Success";
        } else {
            return "Edit Failure";
        }
    }

    @DeleteMapping("/delete{id}")
    public String deleteTask(@PathVariable Long id) {
        if (service.deleteTask(id)) {
            return "Delete Success";
        } else {
            return "Delete Failure";
        }
    }
}