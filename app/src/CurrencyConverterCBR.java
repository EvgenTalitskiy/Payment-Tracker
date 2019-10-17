import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.io.IOException;
import java.util.ArrayList;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

/**
 * Конвертер валют на основании данных полученых с сервиса ЦБРФ https://www.cbr.ru/
 * Данные с сервиса предоставляются в виде курса рубля по отношению к какой-либо другой валюте
 */
public class CurrencyConverterCBR implements CurrencyConverter {
    /**
     * Список курсов обмена
     */
    private static ArrayList<ExchangeRate> exchangeRates = new ArrayList<>();

    /**
     * Метод определения курса перевода из одной валюты в другую
     *
     * @param from - валюта из
     * @param to   - валюта в
     * @return возвращает курс обмена валют. В случае если кода входящих валют равны вовзвращает 0
     * @throws IOException
     * @throws ParserConfigurationException
     * @throws SAXException
     */
    public float convert(String from, String to) throws IOException, ParserConfigurationException, SAXException {
        if (from.equals(to)) {
            return 0;
        }
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser parser = factory.newSAXParser();
        XMLHandler xmlHandler = new XMLHandler();
        parser.parse("http://www.cbr.ru/scripts/XML_daily.asp", xmlHandler);

        float valueFrom = from.equals("RUB") ? 1 : 0;
        float valueTo = to.equals("RUB") ? 1 : 0;
        float ret = 0;

        for (ExchangeRate exchangeRate : exchangeRates) {
            if (exchangeRate.getCurrency().equals(from)) {
                valueFrom = exchangeRate.getValue();
            }
            if (exchangeRate.getCurrency().equals(to)) {
                valueTo = exchangeRate.getValue();
            }
            if (valueFrom != 0 && valueTo != 0) {
                ret = valueFrom / valueTo;
                break;
            }
        }

        return ret;
    }

//    public static void main(String[] arguments) throws IOException, ParserConfigurationException, SAXException
//    {
//    	CurrencyConverterCBR currencyConverter = new CurrencyConverterCBR();
//        float current = currencyConverter.convert("USD", "RUR");
//        System.out.println(current);
//    }

    /**
     * Класс разбора данных получаемых от сервиса в виде XML и сохранения в список курсов обмена
     */
    private static class XMLHandler extends DefaultHandler {
        private String currency, lastElementName;
        private float nominal;
        private String value;

        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) {
            lastElementName = qName;
        }

        @Override
        public void characters(char[] ch, int start, int length) {
            String information = new String(ch, start, length);

            information = information.replace("\n", "").trim();

            if (!information.isEmpty()) {
                if (lastElementName.equals("CharCode"))
                    currency = information;
                if (lastElementName.equals("Value"))
                    value = information.replace(",", ".");
                if (lastElementName.equals("Nominal"))
                    nominal = Float.parseFloat(information.replace(",", "."));
            }
        }

        @Override
        public void endElement(String uri, String localName, String qName) {
            if ((currency != null && !currency.isEmpty())
                    && (value != null && !value.isEmpty())
                    && (nominal != 0)) {
                ExchangeRate exchangeRate = new ExchangeRate();
                exchangeRate.setRate(currency, Float.parseFloat(value) / nominal);
                exchangeRates.add(exchangeRate);
                currency = null;
                value = null;
                nominal = 0;
            }
        }
    }
}
