package Common;

/**
 * Тексты сообщений для классов
 */
public class Messages {
    /**
     * сообщения тестов
     */
    public static class Tests {
        public static String success = "Все тесты пройдены!";
        public static String testError = "Ошибка ожидалось %s получено %s";
        public static String testErrorWithName = "Ошибка в %s ожидалось %s получено %s";
        public static String testIO = "Для тестирования введите строку %s. столбец %s";
    }

    /**
     * собщения в играх
     */
    public static class Games {
        public static String inputFieldSize = "Введите размер поля";
        public static String started = "Игра началась";
        public static String firstPlayer = "Ход первого игрока";
        public static String secondPlayer = "Ход второго игрока";
        public static String playerWon = "Победил игрок %d!";
        public static String draw = "ничья";
    }

    /**
     * тексты исключений
     */
    public static class Exceptions {
        public static String sizeNegative = "Отрицательный размер";
        public static String sizeIncorrect = "Некорректный размер";
        public static String indexIncorrect = "Индекс за пределами массива";
        public static String valueIncorrect = "Некорректное значение";
        public static String binaryEmpty = "Пустое бинарное число";
    }

    public static class Fields {
        public static String sizeError = "указанный рамер не попадает в промежуток от %d до %d";
    }

}
