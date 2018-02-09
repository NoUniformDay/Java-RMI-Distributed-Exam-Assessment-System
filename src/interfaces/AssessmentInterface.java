// Assessment.java 

package interfaces;

import java.util.Date;
import java.util.List;

import exceptions.*;
import server.*;

import java.io.Serializable;

public interface AssessmentInterface extends Serializable {

	// Return information about the assessment	
	public String getInformation();

	// Return the final date / time for submission of completed assessment
	public Date getClosingDate();

	// Return a list of all questions and answer options
	public List<Question> getQuestions();

	// Return one question only with answer options
	public Question getQuestion(int questionNumber) throws 
		InvalidQuestionNumber;

	// Answer a particular question
	public void selectAnswer(int questionNumber, int optionNumber) throws
		InvalidQuestionNumber, InvalidOptionNumber;

	// Return selected answer or zero if none selected yet
	public int getSelectedAnswer(int questionNumber);

	// Return studentid associated with this assessment object
	// This will be preset on the server before object is downloaded
	public int getAssociatedID();
	
	//Additional method to check if Assessment is still up
	public boolean checkAssessmentStillUp();

}



