import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import java.io.IOException;
import java.util.ArrayList;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser; 
import javax.xml.parsers.SAXParserFactory;

public class CurrencyConverterCBR implements CurrencyConverter
{
	private static ArrayList<ExchRate> exchRates = new ArrayList<>();
    public float convert(String from, String to) throws IOException, ParserConfigurationException, SAXException
    {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser parser = factory.newSAXParser();
        XMLHandler xmlHandler = new XMLHandler();
        parser.parse("http://www.cbr.ru/scripts/XML_daily.asp", xmlHandler);
        
        float valueFrom = from.equals("RUB") ? 1 : 0;
        float valueTo = to.equals("RUB") ? 1 : 0;
        float ret = 0;
        
        for (ExchRate exchRate : exchRates)
        {
        	if (exchRate.getCurrency().equals(from))
        	{
        		valueFrom = exchRate.getValue();
        	}
        	if (exchRate.getCurrency().equals(to))
        	{
        		valueTo = exchRate.getValue();
        	}
            if (valueFrom != 0 && valueTo != 0)
            {
            	ret = valueFrom/valueTo;
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
    
    private class XMLHandler extends DefaultHandler
    {
    	private String currency, lastElementName;
    	private float nominal;
    	private String value;

        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
            lastElementName = qName;
        }

        @Override
        public void characters(char[] ch, int start, int length) throws SAXException {
            String information = new String(ch, start, length);

            information = information.replace("\n", "").trim();

            if (!information.isEmpty()) {
                if (lastElementName.equals("CharCode"))
                	currency = information;
                if (lastElementName.equals("Value"))
                	value = information.replace(",", ".");
                if (lastElementName.equals("Nominal"))
                	nominal = Float.valueOf(information.replace(",", "."));
            }
        }
        
        @Override
        public void endElement(String uri, String localName, String qName) throws SAXException {
            if ( (currency != null && !currency.isEmpty()) 
            	&& (value != null && !value.isEmpty())
            	&& (nominal != 0))
        	{
            	ExchRate exchRate = new ExchRate();
            	exchRate.setRate(currency, Float.valueOf(value) / nominal);
              	exchRates.add(exchRate);
              	currency = null;
              	value = null;
              	nominal = 0;
            }
        }
    }
}
