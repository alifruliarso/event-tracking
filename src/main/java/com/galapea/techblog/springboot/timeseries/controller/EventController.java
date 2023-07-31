package com.galapea.techblog.springboot.timeseries.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.galapea.techblog.springboot.timeseries.model.EventRequest;
import com.galapea.techblog.springboot.timeseries.model.EventView;
import com.galapea.techblog.springboot.timeseries.model.ajaxpaging.Page;
import com.galapea.techblog.springboot.timeseries.model.ajaxpaging.PagingRequest;
import com.galapea.techblog.springboot.timeseries.service.EventService;

@RestController
@RequestMapping("/api/track")
public class EventController {
    private EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @PostMapping()
    public ResponseEntity<String> create(@RequestBody EventRequest model) {
        eventService.create(model);
        return ResponseEntity.ok("Saved");
    }

    @PostMapping("/events-ajax")
    public Page<EventView> ajax(@RequestBody PagingRequest pagingRequest) {
        return eventService.getEventsViewPage(pagingRequest);
    }
}
