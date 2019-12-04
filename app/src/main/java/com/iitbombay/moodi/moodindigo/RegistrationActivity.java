package com.iitbombay.moodi.moodindigo;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.iitbombay.moodi.moodindigo.data.RegistrationRequest;
import com.iitbombay.moodi.moodindigo.data.RegistrationResponse;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class RegistrationActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    EditText et_name;
    EditText et_email;
    EditText et_mobile;
    EditText et_city;
    EditText et_college;
    EditText et_address;
    EditText et_zip;
    Spinner year_spinner;
    Button submit_button;
    String year;
    TextView dob;
    ProgressBar progressBar;
    Toast toast;

    SessionManager sessionManager;
    LinearLayout layout;
    String id;
    Object image;
    String name;
    RegistrationResponse registrationResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        sessionManager = new SessionManager(getApplicationContext());

        progressBar = (ProgressBar) findViewById(R.id.registration_progress_bar);
        et_name = (EditText) findViewById(R.id.name);
        et_email = (EditText) findViewById(R.id.email);
        et_address = (EditText) findViewById(R.id.address);
        et_college = (EditText) findViewById(R.id.college);
        et_city = (EditText) findViewById(R.id.city);
        et_zip = (EditText) findViewById(R.id.zip);
        dob = (TextView) findViewById(R.id.dob);
        et_mobile = (EditText) findViewById(R.id.mobile);
        submit_button = (Button) findViewById(R.id.submit);
        year_spinner = (Spinner) findViewById(R.id.year_spinner);
        registrationResponse = new RegistrationResponse();

        name = sessionManager.getString("name");
        et_name.setText(name);
        id = sessionManager.getString("fbid");
        image = sessionManager.getString("image");
        year_spinner.setOnItemSelectedListener(this);

        final List<String> years = new ArrayList<>();
        years.add("First");
        years.add("Second");
        years.add("Third");
        years.add("Fourth");
        years.add("Fifth");
        years.add("Select Year of Study");

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item_1, years);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        year_spinner.setAdapter(arrayAdapter);
        year_spinner.setSelection(5);

        dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                int mYear = calendar.get(Calendar.YEAR);
                int mMonth = calendar.get(Calendar.MONTH);
                int mDay =calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(RegistrationActivity.this, R.style.MyAppTheme, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int dateofmonth) {
                        Calendar cal=Calendar.getInstance();
                        cal.setTimeInMillis(0);
                        cal.set(year,month,dateofmonth);
                        Date d=cal.getTime();
                        dob.setText(new SimpleDateFormat("dd/MM/yyyy").format(d));
                    }
                }, mYear, mMonth, mDay);

                datePickerDialog.show();
            }
        });

        submit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                if (!isDigit(String.valueOf(et_zip.getText()))) {
                    showToast("Give a valid zip code");
                } else if (!isDigit(String.valueOf(et_mobile.getText()))) {
                    showToast("Give a valid mobile no.");
                } else if (et_mobile.getText().toString().length() != 10) {
                    showToast("Give a mobile of 10 digit format");
                    Log.e("here", "reach");
                } else if (year.equals("Select Year of Study")) {
                    showToast("Select Year Of Study");
                } else if (et_zip.getText().equals("") || et_zip.getText().toString().length() != 6) {
                    showToast("Fill in a 6 digit Zip Code");
                } else if ((!android.util.Patterns.EMAIL_ADDRESS.matcher(et_email.getText()).matches() || et_email.getText().toString().equals(""))) {
                    showToast("Give a valid email id");
                } else if (et_address.getText().toString().isEmpty() || et_address.getText().length() > 100) {
                    showToast("Fill in your address (Character limit = 100");
                } else if (et_name.getText().toString().isEmpty() || et_name.getText().length() > 50) {
                    showToast("Fill in your name (Character limit = 50");
                } else if (et_city.getText().toString().length() > 100) {
                    showToast("Fill in your city (Character limit = 100");
                    Log.e("here", "reach");
                } else if (et_college.getText().toString().length() > 300) {
                    showToast("Fill in your college name (Character limit = 300");
                    Log.e("here", "reach");
                } else {
                    Log.e("here", "reach");
                    RetrofitClass rcinitiate;
                    rcinitiate = new RetrofitClass(RegistrationActivity.this);
                    final RegistrationRequest registrationRequest = new RegistrationRequest(et_name.getText().toString(), id, et_email.getText().toString(), Integer.parseInt(et_zip.getText().toString()), year, et_mobile.getText().toString(), et_city.getText().toString(), et_college.getText().toString(), et_address.getText().toString(), dob.getText().toString());
                    SearchInterface client = rcinitiate.createBuilder().create(SearchInterface.class);
                    rcinitiate.startLogging();
                    // Log.e("request", id);
                    Call<RegistrationResponse> call = client.fillRegistrationForm(registrationRequest);

                    call.enqueue(new retrofit2.Callback<RegistrationResponse>() {
                        @Override
                        public void onResponse(Call<RegistrationResponse> call, Response<RegistrationResponse> response) {
                            registrationResponse = response.body();

                            if (response.isSuccessful()) {
                                Log.e("response", response.toString());
                                Intent main = new Intent(RegistrationActivity.this, CongratsActivity.class);
                                sessionManager.enterString("name", registrationResponse.getName());
                                sessionManager.enterString("email", registrationResponse.getEmail());
                                sessionManager.enterString("image", image.toString());
                                sessionManager.enterString("mi number", registrationResponse.getMi_number());
                                sessionManager.enterString("fbid", registrationResponse.getFb_id());
                                sessionManager.enterString("college", registrationResponse.getPresent_college());
                                sessionManager.enterString("city", registrationResponse.getPresent_city());
                                sessionManager.enterString("address", registrationResponse.getPostal_address());
                                sessionManager.enterInt("zip", registrationResponse.getZip_code());
                                sessionManager.enterString("mobile", registrationResponse.getMobile_number());
                                sessionManager.enterString("year", registrationResponse.getYear_of_study());
                                sessionManager.enterBoolean("isLoggedIn", true);

                                startActivity(main);
                            } else {
                                showToast("Please give your correct Email and Mobile Number");
                            }
                        }
                        @Override
                        public void onFailure(Call<RegistrationResponse> call, Throwable t) {
                            showToast("Connection failed");
                        }
                    });
                }
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        year = adapterView.getItemAtPosition(i).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public void showToast(String message) {
        if (toast != null) {
            toast.cancel();
        }
        toast = Toast.makeText(RegistrationActivity.this, message, Toast.LENGTH_LONG);
        toast.show();
        progressBar.setVisibility(View.GONE);
        Log.e("Check", message);
    }

    //TODO Configure on Resume and on Pause
