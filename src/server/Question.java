/**
 * 
 * Question Class implementation
 * Adheres to Question Interface
 * Written By Eric McEvoy - 13513267
 * 30/01/2018
 * 
 */
package server;

import interfaces.QuestionInterface;
import server.Question;

//A Question object, complies with Question interface
public class Question implements QuestionInterface {
	private int idNumber; //The Question ID
	private String qInfo; //The Question information
	private String[] answerOptions;
	private int answerIndex;
	private int answerChosenIndex;
	private static int qID = 0;
	
	public Question(String qInfo, String[] options,int answerIndex) {
		this.idNumber = qID++;
		this.qInfo = qInfo; //the question
		this.answerOptions = options;
		this.answerIndex = answerIndex;
	}
	//Return the question number
	public int getQuestionNumber() {
		return this.idNumber;
	}

	//Return the question text
	public String getQuestionDetail() {
		return this.qInfo;
	}

	//Return the possible answers to select from
	public String[] getAnswerOptions() {
		return this.answerOptions;
	}
	
	//Returns correct answer index
	public int getAnswerIndex() {
		return this.answerIndex;
	}
	
	public void setChosenAnswerIndex(int optionNumber) {
		this.answerChosenIndex = optionNumber;
	}
	
	public int getChosenAnswerIndex() {
		return this.answerChosenIndex;
	}
	
	public boolean answerCorrect() {
		//return true if answer chosen if same as answer specified
		return (this.answerIndex == this.answerChosenIndex);
	}
	
	public String toString() {
		System.out.println("Question : "+this.qInfo);
		System.out.println("Options : "+this.answerOptions.toString());
		return "";
	}
	
}
