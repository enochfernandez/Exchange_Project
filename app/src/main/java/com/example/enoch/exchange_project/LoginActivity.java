package com.example.enoch.exchange_project;

import android.app.LoaderManager.LoaderCallbacks;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Toast;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity implements LoaderCallbacks<Cursor> {

    // UI references.
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private String emailText, passwordText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Set up the login form.
        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);
        mPasswordView = (EditText) findViewById(R.id.password);

    }


    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    public void attemptLogin(View view) {


        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        emailText = mEmailView.getText().toString();
        passwordText = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(passwordText) && !isCredentialsValid(passwordText)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(emailText)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!isEmailValid(emailText)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {

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


    //loader to check if the details exist in the database
    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        String[] projection = {
                ExchangeDataTablesReaderConstant.Members.COLUMN_name,
                ExchangeDataTablesReaderConstant.Members.COLUMN_address,
                ExchangeDataTablesReaderConstant.Members.COLUMN_postcode,
                ExchangeDataTablesReaderConstant.Members.COLUMN_city,
                ExchangeDataTablesReaderConstant.Members.COLUMN_email,
                ExchangeDataTablesReaderConstant.Members.COLUMN_password,
                ExchangeDataTablesReaderConstant.Members.COLUMN_last_login_date
        };

        String[] argsSelection = {
                emailText, passwordText
        };
        String select = ExchangeDataTablesReaderConstant.Members.COLUMN_email + " = ? AND " + ExchangeDataTablesReaderConstant.Members.COLUMN_password + " = ?";

        return new CursorLoader(this, ExchangeProvider.CONTENT_URI_TABLE_MEMBERS, projection, select, argsSelection, null);
    }


    //After data is load check whether data is empty , if not login
    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        if (cursor == null) {
            //Error occured
            Toast.makeText(this, "database returned null", Toast.LENGTH_SHORT).show();
        } else if (cursor.getCount() < 1) {
            Toast.makeText(this, "Email or Password not found", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "I found a record so I would log in", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {

    }


    public void goToSignUp(View view) {
        Intent intent = new Intent(this, Sign_Up.class);
        startActivity(intent);
    }


}