//    @Override
//    public void onSaveInstanceState(Bundle outState) {
//        outState.putString("name", et_name.getText().toString());
//        outState.putString("email", et_email.getText().toString());
//        outState.putString("zip", et_zip.getText().toString());
//        outState.putString("city", et_city.getText().toString());
//        outState.putString("college", et_college.getText().toString());
//        outState.putString("address", et_address.getText().toString());
//        outState.putString("mobile", et_mobile.getText().toString());
//        outState.putString("year", year);
//        outState.putString("dob", dob.getText().toString());
//        Log.e("saved","saved");
//        super.onSaveInstanceState(outState);
//    }
//
//    public void onRestoreInstanceState(Bundle savedInstanceState) {
//        super.onRestoreInstanceState(savedInstanceState);
//        et_name.setText(savedInstanceState.getString("name"));
//        et_email.setText(savedInstanceState.getString("email"));
//        et_zip.setText(savedInstanceState.getString("zip"));
//        et_city.setText(savedInstanceState.getString("city"));
//        et_college.setText(savedInstanceState.getString("college"));
//        et_address.setText(savedInstanceState.getString("address"));
//        et_mobile.setText(savedInstanceState.getString("mobile"));
//        year=savedInstanceState.getString("year");
//        dob.setText(savedInstanceState.getString("dob"));
//        Log.e("restored","restored");
//    }
    public boolean isDigit(String string) {
        try {
            long integer = Long.parseLong(string);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
