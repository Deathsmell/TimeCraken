import java.time.Duration
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

val DAY_PATTERN: DateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yy")
val startTime: LocalDateTime = LocalDateTime.now()
var pointTime = startTime
val date: String = LocalDate.now().format(DAY_PATTERN)
var note = ""

fun main() {
    while (true) {
        println(date + "\t" + getRemainingTime())
        println(note)
        setTaskName()
        setDescription()
        setTime()
        cleanConsole()
    }
}

fun getRemainingTime(): String {
    val time = Duration.between(LocalDateTime.now(), startTime.plusHours(8)).toMinutes()
    return (if (time > 0) "left" else "overtime") + " " + getFormattedTime(time)
}

fun setDescription() {
    print("Enter task description: ")
    val description = readLine() ?: "<No description>"
    note += "\t$description"
}

fun setTaskName() {
    print("Enter task name: ")
    val taskName = readLine() ?: "<N/A>"
    note += "\n${getFormattedTaskName(taskName)}"
}

fun getFormattedTaskName(name: String): String {
    if (name.matches(Regex("\\d+"))) return "MMF-$name"
    return when(name.toLowerCase()) {
        "daily" -> "Daily meeting"
        "arc","architect" -> "Architect meeting"
        "communication","com" -> "Communication"
        "" -> "<N/A>"
        else -> name
    }
}

fun setTime() {
    val minutes = Duration.between(startTime, pointTime).toMinutes()
    note += "\t${getFormattedTime(minutes)}"
    pointTime = LocalDateTime.now()
}

fun getFormattedTime(minutes: Long): String {
    return "${minutes / 60}h ${if (minutes > 0) minutes % 60 else minutes}m"
}

fun cleanConsole() {
    print("\u001B[H\u001B[2J")
}