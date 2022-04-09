package lt.sdacademy.beauty.controller;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import lt.sdacademy.beauty.model.entity.EventCreateParams;
import lt.sdacademy.beauty.model.entity.EventEntity;
import lt.sdacademy.beauty.repository.EventEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class EventEntityController {

    @Autowired
    EventEntityRepository eventEntityRepository;


    @RequestMapping("/events")
    @JsonSerialize(using = LocalDateSerializer.class)
    Iterable<EventEntity> events(@RequestParam("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)LocalDateTime startTime,
                                 @RequestParam("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)LocalDateTime endTime) {
        return this.eventEntityRepository.findBetween(startTime, endTime);
    }

    @PostMapping("/events/create")
    @JsonSerialize(using = LocalDateSerializer.class)
    @Transactional
    ResponseEntity<EventEntity> createEvent(@RequestBody EventCreateParams params) {
        EventEntity event = new EventEntity();
        event.setTitle(params.title);
        event.setStartTime(params.startTime);
        event.setEndTime(params.endTime);
        event.setLocation(params.location);
        eventEntityRepository.save(event);
        return new ResponseEntity<>(event, HttpStatus.CREATED);
    }

    @PostMapping("/events/move")
    @JsonSerialize(using = LocalDateSerializer.class)
    @Transactional
    ResponseEntity<EventEntity> moveEvent(@RequestBody EventMoveParams params) {
        EventEntity event = eventEntityRepository.findById(params.id).get();
        event.setStartTime(params.startTime);
        event.setEndTime(params.endTime);
        this.eventEntityRepository.save(event);
        return new ResponseEntity<>(event, HttpStatus.CREATED);
    }

    @PostMapping("/events/set-color")
    @JsonSerialize(using = LocalDateSerializer.class)
    @Transactional
    ResponseEntity<EventEntity> setColor(@RequestBody SetColorParams params) {
        EventEntity event = eventEntityRepository.findById(params.id).get();
        event.setColor(params.color);
        this.eventEntityRepository.save(event);
        return new ResponseEntity<>(event, HttpStatus.CREATED);
    }

    @PutMapping("/events/update")
    @JsonSerialize(using = LocalDateSerializer.class)
    @Transactional
    ResponseEntity<EventEntity> updateEvent(@RequestBody EventEntity event) {
        EventEntity newEvent = new EventEntity();
        newEvent.setTitle(event.getTitle());
        newEvent.setStartTime(event.getStartTime());
        newEvent.setEndTime(event.getEndTime());
        newEvent.setLocation(event.getLocation());
        eventEntityRepository.save(newEvent);
        return new ResponseEntity<>(event, HttpStatus.CREATED);
    }
    @DeleteMapping("/events/delete/{id}")
    public ResponseEntity<?> deleteEvent(@PathVariable Long id) {
        this.eventEntityRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }



    public static class EventMoveParams {
        public Long id;
        public LocalDateTime startTime;
        public LocalDateTime endTime;
        public Long groupId;
    }

    public static class SetColorParams {
        public Long id;
        public String color;
    }

}
