//import java.util.Scanner;

/**
 * ����� ����� ����� � ���������
 */
public class Launcher {
    /**
     * ����� ����� � ���������
     *
     * @param args - ������ ���������� ������� (����� ��������� ���� � ����� ��������� ��������)
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        new Launcher().run(args);
    }

    /**
     * ������ ��������� ������ ����������� Handler
     *
     * @param args - ������ ���������� ������� ��� ��������� (����� ��������� ���� � ���� ��������� ��������)
     * @throws Exception
     */
    private void run(String[] args) throws Exception {
        Handler handler = new Handler(args);
        handler.run();
        handler.close();
    }
}
