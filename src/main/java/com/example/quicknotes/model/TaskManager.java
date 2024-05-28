package com.example.quicknotes.model;

import java.util.Date;
import java.util.Objects;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.annotation.Nonnull;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table (name="task")
public class TaskManager {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Nonnull
	private Long id;
	
	@Column
	@Nonnull
	private String title;
	
	@Column
	@Nonnull
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date date;

	@Column
	@Nonnull
	private String status;
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
