package school.sorokin.task_management.tasks;

import org.springframework.stereotype.Component;

@Component
public class TaskMapper {
    public Task toDomain(TaskEntity taskEntity) {
        return new Task(
                taskEntity.getId(),
                taskEntity.getCreateId(),
                taskEntity.getAssignedUserId(),
                taskEntity.getStatus(),
                taskEntity.getCreateDateTime(),
                taskEntity.getDeadlineDate(),
                taskEntity.getPriority(),
                taskEntity.getDoneDateTime()
        );
    }

    public TaskEntity toEntity(Task task) {
        return new TaskEntity(
                task.id(),
                task.createId(),
                task.assignedUserId(),
                task.status(),
                task.createDateTime(),
                task.deadlineDate(),
                task.priority(),
                task.doneDateTime()
        );
    }
}