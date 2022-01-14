package ninekothecat.catplugincore.translation

fun parser(rootToken: Token, rootCategory: Category) {
    rootToken.tokensLinkingToThisOne.forEach {
        when (it.tokenType) {
            TokenType.VALUE_NAME_OPEN -> {
                val template = templateParser(it)
                rootCategory.templates[template.second] = template.first
            }
            TokenType.CATEGORY_OPEN -> {
                val newCategory = categoryParser(token = it)
                rootCategory.subcategories[newCategory.name] = newCategory
            }
            else -> {
            }
        }
    }
}

fun templateParser(token: Token): Pair<Template, String> {
    val nameEndToken = token.tokensLinkingToThisOne[0]
    checkIfTokenTypeIsCorrect(nameEndToken, TokenType.VALUE_NAME_CLOSE)
    val templateName = nameEndToken.value
    checkIfTokenTypeIsCorrect(nameEndToken.tokensLinkingToThisOne[0], TokenType.VALUE_ASSIGN)
    val stringStart = nameEndToken.tokensLinkingToThisOne[0].tokensLinkingToThisOne[0]
    checkIfTokenTypeIsCorrect(stringStart, TokenType.STRING_OPEN)
    return Pair(stringTokeniser(stringStart), templateName!!)


}

fun stringTokeniser(token: Token): Template {
    checkIfTokenTypeIsCorrect(token, tokenType = TokenType.STRING_OPEN)
    val closeToken = token.tokensLinkingToThisOne[0]
    checkIfTokenTypeIsCorrect(closeToken, tokenType = TokenType.STRING_CLOSE)
    val string: String = closeToken.value!!
    val strings = mutableListOf<String>()
    val replaceID = mutableListOf<List<String>>()
    val nextToken = closeToken.tokensLinkingToThisOne[0]
    strings.add(string)


    when (nextToken.tokenType) {
        TokenType.STRING_CONCAT_OPEN -> {
            val concatString = stringConcatParser(nextToken)
            replaceID.addAll(listOf(concatString.first))
            replaceID.addAll(concatString.second.replaceIDs)
            strings.addAll(concatString.second.strings)
            return Template(strings = strings, replaceIDs = replaceID)
        }
        TokenType.STRING_OPEN -> {
            val stringParser = stringTokeniser(token)
            strings += stringParser.strings
            replaceID += stringParser.replaceIDs
            return Template(replaceIDs = replaceID, strings = strings)
        }
        TokenType.VALUE_ASSIGN_END -> {
            return Template(replaceIDs = replaceID, strings = strings)
        }
        else -> {
            throw IllegalArgumentException(nextToken.toString())
        }
    }
}

fun stringConcatParser(token: Token): Pair<List<String>, Template> {
    val nextToken = token.tokensLinkingToThisOne[0]
    checkIfTokenTypeIsCorrect(nextToken, TokenType.STRING_CONCAT_CLOSE)
    val listOfConcatIds = mutableListOf<String>()
    listOfConcatIds.add(nextToken.value!!)
    if (nextToken.tokensLinkingToThisOne.isNotEmpty()) {
        val nextTokenOfToken = nextToken.tokensLinkingToThisOne[0]

        return when (nextTokenOfToken.tokenType) {
            TokenType.STRING_OPEN -> {
                Pair(listOfConcatIds, stringTokeniser(nextTokenOfToken))
            }
            TokenType.STRING_CONCAT_OPEN -> {
                val concatStringReturns = stringConcatParser(nextTokenOfToken)
                listOfConcatIds.addAll(concatStringReturns.first)
                Pair(listOfConcatIds, concatStringReturns.second)
            }
            else -> {
                throw IllegalArgumentException(nextTokenOfToken.toString())
            }
        }
    } else {
        return Pair(
            listOfConcatIds, Template(
                strings = emptyList(),
                replaceIDs = emptyList()
            )
        )
    }

}

private fun checkIfTokenTypeIsCorrect(token: Token, tokenType: TokenType) {
    if (token.tokenType != tokenType) throw IllegalArgumentException(token.toString())
}

fun categoryParser(token: Token): Category {
    val newCategory = Category(
        token.value ?: "null",
        templates = mutableMapOf(),
        subcategories = mutableMapOf()
    )
    parser(token, newCategory)
    return newCategory
}