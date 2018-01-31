package server;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import server.*;

//StudentAccount class, which holds user details, transactions and balance
public class StudentAccount implements Serializable {
    //Instance variables for each StudentAccount object
    private int studentID;
    private String name;
    private String password;
    private int courseCode; //course code links to assessments
    private List<Assessment> assessments;

    //static variable to linearly assignment StudentAccount numbers/studen ID's
    private static int nextAcNum = 88769912;

    public StudentAccount (int sID, String pass) {
        this.assessments = new ArrayList<>();
        this.password = pass;
        this.studentID = sID;
        //increment StudentAccount number, so next one will be updated
        nextAcNum++;
    }

    //add new transactions to the StudentAccount
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
