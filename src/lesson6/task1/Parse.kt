@file:Suppress("UNUSED_PARAMETER", "ConvertCallChainIntoSequence")

package lesson6.task1

import lesson2.task2.daysInMonth
import java.lang.IllegalStateException
import java.lang.StringBuilder

/**
 * Пример
 *
 * Время представлено строкой вида "11:34:45", содержащей часы, минуты и секунды, разделённые двоеточием.
 * Разобрать эту строку и рассчитать количество секунд, прошедшее с начала дня.
 */
fun timeStrToSeconds(str: String): Int {
    val parts = str.split(":")
    var result = 0
    for (part in parts) {
        val number = part.toInt()
        result = result * 60 + number
    }
    return result
}

/**
 * Пример
 *
 * Дано число n от 0 до 99.
 * Вернуть его же в виде двухсимвольной строки, от "00" до "99"
 */
fun twoDigitStr(n: Int) = if (n in 0..9) "0$n" else "$n"

/**
 * Пример
 *
 * Дано seconds -- время в секундах, прошедшее с начала дня.
 * Вернуть текущее время в виде строки в формате "ЧЧ:ММ:СС".
 */
fun timeSecondsToStr(seconds: Int): String {
    val hour = seconds / 3600
    val minute = (seconds % 3600) / 60
    val second = seconds % 60
    return String.format("%02d:%02d:%02d", hour, minute, second)
}

/**
 * Пример: консольный ввод
 */
fun main() {
    println("Введите время в формате ЧЧ:ММ:СС")
    val line = readLine()
    if (line != null) {
        val seconds = timeStrToSeconds(line)
        if (seconds == -1) {
            println("Введённая строка $line не соответствует формату ЧЧ:ММ:СС")
        } else {
            println("Прошло секунд с начала суток: $seconds")
        }
    } else {
        println("Достигнут <конец файла> в процессе чтения строки. Программа прервана")
    }
}


/**
 * Средняя
 *
 * Дата представлена строкой вида "15 июля 2016".
 * Перевести её в цифровой формат "15.07.2016".
 * День и месяц всегда представлять двумя цифрами, например: 03.04.2011.
 * При неверном формате входной строки вернуть пустую строку.
 *
 * Обратите внимание: некорректная с точки зрения календаря дата (например, 30.02.2009) считается неверными
 * входными данными.
 */
val months = listOf(
    "января", "февраля", "марта", "апреля", "мая", "июня", "июля", "августа", "сентября",
    "октября", "ноября", "декабря"
)

fun dateStrToDigit(str: String): String {
    val parts = str.split(" ").toMutableList()
    if (parts.size != 3) return ""
    val day = parts[0]
    val month = (months.indexOf(parts[1]) + 1)
    val year = parts[2]
    if (parts[0].toIntOrNull() == null || parts[2].toIntOrNull() == null) return ""
    if (month == 0) return ""
    if (daysInMonth(month, year.toInt()) < day.toInt()) return ""
    return String.format("%02d.%02d.%d", day.toInt(), month, year.toInt())
}

/**
 * Средняя
 *
 * Дата представлена строкой вида "15.07.2016".
 * Перевести её в строковый формат вида "15 июля 2016".
 * При неверном формате входной строки вернуть пустую строку
 *
 * Обратите внимание: некорректная с точки зрения календаря дата (например, 30 февраля 2009) считается неверными
 * входными данными.
 */
fun dateDigitToStr(digital: String): String {
    val parts = digital.split(".").toMutableList()
    if (parts.size != 3) return ""
    val day = parts[0]
    var month = parts[1]
    val year = parts[2]
    if (day.toIntOrNull() != null && month.toIntOrNull() != null && year.toIntOrNull() != null &&
        daysInMonth(month.toInt(), year.toInt()) >= day.toInt() && month.toInt() in 1..12
    ) {
        month = months[month.toInt() - 1]
        return String.format("%d %s %d", day.toInt(), month, year.toInt())
    }
    return ""
}

/**
 * Средняя
 *
 * Номер телефона задан строкой вида "+7 (921) 123-45-67".
 * Префикс (+7) может отсутствовать, код города (в скобках) также может отсутствовать.
 * Может присутствовать неограниченное количество пробелов и чёрточек,
 * например, номер 12 --  34- 5 -- 67 -89 тоже следует считать легальным.
 * Перевести номер в формат без скобок, пробелов и чёрточек (но с +), например,
 * "+79211234567" или "123456789" для приведённых примеров.
 * Все символы в номере, кроме цифр, пробелов и +-(), считать недопустимыми.
 * При неверном формате вернуть пустую строку.
 *
 * PS: Дополнительные примеры работы функции можно посмотреть в соответствующих тестах.
 */
