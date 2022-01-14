package ninekothecat.catplugincore.translation

import java.io.InputStream

fun loadLocaleFile(inputStream: InputStream, categoryName: String): Category {
    val category = Category(
        categoryName,
        true,
        templates = mutableMapOf(),
        subcategories = mutableMapOf()
    )
    val token = tokeniser(inputStream)
    parser(token, category)
    return category
}