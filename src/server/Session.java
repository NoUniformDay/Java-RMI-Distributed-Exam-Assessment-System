/**
 * 
 * Server Class implementation
 * Describes an Object that allows access to the server for a certain time period delay
 * Written By Eric McEvoy - 13513267
 * 30/01/2018
 * 
 */
package server;

import java.io.Serializable;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

//Session Object
//extends TimerTask, a thread that can be called at certain time intervals
//this allows the session to time to be incremented every second, and can be cancelled after 5mins (300s)
public class Session extends TimerTask implements Serializable{
    //Instance variables for each session object
    private int timeAlive;
    private Timer timer;
    private volatile boolean alive;
    private int studentID; //Link session to student user
    public int sessionToken;

    //static variables to specify max session time, and timer delay
    private static final int MAX_SESSION_LENGTH = 600 * 5;
    private static final long DELAY = 1000;

    public Session(int studentID) {
        //generate a random 6 digit sessionToken
        this.sessionToken = (int) ((Math.random()*900000)+100000);
        this.studentID = studentID;
        this.alive = true;
        this.timeAlive = 0;
        //create timer object to allow the task to be scheduled to run every second
        this.timer = new Timer();
        this.startTimer();
        System.out.println(">> Session " + sessionToken + " created\n");
    }

    private void startTimer() {
        //schedule timer to run every second
        this.timer.scheduleAtFixedRate(this, new Date(System.currentTimeMillis()), DELAY);
    }

    @Override
    public void run() {
        //increment the time the session has been alive
        //updates once every second, so it represents the # of seconds the session has been alive for
       // this.timeAlive;
        //if session has been alive for 5 minutes
        if(this.timeAlive == MAX_SESSION_LENGTH) {
            //set alive to false and cancel the timer
            this.alive = false;
            this.timer.cancel();
            System.out.println("\n---------------------------\nSession " + this.sessionToken + " terminated \n---------------------------");
            System.out.println(this);
            System.out.println("---------------------------");
        }
    }

    //Getters and Setters
    public boolean isAlive() {
        return this.alive;
    }

    public int getClientId(){
        return this.sessionToken;
    }

    public int getTimeAlive(){
        return this.timeAlive;
    }

    public int getMaxSessionLength(){
        return MAX_SESSION_LENGTH;
    }

    public int getStudentAccount(){
        return this.studentID;
    }

    @Override
    public String toString() {
        return "StudentAccount: " + this.studentID + "\nSession Token: " +
                this.sessionToken +"\nTime Alive: " + this.timeAlive + "\nAlive: " + this.alive;
    }
}
