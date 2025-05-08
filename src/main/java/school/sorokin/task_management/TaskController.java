package school.sorokin.task_management;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TaskController {
    public static final Logger log = LoggerFactory.getLogger(TaskController.class);

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/{id}")
    public Task getTaskById(@PathVariable Long id) {
        log.info("Start  getTaskById: id={}", id);
        Task task = taskService.getTaskById(id);
        log.info("End getTaskById: id={}", id);
        return task;
    }

    @GetMapping()
    public List<Task> getTasks() {
        log.info("Start getTask");
        List<Task> task = taskService.getAllTasks();
        log.info("End  getTask");
        return task;
    }
}
