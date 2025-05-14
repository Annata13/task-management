package school.sorokin.task_management.tasks;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TaskRepository extends JpaRepository<TaskEntity, Long> {

    @Query("SELECT COUNT(t) FROM TaskEntity t " +
            "WHERE t.assignedUserId = :userId " +
            "AND t.status = 'IN_PROGRESS'")
    int countActiveTasksByUser(@Param("userId") Long userId);

    @Query("""
            SELECT r FROM TaskEntity  r
            WHERE (:createId IS NULL OR r.createId = :createId )
            AND (:assignedUserId IS NULL OR r.assignedUserId = :assignedUserId)
            AND (:status IS NULL OR r.status = :status)
            AND (:priority IS NULL OR r.priority = :priority)
            """)
    List<TaskEntity> searchAllByFilter(@Param("createId") Long createId,
                                       @Param("assignedUserId") Long assignedUserId,
                                       @Param("status") TaskStatus status,
                                       @Param("priority") TaskPriority priority,
                                       Pageable pageable);
}