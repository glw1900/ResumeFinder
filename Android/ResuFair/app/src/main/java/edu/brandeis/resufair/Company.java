package edu.brandeis.resufair;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Company {
    public String name;
    public String intro;
    public String contact;
    public JSONArray candidates;

    //{"id":2,"email":"companytest@gmail.com","password":"123","name":"TestCompany","info":"profile test","list":[]}
    public Company(JSONObject jsonObject) {
        try {
            this.name = jsonObject.getString("name");
            this.intro = jsonObject.getString("info");
            this.candidates = jsonObject.getJSONArray("list");
            this.contact = "test";
        } catch (JSONException e) {

        }

    }

    ArrayList<Candidate> getCandidates() {
        ArrayList<Candidate> result = new ArrayList<>();
        for (int i = 0; i < candidates.length(); i++) {
            try {
                JSONObject candidate = candidates.getJSONObject(i);
                result.add(new Candidate(candidate.getString("first_name"), candidate.getString("email"), candidate.getString("id")));
            } catch (JSONException e) {

            }

        }
        return result;
    }
}
