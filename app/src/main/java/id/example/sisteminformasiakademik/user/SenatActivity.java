package id.example.sisteminformasiakademik.user;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;

import id.example.sisteminformasiakademik.R;
import id.example.sisteminformasiakademik.user.DashboardActivity;

public class SenatActivity extends AppCompatActivity {

    WebView webView;
    ProgressBar bar;
    TextView txtNim, txtNama;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_senat);

        getSupportActionBar().hide();

        webView = findViewById(R.id.webviewSenat);
        bar = findViewById(R.id.progressBarSenat);
        webView.setWebViewClient(new myWebclient());
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("https://cianjurtoday.com/wonderful-ramadan-sema-ft-unpi-berbagi-kebaikan-bersama-puluhan-anak-yatim/");
        webView.getSettings().setDomStorageEnabled(true);
        txtNim =findViewById(R.id.txtNimSenat);
        txtNama =findViewById(R.id.txtNamaSenat);

        Intent i = getIntent();
        String tNim = i.getStringExtra("nim");
        String tName = i.getStringExtra("nama");

        txtNim.setText(tNim);
        txtNama.setText(tName);
    }

    public void moveDashboardSenat(View view){
        String nimData = txtNim.getText().toString().trim();
        String namaData = txtNama.getText().toString().trim();
        Intent intent = new Intent(getApplicationContext(), DashboardActivity.class);
        intent.putExtra("nim", nimData);
        intent.putExtra("nama", namaData);
        startActivity(intent);
        overridePendingTransition(0, 0);
        finish();
    }

    public void onBackPressed() {
        String nimData = txtNim.getText().toString().trim();
        String namaData = txtNama.getText().toString().trim();
        Intent intent = new Intent(getApplicationContext(),DashboardActivity.class);
        intent.putExtra("nim", nimData);
        intent.putExtra("nama", namaData);
        startActivity(intent);
        overridePendingTransition(0, 0);
        finish();
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
            if(url.startsWith("tel:") || url.startsWith("whatsapp:")) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);
                return true;
            }
            return false;
        }
    }
}