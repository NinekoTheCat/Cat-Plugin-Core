package ninekothecat.catplugincore.money.enums

import net.milkbowl.vault.economy.EconomyResponse.ResponseType

/**
 * The Transaction result.
 */
enum class TransactionResult {
    /**
     * Lack of permissions
     */
    LACK_OF_PERMS,

    /**
     * Insufficient amount of currency.
     */
    INSUFFICIENT_AMOUNT_OF_CURRENCY,

    /**
     * User does not exist.
     */
    USER_DOES_NOT_EXIST,

    /**
     * User already exists transaction result.
     */
    USER_ALREADY_EXISTS,  //    /**
    //     * User is banned.
    //     */
    //    USER_IS_BANNED,
    /**
     * Illegal transaction, something went wrong.
     */
    ILLEGAL_TRANSACTION,

    /**
     * Internal error something went really wrong,
     */
    INTERNAL_ERROR,

    /**
     * the transaction is successful.
     */
    SUCCESS;

    companion object {
        /**
         * To economy response type economy response . response type.
         *
         * @param result the result
         * @return the economy response . response type
         */
        fun toEconomyResponseType(result: TransactionResult): ResponseType {
            return if (result == SUCCESS) {
                ResponseType.SUCCESS
            } else ResponseType.FAILURE
        }
    }
}