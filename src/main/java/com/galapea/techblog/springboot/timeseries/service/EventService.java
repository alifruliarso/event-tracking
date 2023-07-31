package com.galapea.techblog.springboot.timeseries.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import com.galapea.techblog.springboot.timeseries.entity.Event;
import com.galapea.techblog.springboot.timeseries.model.EventRequest;
import com.galapea.techblog.springboot.timeseries.model.EventView;
import com.galapea.techblog.springboot.timeseries.model.ajaxpaging.Page;
import com.galapea.techblog.springboot.timeseries.model.ajaxpaging.PagingRequest;
import com.toshiba.mwcloud.gs.GSException;
import com.toshiba.mwcloud.gs.Query;
import com.toshiba.mwcloud.gs.RowSet;
import com.toshiba.mwcloud.gs.TimeSeries;

@Service
public class EventService {
    private final TimeSeries<Event> eventsContainer;

    public EventService(TimeSeries<Event> eventsContainer) {
        this.eventsContainer = eventsContainer;
    }

    public void create(EventRequest request) {
        Event event = new Event();
        event.setAppId(request.getAppId());
        event.setEventType(request.getEventType());
        event.setExternalId(request.getExternalId());
        event.setTimestamp(request.getTimestamp());
        try {
            eventsContainer.append(event);
        } catch (GSException e) {
            e.printStackTrace();
        }
    }

    public Page<EventView> getEventsViewPage(PagingRequest pagingRequest) {
        List<EventView> filtered = getEvents().stream().map(o -> new EventView(o.getTimestamp(),
                o.getAppId(), o.getEventType(), o.getExternalId())).collect(Collectors.toList());

        if (!filtered.isEmpty()) {
            Page<EventView> page = new Page<>(filtered);
            page.setRecordsFiltered(filtered.size());
            page.setRecordsTotal(0);
            page.setDraw(pagingRequest.getDraw());
            return page;
        }

        return new Page<>();
    }

    public List<EventView> getEventsView() {
        List<EventView> views = getEvents().stream().map(o -> new EventView(o.getTimestamp(),
                o.getAppId(), o.getEventType(), o.getExternalId())).collect(Collectors.toList());
        views.forEach(v -> System.out.println(v));
        return views;
    }

    public List<Event> getEvents() {
        List<Event> metricModels = new ArrayList<>();
        Query<Event> query;
        try {
            query = eventsContainer.query("select * from events order by timestamp desc limit 50");
            RowSet<Event> rs = query.fetch();
            while (rs.hasNext()) {
                Event model = rs.next();
                metricModels.add(model);
            }
        } catch (GSException e) {
            e.printStackTrace();
        }
        return metricModels;
    }

}
