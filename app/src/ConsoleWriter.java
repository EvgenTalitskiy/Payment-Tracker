import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * ќбновление информации выводимой на консоль по таймауту
 * ¬ывод на консоль реализован в выделенном потоке
 */
public class ConsoleWriter {
    /**
     * ‘лаг принуждающий выполнить обновление консоли при следующей итерации цикла
     */
    private boolean updateScreen = false;
    /**
     * »нформаци€ о времени последнего обновлени€ консоли (мс)
     */
    private long lastUpdateTime = 0;
    /**
     *  онстанта частоты обновлени€ консоли (мс)
     */
    private int screenUpdateRate = 60 * 1000;
    /**
     * “екстовый буффер, хр€н€щий информацию вывода на консоль
     */
    private List<String> buffer = new ArrayList<>();
    /**
     * ѕоток вывода на консоль
     */
    Thread threadConsoleUpdate;

    /**
     * ћетод возвращает врем€ последнего обновлени€ консоли
     *
     * @return последнее врем€ обновлени€ консоли
     */
    public long parmLastUpdateTime() {
        return lastUpdateTime;
    }

    /**
     * ћетод обновл€ет врем€ последнего обновлени€ консоли
     *
     * @param _lastUpdateTime - врем€ последнего обновлени€ консоли
     * @return последнее врем€ обновлени€ консоли
     */
    protected long parmLastUpdateTime(int _lastUpdateTime) {
        lastUpdateTime = _lastUpdateTime;
        return lastUpdateTime;
    }

    /**
     * ќбновление строкового буффера данных вывода на консоль
     *
     * @param _buffer - буффер строк
     */
    public void setPrintBuffer(List<String> _buffer) {
        buffer = _buffer;
    }

    /**
     * ћетод запуска обновлени€ консоли
     *  онсоль будет обновлена при следующей итерации цикла
     */
    public void updateScreen() {
        updateScreen = true;
    }

    /**
     * ѕроверка возможности запуска обновлени€ консоли
     *
     * @return возвращает результат сравнени€ пройденного времени с момента последнего обновлени€ с константным
     */
    protected boolean canUpdateScreen() {
        return (System.currentTimeMillis() - lastUpdateTime) > screenUpdateRate;
    }

    /**
     * ћетод запуска потока вывода данных в консоль
     */
    public void runProcessor() {
        Runnable taskConsoleUpdate = new Runnable() {
            public void run() {
                while (true) {
                    updateScreen = canUpdateScreen();

                    // ѕауза 1 секунда сделана с целью уменьшени€ нагрузки на систему
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    }

                    // ќбновление консоли
                    if (updateScreen) {
                        Object[] bufferArray = buffer.toArray();
                        // ќчистка консоли
                        try {
                            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
                        } catch (InterruptedException | IOException e) {
                            e.printStackTrace();
                        }
                        for (Object printUnit : bufferArray) {
                            System.out.println(printUnit);
                        }
                        System.out.println("--------------------");

                        lastUpdateTime = System.currentTimeMillis();
                        updateScreen = false;
                    }

                    // «авершение выполнени€ потока по команде
                    if (threadConsoleUpdate.isInterrupted()) {
                        break;
                    }
                }
            }
        };

        threadConsoleUpdate = new Thread(taskConsoleUpdate);
        threadConsoleUpdate.start();
    }

    /**
     * «акрытие текущего класса
     * ѕередаетс€ команда на приостановку выполнени€ потока
     */
    public void close() {
        threadConsoleUpdate.interrupt();
    }
}
