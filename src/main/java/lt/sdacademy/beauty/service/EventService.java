package lt.sdacademy.beauty.service;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import lombok.extern.slf4j.Slf4j;
import lt.sdacademy.beauty.model.entity.EventEntity;
import lt.sdacademy.beauty.model.entity.UserEntity;
import lt.sdacademy.beauty.repository.EventEntityRepository;
import lt.sdacademy.beauty.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
@Slf4j
public class EventService {

    @Autowired
    private EventEntityRepository eventEntityRepository;

    @Autowired
    private UserRepository userRepository;

    @JsonSerialize(using = LocalDateSerializer.class)
    public Iterable<EventEntity> events(@Param("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)LocalDateTime startTime,
                                        @Param("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)LocalDateTime endTime) {
        return this.eventEntityRepository.findBetween(startTime, endTime);
    }

    @JsonSerialize(using = LocalDateSerializer.class)
    public EventEntity createEvent(EventEntity event) {
        return this.eventEntityRepository.save(event);
    }

    @JsonSerialize(using = LocalDateSerializer.class)
    public EventEntity moveEvent(EventEntity moveParams) {
        EventEntity event = eventEntityRepository.findById(moveParams.getId()).get();
        event.setStartTime(moveParams.getStartTime());
        event.setEndTime(moveParams.getEndTime());
        return this.eventEntityRepository.save(event);
    }

    @JsonSerialize(using = LocalDateSerializer.class)
    public EventEntity updateEvent(Long id, EventEntity userRequest) {
        EventEntity event = eventEntityRepository.findEventEntitiesById(id);
        event.setTitle(userRequest.getTitle());
        event.setStartTime(userRequest.getStartTime());
        event.setEndTime(userRequest.getEndTime());
        event.setLocation(userRequest.getLocation());
        event.setColor(userRequest.getColor());
        event.setGroupId(userRequest.getGroupId());
        return this.eventEntityRepository.save(event);
    }

    @JsonSerialize(using = LocalDateSerializer.class)
    public EventEntity setColor(@RequestBody EventEntity event) {
        EventEntity updatedEvent = eventEntityRepository.findById(event.getId()).get();
        updatedEvent.setColor(event.getColor());
        return this.eventEntityRepository.save(event);
    }

    public void deleteEvent(Long id) {
        this.eventEntityRepository.deleteById(id);
    }

    public List<EventEntity> getAllEventsList(Long userId) {
        UserEntity user = this.userRepository.findUserEntitiesById(userId);
        return user.getEvents();
    }
}
