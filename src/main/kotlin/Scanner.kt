
import java.util.ArrayList
import java.util.HashMap
import com.sun.tools.example.debug.expr.ExpressionParserConstants.IDENTIFIER

class Scanner(
    private val source: String,
) {
    var tokens: ArrayList<Token> = ArrayList()
    private var start = 0
    private var current = 0
    private var line = 1
    private lateinit var lox: Lox

    //The scanner works its way through the source code, adding tokens until it runs
    //out of characters. Then it appends one final “end of file” token

    fun scanTokens(): ArrayList<Token> {
        while (!isAtEnd()) {
            start = current
            scanTokens()
        }
        tokens.add(Token(TokenType.EOF, "", null, line))
        return tokens
    }

    private fun scanToken() {
        val c: Char = advance()
        // !*+-/=<> <= == // operators
        if (c.isDigit()){
            number()
        }else if (isAlpha(c)){
            identifier()
        }
        when (c) {
            '(' -> addToken(TokenType.LEFT_PAREN)
            ')' -> addToken(TokenType.RIGHT_PAREN)
            '{' -> addToken(TokenType.LEFT_BRACE)
            '}' -> addToken(TokenType.RIGHT_BRACE)
            ',' -> addToken(TokenType.COMMA)
            '.' -> addToken(TokenType.DOT)
            '-' -> addToken(TokenType.MINUS)
            '+' -> addToken(TokenType.PLUS)
            ';' -> addToken(TokenType.SEMICOLON)
            '*' -> addToken(TokenType.STAR)
            '!' -> addToken((if (match('=')) TokenType.BANG_EQUAL else TokenType.BANG))
            '=' -> addToken((if (match('=')) TokenType.EQUAL_EQUAL else TokenType.EQUAL))
            '<' -> addToken((if (match('=')) TokenType.LESS_EQUAL else TokenType.LESS))
            '>' -> addToken((if (match('=')) TokenType.GREATER_EQUAL else TokenType.GREATER))
            '/' ->{
                if (match('/')) {
                    while (peek() != '\n' && !isAtEnd()) advance();
                } else {
                    addToken(TokenType.SLASH);
                }
            }
            ' ' ->{}
            '\r' ->{}
            '\t' ->{}
            '\n' -> line++;
            '"' -> string()
            'o' ->{
                if (peek() == 'r') {
                    addToken(TokenType.OR);
                }
            }
            else -> lox.error(line, "Unexpected character.")
        }
    }

    private fun identifier() {
        while (isAlphaNumeric(peek())) advance()
        val text = source.substring(start, current)
        val type = keywords!![text]
        if (type == null) IDENTIFIER
        addToken(TokenType.IDENTIFIER)
    }

    private fun isAlphaNumeric(c: Char): Boolean {
        return isAlpha(c) || isDigit(c)
    }

    private fun isAlpha(c: Char): Boolean {
        return (c >= 'a' && c <= 'z') ||
                (c >= 'A' && c <= 'Z') ||
                c == '_'
    }

    private fun isDigit(c: Char): Boolean {
        return c >= '0' && c <= '9'
    }

    private fun number() {
        while (isDigit(peek())) advance()

        if (peek() == '.' && isDigit(peekNext())){
            advance()
            while (isDigit(peek())) advance()
        }
        addToken(TokenType.NUMBER,(source.substring(start,current)).toDouble())
    }

    private fun peekNext(): Char {
        return if (current + 1 >= source.length) '\u0000' else source[current + 1]
    }

    private fun string() {
        while (peek() != '"' && !isAtEnd()){
            if (peek() == '\n') line ++
            advance()
        }
        if (isAtEnd()){
            lox.error(line,"Unterminated string.")
            return
        }
        advance()
        val value : String = source.substring(start + 1,current - 1)
        addToken(TokenType.STRING,value)
    }

    private fun peek(): Char {
        return if (isAtEnd()) '\u0000' else source[current]
    }

    private fun match(expected : Char) : Boolean{
        if (isAtEnd()) return false
        if (source[current] != expected) return false

        current++
        return true
    }
    private fun isAtEnd(): Boolean {
        return current >= source.length
    }

    //The advance() method consumes the next character in the source file and
    //returns it. Where advance() is for input, addToken() is for output.
    private fun advance(): Char {
        current++
        return source[current - 1]
    }

    private fun addToken(type: TokenType) {
        addToken(type, null)
    }

    private fun addToken(type: TokenType, literal: Any?) {
        val text = source.substring(start, current)
        tokens.add(Token(type, text, literal, line))
    }
    private var keywords: MutableMap<String, TokenType>? = HashMap()
    init {

        keywords!!["and"] = TokenType.AND
        keywords!!["class"] = TokenType.CLASS
        keywords!!["else"] = TokenType.ELSE
        keywords!!["false"] = TokenType.FALSE
        keywords!!["for"] = TokenType.FOR
        keywords!!["fun"] = TokenType.FUN
        keywords!!["if"] = TokenType.IF
        keywords!!["nil"] = TokenType.NIL
        keywords!!["or"] = TokenType.OR
        keywords!!["print"] = TokenType.PRINT
        keywords!!["return"] = TokenType.RETURN
        keywords!!["super"] = TokenType.SUPER
        keywords!!["this"] = TokenType.THIS
        keywords!!["true"] = TokenType.TRUE
        keywords!!["var"] = TokenType.VAR
        keywords!!["while"] = TokenType.WHILE
    }

}