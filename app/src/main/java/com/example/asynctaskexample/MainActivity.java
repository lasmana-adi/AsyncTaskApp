package com.example.asynctaskexample;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TextView tvResult;
    private ProgressBar progressBar;
    private Button btnStartTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inisialisasi komponen UI
        tvResult = findViewById(R.id.tvResult);
        progressBar = findViewById(R.id.progressBar);
        btnStartTask = findViewById(R.id.btnStartTask);

        // Mengatur tombol untuk memulai AsyncTask
        btnStartTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Memulai AsyncTask
                new MyAsyncTask().execute();
            }
        });
    }

    // Membuat kelas AsyncTask
    private class MyAsyncTask extends AsyncTask<Void, Integer, String> {

        // 1. Metode ini dijalankan pertama kali sebelum AsyncTask berjalan
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            tvResult.setText("Starting task...");
            progressBar.setVisibility(View.VISIBLE);
        }

        // 2. Metode ini dijalankan di background thread, sehingga operasi yang lama tidak mengganggu UI
        @Override
        protected String doInBackground(Void... voids) {
            for (int i = 1; i <= 10; i++) {
                try {
                    Thread.sleep(500); // Simulasi waktu proses
                    publishProgress(i * 10); // Mengirim progress
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return "Task Completed!";
        }

        // 3. Metode ini digunakan untuk mengupdate UI dengan progress yang sedang berlangsung
        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            tvResult.setText("Progress: " + values[0] + "%");
        }

        // 4. Metode ini dijalankan setelah `doInBackground` selesai, dan menerima hasil akhir
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            tvResult.setText(result);
            progressBar.setVisibility(View.GONE);
        }
    }
}
