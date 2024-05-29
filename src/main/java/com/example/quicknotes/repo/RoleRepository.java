package com.example.quicknotes.repo;

import org.springframework.context.annotation.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> { // унаследование от JpaRepository

    Role findByTitle(String title); // поиск по названию
}
