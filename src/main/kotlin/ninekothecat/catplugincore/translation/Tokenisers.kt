package ninekothecat.catplugincore.translation

import java.io.InputStream

fun tokeniser(inputStream: InputStream): Token {
    val root = Token(TokenType.ROOT)
    var data = inputStream.read()
    var character: Char
    var characterBuffer = ""
    val tokens: MutableList<Token> = emptyList<Token>().toMutableList()
    val categoryTokens: Stack<Token> = ArrayStack()
    val lastValueScope: Stack<Token> = ArrayStack()
    categoryTokens.push(root)
    while (data != -1) {
        character = data.toChar()
        val parentCategory = categoryTokens.peek()

        val lastValueItem = lastValueScope.peek()
        when (character) {
            '\\' -> {
                data = inputStream.read()
                if (data == -1) break
                character = data.toChar()
            }
            '{' -> {
                val token = Token(
                    tokenType = TokenType.CATEGORY_OPEN,
                    value = characterBuffer,
                    linkedToken = parentCategory ?: root
                )
                tokens.add(token)
                categoryTokens.push(token)
                characterBuffer = ""
                data = inputStream.read()
                continue
            }
            '}' -> {
                val lastCategoryToken = categoryTokens.pop()
                tokens.add(Token(tokenType = TokenType.CATEGORY_CLOSE, linkedToken = lastCategoryToken))
                characterBuffer = ""
                data = inputStream.read()
                continue
            }
            ';' -> {
                lastValueScope.clear()
                val token = Token(tokenType = TokenType.VALUE_ASSIGN_END, linkedToken = lastValueItem)
                tokens.add(token)
                characterBuffer = ""
                data = inputStream.read()
                continue
            }
            '=' -> {
                lastValueScope.push(
                    Token(
                        tokenType = TokenType.VALUE_NAME_CLOSE,
                        linkedToken = lastValueItem,
                        value = characterBuffer
                    )
                )
                val token = Token(
                    tokenType = TokenType.VALUE_ASSIGN,
                    linkedToken = lastValueScope.peek(),
                    value = characterBuffer
                )
                lastValueScope.push(token)
                tokens.add(token)
                characterBuffer = ""
                data = inputStream.read()
                continue
            }

            '"' -> {
                characterBuffer = ""
                val stringStartToken = Token(tokenType = TokenType.STRING_OPEN, linkedToken = lastValueItem)
                val parsedString = stringTokeniser(inputStream, stringStartToken)
                tokens += parsedString.toList()
                data = inputStream.read()
                continue
            }
            'l' -> {
                var internalStringBuffer = ""
                internalStringBuffer += character
                data = inputStream.read()
                character = data.toChar()
                if (character != 'e') {
                    data = inputStream.read()
                    characterBuffer += internalStringBuffer
                    continue
                }
                internalStringBuffer += character
                data = inputStream.read()
                character = data.toChar()
                if (character != 't') {
                    data = inputStream.read()
                    characterBuffer += internalStringBuffer
                    continue
                }
                internalStringBuffer += character
                data = inputStream.read()
                character = data.toChar()
                if (character != ' ') {
                    data = inputStream.read()
                    characterBuffer += internalStringBuffer
                    continue
                }
                val token = Token(TokenType.VALUE_NAME_OPEN, linkedToken = parentCategory)
                tokens.add(token)
                lastValueScope.push(token)
                data = inputStream.read()
                characterBuffer = ""
                continue

            }
        }
        if (!character.isWhitespace()) {
            characterBuffer += character
        }

        data = inputStream.read()
    }
    inputStream.close()
    return root
}

fun stringTokeniser(inputStream: InputStream, rootToken: Token): Stack<Token> {
    var data = inputStream.read()
    var stringBuffer = ""
    val stringTokenStack: Stack<Token> = ArrayStack()
    stringTokenStack.push(rootToken)
    while (data != -1) {
        var char = data.toChar()
        val linkedToken = stringTokenStack.peek()
        when (char) {
            '\\' -> {
                data = inputStream.read()
                if (data == -1) break
                char = data.toChar()
            }
            '"' -> {
                stringTokenStack.push(
                    Token(
                        TokenType.STRING_CLOSE,
                        linkedToken = linkedToken ?: rootToken,
                        value = stringBuffer
                    )
                )
                break
            }
            '$' -> {
                val token = Token(TokenType.STRING_CLOSE, linkedToken = linkedToken, value = stringBuffer)
                stringTokenStack.push(token)
                stringTokenStack.pushAll(concatStringTokeniser(inputStream, token))
                val endToken = Token(TokenType.STRING_OPEN, linkedToken = linkedToken, value = stringBuffer)
                stringTokenStack.push(endToken)
                stringBuffer = ""
                data = inputStream.read()
                continue
            }
        }
        stringBuffer += char
        data = inputStream.read()
    }
    return stringTokenStack
}

fun concatStringTokeniser(inputStream: InputStream, rootToken: Token): Stack<Token> {
    var data = inputStream.read()
    var keyBuffer = ""
    val keyStack = ArrayStack<Token>()
    keyStack.push(rootToken)
    while (data != -1) {
        var char = data.toChar()
        when (char) {
            '\\' -> {
                data = inputStream.read()
                if (data == -1) break
                char = data.toChar()
            }
            '{' -> {
                keyStack.push(Token(TokenType.STRING_CONCAT_OPEN, linkedToken = keyStack.peek() ?: rootToken))
                keyBuffer = ""
                data = inputStream.read()
                continue
            }
            '}' -> {
                keyStack.push(
                    Token(
                        TokenType.STRING_CONCAT_CLOSE,
                        keyBuffer,
                        linkedToken = keyStack.peek() ?: rootToken
                    )
                )
                keyBuffer = ""
                data = inputStream.read()
                continue
            }
            else -> {
                val tokenthing = keyStack.peek() != null
                if (tokenthing) if (keyStack.peek()!!.tokenType == TokenType.STRING_CONCAT_CLOSE) {
                    break
                }

            }
        }
        keyBuffer += char
        data = inputStream.read()
    }
    return keyStack
}