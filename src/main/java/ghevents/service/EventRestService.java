package ghevents.service;

import ghevents.model.Actor;
import ghevents.model.Event;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

/**
 * Rest service for GitHub events
 * there are events made by user and received events
 * by default its all public, for private events auth needed
 * Auth @todo
 *
 * */
@Service
public class EventRestService extends RestService {
    @Autowired
    ActorRestService actorRestService;

    private Logger logger = LogManager.getLogger(EventRestService.class);

    public EventRestService(RestTemplateBuilder restTemplateBuilder) {
        super(restTemplateBuilder);
    }

    /**
     * Get public user events for given user
     * First we check by @getActorAsEntity do user exist
     * */
    public Event[] getEventsAsObject(String user) {
        ResponseEntity<Actor> actorAsEntity = actorRestService.getActorAsEntity(user);
        if (actorAsEntity == null || actorAsEntity.getStatusCodeValue() != 200) return null;

        String url = API_GH_USERS + user + "/events";
        Event[] events = null;
        try {
            events = this.restTemplate.getForObject(url, Event[].class);
        } catch (HttpClientErrorException httpE) {
            logger.error("For url - " + url + " result is - " + httpE.getLocalizedMessage());
            logger.error("Response headers are: " + httpE.getResponseHeaders());
        }
        return events;
    }

    /**
     * Get public received events for given user
     * First we check by @getActorAsEntity do user exist
     * */
    public Event[] getReceivedEventsAsObject(String user) {
        ResponseEntity<Actor> actorAsEntity = actorRestService.getActorAsEntity(user);
        if (actorAsEntity == null || actorAsEntity.getStatusCodeValue() != 200) return null;

        String url = API_GH_USERS + user + "/received_events";
        Event[] events = null;
        try {
            events = this.restTemplate.getForObject(url, Event[].class);
        } catch (HttpClientErrorException httpE) {
            logger.error("For url - " + url + " result is - " + httpE.getLocalizedMessage());
            logger.error("Response headers are: " + httpE.getResponseHeaders());
        }
        return events;
    }


}
