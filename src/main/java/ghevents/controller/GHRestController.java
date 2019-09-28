package ghevents.controller;

import ghevents.model.Event;
import ghevents.model.rate.RateLimit;
import ghevents.service.EventRestService;
import ghevents.service.RestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class GHRestController {

    @Autowired
    private EventRestService eventRestService;
    @Autowired
    private RestService restService;

    @GetMapping("/events")
    public Event[] getAllEvents(@RequestParam(value = "username") String user) {
        return eventRestService.getEventsAsObject(user);
    }

    @GetMapping("/received_events")
    public Event[] getAllREvents(@RequestParam(value = "username") String user) {
        return eventRestService.getReceivedEventsAsObject(user);
    }

    @GetMapping("/rate_limit")
    public RateLimit getRateLimit() {
        return restService.getRateLimitForNonAuth();
    }

}
