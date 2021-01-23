package kz.zhanbolat.springboot.health;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.util.Objects;
import java.util.Optional;

@Component
public class PrometheusHealthIndicator implements HealthIndicator {

    private static final Logger logger = LoggerFactory.getLogger(PrometheusHealthIndicator.class);

    @Autowired(required = false)
    @Value("${app.prometheus.url:#{null}}")
    private Optional<String> prometheusUrl;

    @Override
    public Health health() {
        if (!prometheusUrl.isPresent()) {
            return Health.down().withDetail("Value not present", "Prometheus url is not provided").build();
        }
        try {
            ClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
            final ClientHttpRequest request = requestFactory.createRequest(URI.create(prometheusUrl.get()), HttpMethod.GET);
            final ClientHttpResponse response = request.execute();
            if (!Objects.equals(response.getStatusCode(), HttpStatus.OK)) {
                return Health.down().withDetail("Unknown response from prometheus", response.getStatusText()).build();
            }
        } catch (IOException e) {
            logger.error("Cannot make request to prometheus.", e);
            return Health.down(e).build();
        }
        return Health.up().build();
    }
}
