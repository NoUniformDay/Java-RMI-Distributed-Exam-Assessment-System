/**
 * 
 * ExamEngine Class implementation
 * Adheres to ExamServer Interface
 * Written By Eric McEvoy - 13513267
 * 01/02/2018
 * 
 */

package server;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

import exceptions.*;
import interfaces.*;

//Main controller then implements all the methods in the ExamServer interface
public class ExamEngine implements ExamServerInterface {

	private final long serialVersionUID = 1L;
	
	
	private List<StudentAccount> studentAccounts; // users StudentAccounts
	private List<Assessment> assessments; // assessments on the server
	private List<Session> sessions, deadSessions; // user sessions, past and present

	// Constructor is required
	public ExamEngine() throws RemoteException {
		super();
		init(); //method below that adds prepared assessments and questions
	}

	public static void main(String[] args) {
		if (System.getSecurityManager() == null) {
			System.setSecurityManager(new SecurityManager());
		}
		try {
			System.out.println("Inside Exam Server Main");
			String name = "ExamServer";
			ExamServerInterface engine = new ExamEngine();
			ExamServerInterface stub = (ExamServerInterface) UnicastRemoteObject.exportObject(engine, 0);
			System.out.println("Exported Object");
			Registry registry = LocateRegistry.getRegistry(Integer.parseInt(args[0]));
			System.out.println("Located Registry");
			registry.rebind(name, stub);
			System.out.println("Rebinded Successfully");
			System.out.println("ExamEngine Bound and Running");
		} catch (Exception e) {
			System.err.println("ExamEngine exception:");
			e.printStackTrace();
		}
	}

	// Implement the methods defined in the ExamServer interface...
	// Loops through each StudentAccount object and validates user credentials
	// Return an access token or sessionID that allows access to the server for some time period
	public int login(int userID, String password) throws UnauthorizedAccess, RemoteException, InvalidLoginException {
		/// Loop through the StudentAccounts to find the correct one for given username
		/// and password
		for (StudentAccount acc : studentAccounts) {
			if (userID == (acc.getStudentID()) && password.equals(acc.getPassword())) {
				System.out.println(">> StudentAccount " + acc.getStudentID() + " logged in");
				// Create a new session on successful login, and return ID to the client
				Session s = new Session(userID); //New session object linking to student ID
				sessions.add(s);
				System.out.println(">>Session Token : " + s.sessionToken);
				return s.sessionToken; // returns unique access token to user for time period
			}
		}
		// Throw exception if login details are not valid
		throw new InvalidLoginException(userID, password);
	}

	// Returns an Assessment object for a given session, student ID and course code
	public Assessment getAssessment(int token, int studentid, String courseCode)
			throws UnauthorizedAccess, NoMatchingAssessment, RemoteException {
			try {
				System.out.println("Check session for token : "+token);
				if (this.checkSessionActive(token)) { // Client session is active
					for (Assessment assess : assessments) {
						if (assess.getAssociatedID() == studentid && assess.getCourseCode().equals(courseCode)) {
							System.out.println("Found Assessment!");
							return assess; // assessment object with corresponding student id and courseCode
						}
					}
				}
			} catch (InvalidSessionException e) {
				System.out.println("Couldn't validate Token");
				e.printStackTrace();
			}
		return null;
	}
	
	//Submits or saves Assessment object submitted for a given user
	//Check session is still active before doing so
	public void submitAssessment(int token, int studentid, Assessment completed)
			throws UnauthorizedAccess, NoMatchingAssessment, RemoteException {
		try {
			if (this.checkSessionActive(token)) { // Client session is active
				if(completed.checkAssessmentStillUp()) { // Deadline hasn't past
					assessments.add(completed); //add completed assessment to assessment list
					System.out.println("Assessment Submitted Successfully!");
				}else{
					System.out.println("Deadline passed!");
				}
			}
		} catch (InvalidSessionException e) {
			e.printStackTrace();
		}
	}

	// Return a summary list of Assessments currently available for this studentid
	public List<String> getAvailableSummary(int token, int studentid) throws
                UnauthorizedAccess, NoMatchingAssessment, RemoteException {
    		try {
			if(this.checkSessionActive(token)){ //Client session is active
				List<String> summaries = new ArrayList<String>(); //List of assessment summaries
				System.out.println("Getting Available Summary");
				for(Assessment as :  assessments) {
					if(as.getAssociatedID() == studentid) { //if assessment belongs to given student ID
						if(as.checkAssessmentStillUp()) { //if assessment is still up
							String summary = as.getInformation();
							summaries.add(summary);
						}
					}
				}
				return summaries;
			}
		} catch (InvalidSessionException e) {
			e.printStackTrace();
		}
		return null;
    }
    
	// returns access token for given user
	public int getSessionToken(int studentid) throws UnauthorizedAccess, RemoteException {
		for (Session s : sessions) {
			// Checks if the sessionID passed from client is in the sessions list and active
			if (s.getStudentAccount() == studentid && s.isAlive()) {
				return s.sessionToken;
			}
			// If session is in list, but timed out, add it to deadSessions list
			// This flags timed out sessions for removeAll
			// They will be removed next time this method is called
			if (!s.isAlive()) {
				System.out.println("\n>> Cleaning up timed out sessions");
				System.out.println(">> SessionID: " + s.getClientId());
				deadSessions.add(s);
			}
		}
		
		// cleanup dead sessions by removing them from sessions list
		sessions.removeAll(deadSessions);
		return 0;
	}


