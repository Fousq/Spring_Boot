package kz.zhanbolat.springboot.metrics;

import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.binder.MeterBinder;
import kz.zhanbolat.springboot.repository.EventRepository;
import kz.zhanbolat.springboot.repository.TicketRepository;
import kz.zhanbolat.springboot.repository.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MetricsConfig {

    @Bean
    public MeterBinder eventsAmount(EventRepository eventRepository) {
        return (registry) -> Gauge.builder("app.metrics.eventsAmount", eventRepository::count).register(registry);
    }

    @Bean
    public MeterBinder usersAmount(UserRepository userRepository) {
        return (registry) -> Gauge.builder("app.metrics.usersAmount", userRepository::count).register(registry);
    }

    @Bean
    public MeterBinder ticketsAmount(TicketRepository ticketRepository) {
        return (registry) -> Gauge.builder("app.metrics.ticketsAmount", ticketRepository::count).register(registry);
    }
}
