package com.example.quicknotes.service.interfaces;

import com.example.quicknotes.domain.dto.TaskDto;

import java.util.List;

public interface TaskService {

    TaskDto save (TaskDto taskDto);
    List<TaskDto> getAll();
    TaskDto getById(Long id);

    void update(TaskDto taskDto);

    void deleteById(Long id);

    void deleteByTitle(String title);

    void restoreById(Long id);

    int getTotalQuantity();

    void attachImage(String imgUrl, String taskTitle);
}
