package id.example.sisteminformasiakademik.admin.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import id.example.sisteminformasiakademik.R;

public class Lihat_data_admin extends AppCompatActivity {

    TextView ID, nama, email, txtIdAdminLihat, txtNamaAdminLihat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lihat_data_admin);
        getSupportActionBar().hide();

        ID = findViewById(R.id.idLihatdata);
        nama = findViewById(R.id.namaLihatdataADM);
        email = findViewById(R.id.emailLihatdataADM);
        txtIdAdminLihat = findViewById(R.id.txtIdAdminLihat);
        txtNamaAdminLihat = findViewById(R.id.txtNamaAdminLihat);

        txtIdAdminLihat.setText(getIntent().getStringExtra("id_admin"));
        txtNamaAdminLihat.setText(getIntent().getStringExtra("nama"));

        ID.setText(getIntent().getStringExtra("id_adminLihat"));
        nama.setText(getIntent().getStringExtra("namaLihat"));
        email.setText(getIntent().getStringExtra("emailLihat"));

    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }
}