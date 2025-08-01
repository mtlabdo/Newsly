package com.instantsystem.common.core.text

sealed class UIText {
    data class DynamicString(val value: String) : UIText()
    data class StringResource(
        val key: TextKey,
        val args: List<Any> = emptyList()
    ) : UIText()

    object Empty : UIText()

    companion object {
        fun resource(key: TextKey, vararg args: Any): UIText {
            return StringResource(key, args.toList())
        }
        fun dynamic(value: String): UIText {
            return if (value.isBlank()) Empty else DynamicString(value)
        }
    }
}
enum class TextKey {

}