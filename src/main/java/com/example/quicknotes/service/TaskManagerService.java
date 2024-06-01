package com.example.quicknotes.service;

import com.example.quicknotes.model.TaskManager;
import com.example.quicknotes.model.dto.TaskDto;
import com.example.quicknotes.repo.TaskManagerRepository;
import com.example.quicknotes.service.mapping.TaskMappingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskManagerService {


	private TaskManagerRepository repository;
	private TaskMappingService mappingService;

@Autowired
	public TaskManagerService(TaskManagerRepository repository, TaskMappingService mappingService) {
		this.repository = repository;
		this.mappingService = mappingService;
	}

	public List<TaskDto> getAllTasks() {
		return repository.findAll()
				.stream()
				.map(x -> mappingService.mapTaskToDto(x))
				.collect(Collectors.toList());
	}

	public TaskManager getTaskById(Long id) {
		return repository.findById(id).orElse(null);
	}

	public TaskDto updateTask(TaskDto taskDto) {
		Long id = taskDto.getId();
		if (id == null || id < 1) {
			throw new RuntimeException("Task not found");
		}
		TaskManager updateTask = repository.findById(taskDto.getId()).orElseThrow(() ->
				new RuntimeException("Task in repository not found"));

		updateTask.setTitle(taskDto.getTitle());
		updateTask.setDate(taskDto.getDate());
		updateTask.setStatus(taskDto.getStatus());

		repository.save(updateTask);
		return taskDto;
	}

	@Transactional
	public void updateStatus(Long id) {
		TaskManager todo = getTaskById(id);
		if (todo != null) {
			todo.setStatus("Completed");
			repository.save(todo);
		}
	}


	public TaskDto saveTask(TaskDto taskDto) {
		TaskManager task = mappingService.mapDtoToTask(taskDto);
		try {
			repository.save(task);
		} catch (Exception e) {
			throw new RuntimeException("Task not saved", e);
		}
		return mappingService.mapTaskToDto(task);
	}

	public boolean deleteTask(Long id) {
		repository.deleteById(id);
		return true; // Operation is considered successful if no exceptions occurred
	}
}
