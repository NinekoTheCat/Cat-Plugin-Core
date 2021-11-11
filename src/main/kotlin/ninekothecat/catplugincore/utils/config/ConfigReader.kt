package ninekothecat.catplugincore.utils.config

import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.plugin.Plugin
import java.io.File

    fun loadConfigurationFromDataFolder(file: String, self: Plugin): YamlConfiguration {
        val file1 = File(self.dataFolder, file)
        if (!file1.exists()) {
            self.saveResource(file, false)
        }
        return YamlConfiguration.loadConfiguration(file1)
    }