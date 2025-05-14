package school.sorokin.task_management.tasks;

public record TaskSearchFilter(Long createId,
                               Long assignedUserId,
                               TaskStatus status,
                               TaskPriority priority,
                               Integer pageSize,
                               Integer pageNumber
                             ) {
}
