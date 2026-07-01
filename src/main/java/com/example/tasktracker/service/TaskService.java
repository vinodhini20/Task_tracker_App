package com.example.tasktracker.service;

import com.example.tasktracker.model.Project;
import com.example.tasktracker.model.Task;
import com.example.tasktracker.repository.ProjectRepository;
import com.example.tasktracker.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Transactional(readOnly = true)
    public Page<Task> findAllTasks(Task.Status status,
                                   Task.Priority priority,
                                   Pageable pageable) {
        return taskRepository.findByStatusAndPriority(status, priority, pageable);
    }

    @Transactional(readOnly = true)
    public Optional<Task> findTaskById(Long id) {
        return taskRepository.findById(id);
    }

    @Transactional
    public Task saveTask(Task task, Long projectId) {
        if (projectId != null) {
            Project project = projectRepository.findById(projectId)
                    .orElseThrow(() ->
                            new EntityNotFoundException("Project not found with id: " + projectId));

            task.setProject(project);
        }

        return taskRepository.save(task);
    }

    @Transactional
    public Task updateTask(Long id, Task taskDetails, Long projectId) {

        Task existingTask = taskRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException("Task not found with id: " + id));

        existingTask.setTitle(taskDetails.getTitle());
        existingTask.setDescription(taskDetails.getDescription());
        existingTask.setStatus(taskDetails.getStatus());
        existingTask.setPriority(taskDetails.getPriority());
        existingTask.setDueDate(taskDetails.getDueDate());

        if (projectId != null) {
            Project project = projectRepository.findById(projectId)
                    .orElseThrow(() ->
                            new EntityNotFoundException("Project not found with id: " + projectId));

            existingTask.setProject(project);
        } else {
            existingTask.setProject(null);
        }

        return taskRepository.save(existingTask);
    }

    @Transactional
    public void deleteTask(Long id) {
        if (!taskRepository.existsById(id)) {
            throw new EntityNotFoundException("Task not found with id: " + id);
        }

        taskRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<Project> findAllProjects() {
        return projectRepository.findAll();
    }
}