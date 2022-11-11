package id.example.sisteminformasiakademik.walidosen;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import id.example.sisteminformasiakademik.LoginWalidosenActivity;
import id.example.sisteminformasiakademik.R;
import id.example.sisteminformasiakademik.walidosen.khs.KhsWalidosenActivity;
import id.example.sisteminformasiakademik.walidosen.krs.KrsWalidosenActivity;
import id.example.sisteminformasiakademik.walidosen.mahasiswa.UserMahasiswaWalidosenActivity;
import id.example.sisteminformasiakademik.walidosen.tnilai.TnilaiWalidosenActivity;

public class DashboardWalidosenActivity extends AppCompatActivity {

    private static final String FILE_NAME = "myFileWalidosen";
    TextView txtNamaDosen, txtNidn, txtProdiDashboard, txtSemesterDashboard;
    Button btnKeluarWaldos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_walidosen);
        getSupportActionBar().hide();

        txtNamaDosen = findViewById(R.id.txtNamaDashboardWaldos);
        txtNidn = findViewById(R.id.txtNIDN);
        txtProdiDashboard = findViewById(R.id.txtProdiWaldosDashboard);
        txtSemesterDashboard = findViewById(R.id.txtSemesterWaldosDashboard);

        btnKeluarWaldos = findViewById(R.id.btnKeluarWaldos);

        SharedPreferences sharedPreferences = getSharedPreferences(FILE_NAME, MODE_PRIVATE);
        String nim = sharedPreferences.getString("nidnShare", "Data Not Found");
        String password = sharedPreferences.getString("passwordShare", "Data Not Found");
////        String nama = sharedPreferences.getString("nama", "Data Not Found");

        Intent i = getIntent();
        String tNidn = i.getStringExtra("nidn");
        String tName = i.getStringExtra("namaDosen");
        String tProdi = i.getStringExtra("prodi");
        String tSemester = i.getStringExtra("semester");

        txtNidn.setText(tNidn);
        txtNamaDosen.setText(tName);
        txtProdiDashboard.setText(tProdi);
        txtSemesterDashboard.setText(tSemester);

        btnKeluarWaldos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(DashboardWalidosenActivity.this);
                builder.setMessage("Are you sure you want to exit?")
                        .setIcon(R.mipmap.ic_launcher)
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                startActivity(new Intent(getApplicationContext(), LoginWalidosenActivity.class));
                                finish();
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();
            }
        });
    }

    public void MoveUserDataMahasiswaDashboard(View view) {
        String nidnData = txtNidn.getText().toString().trim();
        String namaDosenData = txtNamaDosen.getText().toString().trim();
        String prodiData = txtProdiDashboard.getText().toString().trim();
        String semesterData = txtSemesterDashboard.getText().toString().trim();
        Intent intent = new Intent(DashboardWalidosenActivity.this, UserMahasiswaWalidosenActivity.class);
        intent.putExtra("nidn", nidnData);
        intent.putExtra("namaDosen", namaDosenData);
        intent.putExtra("prodi", prodiData);
        intent.putExtra("semester", semesterData);
        startActivity(intent);
        overridePendingTransition(0, 0);
        finish();
    }

    public void MoveKrsDashboard(View view) {
        String nidnData = txtNidn.getText().toString().trim();
        String namaDosenData = txtNamaDosen.getText().toString().trim();
        String prodiData = txtProdiDashboard.getText().toString().trim();
        String semesterData = txtSemesterDashboard.getText().toString().trim();
        Intent intent = new Intent(DashboardWalidosenActivity.this, KrsWalidosenActivity.class);
        intent.putExtra("nidn", nidnData);
        intent.putExtra("namaDosen", namaDosenData);
        intent.putExtra("prodi", prodiData);
        intent.putExtra("semester", semesterData);
        startActivity(intent);
        overridePendingTransition(0, 0);
        finish();
    }

    public void MoveKhsDashboard(View view) {
        String nidnData = txtNidn.getText().toString().trim();
        String namaDosenData = txtNamaDosen.getText().toString().trim();
        String prodiData = txtProdiDashboard.getText().toString().trim();
        String semesterData = txtSemesterDashboard.getText().toString().trim();
        Intent intent = new Intent(DashboardWalidosenActivity.this, KhsWalidosenActivity.class);
        intent.putExtra("nidn", nidnData);
        intent.putExtra("namaDosen", namaDosenData);
        intent.putExtra("prodi", prodiData);
        intent.putExtra("semester", semesterData);
        startActivity(intent);
        overridePendingTransition(0, 0);
        finish();
    }

    public void MoveTnilaiDashboard(View view) {
        String nidnData = txtNidn.getText().toString().trim();
        String namaDosenData = txtNamaDosen.getText().toString().trim();
        String prodiData = txtProdiDashboard.getText().toString().trim();
        String semesterData = txtSemesterDashboard.getText().toString().trim();
        Intent intent = new Intent(DashboardWalidosenActivity.this, TnilaiWalidosenActivity.class);
        intent.putExtra("nidn", nidnData);
        intent.putExtra("namaDosen", namaDosenData);
        intent.putExtra("prodi", prodiData);
        intent.putExtra("semester", semesterData);
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
                        startActivity(new Intent(getApplicationContext(), LoginWalidosenActivity.class));
                        finish();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }
}