	// Check Student Session is still active.
	private boolean checkSessionActive(int sessID) throws InvalidSessionException {
		// Loop through the sessions
		for (Session s : sessions) {
			// Checks if the sessionID passed from client is in the sessions list and active
			if (s.getClientId() == sessID && s.isAlive()) {
				// Prints session details and returns true if session is alive
				System.out.println(">> Session " + s.getClientId() + " running for " + s.getTimeAlive() + "s");
				System.out.println(">> Time Remaining: " + (s.getMaxSessionLength() - s.getTimeAlive()) + "s");
				return true;
			}
			// If session is in list, but timed out, add it to deadSessions list
			// This flags timed out sessions for removeAll
			// They will be removed next time this method is called
			if (!s.isAlive()) {
				System.out.println("\n>> Cleaning up timed out sessions");
				System.out.println(">> SessionID: " + s.getClientId());
				deadSessions.add(s);
			}
		}
		System.out.println();

		// cleanup dead sessions by removing them from sessions list
		sessions.removeAll(deadSessions);
		return true;
		// throw exception if sessions passed to client is not valid
		//throw new InvalidSessionException();
	}
	
	// Initialize Student accounts, Assessments, Questions etc
	public void init() {
		
		//Define constructor
		studentAccounts = new ArrayList();
		assessments = new ArrayList<>();
		sessions = new ArrayList<>();
		deadSessions = new ArrayList<>();

		// Add sample Student Accounts
		studentAccounts.add(new StudentAccount(0, "pass0"));
		studentAccounts.add(new StudentAccount(1, "pass1"));
		studentAccounts.add(new StudentAccount(2, "pass2"));
		
		//Create questions
		String[] q1Answers = { "Galway", "Limerick", "Dublin", "Cork" };
		String[] q2Answers = { "Louth", "Dublin", "Kerry", "Armagh" };
		String[] q3Answers = { "Dublin", "Tipperary", "Wicklow", "Down" };
		String[] q4Answers = { "Warsaw", "Dresden", "Berlin", "Frankfurt" };
		String[] q5Answers = { "Donald Trump", "Mike Pence", "Barack Obama", "George Bush" };
		String[] q6Answers = { "Michael Collins", "Buzz Aldrin", "Charles Pembroke", "Neil Armstrong" };
	
		Question q1 = new Question("What is the Capital of Ireland?", q1Answers, 2);
		Question q2 = new Question("What is the Orchard County?", q2Answers, 3);
		Question q3 = new Question("What is the Premier County?", q3Answers, 1);
		Question q4 = new Question("What is the Capital of Germany?", q4Answers, 2);
		Question q5 = new Question("Who is the President of America?", q5Answers, 0);
		Question q6 = new Question("Who was the second man on the moon? ", q6Answers, 3);
		
		//Group questions for assessments
		ArrayList<Question> ass1qs = new ArrayList<Question>();
		ass1qs.add(q1);
		ass1qs.add(q2);
		ass1qs.add(q3);
		ArrayList<Question> ass2qs = new ArrayList<Question>();
		ass2qs.add(q3);
		ass2qs.add(q4);
		ass2qs.add(q5);
		ArrayList<Question> ass3qs = new ArrayList<Question>();
		ass3qs.add(q5);
		ass3qs.add(q6);
		ass3qs.add(q2);
		
		// Create Assessment objects assign to Students and saved to Server to be downloaded
		
		// Create Assessment for student number 0 due in 10, 20 and 30 days!
		Assessment a1 = new Assessment(0,"Assessment 1","CT475",ass1qs, 10);
		Assessment a2 = new Assessment(0,"Assessment 2","CT420",ass2qs, 20);
		Assessment a3 = new Assessment(0,"Assessment 3","CT480",ass3qs, 30);
	
		assessments.add(a1);
		assessments.add(a2);
		assessments.add(a3);
		
		// Create Assessment for student number 1 due in 10, 20 and 30 days!
		Assessment b1 = new Assessment(1,"Assessment 1","CT475",ass1qs, 10);
		Assessment b2 = new Assessment(1,"Assessment 2","CT420",ass2qs, 20);
		Assessment b3 = new Assessment(1,"Assessment 3","CT480",ass3qs, 30);
	
		assessments.add(b1);
		assessments.add(b2);
		assessments.add(b3);
		
		// Create Assessment for student number 2 due in 10, 20 and 30 days!
		Assessment c1 = new Assessment(2,"Assessment 1","CT475",ass1qs, 10);
		Assessment c2 = new Assessment(2,"Assessment 2","CT420",ass2qs, 20);
		Assessment c3 = new Assessment(2,"Assessment 3","CT480",ass3qs, 30);
	
		assessments.add(c1);
		assessments.add(c2);
		assessments.add(c3);
	}
}
