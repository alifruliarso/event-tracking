package com.galapea.techblog.springboot.timeseries.model;

import java.util.Date;
import lombok.Data;

@Data
public class EventAggregateView {
    private final Date timestamp;
    private final Long count;
    private final String timeLabel;
}
