package com.galapea.techblog.springboot.timeseries.model;

import java.util.Date;
import lombok.Data;

@Data
public class EventView {
    private final Date timestamp;
    private final String appId;
    private final String eventType;
    private final String externalId;
}
