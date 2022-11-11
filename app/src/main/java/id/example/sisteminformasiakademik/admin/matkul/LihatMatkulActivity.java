package id.example.sisteminformasiakademik.admin.matkul;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import id.example.sisteminformasiakademik.R;
import id.example.sisteminformasiakademik.admin.mahasiswa.UserMahasiswaAdminActivity;

public class LihatMatkulActivity extends AppCompatActivity {

    TextView txtIdAdminLihatmatkul, txtNamaAdminLihatmatkul,namaSKS, SKS, prodi, semester;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lihat_matkul);
        getSupportActionBar().hide();

        txtIdAdminLihatmatkul = findViewById(R.id.txtIdAdminLihatmatkul);
        txtNamaAdminLihatmatkul = findViewById(R.id.txtNamaAdminLihatmatkul);

        namaSKS = findViewById(R.id.LihatNamamatkul);
        SKS = findViewById(R.id.LihatmatkulSKS);
        prodi = findViewById(R.id.LihatmatkulProdi);
        semester = findViewById(R.id.LihatmatkulSemester);

        txtIdAdminLihatmatkul.setText(getIntent().getStringExtra("id_admin"));
        txtNamaAdminLihatmatkul.setText(getIntent().getStringExtra("nama"));

        namaSKS.setText(getIntent().getStringExtra("namaMatkul"));
        SKS.setText(getIntent().getStringExtra("sksLihat"));
        prodi.setText(getIntent().getStringExtra("prodiLihat"));
        semester.setText(getIntent().getStringExtra("semesterLihat"));

    }

    @Override
    public void onBackPressed() {
        String idAdmin = txtIdAdminLihatmatkul.getText().toString().trim();
        String namaAdmin = txtNamaAdminLihatmatkul.getText().toString().trim();
        Intent intent = new Intent(getApplicationContext(), MatkulAdminActivity.class);
        intent.putExtra("id_admin", idAdmin);
        intent.putExtra("nama", namaAdmin);
        startActivity(intent);
        overridePendingTransition(0, 0);
        finish();

    }

}