fun flattenPhoneNumber(phone: String): String {
    val reg = Regex("""\d+|^\+""").findAll(phone)
    val excess = Regex("""[a-z]|[A-Z]|[а-я]|[А-Я]|,|\(\)|\+$|\d.*\+.*""")
    val str = StringBuilder()
    if (!excess.containsMatchIn(phone)) {
        for (i in reg)
            str.append(i.value)
    } else {
        return ""
    }
    if (str.toString().isNotEmpty() && str[0].toString() == "0" || str.length < 2) return ""
    return str.toString()
}

/**
 * * Средняя
 * *
 * Результаты спортсмена на соревнованиях в прыжках в длину представлены строкой вида
 * "706 - % 717 % 703".
 * В строке могут присутствовать числа, черточки - и знаки процента %, разделённые пробелами;
 * число соответствует удачному прыжку, - пропущенной попытке, % заступу.
 * Прочитать строку и вернуть максимальное присутствующее в ней число (717 в примере).
 * При нарушении формата входной строки или при отсутствии в ней чисел, вернуть -1.
 */
fun bestLongJump(jumps: String): Int {
    val regex = Regex("""[$+&!@#^*()_={}|/?":<,;']|[a-z]|[A-Z]|[а-я]|[А-Я]|\\""")
    val reg = Regex("""\d""")
    var max = Int.MIN_VALUE
    if (!reg.containsMatchIn(jumps)) return -1
    if (regex.containsMatchIn(jumps)) return -1
    val p = jumps.split(" ")
    for (parts in p) {
        if (parts.toIntOrNull() != null && parts.toInt() > max) {
            max = parts.toInt()
        }
    }
    return max
}

/**
 * Сложная
 *
 * Результаты спортсмена на соревнованиях в прыжках в высоту представлены строкой вида
 * "220 + 224 %+ 228 %- 230 + 232 %%- 234 %".
 * Здесь + соответствует удачной попытке, % неудачной, - пропущенной.
 * Высота и соответствующие ей попытки разделяются пробелом.
 * Прочитать строку и вернуть максимальную взятую высоту (230 в примере).
 * При нарушении формата входной строки, а также в случае отсутствия удачных попыток,
 * вернуть -1.
 * */
fun bestHighJump(jumps: String): Int {
    val d = Regex("""\s""")
    val regex = Regex("""[$&!@#^*()_={}|/?":<,;']|[a-z]|[A-Z]|[а-я]|[А-Я]|\\""")
    val reg = Regex("""\+""")
    var max = -1
    var i = 1
    var j = 0
    val str = jumps.split(d)
    if (regex.containsMatchIn(jumps)) return -1
    for (k in 1..str.size / 2) {
        if (reg.containsMatchIn(str[i]) && str[j].toIntOrNull() != null && str[j].toInt() > max) max = str[j].toInt()
        i += 2
        j += 2
    }
    return max
}

/**
 * Сложная
 *
 * В строке представлено выражение вида "2 + 31 - 40 + 13",
 * использующее целые положительные числа, плюсы и минусы, разделённые пробелами.
 * Наличие двух знаков подряд "13 + + 10" или двух чисел подряд "1 2" не допускается.
 * Вернуть значение выражения (6 для примера).
 * Про нарушении формата входной строки бросить исключение IllegalArgumentException
 */
fun plusMinus(expression: String): Int {
    val str = expression.split(" ")
    var result: Int
    if (str[0].toIntOrNull() != null) {
        result = str[0].toInt()
    } else throw IllegalArgumentException()
    var i = 1
    val reg = Regex("""^\+|^-|-\s-|\+\s-|-\s\+|\+\s\+""")
    if (expression.contains(reg)) throw IllegalArgumentException()
    while (i != str.size) {
        when {
            str[i] == "+" -> result += str[i + 1].toInt()
            str[i] == "-" -> result -= str[i + 1].toInt()
            else -> throw IllegalArgumentException()
        }
        i += 2
    }
    return result
}

/**
 * Сложная
 *
 * Строка состоит из набора слов, отделённых друг от друга одним пробелом.
 * Определить, имеются ли в строке повторяющиеся слова, идущие друг за другом.
 * Слова, отличающиеся только регистром, считать совпадающими.
 * Вернуть индекс начала первого повторяющегося слова, или -1, если повторов нет.
 * Пример: "Он пошёл в в школу" => результат 9 (индекс первого 'в')
 */
fun firstDuplicateIndex(str: String): Int {
    val string = str.split(" ")
    var i = 0
    var index = -1
    var k = 0
    if (string.size < 2) return index
    while (i != string.size - 1) {
        if (string[i].toUpperCase() == string[i + 1].toUpperCase()) {
            k++
            break
        }
        index += string[i].length + 1
        i++
    }
    if (k != 1) {
        index = -1
    }
    if (index != -1 || i == 0) {
        index++
    }
    return index
}

