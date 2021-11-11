package ninekothecat.catplugincore.utils

import net.milkbowl.vault.economy.Economy
import org.bukkit.plugin.ServicesManager

fun getVaultApiIntegration(servicesManager: ServicesManager): Economy? {
    if (servicesManager.isProvidedFor(Economy::class.java)) {
        return servicesManager.load(Economy::class.java)
    }
    return null
}