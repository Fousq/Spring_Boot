package kz.zhanbolat.springboot.controller;

import kz.zhanbolat.springboot.entity.Event;
import kz.zhanbolat.springboot.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/event")
public class EventController {
    @Autowired
    private EventRepository eventRepository;

    @GetMapping(value = "/{eventId}")
    public Event getEvent(@PathVariable("eventId") int eventId) {
        return eventRepository.findById(eventId).orElseThrow(() -> new IllegalStateException("Event cannot be found"));
    }

    @PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Event createEvent(@RequestBody Event event) {
        return eventRepository.save(event);
    }

    @PutMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Event updateEvent(@RequestBody Event event) {
        return eventRepository.save(event);
    }

    @DeleteMapping("/{eventId}")
    public Event deleteEvent(@PathVariable("eventId") int eventId) {
        Event event = eventRepository.findById(eventId).orElseThrow(() -> new IllegalStateException("Event cannot be found"));
        eventRepository.delete(event);
        return event;
    }
}
