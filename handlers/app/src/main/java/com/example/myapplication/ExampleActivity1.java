package com.example.myapplication;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class ExampleActivity1 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new DontDoThis().execute();
    }

    private final class DontDoThis extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            findViewById(R.id.loading).setVisibility(View.VISIBLE);
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {}

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            findViewById(R.id.done).setVisibility(View.VISIBLE);
        }
    }
}
