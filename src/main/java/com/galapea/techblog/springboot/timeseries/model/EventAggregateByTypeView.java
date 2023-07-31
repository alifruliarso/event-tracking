package com.galapea.techblog.springboot.timeseries.model;

import lombok.Data;

@Data
public class EventAggregateByTypeView {
    private final Long count;
    private final String label;
}
