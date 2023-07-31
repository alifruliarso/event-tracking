package com.galapea.techblog.springboot.timeseries.service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.galapea.techblog.springboot.timeseries.EventTrackerConstants;
import com.galapea.techblog.springboot.timeseries.entity.Event;
import com.galapea.techblog.springboot.timeseries.model.EventAggregateByTypeView;
import com.galapea.techblog.springboot.timeseries.model.EventAggregateView;
import com.toshiba.mwcloud.gs.Aggregation;
import com.toshiba.mwcloud.gs.AggregationResult;
import com.toshiba.mwcloud.gs.Container;
import com.toshiba.mwcloud.gs.GSException;
import com.toshiba.mwcloud.gs.GridStore;
import com.toshiba.mwcloud.gs.Query;
import com.toshiba.mwcloud.gs.Row;
import com.toshiba.mwcloud.gs.RowSet;
import com.toshiba.mwcloud.gs.TimeSeries;

@Service
public class EventAggregateService {
    private final Logger log = LoggerFactory.getLogger(EventAggregateService.class);
    private final TimeSeries<Event> eventsContainer;
    private GridStore gridStore;
    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public EventAggregateService(TimeSeries<Event> eventsContainer, GridStore gridStore) {
        this.eventsContainer = eventsContainer;
        this.gridStore = gridStore;
    }

    public List<EventAggregateView> getEventsView() {
        List<EventAggregateView> views = getEvents();
        views.forEach(v -> log.info("Aggregate {}", v));
        return views;
    }

    java.util.Date convertToDateViaInstant(LocalDateTime dateToConvert) {
        return java.util.Date.from(dateToConvert.atZone(ZoneId.systemDefault()).toInstant());
    }

    public List<EventAggregateView> getEvents() {
        List<EventAggregateView> views = new ArrayList<>();

        LocalDateTime startDate = LocalDateTime.parse("2023-07-01T00:00:00"),
                endDate = LocalDateTime.now();
        for (LocalDateTime date = startDate; date.isBefore(endDate); date = date.plusDays(1)) {
            try {
                java.util.Date start =
                        convertToDateViaInstant(date.withHour(0).withMinute(0).withSecond(0));
                java.util.Date end =
                        convertToDateViaInstant(date.withHour(23).withMinute(59).withSecond(59));
                AggregationResult aggregationResult =
                        eventsContainer.aggregate(start, end, "appId", Aggregation.COUNT);
                Long count = aggregationResult.getLong();
                if (count.compareTo(0L) > 0) {
                    views.add(new EventAggregateView(convertToDateViaInstant(date), count,
                            date.format(dateTimeFormatter)));
                }
            } catch (GSException e) {
                e.printStackTrace();
            }
        }
        return views;
    }

    public List<EventAggregateByTypeView> getAggregateByType() {
        List<EventAggregateByTypeView> views = new ArrayList<>();
        try {
            Container<?, Row> container = gridStore.getContainer("events");
            for (String eventType : EventTrackerConstants.EVENT_TYPE_LIST) {
                Query<AggregationResult> query = container.query(
                        "SELECT COUNT(*) from events WHERE eventType='" + eventType + "'",
                        AggregationResult.class);
                RowSet<AggregationResult> rs = query.fetch();
                if (rs.hasNext()) {
                    AggregationResult row = rs.next();
                    Long count = row.getLong();
                    
                    log.info("{}, count={}", eventType, count);
                    views.add(new EventAggregateByTypeView(count, eventType));
                }
            }
        } catch (GSException e) {
            e.printStackTrace();
        }

        return views;
    }

}
