/**
 * 
 * Assessment Class implementation
 * Adheres to Assessment Interface
 * Written By Eric McEvoy - 13513267
 * 31/01/2018
 * 
 */

package server;

import java.util.ArrayList;
import java.util.Calendar;
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
	
	// Constructor
	public Assessment(int sID, String inf,String c, ArrayList<Question> qs, int daysAway){
		this.studentID = sID;
		this.info = inf;
		this.courseCode =c;
		this.questions = qs;
		this.closingDate = createClosingDate(daysAway);
	}
	
	// Sets the Deadline to be a certain amount of dates away
	public Date createClosingDate(int daysAway) {
		Calendar calendar = Calendar.getInstance();
		// add given amount of days day to the date/calendar
	    calendar.add(Calendar.DAY_OF_YEAR, daysAway);
	    Date dueDate = calendar.getTime();
	    return dueDate;
	}
	
	// Checks if the Assessment deadline has passed
	public boolean checkAssessmentStillUp() {
		Date date = new Date();
		if(date.after(this.closingDate)) {
			System.out.println("Passed Deadline, Cannot submit Assessment");
			System.out.println("Time now : "+date);
			System.out.println("Due date : "+this.closingDate);
			return false;
		}
		return true;
	}
	
	// Return information about the assessment
	public String getInformation() {
		String info = this.info+" \n";
		String courseCode = "For Module : "+this.courseCode+" \n";
		String dueDate = "Due "+this.closingDate.toString();
		
		return info+courseCode+dueDate;
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
		Question q = this.getQuestion(questionNumber);
		if(q != null) {
			q.setChosenAnswerIndex(optionNumber);
		}
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
	public String getCourseCode(){
		return this.courseCode;
	}

	// Return student ID associated with this assessment object
	// This will be preset on the server before object is downloaded
	public int getAssociatedID() {
		return this.studentID; 
	
	}
	public String toString() {
		System.out.println("Name : "+this.info);
		System.out.println("Student ID: "+this.studentID);
		System.out.println("Course Code: "+this.courseCode);
		this.questions.toString();
		System.out.println("Due : "+this.closingDate.toString());
		return "";
	}
}

