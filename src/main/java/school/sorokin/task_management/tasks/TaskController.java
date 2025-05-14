package school.sorokin.task_management.tasks;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {
    public static final Logger log = LoggerFactory.getLogger(TaskController.class);

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable Long id) {
        log.info("Called  getTaskById: id={}", id);
        return ResponseEntity.ok(taskService.getTaskById(id));
    }

    @GetMapping()
    public ResponseEntity<List<Task>> searchAllByFilter(@RequestParam(name = "createId", required = false) Long createId,
                                                        @RequestParam(name = "assignedUserId", required = false) Long assignedUserId,
                                                        @RequestParam(name = "status", required = false) TaskStatus status,
                                                        @RequestParam(name = "priority", required = false) TaskPriority priority,
                                                        @RequestParam(name = "pageSize", required = false) Integer pageSize,
                                                        @RequestParam(name = "pageNumber", required = false) Integer pageNumber
    ) {
        log.info("searchAllByFilter");
        var filter = new TaskSearchFilter(createId, assignedUserId, status, priority, pageSize, pageNumber);
        return ResponseEntity.ok(taskService.searchAllByFilter(filter));
    }

    @PostMapping()
    public ResponseEntity<Task> createTask(@RequestBody @Valid Task taskToCreate) {
        log.info("Called createTask");
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(taskService.createdTask(taskToCreate));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(
            @PathVariable("id") Long id,
            @RequestBody @Valid Task taskToUpdate) {
        log.info("Called  updateTask: id={}, taskToUpdate={}", id, taskToUpdate);
        var updated = taskService.updateTask(id, taskToUpdate);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Task> deleteTask(@PathVariable("id") Long id) {
        log.info("Called  deleteTask: id={}", id);
        taskService.deleteTask(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/start")
    public ResponseEntity<Task> updateStatusTask(@PathVariable("id") Long id) {
        log.info("Called  startTask: id={}", id);
        var updated = taskService.updateStatusTask(id);
        return ResponseEntity.ok(updated);
    }

    @PostMapping("/{id}/complete")
    public ResponseEntity<Task> updateStatusDoneTask(@PathVariable("id") Long id) {
        log.info("Called  updateStatusDoneTask: id={}", id);
        var updated = taskService.updateStatusDoneTask(id);
        return ResponseEntity.ok(updated);
    }
}
