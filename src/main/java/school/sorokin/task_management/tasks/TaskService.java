package school.sorokin.task_management.tasks;

import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class TaskService {
    public static final Logger log = LoggerFactory.getLogger(TaskService.class);
    private final TaskRepository repository;
    private final TaskMapper mapper;

    public TaskService(TaskRepository taskRepository, TaskMapper mapper) {
        this.repository = taskRepository;
        this.mapper = mapper;
    }

    public Task getTaskById(Long id) {
        log.info("Fetching task by ID: {}", id);
        TaskEntity find = repository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Not found reservation by id = " + id));
        return mapper.toDomain(find);
    }

    public Task createdTask(Task taskToCreate) {
        log.info("Creating task: {}", taskToCreate);
        if (taskToCreate.status() != null) {
            throw new IllegalArgumentException("Id status be empty");
        }
        if (!taskToCreate.deadlineDate().isAfter(taskToCreate.createDateTime())) {
            throw new IllegalArgumentException("The start date must be earlier than the end date.");
        }
        TaskEntity newTask = mapper.toEntity(taskToCreate);
        newTask.setStatus(TaskStatus.CREATED);
        repository.save(newTask);
        return mapper.toDomain(newTask);
    }

    public Task updateTask(Long id, Task taskToUpdate) {
        log.info("Updating task with ID: {} -> {}", id, taskToUpdate);
        TaskEntity find = repository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Not found reservation by id = " + id));

        if (find.getStatus() == TaskStatus.DONE && taskToUpdate.status() != TaskStatus.IN_PROGRESS) {
            throw new IllegalStateException("Cannot modify task: status= " + find.getStatus());
        }
        if (!taskToUpdate.deadlineDate().isAfter(taskToUpdate.createDateTime())) {
            throw new IllegalArgumentException("Start date must be earlier than end date");
        }
        TaskEntity newTask = mapper.toEntity(taskToUpdate);
        newTask.setStatus(TaskStatus.CREATED);
        repository.save(newTask);
        return mapper.toDomain(newTask);
    }

    public void deleteTask(Long id) {
        log.info("Deleting task with ID: {}", id);
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Not found reservation by id = " + id);
        }
        repository.deleteById(id);
    }

    public Task updateStatusTask(Long id) {
        log.info("Updating status to IN_PROGRESS for task ID: {}", id);
        var taskEntity = repository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Not found reservation by id = " + id));

        if (taskEntity.getAssignedUserId() == null) {
            throw new EntityNotFoundException("Not found assigned user by id" + taskEntity.getStatus());
        }
        if (repository.countActiveTasksByUser(taskEntity.getAssignedUserId()) > 4) {
            throw new IllegalStateException("Maximum active tasks limit (5) exceeded for user");
        }

        taskEntity.setStatus(TaskStatus.IN_PROGRESS);
        repository.save(taskEntity);
        return mapper.toDomain(taskEntity);
    }

    public Task updateStatusDoneTask(Long id) {
        log.info("Updating status to DONE for task ID: {}", id);
        var taskEntity = repository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Not found reservation by id = " + id));

        if (taskEntity.getAssignedUserId() == null) {
            throw new IllegalArgumentException("No date has been set before which the task must be completed.");
        }

        taskEntity.setStatus(TaskStatus.DONE);
        taskEntity.setDoneDateTime(LocalDateTime.now());
        repository.save(taskEntity);
        return mapper.toDomain(taskEntity);
    }

    public List<Task> searchAllByFilter(TaskSearchFilter filter) {
        log.info("Searching tasks with filter: {}", filter);
        int pageSize = filter.pageSize() != null ? filter.pageSize() : 10;
        int pageNumber = filter.pageNumber() != null ? filter.pageNumber() : 0;
        var pageable = Pageable.ofSize(pageSize).withPage(pageNumber);
        List<TaskEntity> allEntities = repository.searchAllByFilter(
                filter.createId(),
                filter.assignedUserId(),
                filter.status(),
                filter.priority(),
                pageable);
        return allEntities.stream().map(mapper::toDomain).toList();
    }
}