package server;

import interfaces.QuestionInterface;
import server.Question;

//A Question object, complies with Question interface
public class Question implements QuestionInterface {
	private int idNumber; //The Question ID
	private String qInfo; //The Question information
	private String[] answerOptions;
	private int answerIndex;

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
}
