package com.example.enoch.exchange_project;

import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.enoch.exchange_project.ExchangeDataTablesReaderConstant.Members;

import java.text.DateFormat;
import java.util.Date;

public class Sign_Up extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private EditText name,address,city,postCode,email,password;
    private CheckBox editCheckBox;
    private String emailText, passwordText, cityText, addressText, postCodeText, nameText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign__up);

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
        emailText = email.getText().toString();
        passwordText = password.getText().toString();
        cityText = city.getText().toString();
        postCodeText = postCode.getText().toString();
        nameText = name.getText().toString();
        addressText = address.getText().toString();


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


            //getLoaderManager().initLoader(0,null,this);

            getLoaderManager().restartLoader(0, null, this);

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

    //A loader to check for duplication
    @Override
    public Loader onCreateLoader(int id, Bundle args) {

        String[] projection = {
                Members.COLUMN_name,
                Members.COLUMN_address,
                Members.COLUMN_postcode,
                Members.COLUMN_city,
                Members.COLUMN_email,
                Members.COLUMN_password,
                Members.COLUMN_last_login_date
        };

        String[] argsSelection = {
                emailText, passwordText
        };
        String select = Members.COLUMN_email + " = ? AND " + Members.COLUMN_password + " = ? ";

        CursorLoader mCursor = new CursorLoader(this, ExchangeProvider.CONTENT_URI_TABLE_MEMBERS, projection, select, argsSelection, null);
        return mCursor;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (data == null) {
            //Error occured
            Toast.makeText(this, "database returned null", Toast.LENGTH_SHORT).show();
        } else if (data.getCount() == 1) {
            Toast.makeText(this, "UserName and Password already used", Toast.LENGTH_SHORT).show();
        } else {
            //Set time for firstTime login
            String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());

            //inserting the data into the database
            ContentValues values = new ContentValues();
            values.put(Members.COLUMN_name, nameText);
            values.put(Members.COLUMN_address, addressText);
            values.put(Members.COLUMN_postcode, postCodeText);
            values.put(Members.COLUMN_city, cityText);
            values.put(Members.COLUMN_email, emailText);
            values.put(Members.COLUMN_password, passwordText);
            values.put(Members.COLUMN_last_login_date, currentDateTimeString);

            getContentResolver().insert(ExchangeProvider.CONTENT_URI_TABLE_MEMBERS, values);
            Toast.makeText(this, "I was able to insert", Toast.LENGTH_SHORT).show();
        }


    }


    @Override
    public void onLoaderReset(Loader loader) {

    }
}
