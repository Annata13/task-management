package school.sorokin.task_management;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TaskService {
    private final TaskRepository repository;

    public TaskService(TaskRepository taskRepository) {
        this.repository = taskRepository;
    }

    private Task toDomainTask(TaskEntity taskEntity) {
        return new Task(
                taskEntity.getId(),
                taskEntity.getCreateId(),
                taskEntity.getAssignedUserId(),
                taskEntity.getStatus(),
                taskEntity.getCreateDateTime(),
                taskEntity.getDeadlineDate(),
                taskEntity.getPriority()
        );
    }

    public Task getTaskById(Long id) {
        TaskEntity find = repository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Not found reservation by id = " + id));
        return toDomainTask(find);
    }

    public List<Task> getAllTasks() {
        List<TaskEntity> allEntity = repository.findAll();
        return allEntity.stream()
                .map(this::toDomainTask)
                .toList();
    }

    public Task createdTask(Task taskToCreate) {
        if (taskToCreate.id() != null) {
            throw new IllegalArgumentException("Id should be empty");
        }
        if (taskToCreate.status() != null) {
            throw new IllegalArgumentException("Id status be empty");
        }
        var newTask = new TaskEntity(
                null,
                taskToCreate.createId(),
                taskToCreate.assignedUserId(),
                TaskStatus.CREATED,
                taskToCreate.createDateTime(),
                taskToCreate.deadlineDate(),
                TaskPriority.MEDIUM
        );
        repository.save(newTask);
        return toDomainTask(newTask);
    }

    public Task updateTask(Long id, Task taskToUpdate) {
        TaskEntity find = repository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Not found reservation by id = " + id));

        if (find.getStatus() == TaskStatus.DONE) {
            throw new IllegalStateException("Cannot modify task: status= " + find.getStatus());
        }
        var updateTask = new TaskEntity(
                find.getId(),
                taskToUpdate.createId(),
                taskToUpdate.assignedUserId(),
                TaskStatus.IN_PROGRESS,
                taskToUpdate.createDateTime(),
                taskToUpdate.deadlineDate(),
                taskToUpdate.priority()
        );

        return toDomainTask(updateTask);
    }

    public void deleteTask(Long id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Not found reservation by id = " + id);
        }
        repository.deleteById(id);
    }

    public Task updateStatusTask(Long id) {
        var taskEntity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Not found reservation by id = " + id));

        if (taskEntity.getAssignedUserId() == null) {
            throw new EntityNotFoundException("Not found assigned user by id" + taskEntity.getStatus());
        }
        if (repository.countActiveTasksByUser(taskEntity.getAssignedUserId()) > 4) {
            throw new IllegalStateException("Maximum active tasks limit (5) exceeded for user"
            );
        }

        taskEntity.setStatus(TaskStatus.IN_PROGRESS);
        repository.save(taskEntity);
        return toDomainTask(taskEntity);
    }
}