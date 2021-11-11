package ninekothecat.catplugincore.utils

import org.bukkit.ChatColor
import org.bukkit.command.CommandSender

fun sendError(string: String, sender: CommandSender) {
    sender.sendMessage(ChatColor.RED + string)
}

private operator fun ChatColor.plus(string: String): String {
    return this.toString() + string
}

private operator fun ChatColor.plus(chatColor: ChatColor): String {
    return this.toString() + chatColor.toString()
}

fun sendSuccess(string: String, sender: CommandSender) {
    sender.sendMessage(ChatColor.GREEN + string)
}