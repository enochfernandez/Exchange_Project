package com.example.enoch.exchange_project;

import android.Manifest;
import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.enoch.exchange_project.ExchangeDataTablesReaderConstant.Members;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Sign_Up extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private EditText name, address, city, postCode, email, password;
    private CheckBox editCheckBox;
    private String emailText, passwordText, cityText, addressText, postCodeText, nameText;
    //By JCTubio
    private Button addressButton;
    private LocationManager locationManager;
    private LocationListener locationListener;
    private RequestQueue requestQueue;
    //JCTubio until here

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

        //By JCTubio
        addressButton = (Button) findViewById(R.id.getAddressButton);

        requestQueue = Volley.newRequestQueue(this);

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        locationListener = new LocationListener() {

            @Override
            public void onLocationChanged(Location location) {
                String jsoncommand = "https://maps.googleapis.com/maps/api/geocode/json?latlng=" + location.getLatitude() + "," + location.getLongitude() + "&key=AIzaSyAK2CQOHIsrRLCyLckwSNJTneqAwbZk7ks";
                JsonObjectRequest request = new JsonObjectRequest(jsoncommand, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            String streetString = "" + response.getJSONArray("results").getJSONObject(0).getJSONArray("address_components").getJSONObject(1).getString("long_name") + " " + response.getJSONArray("results").getJSONObject(0).getJSONArray("address_components").getJSONObject(0).getString("long_name");
                            String cityString = "" + response.getJSONArray("results").getJSONObject(0).getJSONArray("address_components").getJSONObject(3).getString("long_name");
                            String postcodeString = "" + response.getJSONArray("results").getJSONObject(0).getJSONArray("address_components").getJSONObject(7).getString("long_name");
                            address.setText(streetString);
                            city.setText(cityString);
                            postCode.setText(postcodeString);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
                requestQueue.add(request);
            }


            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        };
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.INTERNET}, 10);
            }
            return;
        } else {
            configureButton();
        }


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 10:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    configureButton();
                return;
        }
    }

    private void configureButton() {
        addressButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Sign_Up.this, "Requesting address from server, this might take a few seconds", Toast.LENGTH_LONG).show();
                if (ActivityCompat.checkSelfPermission(Sign_Up.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(Sign_Up.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                    return;
                }
                locationManager.requestLocationUpdates("gps", 5000, 50, locationListener);
            }
        });
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
