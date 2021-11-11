package ninekothecat.catplugincore.money.interfaces

import ninekothecat.catplugincore.money.enums.TransactionType
import org.bukkit.plugin.Plugin
import java.util.*

interface ITransaction {
    /**
     * The interface Transaction.
     */
    /**
     * Gets the plugin that initiated the transaction.
     *
     * @return the plugin
     */
    val plugin: Plugin

    /**
     * Gets the transaction message.
     *
     * @return the message
     */
    val message: String

    /**
     * Gets transaction type.
     *
     * @return the transaction type
     */
    val transactionType: TransactionType

    /**
     * if the server console is the issuer of the transaction returns true.
     *
     * @return isConsole boolean
     */
    val isConsole: Boolean

    /**
     * Gets the initiator of the transaction.
     * can be null
     *
     * @return the initiator
     */
    val initiator: UUID?

    /**
     * Gets the users involved in the transaction.
     * can be null
     *
     * @return the users involved
     */
    val usersInvolved: Collection<UUID?>?

    /**
     * Gets amount.
     *
     * @return the amount
     */
    val amount: Double
}