package kz.zhanbolat.springboot.health;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class RequiredConfigurationPropertiesIndicator implements HealthIndicator {

    @Autowired(required = false)
    @Value("${app.prometheus.url:#{null}}")
    private Optional<String> prometheusUrlProperty;

    @Override
    public Health health() {
        if (!prometheusUrlProperty.isPresent()) {
            return Health.down().withDetail("Value is not presented", "Prometheus url value is not presented").build();
        }
        return Health.up().build();
    }
}
