public abstract class Question{
	
	//This class represents the generic form of question
	//qText: text of question
	//point: point of a question
	
	protected String qText;
	protected double point;
	
	//default contractor
	public Question() {
		
	};
	
	// Constructor to initialize qText
	public Question(String qText) {
		this.qText = qText;
	}
	
	//Getters and Setters of instance variables
	public String getqText() {
		return qText;
	}
	public void setqText(String qText) {
		this.qText = qText;
	}
	public double getPoint() {
		return point;
	}
	public void setPoint(double point) {
		this.point = point;
	}
	
	//Abstract methods
	//Returns the points of the question if the answer is correct; zero otherwise.
	public abstract double grade(String answer);
	//returns a	String representing the correct answer to the question
	public abstract String getCorrectAnswer();
	
}