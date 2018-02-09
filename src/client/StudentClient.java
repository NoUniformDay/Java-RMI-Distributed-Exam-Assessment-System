package client;

import exceptions.*;
import interfaces.*;
import server.*;

import java.awt.List;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

//Client program, which connects to the bank using RMI and class methods of the remote ExamEngine object
public class StudentClient {
    static int serverAddress, serverPort, account;
    static String operation, password;
    static int studentID; //id of logged in student client
    static int sessionID; //token for logged in student user
    static ExamServerInterface examEng; //Exam Server
    static String courseCode; //course code, for querying assessment
    static Date startDate, endDate;
    static ArrayList<String> assessments; //Student assessments (heading)
    static Assessment assess; //Assessment student is working on
    static int[] submittedAnswers; //Submitted answer variables

    public static void main (String args[]) throws UnauthorizedAccess, NoMatchingAssessment, InvalidQuestionNumber, InvalidOptionNumber {
        try {
            //Parse the command line arguments into the program
        		submittedAnswers = new int[3];
            getCommandLineArguments(args);
            
            //assess = new Assessment(account, courseCode, courseCode, null, account);
            //Set up the rmi registry and get the remote ExamEngine object from it
            String name = "ExamServer";
            Registry registry = LocateRegistry.getRegistry(serverPort);
            examEng = (ExamServerInterface) registry.lookup(name);
            System.out.println("\n----------------\nClient Connected" + "\n----------------\n");
        } catch (InvalidArgumentException ie){
            ie.printStackTrace();
            System.out.println(ie);
        } catch (Exception e){
            e.printStackTrace();
            System.out.println(e);
        }
  
        //Switch based on the operation
        switch (operation){
            case "login":
                try {
                    //Login with studentID and password
                		//Returns a sessionID token certain time period
                    sessionID = examEng.login(studentID, password);
                    System.out.println("Logged in Student ID : "+studentID+ " SessionID: "+sessionID);
                    System.out.println("Use your Session Token to Download, Submit and View Available Assessments!");
                } catch (RemoteException e) {
                    e.printStackTrace();
                } catch (InvalidLoginException e) {
                    e.printStackTrace();
                }
                break;

            case "getAssessment":
                try {
                	
                		//examEng.getSessionID();
                    //Retrieves an assessment for logged in user for particular course code e.g "CT475"
                		System.out.println("Getting Assessment for Student ID : " +studentID+" SessionID : "+sessionID+" Course Code : "+courseCode+"");
                	 	assess = examEng.getAssessment(sessionID, studentID, courseCode);
                	 	if(assess != null) {
                	 		System.out.println("Assesment Downloaded!");
                    	 	assess.toString(); //Print assessment object
                    	 	System.out.println("Submit answers using the Question Number");
                    	 	System.out.println("Answers can be resubmitted multiple times up until the deadline");
                	 	}
                	 	
                //Catch exceptions that can be thrown from the server
                } catch (RemoteException e) {
                    e.printStackTrace();
                } catch (NoMatchingAssessment e) {
                    e.printStackTrace();
                }
                break;
                
            // submit and assignment any amount up until Assessment deadline
            case "submitAssessment":
            		try {
            			System.out.println("Session before get call :"+sessionID);
            			assess = examEng.getAssessment(sessionID, studentID, courseCode);
            			System.out.println("Session after :"+sessionID);
            			System.out.println("Assess downloaded  : "+assess);
            			
            			assess.selectAnswer(0, submittedAnswers[0]); //set selected answers from user
            			assess.selectAnswer(1, submittedAnswers[1]);
            			assess.selectAnswer(2, submittedAnswers[2]);
            			System.out.println("Submmitted Assessment for StudentID: "+studentID+" Session ID :"+sessionID);
            			examEng.submitAssessment(sessionID, studentID, assess); //push assessment object to server
            			System.out.println("Assessment Submitted Successfully!");
            		} catch (RemoteException e) {
                        e.printStackTrace();
                } catch (NoMatchingAssessment e) {
                        e.printStackTrace();
                }
			break;
                
            case "getAvailableSummary":
				try {
					ArrayList<String> summaries = (ArrayList<String>) examEng.getAvailableSummary(sessionID, studentID);
					System.out.println("Available Assessments to User : "+studentID);
					System.out.println("");
					for(String asInfo :  summaries) {
						System.out.println(asInfo);
						System.out.println("");
					}
				} catch (RemoteException e) {
					e.printStackTrace();
				}        			
        		
			break;    
            default:
                //Catch all case for operation that isn't one of the above
                System.out.println("Operation not supported");
                break;
        }
    }

    public static void getCommandLineArguments(String args[]) throws InvalidArgumentException{
        //Makes sure server, port and operation are entered as arguments
        if(args.length < 3) {
            throw new InvalidArgumentException(args.length);
        }

        //Parses arguments from command line
        //arguments are in different places based on operation, so switch needed here
        serverPort = Integer.parseInt(args[1]);
        operation = args[2];
        switch (operation){
            case "login":
                studentID = Integer.parseInt(args[3]);
                password = args[4];
                break;
            case "getAvailableSummary":
            		studentID = Integer.parseInt(args[3]);
            		sessionID = Integer.parseInt(args[4]);
                break;
            case "getAssessment":
            		studentID = Integer.parseInt(args[3]);
                courseCode = args[4];
                sessionID = Integer.parseInt(args[5]);
                break;
            case "submitAssessment":
            		System.out.println("Args size :"+args.length);
            		for(int i =0;i<args.length;i++) {
            			System.out.println("Args : "+i+" "+args[i]);
            		}
            		studentID = Integer.parseInt(args[3]);
            		courseCode = args[4];
            		sessionID = Integer.parseInt(args[5]);
                submittedAnswers[0] = Integer.parseInt(args[6]);
                submittedAnswers[1] = Integer.parseInt(args[7]);
                submittedAnswers[2] = Integer.parseInt(args[8]);
            break;
        }
    }
}
