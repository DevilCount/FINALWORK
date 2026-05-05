package com.lis.hl7.mllp;

import com.lis.hl7.entity.InterfaceConfigDO;
import com.lis.hl7.service.InterfaceConfigService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
@RequiredArgsConstructor
@ConditionalOnProperty(prefix = "mllp", name = "enabled", havingValue = "true", matchIfMissing = true)
public class MllpServerManager implements CommandLineRunner {

    private final MllpProperties mllpProperties;
    private final DefaultMllpMessageHandler messageHandler;
    private final InterfaceConfigService interfaceConfigService;

    private final Map<String, MllpServer> servers = new ConcurrentHashMap<>();

    @Override
    public void run(String... args) throws Exception {
        log.info("Starting MLLP Server Manager...");

        startDefaultServer();

        startConfiguredServers();
    }

    private void startDefaultServer() {
        try {
            String host = mllpProperties.getHost();
            int port = mllpProperties.getPort();

            MllpServer server = new MllpServer(host, port, messageHandler);
            server.start();
            servers.put("default", server);

            log.info("Default MLLP server started on {}:{}", host, port);
        } catch (Exception e) {
            log.error("Failed to start default MLLP server", e);
        }
    }

    private void startConfiguredServers() {
        Map<String, MllpProperties.InterfaceConfig> interfaces = mllpProperties.getInterfaces();

        if (interfaces != null && !interfaces.isEmpty()) {
            for (Map.Entry<String, MllpProperties.InterfaceConfig> entry : interfaces.entrySet()) {
                MllpProperties.InterfaceConfig config = entry.getValue();

                if (Boolean.TRUE.equals(config.getEnabled())) {
                    try {
                        String host = config.getHost() != null ? config.getHost() : "0.0.0.0";
                        int port = config.getPort();

                        MllpServer server = new MllpServer(host, port, messageHandler);
                        server.start();
                        servers.put(entry.getKey(), server);

                        log.info("MLLP server '{}' started on {}:{}", entry.getKey(), host, port);
                    } catch (Exception e) {
                        log.error("Failed to start MLLP server '{}'", entry.getKey(), e);
                    }
                }
            }
        }
    }

    public void startServer(String interfaceCode) {
        try {
            InterfaceConfigDO config = interfaceConfigService.getByCode(interfaceCode);
            if (config == null) {
                log.error("Interface config not found: {}", interfaceCode);
                return;
            }

            if (servers.containsKey(interfaceCode)) {
                log.warn("Server already running for interface: {}", interfaceCode);
                return;
            }

            String host = config.getHost() != null ? config.getHost() : "0.0.0.0";
            int port = config.getPort();

            MllpServer server = new MllpServer(host, port, messageHandler);
            server.start();
            servers.put(interfaceCode, server);

            interfaceConfigService.updateStatus(config.getId(), "running");

            log.info("MLLP server started for interface '{}' on {}:{}", interfaceCode, host, port);
        } catch (Exception e) {
            log.error("Failed to start MLLP server for interface: {}", interfaceCode, e);
        }
    }

    public void stopServer(String interfaceCode) {
        MllpServer server = servers.remove(interfaceCode);
        if (server != null) {
            server.shutdown();

            try {
                InterfaceConfigDO config = interfaceConfigService.getByCode(interfaceCode);
                if (config != null) {
                    interfaceConfigService.updateStatus(config.getId(), "stopped");
                }
            } catch (Exception e) {
                log.error("Failed to update interface status", e);
            }

            log.info("MLLP server stopped for interface: {}", interfaceCode);
        }
    }

    public void restartServer(String interfaceCode) {
        stopServer(interfaceCode);
        startServer(interfaceCode);
    }

    public boolean isServerRunning(String interfaceCode) {
        MllpServer server = servers.get(interfaceCode);
        return server != null && server.isRunning();
    }

    public Map<String, MllpServer> getRunningServers() {
        return new ConcurrentHashMap<>(servers);
    }

    @PreDestroy
    public void shutdown() {
        log.info("Shutting down all MLLP servers...");

        for (Map.Entry<String, MllpServer> entry : servers.entrySet()) {
            try {
                entry.getValue().shutdown();
                log.info("MLLP server '{}' stopped", entry.getKey());
            } catch (Exception e) {
                log.error("Error stopping MLLP server '{}'", entry.getKey(), e);
            }
        }

        servers.clear();
    }
}
