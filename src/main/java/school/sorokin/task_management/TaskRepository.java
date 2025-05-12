package school.sorokin.task_management;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TaskRepository extends JpaRepository<TaskEntity, Long> {
    @Query("SELECT COUNT(t) FROM TaskEntity t " +
            "WHERE t.assignedUserId = :userId " +
            "AND t.status = 'IN_PROGRESS'")
    int countActiveTasksByUser(@Param("userId") Long userId);
}