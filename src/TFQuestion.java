//subclass of Question and presents a True/False question
public class TFQuestion extends Question{

	private boolean answer;
	
	//default contractor
	public TFQuestion() {
		
	};
	
	//The constructor TFQuestion(String qText, boolean answer, double point) creates an TFQuestion object by setting the qText, answer, and point by reading the
	//qText, Answer, and Point fields from the database
	
	public TFQuestion(String qText, boolean answer, double point) {
		setqText(qText);
		setPoint(point);
		this.answer = answer;
	};
	
	//The grade method returns the points of the question if the parameter, which can be “T” or
	//“F”, is equal to the instance variable answer, zero otherwise
	
	@Override
	public double grade(String answer) {

		String answerPrint = "";
		
		if (getCorrectAnswer().equals("T")) {			
			answerPrint = "True";
		}else if (getCorrectAnswer().equals("F")) {
			answerPrint = "False";
		}
				
		if(getCorrectAnswer().equals(answer)){
			System.out.println("You are correct!");
			return getPoint();
		}
		else {
			System.out.println("You are wrong. The correct answer is " + answerPrint.toLowerCase());					
		}
		return 0;
	}

	//The grade method returns the points of the question if the parameter, which can be “T” or
	//“F”, is equal to the instance variable answer, zero otherwise
	
	@Override
	public String getCorrectAnswer() {

		if(answer == true) {
			return "T";
		}else {
			return "F";
		}		
	}

	public boolean isAnswer() {
		return answer;
	}

	public void setAnswer(boolean answer) {
		this.answer = answer;
	}
	
	public Boolean getAnswer() {
		return answer;
	}
	
	public String toString() {
		return qText + " (" + getPoint() + ")" + "\n" +  "True(T) or False(F) >>";				
	}

}
