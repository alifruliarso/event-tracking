package com.galapea.techblog.springboot.timeseries.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.galapea.techblog.springboot.timeseries.service.EventAggregateService;
import com.galapea.techblog.springboot.timeseries.service.EventService;

@Controller
public class EventWebController {
    private EventService eventService;
    private EventAggregateService eventAggregateService;

    public EventWebController(EventService eventService,
            EventAggregateService eventAggregateService) {
        this.eventService = eventService;
        this.eventAggregateService = eventAggregateService;
    }

    @GetMapping("/events")
    public String showEvents(Model model) {
        model.addAttribute("events", eventService.getEventsView());
        return "events";
    }

    @GetMapping("/aggregate")
    public String showAggregateEvents(Model model) {
        model.addAttribute("aggregates", eventAggregateService.getEventsView());
        model.addAttribute("aggregatesByType", eventAggregateService.getAggregateByType());

        return "aggregate";
    }
}
