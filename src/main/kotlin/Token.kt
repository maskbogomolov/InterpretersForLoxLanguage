
//Now we have an object with enough structure to be useful for all of the later
//phases of the interpreter.
open class Token(
    val type: TokenType,
    val lexeme: String,
    val literal: Any?,
    val line: Int)
{
    override fun toString(): String {
        return "$type $lexeme $literal"
    }
}
