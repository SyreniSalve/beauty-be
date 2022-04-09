package lt.sdacademy.beauty.repository;

import lt.sdacademy.beauty.model.entity.EventEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EventEntityRepository extends CrudRepository<EventEntity, Long> {

    @Query("from EventEntity e where not(e.endTime < :from or e.startTime > :to)")
    List<EventEntity> findBetween(@Param("from") LocalDateTime start,
                                         @Param("to") LocalDateTime end);
}
