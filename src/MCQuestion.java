import java.util.ArrayList;
//subclass of Question
public class MCQuestion extends Question {
	
	//options: Each element of the instance variable options refers to an option in this question
	private ArrayList<String> options;
	//answer: Answer saves the correct answer (“A”,“B”, “C”, “D” or “E”) to this question.
	private String answer;
	//Default constructor	
	public MCQuestion() {
		
	};
	//Parameterized constructor for qText
	public MCQuestion(String qText) {
		setqText(qText);
	};
	
	//Parameterized constructor for  all instance variables; creates an MCQuestion object by setting the qText, 
	//adding option to options , setting the correctAnswer to answer and point by reading the QText,
	// Answer, and Point fields from the database
	public MCQuestion(String qText, ArrayList<String> options, double point, String answer) {
		setqText(qText);
		setPoint(point);
		setOptions(options);
		this.answer = answer;
	};
	
	//setOption: Add all options to the instance variable options and set the instance variable answer
	public void setOptions(ArrayList<String> options) {
		this.options = options;
		for(int i=0; i<options.size(); i++) {
			if(options.contains("*")){
				this.answer = Choice.values()[i].toString();
			}
		}
	}
	//Getters and Setters
	public ArrayList<String> getOptions() {
		return options;	
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	//grade: The grade method returns the points of the question if the parameter, which can be “A”, “B”,
	//“C”, …, is equal to the instance variable answer, zero otherwise
	@Override
	public double grade(String answer) {
		
		if(getCorrectAnswer().equals(answer)){
			System.out.println("You are correct!");
			return getPoint();
		}
		else {
			System.out.println("You are wrong. The correct answer is " + getCorrectAnswer());					
		}
		return 0;
	}

	
	// getCorrectAnswer method returns the letter (i.e. “A”, “B”, “C”, …) representing the correct answer
	@Override
	public String getCorrectAnswer() {
		return answer;
	}
	
	
	@Override
	public String toString() {		
		String answer = "";
		for (int i=0; i<options.size(); i++) {
			answer += Choice.values()[i] +": " + options.get(i) + "\n";
		}
		
		return qText +  "(" + getPoint() + ") Points" + "\n" + answer ;
	}

	
}
