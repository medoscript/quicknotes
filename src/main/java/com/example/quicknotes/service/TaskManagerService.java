package com.example.quicknotes.service;

import com.example.quicknotes.domain.entity.TaskManager;
import com.example.quicknotes.domain.dto.TaskDto;
import com.example.quicknotes.exception_handling.exceptions.ProductNotFoundException;
import com.example.quicknotes.repo.TaskManagerRepository;
import com.example.quicknotes.service.interfaces.TaskService;
import com.example.quicknotes.service.mapping.TaskMappingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskManagerService implements TaskService {


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

	@Override
	public TaskDto save(TaskDto taskDto) {
		return null;
	}

	@Override
	public List<TaskDto> getAll() {
		return null;
	}

	@Override
	public TaskDto getById(Long id) {
		return null;
	}

	@Override
	public void update(TaskDto taskDto) {

	}

	@Override
	public void deleteById(Long id) {

	}

	@Override
	public void deleteByTitle(String title) {

	}

	@Override
	public void restoreById(Long id) {

	}

	@Override
	public int getTotalQuantity() {
		return 0;
	}

	@Override
	@Transactional
	public void attachImage(String imgUrl, String productTitle) {
		TaskManager taskManager = repository.findByTitle(productTitle);

		if(taskManager == null){
			throw new ProductNotFoundException(productTitle);
		}

		//Lifecycle states: transient, managed, removed, detached

		taskManager.setImage(imgUrl);
		repository.save(taskManager);
	}
}
