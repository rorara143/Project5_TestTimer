package com.murach.ch10_ex5;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends Activity implements OnClickListener{

    private TextView messageTextView;
    private Button startbutton;
    private Button stopbutton;
    private Timer restart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //reference into widget
        messageTextView = (TextView) findViewById(R.id.messageTextView);
        startbutton = (Button) findViewById(R.id.startbutton);
        stopbutton = (Button) findViewById(R.id.stopbutton);


        //set listener
        startbutton.setOnClickListener(this);
        stopbutton.setOnClickListener(this);
        startTimer();
    }




    private void startTimer() {
        final long startMillis = System.currentTimeMillis();
        Timer timer = new Timer(true);
        TimerTask task = new TimerTask() {

            @Override
            public void run() {
                long elapsedMillis = System.currentTimeMillis() - startMillis;
                updateView(elapsedMillis);
            }
        };
        timer.schedule(task, 0, 1000);
    }

    private void updateView(final long elapsedMillis) {
        // UI changes need to be run on the UI thread
        messageTextView.post(new Runnable() {

            int elapsedSeconds = (int) elapsedMillis/1000;

            @Override
            public void run() {
                messageTextView.setText("Seconds: " + elapsedSeconds / 10 + " time(s). ");
            }
        });
    }

    @Override
    //
    protected void onPause() {
        super.onPause();
    }




    @Override
    public void onClick(View view) {
switch (view.getId()) {
    case R.id.startbutton:
        startTimer();
        break;
    case R.id.stopbutton:
        restart.cancel();
        break;
}
    }
}

