
public class ValidationUtil {

	public static boolean checkAllLetters(String text) // method to check if we have only entered letters
	{
		for(int i = 0; i < text.length(); i++) // loops through every character of the string
		{
			char character = text.charAt(i);
			if(Character.isLetter(character)) // checks if letter
			{
				continue; // continue to the next iteration if so
			} else {
				return false; // else we want to go no fuher we break out of the loop and return false
			}
		} 
		return true;
	}
	
	public static boolean isEntryLongEnough(String text, int minLength, int maxLength) // ensure our string is between a certain length
	{
		return	(text.length() >= minLength && text.length() <= maxLength); //returns true or false depending
	}
	
	public static boolean atLeastOneCap(String text) // method to check if theres at least one capital in the string
	{
		if(!text.equals(text.toLowerCase()) && !text.equals(text.toUpperCase())){ // checks that the string does not match all lower case or all higher case,
			return true; // if it doesnt return true, we have a password that has a capital and lower case
		}
		return false; // else false
	}
	
	public static boolean isAllNumberic(String text) // this method checks if we have entered all numbers, similar to the letter method, loops through and
	{													// checks each character, if we have a digit, we continue to the next iteration, else we break out of the loop
		for(int i = 0; i < text.length(); i++)			// as we do not have matchin criteria for the string
		{
			char character = text.charAt(i);
			if(Character.isDigit(character)){
				continue;
			} else{
				return false;
			}
		}
		return true;
	}
}
