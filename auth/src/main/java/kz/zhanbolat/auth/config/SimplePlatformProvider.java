package kz.zhanbolat.auth.config;

import org.keycloak.platform.PlatformProvider;
import org.keycloak.services.ServicesLogger;

public class SimplePlatformProvider implements PlatformProvider {

    Runnable shutdownHook;

    @Override
    public void onStartup(Runnable runnable) {
        runnable.run();
    }

    @Override
    public void onShutdown(Runnable runnable) {
        this.shutdownHook = runnable;
    }

    @Override
    public void exit(Throwable cause) {
        ServicesLogger.LOGGER.fatal(cause);
        new Thread(() -> System.exit(1)).start();
    }
}
