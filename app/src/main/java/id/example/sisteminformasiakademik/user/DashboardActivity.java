package id.example.sisteminformasiakademik.user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import id.example.sisteminformasiakademik.LoginActivity;
import id.example.sisteminformasiakademik.R;
import id.example.sisteminformasiakademik.user.krs.IsiKrsActivity;
import id.example.sisteminformasiakademik.user.menu.MenuActivity;

public class DashboardActivity extends AppCompatActivity {

    private static final String FILE_NAME = "myFile";
    WebView webView;
    ProgressBar bar;
    TextView txtNamaDashboard, txtNimDashboard;
    String snama, snim;
    Button btnIsiKrs, btnSenat, btnToHelp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        getSupportActionBar().hide();

        webView = findViewById(R.id.webviewDashboard);
        bar = findViewById(R.id.progressBarDashboard);
        webView.setWebViewClient(new myWebclient());
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("https://www.unpi-cianjur.ac.id/programstudi-3487-polisi-amankan-uang-rp--8-2-juta-dari-kpps-desa-hegarsari");
        webView.getSettings().setDomStorageEnabled(true);

        txtNamaDashboard = findViewById(R.id.txtNamaDashboard);
        txtNimDashboard = findViewById(R.id.txtNimDashboard);


        btnIsiKrs = findViewById(R.id.btnMoveisikrs);
        btnSenat = findViewById(R.id.btnMoveSenat);
        btnToHelp = findViewById(R.id.btnToHelp);

        SharedPreferences sharedPreferences = getSharedPreferences(FILE_NAME, MODE_PRIVATE);
        String nim = sharedPreferences.getString("nimShare", "Data Not Found");
        String password = sharedPreferences.getString("passwordShare", "Data Not Found");
////        String nama = sharedPreferences.getString("nama", "Data Not Found");

        Intent i = getIntent();
        String tNim = i.getStringExtra("nim");
        String tName = i.getStringExtra("nama");

        txtNimDashboard.setText(tNim);
        txtNamaDashboard.setText(tName);

        btnIsiKrs.setOnClickListener(v -> moveToisiKrs());
        btnSenat.setOnClickListener(v -> moveToSenat());
        btnToHelp.setOnClickListener(v -> moveToHelp());



        //initialize and assign variable
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        //set home selected
        bottomNavigationView.setSelectedItemId(R.id.navdashboard);

        //perform itemselectedlistener
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.navdashboard:
//                        String nimData1 = txtNimDashboard.getText().toString().trim();
//                        String namaData1 = txtNamaDashboard.getText().toString().trim();
//                        Intent intent = new Intent(getApplicationContext(),DashboardActivity.class);
//                        intent.putExtra("nim", nimData1);
//                        intent.putExtra("nama", namaData1);
//                        startActivity(intent);
//                        overridePendingTransition(0, 0);
//                        finish();
                        return true;
                    case R.id.navmenu:
                        String nimData = txtNimDashboard.getText().toString().trim();
                        String namaData = txtNamaDashboard.getText().toString().trim();
                        Intent intent = new Intent(DashboardActivity.this, MenuActivity.class);
                        intent.putExtra("nim", nimData);
                        intent.putExtra("nama", namaData);
                        startActivity(intent);
                        overridePendingTransition(0, 0);
                        finish();
                        return true;
                }
                return false;
            }
        });

    }


    public void moveToisiKrs() {
        String nimData = txtNimDashboard.getText().toString().trim();
        String namaData = txtNamaDashboard.getText().toString().trim();
        Intent intent = new Intent(DashboardActivity.this, IsiKrsActivity.class);
        intent.putExtra("nim", nimData);
        intent.putExtra("nama", namaData);
        startActivity(intent);
        overridePendingTransition(0, 0);
        finish();
    }

    public void moveToSenat() {
        String nimData = txtNimDashboard.getText().toString().trim();
        String namaData = txtNamaDashboard.getText().toString().trim();
        Intent intent = new Intent(DashboardActivity.this, SenatActivity.class);
        intent.putExtra("nim", nimData);
        intent.putExtra("nama", namaData);
        startActivity(intent);
        overridePendingTransition(0, 0);
        finish();
    }

    public void moveToHelp() {
        String nimData = txtNimDashboard.getText().toString().trim();
        String namaData = txtNamaDashboard.getText().toString().trim();
        Intent intent = new Intent(DashboardActivity.this, HelpDashboardActivity.class);
        intent.putExtra("nim", nimData);
        intent.putExtra("nama", namaData);
        startActivity(intent);
        overridePendingTransition(0, 0);
        finish();
    }

    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setMessage("Are you sure you want to exit?")
                .setIcon(R.mipmap.ic_launcher)
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                        finish();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }

    public class myWebclient extends WebViewClient {
        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            bar.setVisibility(View.GONE);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView wv, String url) {
            if (url.startsWith("tel:") || url.startsWith("whatsapp:")) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);
                return true;
            }
            return false;
        }
    }
}