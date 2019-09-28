package ghevents.service;


import ghevents.model.Actor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

/**
 * Rest service for GitHub user
 * */
@Service
public class ActorRestService extends RestService {

    private Logger logger = LogManager.getLogger(ActorRestService.class);

    ActorRestService(RestTemplateBuilder restTemplateBuilder) {
        super(restTemplateBuilder);
    }

    /**
     * Get github user data if exists
     * Use wrap @ResponseEntity for variety
     * */
    public ResponseEntity<Actor> getActorAsEntity(String user) {
        String url = API_GH_USERS +user;
        ResponseEntity<Actor> actor=null;
        try {
            actor = this.restTemplate.getForEntity(url, Actor.class);
        } catch (HttpClientErrorException httpE) {
            logger.error("For url - " + url + " result is - "+httpE.getLocalizedMessage());
            logger.error("Response headers are: " + httpE.getResponseHeaders());
        }
        return actor;
    }
}
