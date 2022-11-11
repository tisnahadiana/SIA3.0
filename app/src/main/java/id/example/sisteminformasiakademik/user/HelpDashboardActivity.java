package id.example.sisteminformasiakademik.user;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import id.example.sisteminformasiakademik.R;
import id.example.sisteminformasiakademik.user.DashboardActivity;

public class HelpDashboardActivity extends AppCompatActivity {

    TextView txtNimHelpD, txtNamaHelpD;
    Button btnBackDashHelp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_dashboard);
        getSupportActionBar().hide();

        txtNimHelpD = findViewById(R.id.txtNimHelp);
        txtNamaHelpD = findViewById(R.id.txtNamaHelp);

        Intent i = getIntent();
        String tNim = i.getStringExtra("nim");
        String tName = i.getStringExtra("nama");

        txtNimHelpD.setText(tNim);
        txtNamaHelpD.setText(tName);
    }

    public void moveDashboardHelp(View view){
        String nimData = txtNimHelpD.getText().toString().trim();
        String namaData = txtNamaHelpD.getText().toString().trim();
        Intent intent = new Intent(getApplicationContext(), DashboardActivity.class);
        intent.putExtra("nim", nimData);
        intent.putExtra("nama", namaData);
        startActivity(intent);
        overridePendingTransition(0, 0);
        finish();
    }

    public void onBackPressed() {
        String nimData = txtNimHelpD.getText().toString().trim();
        String namaData = txtNamaHelpD.getText().toString().trim();
        Intent intent = new Intent(getApplicationContext(),DashboardActivity.class);
        intent.putExtra("nim", nimData);
        intent.putExtra("nama", namaData);
        startActivity(intent);
        overridePendingTransition(0, 0);
        finish();
    }
}