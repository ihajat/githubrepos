package com.example.owner.myreposapplication.ui;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.example.owner.myreposapplication.R;

public class AuthActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }
}