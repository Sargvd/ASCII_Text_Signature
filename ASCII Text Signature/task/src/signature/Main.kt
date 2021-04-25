package signature
import java.io.File
import java.lang.Integer.max

fun getHeight(font: String): Int {
    return File("src/signature/$font.txt").useLines { it.first().split(" ")[0].toInt() }
}

fun getChar(letter: Char, font: String): Array<String> {
    val lines = File("src/signature/$font.txt").readLines()
    val height = getHeight(font)
    val l = Array<String>(height) { " ".repeat(if (font == "roman") 10 else 5) }
    for (i in 1 until lines.size step (height + 1)) {
        val (indexLetter, width) = lines[i].split(" ")
        if (indexLetter != letter.toString()) continue
        for (j in 0 until height) {
            l[j] = lines[i + j + 1]
            if (l[j].length < width.toInt()) l[j] += " ".repeat(width.toInt() - l[j].length)
        }
        break
    }
    return l
}

fun getString(str: String, font: String): Array<String> {
    val height = getHeight(font)
    val l = Array<Array<String>>(str.length) { Array(height) {""} }
    for(i in str.indices) l[i] = getChar(str[i], font)
    val out = Array<String> (height) {""}
    for (i in out.indices) {
        for (j in l.indices) out[i] += l[j][i]
    }
    return out
}

fun renderWithBorders(width: Int, text: Array<String>): Array<String> {
    val lOffset = (width - text[0].length - 4)/2
    val rOffset = width - text[0].length - lOffset - 4
    return text.map { "88" + " ".repeat(lOffset) + it + " ".repeat(rOffset) + "88" }.toTypedArray()
}

fun main() {
    print("Enter name and surname: ")
    val name = readLine()!!
    print("Enter person's status: ")
    val status = readLine()!!

    val bName = getString(name, "roman")
    val bStatus = getString(status, "medium")
    val totalWidth = max(bName[0].length, bStatus[0].length) + 8

    println("8".repeat(totalWidth))
    println(renderWithBorders(totalWidth, bName).joinToString("\n"))
    println(renderWithBorders(totalWidth, bStatus).joinToString("\n"))
    println("8".repeat(totalWidth))
}


