package kz.zhanbolat.auth.config;

import org.jboss.resteasy.plugins.server.servlet.HttpServlet30Dispatcher;
import org.jboss.resteasy.plugins.server.servlet.ResteasyContextParameters;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.naming.*;
import javax.naming.spi.NamingManager;
import javax.sql.DataSource;

@Configuration
public class EmbeddedKeycloakConfig {

    @Bean
    ServletRegistrationBean<HttpServlet30Dispatcher> keycloakJaxRsApplication(KeycloakServerProperties properties,
                                                                                     DataSource dataSource) throws Exception {
        mockJndiEnvironment(dataSource);
        EmbeddedKeycloakApplication.keycloakServerProperties = properties;

        ServletRegistrationBean<HttpServlet30Dispatcher> servlet = new ServletRegistrationBean<>(new HttpServlet30Dispatcher());
        servlet.addInitParameter("javax.ws.rs.Application", EmbeddedKeycloakApplication.class.getName());
        servlet.addInitParameter(ResteasyContextParameters.RESTEASY_SERVLET_MAPPING_PREFIX, properties.getContextPath());
        servlet.addInitParameter(ResteasyContextParameters.RESTEASY_USE_CONTAINER_FORM_PARAMS, "true");
        servlet.addUrlMappings(properties.getContextPath() + "/*");
        servlet.setLoadOnStartup(1);
        servlet.setAsyncSupported(true);

        return servlet;
    }

    @Bean
    FilterRegistrationBean<EmbeddedKeycloakRequestFilter> keycloakSessionManagement(KeycloakServerProperties properties) {
        FilterRegistrationBean<EmbeddedKeycloakRequestFilter> filter = new FilterRegistrationBean<>();
        filter.setName("Keycloak Session Management");
        filter.setFilter(new EmbeddedKeycloakRequestFilter());
        filter.addUrlPatterns(properties.getContextPath() + "/");

        return filter;
    }

    private void mockJndiEnvironment(DataSource dataSource) throws NamingException {
        NamingManager.setInitialContextFactoryBuilder((env) -> (environment) -> new InitialContext() {
            @Override
            public Object lookup(Name name) throws NamingException {
                return lookup(name.toString());
            }

            @Override
            public Object lookup(String name) throws NamingException {
                return "spring/datasource".equals(name) ? dataSource : null;
            }

            @Override
            public NameParser getNameParser(String name) throws NamingException {
                return CompositeName::new;
            }

            @Override
            public void close() throws NamingException {

            }
        });
    }
}
