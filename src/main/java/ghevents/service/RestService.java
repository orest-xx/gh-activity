package ghevents.service;

import ghevents.model.rate.RateLimit;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;


import java.time.Duration;

/**
 * Base service for rest
 * */
@Service
public class RestService {

    private Logger logger = LogManager.getLogger(RestService.class);

    public static final String API_GH_USERS = "https://api.github.com/users/";


    final RestTemplate restTemplate;

    /**
     * Set connection and read timeouts
    * */
    RestService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder
                .setConnectTimeout(Duration.ofSeconds(500))
                .setReadTimeout(Duration.ofSeconds(500))
                .build();
    }

    /**
     * Check how much attempts are
     * */
    public RateLimit getRateLimitForNonAuth() {
        RateLimit rateLimit = null;
        String rateLimitUrl = "https://api.github.com/rate_limit";
        try {
            rateLimit = this.restTemplate.getForObject(rateLimitUrl, RateLimit.class);
        } catch (HttpClientErrorException httpE) {
            logger.error("For url - " + rateLimitUrl + " result is - " + httpE.getLocalizedMessage());
            logger.error("Response headers are: " + httpE.getResponseHeaders());
        }
        return rateLimit;
    }


}
