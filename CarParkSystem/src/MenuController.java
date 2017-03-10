import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class MenuController extends JPanel implements ActionListener{

	public MenuController()
	{
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
	
}
/*
 * 
 * String[] opt = {"Button","Button","Button","Register","Login"};
		getContentPane().add(buildButtonArea(opt), BorderLayout.WEST);
			JPanel buildButtonArea(String[] options)
	{
		JPanel buttonContainer = new JPanel();
		
		JPanel buttonHolder = new JPanel();
		buttonHolder.setLayout(new BoxLayout(buttonHolder, BoxLayout.Y_AXIS));
		
		buttonHolder.add(Box.createRigidArea(new Dimension(0,45)));
		
		for(int i = 0; i < options.length; i++)
		{
			if(options[i] == "Login")
			{
				login = new JButton(options[i]);
				login = buildSideMenuButtons(login, options[i]);
				buttonHolder.add(login);
				buttonHolder.add(Box.createRigidArea(new Dimension(0,12)));
			} else{
				JButton button = new JButton(options[i]);
				button = buildSideMenuButtons(button, options[i]);
				buttonHolder.add(button);
				buttonHolder.add(Box.createRigidArea(new Dimension(0,12)));
				
			}
		}
		
		buttonContainer.add(buttonHolder);
		
		return buttonContainer;
	}
		*
		*
		*/

 