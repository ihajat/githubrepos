package com.example.owner.myreposapplication.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.owner.myreposapplication.R;
import com.example.owner.myreposapplication.application.MyApplication;

public class DetailedActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_detailed);
        TextView textName = findViewById(R.id.textHeader);//textName
        TextView textDescription = findViewById(R.id.textDescription);
        TextView textURL = findViewById(R.id.textURL);
        TextView textPushedAt = findViewById(R.id.textPushedAt);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {

            textName.setText(extras.getString("name"));
            textDescription.setText(extras.getString("description"));
            textURL.setText(extras.getString("html_url"));
            textPushedAt.setText(extras.getString("pushed_at"));
        }

        CollapsingToolbarLayout appBarLayout = findViewById(R.id.toolbar_layout);
        if (appBarLayout != null) {
            appBarLayout.setTitle(extras.getString("title"));
        }

        // Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            // This ID represents the Home or Up button. In the case of this
            // activity, the Up button is shown. Use NavUtils to allow users
            // to navigate up one level in the application structure. For
            // more details, see the Navigation pattern on Android Design:
            //
            // http://developer.android.com/design/patterns/navigation.html#up-vs-back
            //
            NavUtils.navigateUpTo(this, new Intent(this, MainActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
