package com.example.quicknotes.repo;

import com.example.quicknotes.model.TaskManager;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskManagerRepository extends JpaRepository<TaskManager,Long> {

    TaskManager findTaskManagerById(Long id);
//    TaskManager findTaskManagerByName(String title);
}
