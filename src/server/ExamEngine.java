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
public class ExamEngine extends UnicastRemoteObject implements ExamServerInterface {
	
	private static final long serialVersionUID = 1L;
	private List<StudentAccount> studentAccounts; // users StudentAccounts
	private List<Assessment> assessments; // assessments on the server
	private List<Session> sessions, deadSessions; // user sessions, past and present
	
    // Constructor is required
    public ExamEngine() throws RemoteException {
        super();
        
        studentAccounts = new ArrayList();
        assessments = new ArrayList<>();
        sessions = new ArrayList<>();
        deadSessions = new ArrayList<>();

        studentAccounts.add(new StudentAccount(123, "pass1"));
        studentAccounts.add(new StudentAccount(456, "pass2"));
        studentAccounts.add(new StudentAccount(678, "pass3"));
    }

    // Implement the methods defined in the ExamServer interface...
    // Return an access token that allows access to the server for some time period
    public int login(int username, String password) throws 
                UnauthorizedAccess, RemoteException, InvalidLoginException {
	///Loop through the StudentAccounts to find the correct one for given username and password
        for(StudentAccount acc : studentAccounts) {
            if(username == (acc.getStudentID()) && password.equals(acc.getPassword())){
                System.out.println(">> StudentAccount " + acc.getStudentID() + " logged in");
                //Create a new session on successful login, and return ID to the client
                //Session s = new Session(acc);
                //sessions.add(s);
                //System.out.println("Session Token : "+s);
                return 1;//(int) s.sessionToken; //returns unique access token to user for time period
            }
        }
        //Throw exception if login details are not valid
        throw new InvalidLoginException(username,password);	
    }
    
  
    
   
	public Assessment getAssessment(int token, int studentid, String courseCode)
			throws UnauthorizedAccess, NoMatchingAssessment, RemoteException {
		
		return null;
	}

	
	public void submitAssessment(int token, int studentid, Assessment completed)
			throws UnauthorizedAccess, NoMatchingAssessment, RemoteException {
		// TODO Auto-generated method stub
		
	}


    // Return a summary list of Assessments currently available for this studentid
    public List<String> getAvailableSummary(int sessionToken, int studentid) throws
                UnauthorizedAccess, NoMatchingAssessment, RemoteException {

        // TBD: You need to implement this method!
        // For the moment method just returns an empty or null value to allow it to compile
    	    		
        return null;
    }

    
    // Check Student Session is still active.
    private boolean checkSessionActive(long sessID) throws InvalidSessionException{
        //Loop through the sessions
        for(Session s : sessions){
            //Checks if the sessionID passed from client is in the sessions list and active
            if(s.getClientId() == sessID && s.isAlive()) {
                //Prints session details and returns true if session is alive
                System.out.println(">> Session " + s.getClientId() + " running for " + s.getTimeAlive() + "s");
                System.out.println(">> Time Remaining: " + (s.getMaxSessionLength() - s.getTimeAlive()) + "s");
                return true;
            }
            //If session is in list, but timed out, add it to deadSessions list
            //This flags timed out sessions for removeAll
            //They will be removed next time this method is called
            if(!s.isAlive()) {
                System.out.println("\n>> Cleaning up timed out sessions");
                System.out.println(">> SessionID: " + s.getClientId());
                deadSessions.add(s);
            }
        }
        System.out.println();

        // cleanup dead sessions by removing them from sessions list
        sessions.removeAll(deadSessions);

        //throw exception if sessions passed to client is not valid
        throw new InvalidSessionException();
    }

    public static void main(String[] args) {
        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }
        try {
            String name = "ExamServer";
            ExamServerInterface engine = new ExamEngine();
            ExamServerInterface stub =
                (ExamServerInterface) UnicastRemoteObject.exportObject(engine, 0);
            Registry registry = LocateRegistry.getRegistry();
            registry.rebind(name, stub);
            System.out.println("ExamEngine bound");
        } catch (Exception e) {
            System.err.println("ExamEngine exception:");
            e.printStackTrace();
        }
    }
}
