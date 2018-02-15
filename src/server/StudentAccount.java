/**
 * 
 * StudentAccount Class implementation
 * Accounts created when instantiating ExamEngine server
 * Written By Eric McEvoy - 13513267
 * 01/02/2018
 * 
 */
package server;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

//StudentAccount class, which holds student credentials, assessments 
public class StudentAccount implements Serializable {
    //Instance variables for each StudentAccount object
    private int studentID;
    private String name;
    private String password;
    private List<Assessment> assessments;

    public StudentAccount (int sID, String pass) {
        this.assessments = new ArrayList<>();
        this.password = pass;
        this.studentID = sID;
    }

    //add a new Assessment to the StudentAccount
    public void addAssessment(Assessment a) {
        this.assessments.add(a);
    }

    //getters and setters
    public int getStudentID() {
        return this.studentID;
    }

    public void setStudentName(String name) {
        this.name = name;
    }


    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Assessment> getAssessment(){
        return this.assessments;
    }

    @Override
    public String toString() {
        return this.name + " " + this.studentID;
    }
}
