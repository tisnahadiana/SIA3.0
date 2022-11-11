package id.example.sisteminformasiakademik.admin.mahasiswa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import id.example.sisteminformasiakademik.R;

public class Lihat_data_mahasiswa extends AppCompatActivity {

    TextView txtIdAdminLihatMHS, txtNamaAdminLihatMHS,nim, nama, email, prodi, semester;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lihat_data_mahasiswa);
        getSupportActionBar().hide();

        txtIdAdminLihatMHS = findViewById(R.id.txtIdAdminLihatMHS);
        txtNamaAdminLihatMHS = findViewById(R.id.txtNamaAdminLihatMHS);

        Intent i = getIntent();
        String tNim = i.getStringExtra("id_admin");
        String tName = i.getStringExtra("nama");

        txtIdAdminLihatMHS.setText(tNim);
        txtNamaAdminLihatMHS.setText(tName);

        nim = findViewById(R.id.nimLihatdata);
        nama = findViewById(R.id.namaLihatdata);
        email = findViewById(R.id.emailLihatdata);
        prodi = findViewById(R.id.prodiLihatdata);
        semester = findViewById(R.id.semesterLihatdata);


        nim.setText(getIntent().getStringExtra("nimMHS"));
        nama.setText(getIntent().getStringExtra("namaMHS"));
        email.setText(getIntent().getStringExtra("emailMHS"));
        prodi.setText(getIntent().getStringExtra("prodiMHS"));
        semester.setText(getIntent().getStringExtra("semesterMHS"));
    }

    public void onBackPressed() {
        String idAdmin = txtIdAdminLihatMHS.getText().toString().trim();
        String namaAdmin = txtNamaAdminLihatMHS.getText().toString().trim();
        Intent intent = new Intent(getApplicationContext(), UserMahasiswaAdminActivity.class);
        intent.putExtra("id_admin", idAdmin);
        intent.putExtra("nama", namaAdmin);
        startActivity(intent);
        overridePendingTransition(0, 0);
        finish();

    }
}