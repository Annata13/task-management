package school.sorokin.task_management;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "task")
public class TaskEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "create_id")
    private Long createId;                 // id создавшего задачу

    @Column(name = "assigned_user_id")
    private Long assignedUserId;           // id исполнителя

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private TaskStatus status;             // создан, в процессе, закончен

    @Column(name = "create_date_time")
    private LocalDateTime createDateTime;  // дата и время создания задач

    @Column(name = "dead_line_date")
    private LocalDateTime deadlineDate;    // дата, до которой задача должна быть выполнена

    @Enumerated(EnumType.STRING)
    @Column(name = "priority")
    private TaskPriority priority;          // низкий, средний, высокий

    public TaskEntity() {
    }

    public TaskEntity(Long id, Long createId, Long assignedUserId, TaskStatus status, LocalDateTime createDateTime,
                      LocalDateTime deadlineDate, TaskPriority priority) {
        this.id = id;
        this.createId = createId;
        this.assignedUserId = assignedUserId;
        this.status = status;
        this.createDateTime = createDateTime;
        this.deadlineDate = deadlineDate;
        this.priority = priority;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TaskPriority getPriority() {
        return priority;
    }

    public void setPriority(TaskPriority priority) {
        this.priority = priority;
    }

    public LocalDateTime getDeadlineDate() {
        return deadlineDate;
    }

    public void setDeadlineDate(LocalDateTime deadlineDate) {
        this.deadlineDate = deadlineDate;
    }

    public LocalDateTime getCreateDateTime() {
        return createDateTime;
    }

    public void setCreateDateTime(LocalDateTime createDateTime) {
        this.createDateTime = createDateTime;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public Long getAssignedUserId() {
        return assignedUserId;
    }

    public void setAssignedUserId(Long assignedUserId) {
        this.assignedUserId = assignedUserId;
    }

    public Long getCreateId() {
        return createId;
    }

    public void setCreateId(Long createId) {
        this.createId = createId;
    }

    public Long getId() {
        return id;
    }
}
