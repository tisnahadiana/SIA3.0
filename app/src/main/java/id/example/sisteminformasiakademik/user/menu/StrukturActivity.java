package id.example.sisteminformasiakademik.user.menu;

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
import id.example.sisteminformasiakademik.user.menu.MenuActivity;

public class StrukturActivity extends AppCompatActivity {

    TextView txtNimStruktur, txtNamaStruktur;
    WebView webView;
    ProgressBar bar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_struktur);

        getSupportActionBar().hide();
        webView = findViewById(R.id.webviewStruktur);
        bar = findViewById(R.id.progressBar2);
        webView.setWebViewClient(new myWebclient());
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("https://www.unpi-cianjur.ac.id/struktur-unpi");
        webView.getSettings().setDomStorageEnabled(true);
        txtNimStruktur = findViewById(R.id.txtNimStruktur);
        txtNamaStruktur = findViewById(R.id.txtNamaStruktur);

        Intent i = getIntent();
        String tNim = i.getStringExtra("nim");
        String tName = i.getStringExtra("nama");

        txtNimStruktur.setText(tNim);
        txtNamaStruktur.setText(tName);
    }



    public void moveMenuStruktur(View view){
        String nimData = txtNimStruktur.getText().toString().trim();
        String namaData = txtNamaStruktur.getText().toString().trim();
        Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
        intent.putExtra("nim", nimData);
        intent.putExtra("nama", namaData);
        startActivity(intent);
        overridePendingTransition(0, 0);
        finish();
    }

    public void onBackPressed() {
        String nimData = txtNimStruktur.getText().toString().trim();
        String namaData = txtNamaStruktur.getText().toString().trim();
        Intent intent = new Intent(getApplicationContext(),MenuActivity.class);
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