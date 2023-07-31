package com.galapea.techblog.springboot.timeseries.model;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

@Data
public class EventRequest {
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss", shape = JsonFormat.Shape.STRING)
    Date timestamp;
    String appId;
    String eventType;
    String externalId;
}
