import java.util.Scanner;

public class Launcher 
{	
	public static void main(String[] args) throws Exception
	{
		Launcher launcher = new Launcher();
		
		launcher.run(args);
	}
	public void run(String[] args) throws Exception
	{
		Handler handler = new Handler();
		handler.init(args);

		Scanner in = new Scanner(System.in);
		while (true)
		{
			try
			{		
				if (in.hasNextLine())
				{
					String inputData = in.nextLine();
					handler.processData(inputData);
				}
			}
			catch (Throwable t)
			{
				System.out.println(t.getStackTrace());
				break;
			}
		}
		in.close();
		handler.close();
	}
}
