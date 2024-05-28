package com.example.quicknotes.controller;

import com.example.quicknotes.model.TaskManager;
import com.example.quicknotes.service.TaskManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
public class TaskManagerController {

	@Autowired
	private TaskManagerService service;

	@GetMapping("/tasks")
	public String viewAllTasks(Model model, @ModelAttribute("message") String message) {
		model.addAttribute("list", service.getAllTasks());
		model.addAttribute("message", message);
		return "viewTasks";
	}

	@PostMapping("/tasks/{id}/update")
	public String updateTaskStatus(@PathVariable Long id, RedirectAttributes redirectAttributes) {
		if (service.updateStatus(id)) {
			redirectAttributes.addFlashAttribute("message", "Update Success");
		} else {
			redirectAttributes.addFlashAttribute("message", "Update Failure");
		}
		return "redirect:/tasks";
	}

	@GetMapping("/tasks/add")
	public String addTask(Model model) {
		model.addAttribute("todo", new TaskManager());
		return "addTask";
	}

	@PostMapping("/tasks/save")
	public String saveTask(@ModelAttribute("todo") TaskManager todo, RedirectAttributes redirectAttributes) {
		if (service.saveOrUpdateTask(todo)) {
			redirectAttributes.addFlashAttribute("message", "Save Success");
		} else {
			redirectAttributes.addFlashAttribute("message", "Save Failure");
		}
		return "redirect:/tasks";
	}

	@GetMapping("/tasks/{id}/edit")
	public String editTask(@PathVariable Long id, Model model) {
		model.addAttribute("todo", service.getTaskById(id));
		return "editTask";
	}

	@PostMapping("/tasks/{id}/edit/save")
	public String editSaveTask(@ModelAttribute("todo") TaskManager todo, RedirectAttributes redirectAttributes) {
		if (service.saveOrUpdateTask(todo)) {
			redirectAttributes.addFlashAttribute("message", "Edit Success");
			return "redirect:/tasks";
		} else {
			redirectAttributes.addFlashAttribute("message", "Edit Failure");
			return "redirect:/tasks/" + todo.getId() + "/edit";
		}
	}

	@PostMapping("/tasks/{id}/delete")
	public String deleteTask(@PathVariable Long id, RedirectAttributes redirectAttributes) {
		if (service.deleteTask(id)) {
			redirectAttributes.addFlashAttribute("message", "Delete Success");
		} else {
			redirectAttributes.addFlashAttribute("message", "Delete Failure");
		}
		return "redirect:/tasks";
	}
}
