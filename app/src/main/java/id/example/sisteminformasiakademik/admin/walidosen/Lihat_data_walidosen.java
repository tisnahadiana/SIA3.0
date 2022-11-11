package id.example.sisteminformasiakademik.admin.walidosen;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import id.example.sisteminformasiakademik.R;

public class Lihat_data_walidosen extends AppCompatActivity {

    TextView nidn, nama, email, prodi, semester, txtIdWDLihat, txtNamaWDLihat;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lihat_data_walidosen);
        getSupportActionBar().hide();

        nidn = findViewById(R.id.nidnLihatdataWD);
        nama = findViewById(R.id.namaLihatdataWD);
        email = findViewById(R.id.emailLihatdataWD);
        prodi = findViewById(R.id.prodiLihatdataWD);
        semester = findViewById(R.id.semesterLihatdataWD);

        txtIdWDLihat = findViewById(R.id.idAdminLihatWD);
        txtNamaWDLihat = findViewById(R.id.namaAdminLihatWD);

        txtIdWDLihat.setText(getIntent().getStringExtra("id_admin"));
        txtNamaWDLihat.setText(getIntent().getStringExtra("nama"));

        nidn.setText(getIntent().getStringExtra("nidnWD"));
        nama.setText(getIntent().getStringExtra("namaWD"));
        email.setText(getIntent().getStringExtra("emailWD"));
        prodi.setText(getIntent().getStringExtra("prodiWD"));
        semester.setText(getIntent().getStringExtra("semesterWD"));
    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }
}