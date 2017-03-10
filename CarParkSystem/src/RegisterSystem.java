import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class RegisterSystem extends JPanel implements ActionListener{

	boolean isAdminUser; // we will need this when we are logged in as a staff member and want to register more staff or members
	JPanel centerPanel; // for passing to other method to get textfields
	
	public RegisterSystem(boolean isAdminUser)
	{ // constructor
		this.isAdminUser = true; 
		this.setLayout(new BorderLayout());
		this.add(buildHeading(), BorderLayout.NORTH);
		this.add(buildRegisterInputArea());
	}
	
	JPanel buildHeading() // builds the heading of the log in area
	{
		JPanel heading = new JPanel(); 
		JLabel header = new JLabel("Register User");
		header.setHorizontalAlignment(JLabel.CENTER);
		heading.add(header);
		return heading;
	}
	
	JPanel buildRegisterInputArea()
	{
		JPanel outerCenterPanel = new JPanel();
		outerCenterPanel.setLayout(new BoxLayout(outerCenterPanel, BoxLayout.Y_AXIS)); // outer panel is set to boxlayout here so buttons go below text fields
		centerPanel = new JPanel();
		if(!isAdminUser){ // if we are creating the admin/staff register, we will want extra fields, so we set a larger grid layout here if admin
		centerPanel.setLayout(new GridLayout(8,8));
		} else {
			centerPanel.setLayout(new GridLayout(10,10));
		}
		String[] fields = {"Username","Password", "First Name", "Second Name", 
				"Address Line 1", "Address Line 2", "County", "Phone"}; // a array of topics we want, done this to use a loop to create the fields and label
		for(int i = 0; i < fields.length; i++)
		{
			centerPanel.add(new JLabel(fields[i])); // adds new jlabel of the field name
			if(i != 1){ // if we are not the password label, we create normal jTextfields
			centerPanel.add(registerSystemTextField(i));
			} else {
				centerPanel.add(registerSystemPasswordField(i)); // else we want a password field
			}
		}
		
		if(isAdminUser) // if admin user, gives the extra fields
		{
			centerPanel.add(new JLabel("Assign Staff Spot"));
			centerPanel.add(buildButtons("Assign Spot"));
		}
		if(isAdminUser) 	// if admin user, gives the extra fields
		{	
			centerPanel.add(new JLabel("Are they staff "));
			centerPanel.add(new JCheckBox("Staff ")); // for use with staff registration
			
		} 
		
				
		
		
		outerCenterPanel.add(centerPanel);
		outerCenterPanel.add(buttonPanel()); // add each to panel and return it
		return outerCenterPanel;
	}
	
	 	
	
	JPanel buttonPanel() // method to create button panel
	{
		JPanel buttonPanel= new JPanel();
		buttonPanel.add(buildButtons("Register User")); 
		buttonPanel.add(buildButtons("Reset"));
		return buttonPanel;
	}
	
	JButton buildButtons(String name) // method that adds string to button and add a actionlistener
	{
		JButton button = new JButton(name);
		button.addActionListener(this);
		return button;
	}
	
	JTextField registerSystemTextField(int id)
	{
		JTextField field = new JTextField(20); // creates Jtextfield
		return field;
	}
	
	JTextField registerSystemPasswordField(int id)
	{
		JPasswordField field = new JPasswordField(20); // password field
		return field;
	}
	
	void getInformationFromTextFields(Component[] components) // finds our jtextfields from our panel
	{
		String[] userInformation ; 
				
				if(isAdminUser){
					userInformation = new String[9]; // string array to store user information
				}
				else{
					userInformation = new String[8];
				}
		int addedId = 0; // where we want to put the item in the array
		
		for(int i = 0; i < components.length; i++)
		{
			if(components[i] instanceof JTextField) // if we find a Jtextfield
			{
				JTextField tempField = (JTextField)components[i]; // create a jtextfield temporary
				userInformation[addedId] = tempField.getText(); // gets the text of the field and puts the value in the correct index in array
				addedId++; // adds to counter so we add to the right index
				
			}
			if(components[i] instanceof JCheckBox){
				JCheckBox tempField = (JCheckBox)components[i]; // create a JCheckBox temporary
				String value = (tempField.isSelected() == true ? "true": "false");
				userInformation[addedId] = value ; // gets the text of the field and puts the value in the correct index in array
				addedId++; // adds to counter so we add to the right index
				System.out.println(value);
			}
		}
		//validateUserInformation(userInformation);
	}
	
	void validateUserInformation(String[] fields) // controller for the register validation
	{
		boolean isValid = true;
		int currentCheck = 0;  // current state on our check
					
		while(isValid && currentCheck < fields.length){ // if we still have valid information and are still checking in the array bounds
			switch(currentCheck){
			case 0 :isValid = ValidationUtil.isEntryLongEnough(fields[0], 4, 12) && ValidationUtil.checkAllLetters(fields[0]); // each case is a different state of check, since if we have one field in not
					currentCheck++;																							// valid, we will not want to continue, if is valid, add to the check state
				break;
			case 1 : isValid = ValidationUtil.isEntryLongEnough(fields[1], 6, 12) && ValidationUtil.atLeastOneCap(fields[1]);
					currentCheck++;
				break;
			case 2 : isValid = ValidationUtil.isEntryLongEnough(fields[2], 1, 12) && ValidationUtil.checkAllLetters(fields[2]);
					currentCheck++;
				break;
			case 3 : isValid = ValidationUtil.isEntryLongEnough(fields[3], 1, 12) && ValidationUtil.checkAllLetters(fields[3]);
					currentCheck++;
				break;
			case 4 : isValid = ValidationUtil.isEntryLongEnough(fields[4], 1, 12);
					currentCheck++;
				break;
			case 5 : isValid = ValidationUtil.isEntryLongEnough(fields[5], 1, 12);
					currentCheck++;
				break;
			case 6 : isValid = ValidationUtil.isEntryLongEnough(fields[6], 1, 12);
					currentCheck++;
				break;
			case 7 : isValid = ValidationUtil.isEntryLongEnough(fields[7], 10, 10) && ValidationUtil.isAllNumberic(fields[7]);
					currentCheck++;
				break;
			default:
				break;
			} 
		} 
		
		if(isValid) // if all our information is valid, we start preparing to add to database
		{
			DatabaseController db = new DatabaseController();
			if(!db.checkForUsernameConflicts(fields[0])) // if our database has no username conflicts
			{
				String [] userInfo = {fields[0], fields[1], fields[2], fields[3], fields[4], fields[5], fields[6], fields[7]};
				addUserToDatabase(userInfo, db); // method that addeds to handles database
			} else{
				JOptionPane.showMessageDialog(null, "A user with that username exists already, please pick another name.", "Username taken", JOptionPane.ERROR_MESSAGE);
			}
		} else {
			WarningUser(currentCheck); // if its invalid information, displays error state to user
		}
	}
	
	void addUserToDatabase(String[] userInformation, DatabaseController database)
	{
		boolean isSuccessful = database.addNewUserToDatabase(userInformation); // boolean if we are successful adding to database
		if(isSuccessful)
		{
			JOptionPane.showMessageDialog(null, "Account created successfully, Thank you.", "Account Created", JOptionPane.INFORMATION_MESSAGE); // message to user
		} else{
			JOptionPane.showMessageDialog(null, "A unknown error has occured, please try again.", "Something went wrong", JOptionPane.ERROR_MESSAGE); // error state
		}
	}
	
	void WarningUser(int failureID) // shows different message dialogs to the user for each error state
	{
		switch(failureID)
		{
			case 1: JOptionPane.showMessageDialog(null, "Usernames must be between 4 and 12 characters long & \nmust be all letters.", "Incorrect username", JOptionPane.ERROR_MESSAGE);
				break;
			case 2:	JOptionPane.showMessageDialog(null, "Passwords must be between 6 and 12 characters long & must have at least one higher case letter", "Incorrect password", JOptionPane.ERROR_MESSAGE);
				break;
			case 3: JOptionPane.showMessageDialog(null, "First names can not be blank and must not contain numbers.", "Incorrect first name", JOptionPane.ERROR_MESSAGE);
				break;
			case 4: JOptionPane.showMessageDialog(null, "Second names can not be blank and must not contain numbers.", "Incorrect second name", JOptionPane.ERROR_MESSAGE);
				break;
			case 5: JOptionPane.showMessageDialog(null, "Addresse's can not be blank.", "Incorrect address line 1", JOptionPane.ERROR_MESSAGE);
				break;
			case 6: JOptionPane.showMessageDialog(null, "Addresse's can not be blank.", "Incorrect address line 2", JOptionPane.ERROR_MESSAGE);
				break;
			case 7: JOptionPane.showMessageDialog(null, "Countys can not be blank.", "Incorrect county", JOptionPane.ERROR_MESSAGE);
				break;
			case 8: JOptionPane.showMessageDialog(null, "Phone numbers must be 10 digits and only contain numbers.", "Incorrect phone number", JOptionPane.ERROR_MESSAGE);
				break;
			default: 
				break;
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Component[] comp = centerPanel.getComponents();
		getInformationFromTextFields(comp);
	
	}
}
