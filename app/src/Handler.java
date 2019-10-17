import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

/**
 * Класс Handler содержит основную логику программы
 */
public class Handler {
    /**
     * Объект класса чтение с консоли
     */
    private ConsoleWriter consoleWriter = new ConsoleWriter();
    /**
     * Объект класса работы с проводками
     */
    private LedgerTrans ledgerTrans = new LedgerTrans();

    /**
     * Конструктор - создание нового объекта
     *
     * @param args - массив параметров запуска программы (может содержать путь к файлу первичной загрузки)
     */
    public Handler(String[] args) {
        if (args.length > 0) {
            this.importFile(args[0]);
        }
    }

    /**
     * Метод построчной обработки файла первичной загрузки данных
     *
     * @param filePath - путь к файлу первичной загрузки
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
     * Проверка неотформатированной введенной строки на валидность данных
     *
     * @param _data - неотформатированная строка с консоли
     * @return - результат првоерки строки. true - строка корректна
     */
    protected boolean validateData(String _data) {
        boolean ret = true;
        try {
            this.convertInputData(_data);
        } catch (Throwable t) {
            System.err.println(String.format("%s - Неверный формат строки!", _data));
            ret = false;
        }

        return ret;
    }

    /**
     * Метод обработки введенной информации с консоли
     *
     * @param _data - неотформатированная строка с консоли
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
     * Конвертирование введенной в консоле строки в формат {Валюта, Сумма}
     *
     * @param _data - неотформатированная строка с консоли
     * @return преобразованные данные в массив {Валюта, Сумма}
     */
    protected Object[] convertInputData(String _data) {
        String currency = _data.substring(0, 3).toUpperCase();
        String amountStr = _data.substring(4);
        amountStr = amountStr.replace(",", ".");
        double amount = Double.parseDouble(amountStr);

        return new Object[]{currency, amount};
    }

    /**
     * Закрытие текущего класса
     * Если класс не будет закрыт, поток обновления консоли так же не будет завершен
     */
    public void close() {
        consoleWriter.close();
    }

    /**
     * Класс запуска обработчика
     * Реализует чтение с консоли и вывод на консоль
     */
    public void run() {
        consoleWriter.runProcessor();
        new ConsoleReader().run(this);
    }
}
