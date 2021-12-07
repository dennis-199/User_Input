package com.example.userinput;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.text.method.TransformationMethod;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private EditText date,firstName, surName, emailAdress, editpassword, conPassord, phoneNumber;
    ImageView cal;
    private int mDate, mMonth, mYear;
    private Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        date = findViewById(R.id.date);
        cal = findViewById(R.id.datepicker);
        firstName= findViewById(R.id.firstname);
        surName= findViewById(R.id.surname);
        emailAdress = findViewById(R.id.emailadress);
        editpassword = findViewById(R.id.password);
        conPassord = findViewById(R.id.confirmpassword);
        phoneNumber = findViewById(R.id.phonenumber);

        spinner = (Spinner) findViewById(R.id.spinner1);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.type, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        editpassword.setTransformationMethod(new AsteriskPasswordTransformationMethod());
        conPassord.setTransformationMethod(new AsteriskPasswordTransformationMethod());

        cal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar Cal = Calendar.getInstance();
                mDate = Cal.get(Calendar.DATE);
                mMonth = Cal.get(Calendar.MONTH);
                mYear = Cal.get(Calendar.YEAR);

                DatePickerDialog datePickerDialog = new DatePickerDialog(MainActivity.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        date.setText(dayOfMonth+"/"+month+"/"+year);
                    }
                }, mYear, mMonth, mDate);
                datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis()-1000);
                datePickerDialog.show();
            }
        });
    }

    public void LoginPage(View view) {
        String email = emailAdress.getText().toString().trim();
        String password = editpassword.getText().toString().trim();
        String fName = firstName.getText().toString().trim();
        String sName = surName.getText().toString().trim();
        String cPassword = conPassord.getText().toString().trim();
        String phoneNo = phoneNumber.getText().toString().trim();

        if(fName.isEmpty()) {
            firstName.setError("First name is required ");
            firstName.requestFocus();
            return;
        }
        if(sName.isEmpty()) {
            surName.setError("Surname is required ");
            surName.requestFocus();
            return;
        }
        if(email.isEmpty()){
            emailAdress.setError("Email is Required");
            emailAdress.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailAdress.setError("Please provide valid email");
            emailAdress.requestFocus();
            return;
        }
        if (password.isEmpty()) {
            editpassword.setError("Password is required");
            editpassword.requestFocus();
            return;
        }
        if (password.length() < 6) {
            editpassword.setError("password has to be more than 6 charcters");
            editpassword.requestFocus();
            return;
        }
        if (phoneNo.isEmpty()) {
            phoneNumber.setError("Phone number is required");
            phoneNumber.requestFocus();
            return;
        }
        if (!cPassword.equals(password)){
            conPassord.setError("Password do not match");
            conPassord.requestFocus();
        }
        if (cPassword.isEmpty())  {
            conPassord.setError("Confirm password");
            conPassord.requestFocus();
            return;
        }

        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);

    }

    public class AsteriskPasswordTransformationMethod extends PasswordTransformationMethod {
        @Override
        public CharSequence getTransformation(CharSequence source, View view) {
            return new PasswordCharSequence(source);
        }

        private class PasswordCharSequence implements CharSequence {
            private CharSequence mSource;

            public PasswordCharSequence(CharSequence source) {
                mSource = source; // Store char sequence
            }

            public char charAt(int index) {
                return '*'; // This is the important part
            }

            public int length() {
                return mSource.length(); // Return default
            }

            public CharSequence subSequence(int start, int end) {
                return mSource.subSequence(start, end); // Return default
            }
        }
    }

}