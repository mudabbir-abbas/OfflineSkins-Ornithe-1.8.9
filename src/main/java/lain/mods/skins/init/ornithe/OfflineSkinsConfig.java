package lain.mods.skins.init.ornithe;

import net.fabricmc.loader.api.FabricLoader;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

public class OfflineSkinsConfig {

    public boolean useMojang = true;
    public boolean useCrafatar = true;
    public boolean useCustomServer = true;
    public String hostCustomServer = "http://example.com";

    public static OfflineSkinsConfig load() {
        OfflineSkinsConfig cfg = new OfflineSkinsConfig();
        Path configPath = FabricLoader.getInstance().getConfigDir().resolve("offlineskins.properties");
        Properties props = new Properties();

        if (Files.exists(configPath)) {
            try (InputStream in = Files.newInputStream(configPath)) {
                props.load(in);
                cfg.useMojang = Boolean.parseBoolean(props.getProperty("useMojang", String.valueOf(cfg.useMojang)));
                cfg.useCrafatar = Boolean.parseBoolean(props.getProperty("useCrafatar", String.valueOf(cfg.useCrafatar)));
                cfg.useCustomServer = Boolean.parseBoolean(props.getProperty("useCustomServer", String.valueOf(cfg.useCustomServer)));
                cfg.hostCustomServer = props.getProperty("hostCustomServer", cfg.hostCustomServer);
                return cfg;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        props.setProperty("useMojang", String.valueOf(cfg.useMojang));
        props.setProperty("useCrafatar", String.valueOf(cfg.useCrafatar));
        props.setProperty("useCustomServer", String.valueOf(cfg.useCustomServer));
        props.setProperty("hostCustomServer", cfg.hostCustomServer);

        try {
            Files.createDirectories(configPath.getParent());
            try (OutputStream out = Files.newOutputStream(configPath)) {
                props.store(out, "OfflineSkins Ornithe 1.8.9 Configuration");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return cfg;
    }
}