/**
 * Сложная
 *
 * Строка содержит названия товаров и цены на них в формате вида
 * "Хлеб 39.9; Молоко 62; Курица 184.0; Конфеты 89.9".
 * То есть, название товара отделено от цены пробелом,
 * а цена отделена от названия следующего товара точкой с запятой и пробелом.
 * Вернуть название самого дорогого товара в списке (в примере это Курица),
 * или пустую строку при нарушении формата строки.
 * Все цены должны быть больше либо равны нуля.
 */
fun mostExpensive(description: String): String {
    var max = 0.0
    val a = mutableMapOf<String, Double>()
    var name = ""
    val str = description.split("; ")
    for (parts in str) {
        val s = parts.split(" ")
        if (s.size == 2) {
            a[s[0]] = s[1].toDouble()
        }
    }
    for ((k, v) in a)
        if (v >= max) {
            max = v
            name = k
        }
    return name
}

/**
 * Сложная
 *
 * Перевести число roman, заданное в римской системе счисления,
 * в десятичную систему и вернуть как результат.
 * Римские цифры: 1 = I, 4 = IV, 5 = V, 9 = IX, 10 = X, 40 = XL, 50 = L,
 * 90 = XC, 100 = C, 400 = CD, 500 = D, 900 = CM, 1000 = M.
 * Например: XXIII = 23, XLIV = 44, C = 100
 *
 * Вернуть -1, если roman не является корректным римским числом
 */
fun fromRoman(roman: String): Int = TODO()

/**
 * Очень сложная
 *
 * Имеется специальное устройство, представляющее собой
 * конвейер из cells ячеек (нумеруются от 0 до cells - 1 слева направо) и датчик, двигающийся над этим конвейером.
 * Строка commands содержит последовательность команд, выполняемых данным устройством, например +>+>+>+>+
 * Каждая команда кодируется одним специальным символом:
 *	> - сдвиг датчика вправо на 1 ячейку;
 *  < - сдвиг датчика влево на 1 ячейку;
 *	+ - увеличение значения в ячейке под датчиком на 1 ед.;
 *	- - уменьшение значения в ячейке под датчиком на 1 ед.;
 *	[ - если значение под датчиком равно 0, в качестве следующей команды следует воспринимать
 *  	не следующую по порядку, а идущую за соответствующей следующей командой ']' (с учётом вложенности);
 *	] - если значение под датчиком не равно 0, в качестве следующей команды следует воспринимать
 *  	не следующую по порядку, а идущую за соответствующей предыдущей командой '[' (с учётом вложенности);
 *      (комбинация [] имитирует цикл)
 *  пробел - пустая команда
 *
 * Изначально все ячейки заполнены значением 0 и датчик стоит на ячейке с номером N/2 (округлять вниз)
 *
 * После выполнения limit команд или всех команд из commands следует прекратить выполнение последовательности команд.
 * Учитываются все команды, в том числе несостоявшиеся переходы ("[" при значении под датчиком не равном 0 и "]" при
 * значении под датчиком равном 0) и пробелы.
 *
 * Вернуть список размера cells, содержащий элементы ячеек устройства после завершения выполнения последовательности.
 * Например, для 10 ячеек и командной строки +>+>+>+>+ результат должен быть 0,0,0,0,0,1,1,1,1,1
 *
 * Все прочие символы следует считать ошибочными и формировать исключение IllegalArgumentException.
 * То же исключение формируется, если у символов [ ] не оказывается пары.
 * Выход за границу конвейера также следует считать ошибкой и формировать исключение IllegalStateException.
 * Считать, что ошибочные символы и непарные скобки являются более приоритетной ошибкой чем выход за границу ленты,
 * то есть если в программе присутствует некорректный символ или непарная скобка, то должно быть выброшено
 * IllegalArgumentException.
 * IllegalArgumentException должен бросаться даже если ошибочная команда не была достигнута в ходе выполнения.
 *
 */

fun computeDeviceCells(cells: Int, commands: String, limit: Int): List<Int> {
    val result = mutableListOf<Int>()
    for (i in 0 until cells)
        result.add(0)
    val exception = Regex("""[^ ><\[\]+\-]""")
    var l = 0
    var k = 0
    var d = cells / 2
    if (exception.containsMatchIn(commands)) throw IllegalArgumentException()
    while (k < commands.length) {
        if (l > limit) throw IllegalArgumentException()
        when (commands[k]) {
            '>' -> d++
            '<' -> d--
            '+' -> result[d]++
            '-' -> result[d]--
            ' ' -> l++
        }
        if (commands[k] == '[' && result[d] == 0) {
            val list = mutableListOf<Char>()
            while (commands[k] != ']') {
                list.add(commands[k])
            }
            for (el in list) {
                when (el) {
                    '>' -> d++
                    '<' -> d--
                    '+' -> result[d]++
                    '-' -> result[d]--
                    ' ' -> l++
                }
            }
        }
        if (commands[k] != ' ') l++
        if (commands.length > k) k++
    }
    return result.toList()
}

