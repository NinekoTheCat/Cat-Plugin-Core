package ninekothecat.catplugincore.translation

import org.junit.jupiter.api.Test

internal class ParsersKtTest {

    @Test
    fun parser() {
       println(loadLocaleFile("let owo = \"\${owo}\";".byteInputStream(),"owo"))
    }
}