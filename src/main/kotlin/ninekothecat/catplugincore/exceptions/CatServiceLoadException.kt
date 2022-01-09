package ninekothecat.catplugincore.exceptions

class CatServiceLoadException(val serviceClassType: Class<*>) : Exception() {
    override val message: String
        get() = "Couldn't Load ${serviceClassType.simpleName}"
}