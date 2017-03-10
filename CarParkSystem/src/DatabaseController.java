import java.sql.*;

public class DatabaseController {

	String url = "jdbc:mysql://localhost/carparksystem?useSSL=false";
	String user = "root";
	String password = "root";
	
	Connection connectToDatabase() // connects to our database
	{
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.err.println("Class not found"); // errot state
		}
		Connection dbConnection = null;
		
		try {
			dbConnection = DriverManager.getConnection(url,user,password); // gets a connection using device manager
		} catch (SQLException e) {
			System.err.println("Failed To Connect to database");
		}
		return dbConnection;  
	}
	
	int createAccountId(Connection conn) // for use with creation of users. gets the amount of users and adds one to create account_id
	{
		try {
			PreparedStatement statement = conn.prepareStatement("SELECT COUNT(*) FROM users");
			ResultSet result = statement.executeQuery();
			result.next();
			return Integer.parseInt(result.getString(1)) + 1;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1; // failed state, ensures we dont over write a member
	}
	
	public boolean addNewUserToDatabase(String[] information)
	{
		Connection databaseConnection = connectToDatabase(); // takes in an array of user information
		
		try { 
			PreparedStatement statement = databaseConnection.prepareStatement("INSERT INTO users(account_id, username,user_password,first_name,second_name, "
					+ "address_line1, address_line2,county,phone) VALUES(?,?,?,?,?,?,?,?,?)");
			statement.setInt(1, createAccountId(databaseConnection));
			statement.setString(2, information[0]);
			statement.setString(3, information[1]);
			statement.setString(4, information[2]);
			statement.setString(5, information[3]);
			statement.setString(6, information[4]);
			statement.setString(7, information[5]);
			statement.setString(8, information[6]);
			statement.setString(9, information[7]);
			statement.execute(); // creates the statement and executes, if no errors, returns true, else false which is an error state
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} 
	}
	
	int AddToDatabase(String sql) // generic add statement, takes a string of sql but has no way of being prepared, may not be used
	{
		int numOfRowsInserted = 0; 
		try{
			Connection databaseConnection = connectToDatabase();
			PreparedStatement statement = databaseConnection.prepareStatement(sql);
			numOfRowsInserted = statement.executeUpdate();
			statement.close();
			databaseConnection.close();
		} catch(SQLException e){
			System.err.println("Add Query Failed");
			e.getMessage();
		}
		return numOfRowsInserted;
	}
	
	String[][] QueryDatabase(PreparedStatement sql) // takes a prepared statement and returns the results to a result set which
	{												// is converted to a 2d array
		String[][] resultArray = new String[0][0]; 
		
		try{
			Connection databaseConnection = connectToDatabase();	
			ResultSet results = sql.executeQuery();
			ResultSetMetaData  resultMetaData = results.getMetaData(); 
			
			int columns = resultMetaData.getColumnCount(); // for use with initalizing the 2d array, column amount  
			results.last();
			int row = results.getRow(); // gets amount of rows in result
			int counter = 0; 
			results.beforeFirst();
			
			resultArray = new String[row][columns]; 
			while(results.next()) // loops through results and adds them to the 2d array
			{
				for(int i = 1; i <= columns; i++)
				{
					resultArray[counter][i - 1] = results.getString(i);
				}
				counter++;
			}
		} catch(SQLException e)
		{
			System.err.println("Read Query Failed");
			e.getMessage();
		}
		return resultArray; 
	}
	
	int DeleteFromDatabase(String sql) // generic delete method
	{
		int numOfRowsDeleted = 0; 
		try{
			Connection databaseConnection = connectToDatabase();
			PreparedStatement statement = databaseConnection.prepareStatement(sql);
			numOfRowsDeleted = statement.executeUpdate();
			statement.close();
			databaseConnection.close();
		} catch(SQLException e){
			System.err.println("Delete Query Failed");
			e.getMessage();
		}
		return numOfRowsDeleted;
	}
	
	int UpdateFromDatabase(String sql)
	{
		int numOfRowsUpdated = 0; 
		try{
			Connection databaseConnection = connectToDatabase();
			PreparedStatement statement = databaseConnection.prepareStatement(sql);
			numOfRowsUpdated = statement.executeUpdate();
			statement.close();
			databaseConnection.close();
		} catch(SQLException e){
			System.err.println("Update Query Failed");
			e.getMessage();
		}
		return numOfRowsUpdated;
	}
	
	String LoginUser(String userName, String password) // takes in the user name and password
	{
		String loggedInUser = null; // init of string
		try{
			Connection databaseConnection = connectToDatabase();
			PreparedStatement statement = databaseConnection.prepareStatement("SELECT username FROM users WHERE username = ? AND user_password = ?"); // looks within database for match
			statement.setString(1, userName);
			statement.setString(2, password);
			String [][] results = QueryDatabase(statement); // results
	
			if(results.length == 0) // if no members found.
			{
				System.out.println("User not found");
			} else if (results.length == 1){ // if we find one, its the user so we add the name to the string loggedinuser
				loggedInUser = results[0][0];
			} else if(results.length > 1){ // if more this is a probelm that should not have happened. its a broken state
				System.err.println("Login in failed, please contact member of staff");
			}
			
		}catch(SQLException e){
			
		}
		return loggedInUser;
	}
	
	public boolean checkForUsernameConflicts(String username) // when user is registering
	{
		String [][] results = new String[0][0];
		try{
			Connection databaseConnection = connectToDatabase();
			PreparedStatement statement = databaseConnection.prepareStatement("SELECT username FROM users WHERE username = ?"); // looks through the db for the same username
			statement.setString(1, username);
			results = QueryDatabase(statement);
		} catch(SQLException e){
		}
		
		if(results.length >= 1){ // if we find a user,
			return true;
			}
		
		return false; // no user found
	}
}
