import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Console  
{
	private boolean updateScreen = false;
	private long lastUpdateTime = 0;
	private int screenUpdateRate = 60*1000; 
	private List<String> buffer = new ArrayList<>();
	Thread threadConsoleUpdate;
	
	public long parmLastUpdateTime()
	{
		return lastUpdateTime;
	}
	
	public long parmLastUpdateTime(int _lastUpdateTime)
	{
		lastUpdateTime = _lastUpdateTime;
		return lastUpdateTime;
	}
	
	public void setPrintBuffer(List<String> _buffer)
	{
		buffer = _buffer;
	}
	
	public void updateScreen()
	{
		updateScreen = true;
	}
	
	public void runProcessor() 
	{
		Runnable taskConsoleUpdate = new Runnable() 
		{
			public void run()
			{
				while (true)
				{
					if ((System.currentTimeMillis() - lastUpdateTime) > screenUpdateRate)
					{
						updateScreen = true;
					}
					if (updateScreen)
					{
						Object[] bufferArray = buffer.toArray();
						try {
							new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
						} catch (InterruptedException | IOException e) {
							e.printStackTrace();
						}
						for (Object printUnit : bufferArray)
						{
							System.out.println(printUnit);
						}
						System.out.println("--------------------");
						
						lastUpdateTime = System.currentTimeMillis();
						updateScreen = false;
					}
					if (threadConsoleUpdate.isInterrupted())
					{
						break;
					}
				}
			}
		};
		
		threadConsoleUpdate = new Thread(taskConsoleUpdate);
		threadConsoleUpdate.start();
	}

	public void close() 
	{
		threadConsoleUpdate.interrupt(); // TODO why not?
	}
}
