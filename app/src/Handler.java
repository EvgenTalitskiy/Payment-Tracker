import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

/**
 * ����� Handler �������� �������� ������ ���������
 */
public class Handler {
    /**
     * ������ ������ ������ � �������
     */
    private ConsoleWriter consoleWriter = new ConsoleWriter();
    /**
     * ������ ������ ������ � ����������
     */
    private LedgerTrans ledgerTrans = new LedgerTrans();

    /**
     * ����������� - �������� ������ �������
     *
     * @param args - ������ ���������� ������� ��������� (����� ��������� ���� � ����� ��������� ��������)
     */
    public Handler(String[] args) {
        if (args.length > 0) {
            this.importFile(args[0]);
        }
    }

    /**
     * ����� ���������� ��������� ����� ��������� �������� ������
     *
     * @param filePath - ���� � ����� ��������� ��������
     */
    protected void importFile(String filePath) {
        if (!filePath.equals("")) {
            File importFile = new File(filePath);
            if (importFile.exists()) {
                try {
                    FileReader reader = new FileReader(importFile);
                    Scanner scan = new Scanner(reader);

                    while (scan.hasNextLine()) {
                        this.processData(scan.nextLine());
                    }

                    reader.close();
                    scan.close();
                } catch (IOException e) {
                    e.getMessage();
                }
            }
        }
    }

    /**
     * �������� ������������������� ��������� ������ �� ���������� ������
     *
     * @param _data - ������������������� ������ � �������
     * @return - ��������� �������� ������. true - ������ ���������
     */
    protected boolean validateData(String _data) {
        boolean ret = true;
        try {
            this.convertInputData(_data);
        } catch (Throwable t) {
            System.err.println(String.format("%s - �������� ������ ������!", _data));
            ret = false;
        }

        return ret;
    }

    /**
     * ����� ��������� ��������� ���������� � �������
     *
     * @param _data - ������������������� ������ � �������
     */
    protected void processData(String _data) {
        if (this.validateData(_data)) {
            Object[] inputData = this.convertInputData(_data);

            ledgerTrans.addTrans(inputData);
            consoleWriter.setPrintBuffer(ledgerTrans.getLedgerSumGroupCurrencyStr());
            //console.updateScreen();
        }
    }

    /**
     * ��������������� ��������� � ������� ������ � ������ {������, �����}
     *
     * @param _data - ������������������� ������ � �������
     * @return ��������������� ������ � ������ {������, �����}
     */
    protected Object[] convertInputData(String _data) {
        String currency = _data.substring(0, 3).toUpperCase();
        String amountStr = _data.substring(4);
        amountStr = amountStr.replace(",", ".");
        double amount = Double.parseDouble(amountStr);

        return new Object[]{currency, amount};
    }

    /**
     * �������� �������� ������
     * ���� ����� �� ����� ������, ����� ���������� ������� ��� �� �� ����� ��������
     */
    public void close() {
        consoleWriter.close();
    }

    /**
     * ����� ������� �����������
     * ��������� ������ � ������� � ����� �� �������
     */
    public void run() {
        consoleWriter.runProcessor();
        new ConsoleReader().run(this);
    }
}
