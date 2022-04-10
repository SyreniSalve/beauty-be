package lt.sdacademy.beauty.controller;

import lt.sdacademy.beauty.model.dto.*;
import lt.sdacademy.beauty.model.entity.EventEntity;
import lt.sdacademy.beauty.service.EventService;
import lt.sdacademy.beauty.service.UserDetailsImpl;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class EventController {


    @Autowired
    private EventService eventService;

    @Autowired
    private ModelMapper modelMapper;


    @GetMapping("/events")
    public ResponseEntity<Iterable<EventEntity>> events(@RequestParam("start")LocalDateTime startTime,
                                 @RequestParam("end") LocalDateTime endTime) {
        Iterable<EventEntity> eventsList = this.eventService.events(startTime, endTime);
        return new ResponseEntity<>(eventsList, HttpStatus.OK);
    }

    @GetMapping("/events/user-events/{userId}")
    public ResponseEntity<List<EventEntity>> findAllUserEvents(@PathVariable("userId") Long userId){
        List<EventEntity> eventList = this.eventService.getAllEventsList(userId);
        return new ResponseEntity<>(eventList, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_OWNER')")
    @PostMapping("/events/create")
    public ResponseEntity<EventCreateParams> createEvent(@RequestBody EventCreateParams params) {
        EventEntity userRequest = modelMapper.map(params, EventEntity.class);
        userRequest.setUserId(((UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId());
        EventEntity user = eventService.createEvent(userRequest);
        EventCreateParams postResponse = modelMapper.map(user, EventCreateParams.class);
        return new ResponseEntity<>(postResponse, HttpStatus.CREATED);
    }

    @PutMapping("/events/update/{id}")
    public ResponseEntity<EventEntityDto> updateEvent(@PathVariable("id") Long id, @RequestBody EventEntityDto event) {
        EventEntity userRequest = modelMapper.map(event, EventEntity.class);
        EventEntity user = eventService.updateEvent(id, userRequest);
        EventEntityDto postResponse = modelMapper.map(user, EventEntityDto.class);
        return new ResponseEntity<>(postResponse, HttpStatus.OK);
    }

    @PostMapping("/events/move")
    public ResponseEntity<EventMoveParams> moveEvent(@RequestBody EventMoveParams moveParams) {
        EventEntity userRequest = modelMapper.map(moveParams, EventEntity.class);
        EventEntity user = eventService.moveEvent(userRequest);
        EventMoveParams postResponse = modelMapper.map(user, EventMoveParams.class);
        return new ResponseEntity<>(postResponse, HttpStatus.CREATED);
    }


    @PostMapping("/events/set-color")
   public ResponseEntity<SetColorParams> setColor(@RequestBody SetColorParams params) {
        EventEntity userRequest = modelMapper.map(params, EventEntity.class);
        EventEntity user = eventService.setColor(userRequest);
        SetColorParams postResponse = modelMapper.map(user, SetColorParams.class);
        return new ResponseEntity<>(postResponse, HttpStatus.CREATED);
    }

    @DeleteMapping("/events/delete/{id}")
    public ResponseEntity<?> deleteEvent(@PathVariable Long id) {
        this.eventService.deleteEvent(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
