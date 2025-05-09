package school.sorokin.task_management;

import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class TaskService {
    private final Map<Long, Task> taskMap;
    private final AtomicLong idCounter;

    public TaskService() {
        taskMap = new HashMap<Long, Task>();
        idCounter = new AtomicLong();
    }

    public Task getTaskById(Long id) {
        if (!taskMap.containsKey(id)) {
            throw new NoSuchElementException("Not found reservation by id = " + id);
        }
        return taskMap.get(id);
    }

    public List<Task> getAllTasks() {
        return taskMap.values().stream().toList();
    }

    public Task createdTask(Task taskToCreate) {
        if (taskToCreate.id() != null) {
            throw new IllegalArgumentException("Id should be empty");
        }
        if (taskToCreate.status() != null) {
            throw new IllegalArgumentException("Id status be empty");
        }
        var newTask = new Task(
                idCounter.incrementAndGet(),
                taskToCreate.createId(),
                taskToCreate.assignedUserId(),
                TaskStatus.CREATED,
                taskToCreate.createDateTime(),
                taskToCreate.deadlineDate(),
                TaskPriority.MEDIUM
        );
        taskMap.put(newTask.id(), newTask);
        return newTask;
    }

    public Task updateTask(Long id, Task taskToUpdate) {
        if (!taskMap.containsKey(id)) {
            throw new NoSuchElementException("Not found task by id = " + id);
        }
        var task = taskMap.get(id);
        if (task.status() == task.status().DONE) {
            throw new IllegalStateException("Cannot modify task: status= " + task.status());
        }
        var updateTask = new Task(
                task.id(),
                taskToUpdate.createId(),
                taskToUpdate.assignedUserId(),
                taskToUpdate.status(),
                taskToUpdate.createDateTime(),
                taskToUpdate.deadlineDate(),
                taskToUpdate.priority()
        );
        taskMap.put(task.id(), updateTask);
        return updateTask;
    }

    public void deleteTask(Long id) {
        if (!taskMap.containsKey(id)) {
            throw new NoSuchElementException("Not found task by id = " + id);
        }
        taskMap.remove(id);
    }
}