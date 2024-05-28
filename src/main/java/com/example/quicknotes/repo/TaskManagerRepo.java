package com.example.quicknotes.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.quicknotes.model.TaskManager;

@Repository
public interface TaskManagerRepo extends JpaRepository<TaskManager, Long>{

}
