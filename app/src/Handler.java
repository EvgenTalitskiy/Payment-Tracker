import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;

public class Handler 
{
	private Console console;
	private LedgerTrans ledgerTrans;	
	
	public void init(String[] args) throws IOException, ParserConfigurationException, SAXException
	{
		console = new Console();
		ledgerTrans = new LedgerTrans();
		
		if (args.length > 0)
		{
			this.importFile(args[0]);
		}
		console.runProcessor();
	}
	private void importFile(String filePath) throws IOException, ParserConfigurationException, SAXException 
	{
		if (!filePath.equals(""))
		{
			File importFile = new File(filePath);
			if(importFile.exists())
			{
				try 
				{
					FileReader reader = new FileReader(importFile);
			        Scanner scan = new Scanner(reader);
			        
			        while (scan.hasNextLine())
			        {
			        	this.processData(scan.nextLine());
			        }
			 
			        reader.close();
			        scan.close();
				} 
				catch (IOException e) 
				{
					e.getMessage();
				}
			}
		}
	}
	public boolean validateData(String _data)
	{
		boolean ret = true;
		try
		{
			this.convertInputData(_data);
		}
		catch (Throwable t)
		{
			System.err.println(String.format("%s - Неверный формат строки!", _data));
			ret = false;
		}
			
		return ret;
	}
	public void processData(String _data) throws IOException, ParserConfigurationException, SAXException
	{
		if (this.validateData(_data))
		{
			Object[] inputData = this.convertInputData(_data);
			
			ledgerTrans.addTrans(Arrays.asList(inputData));
			console.setPrintBuffer(ledgerTrans.getLedgerSumGroupCurrency());
			//console.updateScreen();
		}
	}
	public Object[] convertInputData(String _data)
	{
		String currency = _data.substring(0, 3).toUpperCase();
		String amountStr = _data.substring(4);
		double amount = Double.valueOf(amountStr);
		
		return new Object[]{currency, amount};
	}
	public void close() {
		console.close();
	}
}
