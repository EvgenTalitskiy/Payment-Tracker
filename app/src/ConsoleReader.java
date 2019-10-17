import java.util.Scanner;

/**
 * ����� ������������� ����� � ������� � �������� ��������� ������ �� ���������
 */
public class ConsoleReader {

    /**
     * ����� ������ � �������
     *
     * @param handler - ����� ��������� ��������� ������
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
