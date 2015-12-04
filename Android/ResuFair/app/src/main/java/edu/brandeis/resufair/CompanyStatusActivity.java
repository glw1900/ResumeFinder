package edu.brandeis.resufair;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CompanyStatusActivity extends AppCompatActivity {

    private ServerAPI server;
    private Company company;
    private ListView listview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_status);
        server = ServerAPI.getInstance(this);


        this.listview = (ListView) findViewById(R.id.company_status_list_view);
        refreshCompanyInfo();
        listview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view,
                                           int position, long id) {
                removeItemFromList(position);
                return true;
            }

        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.company_options_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.new_company_candidate:
                AddNewCandidate();
                return true;
            case R.id.edit_company_profile:
//                Intent intent = new Intent(this, EditPassword.class);
//                startActivity(intent);
            default:
                return false;
        }
    }

    private void refreshCompanyInfo() {
        server.getCompany(new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                company = new Company(response);

                CandidateArrayAdapter temp = new CandidateArrayAdapter(CompanyStatusActivity.this, R.layout.candidate_listview_entry, company.getCandidates());
                listview.setAdapter(temp);
                TextView nameView = (TextView) findViewById(R.id.company_name_status);
                TextView contactView = (TextView) findViewById(R.id.company_address_status);
                TextView introView = (TextView) findViewById(R.id.company_intro_status);
                nameView.setText(company.name);
                contactView.setText(company.contact);
                introView.setText(company.intro);
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(CompanyStatusActivity.this, "Something wrong, try again", Toast.LENGTH_LONG).show();

            }
        });

    }
    private void AddNewCandidate() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Put in candidate's email");

        //edit the input textview
        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);

        builder.setView(input);
        builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String candidateEmail = input.getText().toString();
                server.addNewCandidate(candidateEmail, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Toast.makeText(CompanyStatusActivity.this, response.getString("status"), Toast.LENGTH_SHORT).show();
                            CompanyStatusActivity.this.refreshCompanyInfo();
                        } catch (JSONException e) {
                            Toast.makeText(CompanyStatusActivity.this, "Transmission Error", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(CompanyStatusActivity.this, "Response Error", Toast.LENGTH_SHORT).show();

                    }
                });
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();

    }
    protected void removeItemFromList(final int position) {
        AlertDialog.Builder alert = new AlertDialog.Builder(
                CompanyStatusActivity.this);

        alert.setTitle("Delete");
        alert.setMessage("Do you want remove this candidate?");
        alert.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // main code on after clicking yes
                Candidate candidate = (Candidate) listview.getAdapter().getItem(position);
                server.deleteCandidate(candidate.birthday, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Toast.makeText(CompanyStatusActivity.this, response.getString("status"), Toast.LENGTH_SHORT).show();
                            CompanyStatusActivity.this.refreshCompanyInfo();
                        } catch (JSONException e) {
                            Toast.makeText(CompanyStatusActivity.this, "Transmission Error", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(CompanyStatusActivity.this, "Response Error", Toast.LENGTH_SHORT).show();

                    }
                });
            }
        });
        alert.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        alert.show();

    }

    private class CandidateArrayAdapter extends ArrayAdapter<Candidate> {

        Context context;
        int layoutResourceId;

        public CandidateArrayAdapter(Context context, int textViewResourceId,
                                   ArrayList<Candidate> expenses) {
            super(context, textViewResourceId, expenses);
            this.context = context;
            layoutResourceId = textViewResourceId;
        }

        public View getView(int index, View view, ViewGroup parent) {
            View row = view;
            CandidateHolder holder;

            if (row == null) {
                LayoutInflater inflater = ((Activity) this.context).getLayoutInflater();
                row = inflater.inflate(layoutResourceId, parent, false);

                holder = new CandidateHolder();
                holder.name = (TextView) row.findViewById(R.id.entry_name_view);
                holder.birthday = (TextView) row.findViewById(R.id.entry_birthday_view);
                holder.school = (TextView) row.findViewById(R.id.entry_school_view);

                row.setTag(holder);
            } else {
                holder = (CandidateHolder) row.getTag();
            }

            Candidate candidate = this.getItem(index);
            holder.name.setText(candidate.name);
            holder.birthday.setText(candidate.birthday);
            holder.school.setText(candidate.school);

            return row;
        }

        class CandidateHolder {
            TextView name;
            TextView birthday;
            TextView school;
        }

    }
}

