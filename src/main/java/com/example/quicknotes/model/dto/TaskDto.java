package com.example.quicknotes.model.dto;

import java.util.Date;
import java.util.Objects;

public class TaskDto {

    private Long taskId;
    private String taskTitle;
    private Date dateOfTask;
    private String statusOfTask;

    public TaskDto() {
    }

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public String getTaskTitle() {
        return taskTitle;
    }

    public void setTaskTitle(String taskTitle) {
        this.taskTitle = taskTitle;
    }

    public Date getDateOfTask() {
        return dateOfTask;
    }

    public void setDateOfTask(Date dateOfTask) {
        this.dateOfTask = dateOfTask;
    }

    public String getStatusOfTask() {
        return statusOfTask;
    }

    public void setStatusOfTask(String statusOfTask) {
        this.statusOfTask = statusOfTask;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TaskDto taskDto = (TaskDto) o;
        return Objects.equals(taskId, taskDto.taskId) && Objects.equals(taskTitle, taskDto.taskTitle) && Objects.equals(dateOfTask, taskDto.dateOfTask) && Objects.equals(statusOfTask, taskDto.statusOfTask);
    }

    @Override
    public int hashCode() {
        return Objects.hash(taskId, taskTitle, dateOfTask, statusOfTask);
    }

    @Override
    public String toString() {
        return String.format("ID - %d, Title - %s, status - %s" , taskId, taskTitle, statusOfTask);
    }
}
