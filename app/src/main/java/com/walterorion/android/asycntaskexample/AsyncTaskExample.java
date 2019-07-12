package com.walterorion.android.asycntaskexample;

import android.os.AsyncTask;
import android.view.View;

import java.lang.ref.WeakReference;

public class AsyncTaskExample extends AsyncTask<Integer, Integer, String> {

    private WeakReference<MainActivity> activityWeakReference;

    AsyncTaskExample(MainActivity activity) {
        activityWeakReference = new WeakReference<>(activity);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        MainActivity activity = activityWeakReference.get();

        if (activity == null || activity.isFinishing()) {
            return;
        }
        activity.progressBar.setVisibility(View.VISIBLE);
        activity.textProgress.setVisibility(View.VISIBLE);
    }

    @Override
    protected String doInBackground(Integer... integers) {
        for (int i = 0; i < integers[0]; i++) {
            publishProgress((i * 100) / integers[0]);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return "Finished";
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);

        MainActivity activity = activityWeakReference.get();

        if (activity == null || activity.isFinishing()) {
            return;
        }

        activity.progressBar.setProgress(values[0] + 10);

        activity.textProgress.setText(String.valueOf(values[0] + 10));
    }

    @Override
    protected void onPostExecute(String str) {
        super.onPostExecute(str);

        MainActivity activity = activityWeakReference.get();

        if (activity == null || activity.isFinishing()) {
            return;
        }

        activity.progressBar.setProgress(0);
        activity.progressBar.setVisibility(View.INVISIBLE);
        activity.textProgress.setText(str);
    }

}
