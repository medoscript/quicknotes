package com.example.quicknotes.repo;

import com.example.quicknotes.domain.entity.TaskManager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskManagerRepository extends JpaRepository<TaskManager, Long> {

    TaskManager findByTitle(String title);
//    TaskManager findTaskManagerByName(String title);
}
