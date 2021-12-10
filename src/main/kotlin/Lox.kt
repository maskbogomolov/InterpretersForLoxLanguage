
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.nio.charset.Charset
import java.nio.file.Paths
import java.nio.file.Files
import java.util.*

open class Lox {

    var hadError = false

    fun main(args: Array<String>) {
        if (args.size > 1) {
            println("Usage: jlox [script]")
            System.exit(64)
        } else if (args.size == 1) {
            runFile(args[0])
        } else {
            runPrompt()
        }

    }
    @Throws(IOException::class)
    private fun runFile(path: String) {
        val bytes = Files.readAllBytes(Paths.get(path))
        run(String(bytes, Charset.defaultCharset()))
        if (hadError) System.exit(65)
    }

    @Throws(IOException::class)
    fun runPrompt(){
        val input = InputStreamReader(System.`in`)
        val reader = BufferedReader(input)

        while (true) {
            print("> ")
            val line = reader.readLine() ?: break
            run(line)
            hadError = false
        }
    }

    private fun run(source: String) {
        val scanner = Scanner(source)
        val tokens: ArrayList<Token> = scanner.scanTokens()
        for (token in tokens) {
            System.out.println(token)
        }
    }

    //handling error
    //This error() function and its report() helper tells the user some syntax
    //error occurred on a given line
      fun error(line: Int, message: String?) {
        if (message != null) {
            report(line, "", message)
        }
    }
    private fun report(line: Int, where: String, message: String, ) {
        System.err.println("[line $line] Error$where: $message")
        hadError = true
    }



}
