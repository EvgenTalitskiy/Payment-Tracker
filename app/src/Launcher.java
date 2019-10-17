//import java.util.Scanner;

/**
 * Класс точки входа в программу
 */
public class Launcher {
    /**
     * Точка входа в программу
     *
     * @param args - массив параметров запуска (может содержать путь к файлу первичной загрузки)
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        new Launcher().run(args);
    }

    /**
     * Запуск основного класса обработчика Handler
     *
     * @param args - массив параметров запуска для обработки (может содержать путь к файл первичной загрузки)
     * @throws Exception
     */
    private void run(String[] args) throws Exception {
        Handler handler = new Handler(args);
        handler.run();
        handler.close();
    }
}
