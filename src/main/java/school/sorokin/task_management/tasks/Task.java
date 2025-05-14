package school.sorokin.task_management.tasks;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;

import java.time.LocalDateTime;

public record Task(
        @Null
        Long id,
        @NotNull
        Long createId,                 // id создавшего задачу
        @NotNull
        Long assignedUserId,           // id исполнителя
        TaskStatus status,             // создан, в процессе, закончен
        @FutureOrPresent
        @NotNull
        LocalDateTime createDateTime,  // дата и время создания задач
        @FutureOrPresent
        @NotNull
        LocalDateTime deadlineDate,    // дата, до которой задача должна быть выполнена
        @NotNull
        TaskPriority priority,          // низкий, средний, высокий

        LocalDateTime doneDateTime
) {
}
