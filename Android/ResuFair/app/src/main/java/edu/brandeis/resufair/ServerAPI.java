package edu.brandeis.resufair;


import java.io.Serializable;
import java.util.ArrayList;

/*This file is pretending an API getting data from server*/


public class ServerAPI implements Serializable{
    ArrayList<Candidate> result;
    boolean logIn(String username, String password, String userType) {
        try {
            Thread.sleep(1000);                 //1000 milliseconds is one second.
        } catch(InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
        return true;
    }

    Company getCompanyInfo() {
        Company company = new Company("test company1", "781-187-8781 hope ave", "This is a test introduction");
        return company;
    }

    ArrayList<Candidate> getCandidates() {
        result = new ArrayList<>();
        for(int i = 0; i < 9; i ++) {
            result.add(new Candidate("John Shit"+String.valueOf(i), "09/10/190"+String.valueOf(i), "Brendais"+String.valueOf(i)));
        }
        return result;
    }

    boolean addNewCandidate(String email) {
        result.add(new Candidate("new"+email, "8/4/2123", "BDDF college"));
        return true;
    }
}
