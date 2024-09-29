package com.codewithtanmay.fullstack_backend.repo;

import com.codewithtanmay.fullstack_backend.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
}
