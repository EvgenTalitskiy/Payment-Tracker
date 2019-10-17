import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Класс работы с введенными проводками
 * Хранит список проводок в виде списка
 */
class LedgerTrans {
    /**
     * Список проводок
     */
    private List<Trans> listTrans = new ArrayList<>();

    /**
     * Метод создания новой проводки
     *
     * @param transData - массив данных с ифнормацией о проводке
     */
    void addTrans(Object[] transData) {
        Trans trans = new Trans();
        trans.setTrans(String.valueOf(transData[0]), Double.parseDouble(transData[1].toString()));

        listTrans.add(trans);
    }

    /**
     * Метод объединяющий сумму всех заведенных проводок по коду валюты
     *
     * @return возвращает список суммы проводок
     */
    private List<TransSum> getListTransSum() {
        List<TransSum> listTransSum = new ArrayList<>();
        for (Trans trans : listTrans) {
            if (listTransSum.contains(trans)) {
                TransSum transSum = listTransSum.get(listTransSum.indexOf(trans));
                transSum.setTrans(trans.getCurrency(), transSum.getAmount() + trans.getAmount());
            } else {
                TransSum transSum = new TransSum();
                transSum.setTrans(trans);
                listTransSum.add(transSum);
            }
        }
        return listTransSum;
    }

    /**
     * Метод конвертирует список сумм проводок в список строк подготовленных к выводу на консоль
     *
     * @return Вовзращает в виде списка строк просуммированные проводки
     */
    List<String> getLedgerSumGroupCurrencyStr() {
        List<String> transDataSumStr = new ArrayList<>();

        for (TransSum transSum : this.getListTransSum()) {
            if (transSum.getAmount() != 0) {
                transDataSumStr.add(String.format("%s%s", transSum.toString(), this.getConvertedValue(transSum)));
            }
        }

        return transDataSumStr;
    }

    /**
     * Метод исходя из проводки рассчитывает сумму сконвертированную по курсу к валюте по умолчанию (USD)
     *
     * @param trans - проводка
     * @return возвращает сумму по курсу
     */
    private String getConvertedValue(Trans trans) {
        String defaultCurrency = "USD";
        String convertedValue = "";
        try {
            CurrencyConverterCBR currencyConverter = new CurrencyConverterCBR();
            float curExchangeRate = currencyConverter.convert(trans.getCurrency(), defaultCurrency);
            if (curExchangeRate != 0) {
                convertedValue = String.format(" (%s %.4f)", defaultCurrency, curExchangeRate * trans.getAmount());
            }
        } catch (IOException e) {
            e.getMessage();
        } catch (SAXException | ParserConfigurationException e) {
            e.printStackTrace();
        }
        return convertedValue;
    }
}
