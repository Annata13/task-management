package school.sorokin.task_management;

import jakarta.persistence.EntityNotFoundException;
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
        try {
            return ResponseEntity.ok(taskService.getTaskById(id));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping()
    public ResponseEntity<List<Task>> getAllTasks() {
        log.info("Called getAllTasks");
        return ResponseEntity.ok(taskService.getAllTasks());
    }

    @PostMapping()
    public ResponseEntity<Task> createTask(@RequestBody Task taskToCreate) {
        log.info("Called createTask");
        try {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(taskService.createdTask(taskToCreate));
        } catch (IllegalArgumentException e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(
            @PathVariable("id") Long id,
            @RequestBody Task taskToUpdate) {
        log.info("Called  updateTask: id={}, taskToUpdate={}", id, taskToUpdate);
        try {
            var updated = taskService.updateTask(id, taskToUpdate);
            return ResponseEntity.ok(updated);
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Task> deleteTask(@PathVariable("id") Long id) {
        log.info("Called  deleteTask: id={}", id);
        try {
            taskService.deleteTask(id);
            return ResponseEntity.ok().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{id}/start")
    public ResponseEntity<Task> updateStatusTask(@PathVariable("id") Long id) {
        log.info("Called  startTask: id={}", id);
        try {
            var updated = taskService.updateStatusTask(id);
            return ResponseEntity.ok(updated);
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
