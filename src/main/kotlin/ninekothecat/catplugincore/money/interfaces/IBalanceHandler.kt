package ninekothecat.catplugincore.money.interfaces

import ninekothecat.catplugincore.money.enums.TransactionResult
import java.util.*

/**
 * the balance handler interface
 */
interface IBalanceHandler {
    /**
     * does a transaction and returns the result
     *
     * @param transaction the transaction
     * @return the transaction result
     */
    fun doTransaction(transaction: ITransaction): TransactionResult
    fun userExists(user: UUID): Boolean
    fun getBalance(user: UUID): Double
}