package com.galapea.techblog.springboot.timeseries.entity;

import java.util.Date;
import com.toshiba.mwcloud.gs.RowKey;
import lombok.Data;

@Data
public class Event {
    @RowKey
    Date timestamp;
    String appId;
    String eventType;
    String externalId;
}
