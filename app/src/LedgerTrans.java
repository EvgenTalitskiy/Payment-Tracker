import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import java.util.HashMap;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class LedgerTrans 
{
	private List<List<Object>> trans = new ArrayList<>();
	
	public void addTrans(List<Object> _transData)
	{		
		trans.add(_transData);
	}
	public List<String> getLedgerSumGroupCurrency() throws IOException, ParserConfigurationException, SAXException
	{
		ListIterator<List<Object>> transIterator = trans.listIterator();
		Map<String, Double> transDataMapSum = new HashMap<String, Double>();
		
		while (transIterator.hasNext())
		{
			List<Object> trandDataList = transIterator.next();
			String keyCurrency = trandDataList.get(0).toString();
			Double valueAmount = Double.valueOf(trandDataList.get(1).toString());
			
			if (transDataMapSum.containsKey(keyCurrency))
			{
				transDataMapSum.replace(keyCurrency, transDataMapSum.get(keyCurrency) + valueAmount);
			}
			else
			{
				transDataMapSum.put(keyCurrency, valueAmount);
			}
		}
		
		List<String> transDataSumStr = new ArrayList<>(); 
		for(Map.Entry<String, Double> item : transDataMapSum.entrySet())
		{
			double valueAmount = item.getValue();
			if (valueAmount != 0)
			{
				String convertedValue = "";
				String defaultCurrency = "USD";
				String curCurrency = item.getKey();
				if (!curCurrency.equals(defaultCurrency))
				{
					try
					{
						CurrencyConverterCBR currencyConverter = new CurrencyConverterCBR();
						float curExchaRate = currencyConverter.convert(curCurrency, defaultCurrency);
						if (curExchaRate != 0)
						{
							convertedValue =  String.format(" (%s %.4f)", defaultCurrency, curExchaRate * item.getValue());
						}
					}
					catch (IOException e) 
					{
						e.getMessage();
					}
				}
				transDataSumStr.add(String.format("%s %.4f%s", curCurrency, item.getValue(), convertedValue));
			}
		}
		
		return transDataSumStr;
	}
}
