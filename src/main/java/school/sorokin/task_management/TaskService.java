package school.sorokin.task_management;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@Service
public class TaskService {
    private final Map<Long, Task> tasks = Map.of(
            1L, new Task(
                    1L,
                    1L,
                    1L,
                    TaskStatus.IN_PROGRESS,
                    LocalDateTime.now(),
                    LocalDateTime.now().plusDays(2),
                    TaskPriority.HIGH
            ),
            2L, new Task(
                    2L,
                    2L,
                    22L,
                    TaskStatus.CREATED,
                    LocalDateTime.now(),
                    LocalDateTime.now().plusDays(6),
                    TaskPriority.LOW
            ),
            3L, new Task(
                    3L,
                    3L,
                    1L,
                    TaskStatus.IN_PROGRESS,
                    LocalDateTime.now(),
                    LocalDateTime.now().plusDays(1),
                    TaskPriority.MEDIUM
            ),
            4L, new Task(
                    4L,
                    4L,
                    2L,
                    TaskStatus.IN_PROGRESS,
                    LocalDateTime.now(),
                    LocalDateTime.now().plusDays(8),
                    TaskPriority.HIGH
            )
    );

    public Task getTaskById(Long id) {
        if (!tasks.containsKey(id)) {
            throw new NoSuchElementException("Not found reservation by id = " + id);
        }
        return tasks.get(id);
    }

    public List<Task> getAllTasks() {
        return new ArrayList<>(tasks.values());
    }
}