package com.example.quicknotes.service;

import com.example.quicknotes.model.TaskManager;
import com.example.quicknotes.repo.TaskManagerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskManagerService {

	@Autowired
	private TaskManagerRepo repo;

	public List<TaskManager> getAllTasks() {
		return repo.findAll();
	}

	public TaskManager getTaskById(Long id) {
		return repo.findById(id).orElse(null);
	}

	public boolean updateStatus(Long id) {
		TaskManager todo = getTaskById(id);
		if (todo != null) {
			todo.setStatus("Completed");
			repo.save(todo);
			return true;
		}
		return false;
	}

	public boolean saveOrUpdateTask(TaskManager todo) {
		TaskManager updatedTask = repo.save(todo);
		return updatedTask != null;
	}

	public boolean deleteTask(Long id) {
		repo.deleteById(id);
		return true; // Operation is considered successful if no exceptions occurred
	}
}
