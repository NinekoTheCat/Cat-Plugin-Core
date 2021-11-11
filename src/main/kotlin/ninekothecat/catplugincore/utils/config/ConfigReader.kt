package ninekothecat.catplugincore.utils.config;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;

public class ConfigReader {
    public static YamlConfiguration loadConfigurationFromDataFolder(String file, Plugin self) {
        File file1 = new File(self.getDataFolder(), file);
        if (!file1.exists()) {
            self.saveResource(file, false);
        }
        return YamlConfiguration.loadConfiguration(file1);
    }
}
