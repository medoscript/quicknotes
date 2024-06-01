package com.example.quicknotes.domain.dto;

import java.util.Date;
import java.util.Objects;

public class TaskDto {

    private Long id;
    private String title;
    private Date date;
    private String status;

    public TaskDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TaskDto taskDto = (TaskDto) o;
        return Objects.equals(id, taskDto.id) && Objects.equals(title, taskDto.title) && Objects.equals(date, taskDto.date) && Objects.equals(status, taskDto.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, date, status);
    }

    @Override
    public String toString() {
        return String.format("ID - %d, Title - %s, status - %s" , id, title, status);
    }
}
