package ninekothecat.catplugincore.translation

data class Category(
    val name: String,
    val root: Boolean = false,
    val templates: MutableMap<String, Template>,
    val subcategories: MutableMap<String, Category>

)

data class Template(
    val strings: List<String>,
    val replaceIDs: List<List<String>>
)


data class Token(
    val tokenType: TokenType,
    val value: String? = null,
    val linkedToken: Token? = null,
    val tokensLinkingToThisOne: MutableList<Token> = mutableListOf()
) {
    init {
        linkedToken?.tokensLinkingToThisOne?.add(this)
    }

    override fun toString(): String {
        return "value: $value \n" +
                "linkedToken: ${linkedToken?.tokenType}"
    }


}

enum class TokenType {
    ROOT,
    VALUE_NAME_OPEN,
    VALUE_NAME_CLOSE,
    VALUE_ASSIGN,
    VALUE_ASSIGN_END,
    STRING_OPEN,
    STRING_CLOSE,
    STRING_CONCAT_OPEN,
    STRING_CONCAT_CLOSE,
    CATEGORY_OPEN,
    CATEGORY_CLOSE
}

interface Stack<T : Any> {
    fun pop(): T?
    fun pushAll(other: Stack<T>)
    fun push(element: T)
    fun peek(): T?
    fun clear()
    val count: Int
    val isEmpty: Boolean
        get() = count == 0
}

fun <T : Any> Stack<T>.toList(): List<T> {
    var pop = this.pop()
    val list: MutableList<T> = mutableListOf()
    while (pop != null) {
        list.add(pop)
        pop = this.pop()
    }
    return list
}

class ArrayStack<T : Any> : Stack<T> {
    private val storage = arrayListOf<T>()
    override val count: Int
        get() = storage.size

    override fun pop(): T? {
        return storage.removeLastOrNull()
    }

    override fun push(element: T) {
        storage.add(element)
    }

    override fun peek(): T? {
        return storage.lastOrNull()
    }

    override fun clear() {
        storage.clear()
    }

    override fun pushAll(other: Stack<T>) {
        other.toList().forEach {
            this.push(it)
        }
    }


}