package com.example.quicknotes.domain.entity;

import java.util.Date;
import java.util.Objects;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import jakarta.annotation.Nonnull;

@Entity
@Table (name="task")
public class TaskManager {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
//	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "task_generator")
//	@SequenceGenerator(name="task_generator", sequenceName = "task_seq", allocationSize=1)
	private Long id;
	
	@Column(name = "title")
	@NotNull(message = "Title cannot be null")
	private String title;
	
	@Column(name = "date")
	@Nonnull
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date date;

	@Column(name = "status")
	@Nonnull
	private String status;

	@Column(name = "image")
	private String image;

	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
//
	public TaskManager() {
		
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
		TaskManager that = (TaskManager) o;
		return Objects.equals(id, that.id) && Objects.equals(title, that.title) && Objects.equals(date, that.date) && Objects.equals(status, that.status);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, title, date, status);
	}
}
