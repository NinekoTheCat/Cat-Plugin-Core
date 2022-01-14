package ninekothecat.catplugincore.translation

import org.junit.jupiter.api.Test

internal class TokenisersKtTest {

    @Test
    fun tokeniser() {
        tokeniser("let owo = \"\${owo}\";".byteInputStream())
    }
}