package server;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import exceptions.InvalidOptionNumber;
import exceptions.InvalidQuestionNumber;
import interfaces.*;

//Assessment object that contains multiple question objects,
//adheres to the Assessment interface
public class Assessment implements AssessmentInterface {
	
	private int studentID; //Student ID associated with this assessment
	private String info; //Information about an assessment
	private String courseCode; //Course giving assessment
	private Date closingDate; //Closing date of assignment
	public List<Question> questions; //list of questions in assessment 
	
	// Needs link to user ? 
	public Assessment(int sID, String inf,String c, ArrayList<Question> qs, Date closingDate){
		this.studentID = sID;
		this.info = inf;
		this.courseCode =c;
		this.questions = qs;
		this.closingDate = closingDate;
	}

	// Return information about the assessment
	public String getInformation() {
		this.toString(); //print assesment details
		for(Question q : questions) {
			q.toString(); //prints assessment questions
		}
		return courseCode;
	}

	// Return the final date / time for submission of completed assessment
	public Date getClosingDate() {
		return this.closingDate;
	}

	// Return a list of all questions and anser options
	public List<Question> getQuestions(){
		return this.questions;
	}

	// Return one question only with answer options
	public Question getQuestion(int questionNumber) throws InvalidQuestionNumber{
		return this.questions.get(questionNumber);
	}

	// Answer a particular question
	public void selectAnswer(int questionNumber, int optionNumber) throws InvalidQuestionNumber, InvalidOptionNumber{
		return;
	}
	
	// Return selected answer or zero if none selected yet
	public int getSelectedAnswer(int questionNumber) {
		Question q = this.questions.get(questionNumber);
		if(q != null) {
			return q.getAnswerIndex();
		}
		return 0;
	}

	// Return studentid associated with this assessment object
	// This will be preset on the server before object is downloaded
	public int getAssociatedID() {
		return this.studentID; 
		// Return current session token/student id
	}
}

