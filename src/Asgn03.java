import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Asgn03 {

	private static Connection connection = null;
	private static Statement statement = null;
	private static ResultSet resultSet = null;

	private static final String MS_FILE = "./Question.accdb";
	private static final String DB_URL = "jdbc:ucanaccess://" + MS_FILE;
	private static final String JDBC_DRIVER = "net.ucanaccess.jdbc.UcanaccessDriver";

	public static void main(String[] args) {


		boolean programOn = true;
		String menuSelection;

		while (programOn) {
			Scanner keyboard = new Scanner(System.in);
			displayMenu();

			switch ((getMenuSelection())) {

			case "c":

				switch ((getQuestionSelection())) {

				case "MC":					
					ArrayList<String> opt = new ArrayList<String>();
					String answerMC = "";
					Double pointMC;
					String qtextMC;
					System.out.println("Enter the question text >> ");
					qtextMC = keyboard.nextLine();
					
					int numberOfOptions = getIntegerValue();

					for (int i = 0; i < numberOfOptions; i++) {
						System.out.println(
								"Enter Option " + Choice.values()[i] + " (Start with * for correct answer) >> ");
						if (i < numberOfOptions - 1) {
							answerMC += keyboard.next() + "##";
						} else {
							answerMC += keyboard.next();
						}
					}

					pointMC = getDoubleValue();
					
					insertDB(qtextMC, answerMC, pointMC, "MC");
					break;

				case "TF":
					String qtextTF;
					String answerTF;
					Double pointTF = 0.0;
					System.out.println("Enter the question text >> ");
					qtextTF = keyboard.nextLine();
	
					System.out.println("Answer is True or False? >> ");
					answerTF = getTrueFalse();
					
					pointTF = getDoubleValue();

					insertDB(qtextTF, answerTF, pointTF, "TF");
					break;

				}
			break;
			case "p":
				ArrayList<Question> qst = new ArrayList<Question>();
				qst = readDB();
				System.out.println("Enter your choice >> ");		
				Double cnt = 0.0;
				Double userPoint = 0.0;
				for (Question q : qst) {
					System.out.println(q);
					System.out.println("Enter your choice >> ");
					String userAns = keyboard.next().toUpperCase();
					
					userPoint += q.grade(userAns);

				}
				System.out.println("The quiz ends. Your score is " + userPoint);
				break;

			case "e":
				programOn = false;
				System.out.println("Goodbye!");
				break;

			}
		}

	}

	//Starts the database Connection
	public static void initDB() {

		try {
			Class.forName(JDBC_DRIVER);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		try {
			connection = DriverManager.getConnection(DB_URL);
			statement = connection.createStatement();
		} catch (SQLException e) {

			e.printStackTrace();
		}

	}

	// Reads all values from database and creates Question object
	public static ArrayList<Question> readDB() {
		initDB();
		String SqlStr = "SELECT * FROM Questions ";
		ArrayList<Question> qst = new ArrayList<Question>();
		try {
			resultSet = statement.executeQuery(SqlStr);

			while (resultSet.next()) {
				ArrayList<String> answerList = new ArrayList<String>();
				String qtext = resultSet.getString("QText");
				String answer = resultSet.getString("Answer");
				double point = resultSet.getDouble("Point");
				String type = resultSet.getString("Type");
				boolean answerTF;
				String correctAnswer;

				if (type.contains("MC")) {
					String[] ans = answer.split("##");

					for (int i = 0; i < ans.length; i++) {
						
						answerList.add(ans[i].replace("*", ""));
						if (ans[i].contains("*")) {
							{
								correctAnswer = Choice.values()[i].toString();
							}
							Question tfq = new MCQuestion(qtext, answerList, point, correctAnswer);
							qst.add(tfq);
						}
					}
				}

				else if (type.contains("TF")) {
					if (answer.equals("TRUE")) {
						answerTF = true;
					} else {
						answerTF = false;
					}
					Question tfq = new TFQuestion(qtext, answerTF, point);
					qst.add(tfq);

				}

			}
			return qst;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return qst;

	}

	//Enters new value to database
	public static void insertDB(String QText, String answer, double point, String Type) {

		initDB();

		String SqlStr = "INSERT INTO QUESTIONS (QTEXT, ANSWER, POINT, TYPE) VALUES (" + "'" + QText + "'" + "," + "'"
				+ answer + "'" + "," + "'" + point + "'" + "," + "'" + Type + "'" + ")";
		try {
			statement.executeUpdate(SqlStr);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeDB();
		}
	}

	//Closed database connection
	public static void closeDB() {
		try {
			if (resultSet != null) {
				resultSet.close();
			}
			if (connection != null) {
				connection.close();
			}
			if (statement != null) {
				statement.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	//Display menu
	public static void displayMenu() {
		System.out.println("Please choose (c)reate a question, (p)review or (e)xit >> ");
	}

	//Returns only c, p or e from user
	public static String getMenuSelection() {
		Scanner keyboard = new Scanner(System.in);
		String input = keyboard.nextLine().toLowerCase();
		
		while( (input.equals("c")|| input.equals("p") || input.equals("e")) == false) {
			displayMenu();
			input = keyboard.nextLine().toLowerCase();
		}
		return input;
	}

	
	//Returns only MC or TF from user
	public static String getQuestionSelection() {
		Scanner keyboard = new Scanner(System.in);
		System.out.println("Enter the type of question (MC or TF) >> ");
		String input = keyboard.nextLine().toUpperCase();
		
		while( (input.equals("MC") || input.equals("TF")) == false) {
			System.out.println("Enter the type of question (MC or TF) >> ");
			input = keyboard.nextLine().toUpperCase();
		}
		return input;
	}

	//Returns only True or False from User
	public static String getTrueFalse() {
		Scanner keyboard = new Scanner(System.in);
		String input = keyboard.nextLine().toUpperCase();
		
		while( (input.equals("TRUE") || input.equals("FALSE")) == false) {
			System.out.println("Answer is True or False? >> ");
			input = keyboard.nextLine().toUpperCase();
		}
		return input;
	}
	
	//returns only integer value between 3 and 5 from user
	public static int getIntegerValue() {
		Scanner keyboard = new Scanner(System.in);
		String input;
		boolean checkInteger = false;
		System.out.println("How many options?");
		while (!checkInteger) {

			try {
				int number = keyboard.nextInt();
				if (number > 2 && number < 6) {
					checkInteger = true;
					return number;
				} else {
					System.out.println("Please enter value between 3 and 5");
				}
			} catch (InputMismatchException e) {
				System.out.println("Please enter an integer value");
				// Line seperator
				keyboard.next();
			}
		}
		return 0;
	}
	
	
	//Returns only double value from user
	public static double getDoubleValue() {
		Scanner keyboard = new Scanner(System.in);
		boolean checkInteger = false;
		double number = 0;
		while (!checkInteger) {
			System.out.println("How many points?");

			try {
				number = keyboard.nextDouble();
				checkInteger = true;
			} catch (InputMismatchException e) {
			
			}
		}
		return number;
	}

}













