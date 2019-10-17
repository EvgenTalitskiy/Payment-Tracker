import java.util.Scanner;

/**
 * Класс прослушивания ввода с консоли и передачи введенных данных на обработку
 */
public class ConsoleReader {

    /**
     * Метод чтение с консоли
     *
     * @param handler - класс обработки введенных данных
     */
    public void run(Handler handler) {
        Scanner in = new Scanner(System.in);
        while (true) {
            try {
                if (in.hasNextLine()) {
                    String inputData = in.nextLine();
                    handler.processData(inputData);
                }
            } catch (Throwable t) {
                System.out.println(t.getStackTrace());
                break;
            }
        }
        in.close();
    }
}
