package com.example.enoch.exchange_project;

import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.Loader;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.format.Time;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.enoch.exchange_project.ExchangeDataTablesReaderConstant.Members;

import java.text.DateFormat;
import java.util.Date;
import java.util.Timer;

public class Sign_Up extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private EditText name,address,city,postCode,email,password;
    private CheckBox editCheckBox;
    private ExchangeProvider myProvider;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign__up);

        myProvider = new ExchangeProvider();

        name = (EditText) findViewById(R.id.name);
        address = (EditText) findViewById(R.id.address);
        city = (EditText) findViewById(R.id.city);
        postCode = (EditText) findViewById(R.id.postCode);
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        editCheckBox = (CheckBox) findViewById(R.id.editProfile);


    }

    //dont forget to write comment
    public void attemptSignUp(View view) {

        name.setError(null);
        address.setError(null);
        city.setError(null);
        postCode.setError(null);
        email.setError(null);
        password.setError(null);

        // Store values at the time of the login attempt.
        String emailText = email.getText().toString();
        String passwordText = password.getText().toString();
        String cityText = city.getText().toString();
        String postCodeText = postCode.getText().toString();
        String nameText = name.getText().toString();
        String addressText = address.getText().toString();


        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(passwordText) && !isCredentialsValid(passwordText)) {
            password.setError(getString(R.string.error_invalid_password));
            focusView = password;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(emailText)) {
            email.setError(getString(R.string.error_field_required));
            focusView = email;
            cancel = true;
        } else if (!isEmailValid(emailText)) {
            email.setError(getString(R.string.error_invalid_email));
            focusView = email;
            cancel = true;
        }

        //Check for valid city
        if (TextUtils.isEmpty(cityText) ) {
            city.setError("This field is required");
            focusView = city;
            cancel = true;
        }


        //Check for a valid PostCode
        if (TextUtils.isEmpty(postCodeText) ) {
            postCode.setError("This field is required");
            focusView = postCode;
            cancel = true;
        }

        //Check for a valid name
        if (TextUtils.isEmpty(nameText) ) {
            name.setError("This field is required");
            focusView = name;
            cancel = true;
        }
        //Check for a valid address
        if (TextUtils.isEmpty(addressText) ) {
            address.setError("This field is required");
            focusView = address;
            cancel = true;
        }


        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {

            //Set time for firstTime login
            String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());

            //inserting the data into the database
            ContentValues values = new ContentValues();
            values.put(Members.COLUMN_name,nameText);
            values.put(Members.COLUMN_address, addressText);
            values.put(Members.COLUMN_postcode,postCodeText);
            values.put(Members.COLUMN_city , cityText);
            values.put(Members.COLUMN_email, emailText);
            values.put(Members.COLUMN_password, passwordText);
            values.put(Members.COLUMN_last_login_date, currentDateTimeString);

            getContentResolver().insert(ExchangeProvider.CONTENT_URI_TABLE_MEMBERS, values);
            Toast.makeText(this, "I was able to insert", Toast.LENGTH_SHORT).show();



        }
    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    private boolean isCredentialsValid(String credentials) {
        //TODO: Replace this with your own logic
        return credentials.length() > 4;
    }

    @Override
    public Loader onCreateLoader(int id, Bundle args) {
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

    }


    @Override
    public void onLoaderReset(Loader loader) {

    }
}
