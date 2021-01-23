package kz.zhanbolat.auth.config;

import liquibase.pro.packaged.L;
import org.keycloak.Config;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakTransactionManager;
import org.keycloak.representations.idm.RealmRepresentation;
import org.keycloak.services.managers.ApplianceBootstrap;
import org.keycloak.services.managers.RealmManager;
import org.keycloak.services.resources.KeycloakApplication;
import org.keycloak.services.util.JsonConfigProviderFactory;
import org.keycloak.util.JsonSerialization;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.util.NoSuchElementException;

public class EmbeddedKeycloakApplication extends KeycloakApplication {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmbeddedKeycloakApplication.class);

    public static KeycloakServerProperties keycloakServerProperties;

    @Override
    protected void loadConfig() {
        JsonConfigProviderFactory factory = new RegularJsonConfigProverFactory();
        Config.init(factory.create().orElseThrow(() -> new NoSuchElementException("No value present")));
    }

    public EmbeddedKeycloakApplication() {
        super();

        createMasterRealmAdminUser();

        createApplicationRealm();
    }

    private void createApplicationRealm() {
        KeycloakSession session = getSessionFactory().create();

        final KeycloakTransactionManager transactionManager = session.getTransactionManager();

        try {
            transactionManager.begin();
            RealmManager manager = new RealmManager(session);
            Resource resource = new ClassPathResource(keycloakServerProperties.getRealImportFile());

            manager.importRealm(JsonSerialization.readValue(resource.getInputStream(), RealmRepresentation.class));

            transactionManager.commit();
        } catch (Exception e) {
            LOGGER.error("Failed to import Realm json file: {}", e.getMessage());
            transactionManager.rollback();
        }

        session.close();
    }

    private void createMasterRealmAdminUser() {
        KeycloakSession session = getSessionFactory().create();

        ApplianceBootstrap applianceBootstrap = new ApplianceBootstrap(session);

        KeycloakServerProperties.AdminUser adminUser = keycloakServerProperties.getAdminUser();

        final KeycloakTransactionManager transactionManager = session.getTransactionManager();
        try {
            transactionManager.begin();
            applianceBootstrap.createMasterRealmUser(adminUser.getUsername(), adminUser.getPassword());
            transactionManager.commit();
        } catch (Exception e) {
            LOGGER.error("Couldn't create keycloak master admin user: {}", e.getMessage());
            transactionManager.rollback();
        }

        session.close();
    }


}
