
public class UtilityFunctions {

	static String buildPasswordToString(char[] charArray)
	{
		String builtString = "";
		for(int i = 0; i < charArray.length; i++)
		{
			builtString += charArray[i];
		}
		return builtString;
	} 
}
