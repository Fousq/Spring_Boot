package kz.zhanbolat.auth;

import kz.zhanbolat.auth.config.KeycloakServerProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseAutoConfiguration;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(exclude = LiquibaseAutoConfiguration.class)
@EnableConfigurationProperties(KeycloakServerProperties.class)
public class AuthorizationServerApplication {

	private static final Logger LOGGER = LoggerFactory.getLogger(AuthorizationServerApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(AuthorizationServerApplication.class, args);
	}

	@Bean
	ApplicationListener<ApplicationReadyEvent> onApplicationReadyEventListener(ServerProperties serverProperties,
																			   KeycloakServerProperties keycloakServerProperties) {
		return (evt) -> {
			final Integer port = serverProperties.getPort();
			final String contextPath = keycloakServerProperties.getContextPath();

			LOGGER.info("Embedded Keycloak started: http://localhost:{}{} to use keycloak", port, contextPath);
		};
	}
}
