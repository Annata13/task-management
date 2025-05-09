package school.sorokin.task_management;

import java.time.LocalDateTime;

public record Task(
        Long id,
        Long createId,                 // id создавшего задачу
        Long assignedUserId,           // id исполнителя
        TaskStatus status,             // создан, в процессе, закончен
        LocalDateTime createDateTime,  // дата и время создания задач
        LocalDateTime deadlineDate,    // дата, до которой задача должна быть выполнена
        TaskPriority priority          // низкий, средний, высокий
) {
}
