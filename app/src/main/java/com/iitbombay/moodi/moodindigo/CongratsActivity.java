package com.iitbombay.moodi.moodindigo;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class CongratsActivity extends AppCompatActivity {
SessionManager sessionManager;
TextView MI_No;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_congrats);
        sessionManager=new SessionManager(this);
        MI_No=(TextView) findViewById(R.id.mino);
        MI_No.setText(sessionManager.getString("mi number"));


        new Handler().postDelayed(new Runnable() {
                                      @Override
                                      public void run() {
                                          startActivity(new Intent(CongratsActivity.this,MainActivity.class));
                                      }
                                  },2000);
    }
}
