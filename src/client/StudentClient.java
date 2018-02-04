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

//Client program, which connects to the bank using RMI and class methods of the remote bank object
public class StudentClient {
    static int serverAddress, serverPort, account;
    static String operation, password;
    static int studentID; //id of logged in student client
    static int sessionID, id=0; //token for logged in student user
    static ExamServerInterface examEng; //Exam Server
    static String courseCode; //new students course code, for querying assessment
    static Date startDate, endDate;
    static ArrayList<String> assessments; //Student assessments (details)
    static Assessment assess;

    public static void main (String args[]) throws UnauthorizedAccess, NoMatchingAssessment {
        try {
            //Parse the command line arguments into the program
            getCommandLineArguments(args);
            //Set up the rmi registry and get the remote bank object from it
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
                		//Returns a sessionID for this Assessment period
                    sessionID = examEng.login(studentID, password);
                    
                    //StudentAccount acc = 
                  
                } catch (RemoteException e) {
                    e.printStackTrace();
                } catch (InvalidLoginException e) {
                    e.printStackTrace();
                }
                break;

            case "getAssessment":
                try {
                    //Retrieves an assessment for logged in user for particular course code e.g "CT475"
                	 	assess = examEng.getAssessment(sessionID, studentID, courseCode);
                	 	System.out.println("getAssessment successful");
                	 	System.out.println("Assesment Downloaded : "+assess.getInformation());
                //Catch exceptions that can be thrown from the server
                } catch (RemoteException e) {
                    e.printStackTrace();
                } catch (NoMatchingAssessment e) {
                    e.printStackTrace();
                }
                break;
                
            // submit and assignment
            case "submitAssessment":
            		try {
            			examEng.submitAssessment(sessionID, studentID, assess);
            			
            		} catch (RemoteException e) {
                        e.printStackTrace();
                } catch (NoMatchingAssessment e) {
                        e.printStackTrace();
                }
			break;
                
            case "getAvailableSummary":
     
				try {
					List summaries = (List) examEng.getAvailableSummary(sessionID, studentID);
					//for(String ass :  summaries) {
						
					//}
					
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
        if(args.length < 4) {
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
                account = Integer.parseInt(args[3]);
                sessionID = Integer.parseInt(args[4]);
                break;
            case "getAssessment":
                //studentID = Integer.parseInt(args[3]);
                //sessionID = Integer.parseInt(args[4]);
                courseCode = args[3];
                break;
            case "submitAssignment":
                account = Integer.parseInt(args[3]);
                sessionID = Integer.parseInt(args[4]);
            break;
        }
    }
}
