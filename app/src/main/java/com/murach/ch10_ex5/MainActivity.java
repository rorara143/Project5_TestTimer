package com.murach.ch10_ex5;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends Activity implements OnClickListener{

    private TextView messageTextView;
    private Button startbutton;
    private Button stopbutton;
    private Timer restart;
    private Timer stopTimer;



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

    }




    private void startTimer() {
        final long startMillis = System.currentTimeMillis();
        Timer timer = new Timer(true);
        TimerTask task = new TimerTask() {

            @Override
            public void run() {
                long elapsedMillis = System.currentTimeMillis() - startMillis;
                final String FILENAME = "news_feed.xml";
                try {
                    //get the input in the stream
                    URL url = new URL("http://rss.cnn.com/rss/cnn_tech.rss");
                    InputStream in = url.openStream();

                    //get the output stream
                    FileOutputStream out = openFileOutput(FILENAME, Context.MODE_PRIVATE);

                    //read input and write output
                    byte[] buffer = new byte[1024];
                    int bytesRead = in.read(buffer);
                    while (bytesRead != -1) {
                        out.write(buffer, 0, bytesRead);
                        bytesRead = in.read(buffer);
                    }
                    out.close();
                    in.close();
                }
                catch (IOException e) {
                    Log.e("News reader", e.toString());
                }

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
                messageTextView.setText("File downloaded " + elapsedSeconds / 10 + "time(s)");
            }
        });
    }

    @Override
    //cancels the timer when the app is paused.
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

