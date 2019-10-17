import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * ����� ������ � ���������� ����������
 * ������ ������ �������� � ���� ������
 */
class LedgerTrans {
    /**
     * ������ ��������
     */
    private List<Trans> listTrans = new ArrayList<>();

    /**
     * ����� �������� ����� ��������
     *
     * @param transData - ������ ������ � ����������� � ��������
     */
    void addTrans(Object[] transData) {
        Trans trans = new Trans();
        trans.setTrans(String.valueOf(transData[0]), Double.parseDouble(transData[1].toString()));

        listTrans.add(trans);
    }

    /**
     * ����� ������������ ����� ���� ���������� �������� �� ���� ������
     *
     * @return ���������� ������ ����� ��������
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
     * ����� ������������ ������ ���� �������� � ������ ����� �������������� � ������ �� �������
     *
     * @return ���������� � ���� ������ ����� ���������������� ��������
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
     * ����� ������ �� �������� ������������ ����� ����������������� �� ����� � ������ �� ��������� (USD)
     *
     * @param trans - ��������
     * @return ���������� ����� �� �����
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
