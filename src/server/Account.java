package server;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import server.*;

//Account class, which holds user details, transactions and balance
public class Account implements Serializable {
    //Instance variables for each account object
    private int accID;
    private String username, password;
    private int accountNumber;
    private int courseCode; //course code links to assessments
    private List<Assessment> assessments;

    //static variable to linearly assignment account numbers/studen ID's
    private static int nextAcNum = 88769912;

    public Account (String uName, String pass) {
        this.assessments = new ArrayList<>();
        this.username = uName;
        this.password = pass;
        this.accountNumber = nextAcNum;
        //increment account number, so next one will be updated
        nextAcNum++;
    }

    //add new transactions to the account
    public void addAssessment(Assessment a) {
        this.assessments.add(a);
    }

    //getters and setters
    public String getUserName() {
        return this.username;
    }

    public void setUserName(String userName) {
        this.username = userName;
    }


    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getAccountNumber() {
        return this.accountNumber;
    }

    public void setAccountNumber(int accountNumber) {
        this.accountNumber = accountNumber;
    }

    public List<Assessment> getAssessment(){
        return this.assessments;
    }

    @Override
    public String toString() {
        return this.accountNumber + " " + this.accID;
    }
}
