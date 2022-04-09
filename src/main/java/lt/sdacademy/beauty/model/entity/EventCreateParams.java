package lt.sdacademy.beauty.model.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class EventCreateParams {
    public String title;
    public LocalDateTime startTime;
    public LocalDateTime endTime;
    public String location;
    public Long groupId;

}
