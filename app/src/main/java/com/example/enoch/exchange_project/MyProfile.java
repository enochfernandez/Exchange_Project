package com.example.enoch.exchange_project;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class MyProfile extends AppCompatActivity {

    Button skillsButton;
    Button profileButton;
    EditText bio;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);
        bio = (EditText) findViewById(R.id.bioText);
        skillsButton = (Button) findViewById(R.id.skillsButtonProfile);
        profileButton = (Button) findViewById(R.id.editContactInfoButtonProfile);
    }
}
