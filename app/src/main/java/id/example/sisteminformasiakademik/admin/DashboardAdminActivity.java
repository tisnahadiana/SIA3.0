package id.example.sisteminformasiakademik.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import id.example.sisteminformasiakademik.LoginAdminActivity;
import id.example.sisteminformasiakademik.R;
import id.example.sisteminformasiakademik.admin.admin.UserAdminActivity;
import id.example.sisteminformasiakademik.admin.khs.KhsAdminActivity;
import id.example.sisteminformasiakademik.admin.krs.KrsAdminActivity;
import id.example.sisteminformasiakademik.admin.mahasiswa.UserMahasiswaAdminActivity;
import id.example.sisteminformasiakademik.admin.matkul.MatkulAdminActivity;
import id.example.sisteminformasiakademik.admin.tnilaiAdmin.TnilaiAdminActivity;
import id.example.sisteminformasiakademik.admin.walidosen.UserWalidosenAdminActivity;

public class DashboardAdminActivity extends AppCompatActivity {

    private static final String FILE_NAME = "myFileAdmin";
    TextView txtNamaAdmin, txtIdAdmin;
    Button btnKeluarAdmin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_admin);
        getSupportActionBar().hide();

        txtNamaAdmin = findViewById(R.id.txtNamaDashboardAdmin);
        txtIdAdmin = findViewById(R.id.txtIdAdmin);
        btnKeluarAdmin = findViewById(R.id.btnKeluarAdmin);

        SharedPreferences sharedPreferences = getSharedPreferences(FILE_NAME, MODE_PRIVATE);
        String nim = sharedPreferences.getString("idAdminShare", "Data Not Found");
        String password = sharedPreferences.getString("passwordShare", "Data Not Found");
////        String nama = sharedPreferences.getString("nama", "Data Not Found");

        Intent i = getIntent();
        String tNim = i.getStringExtra("id_admin");
        String tName = i.getStringExtra("nama");

        txtIdAdmin.setText(tNim);
        txtNamaAdmin.setText(tName);

        btnKeluarAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(DashboardAdminActivity.this);
                builder.setMessage("Are you sure you want to exit?")
                        .setIcon(R.mipmap.ic_launcher)
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                startActivity(new Intent(getApplicationContext(), LoginAdminActivity.class));
                                finish();
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();
            }
        });
    }

    public void MoveUserDataMahasiswa(View view) {
        String nimData = txtIdAdmin.getText().toString().trim();
        String namaData = txtNamaAdmin.getText().toString().trim();
        Intent intent = new Intent(DashboardAdminActivity.this, UserMahasiswaAdminActivity.class);
        intent.putExtra("id_admin", nimData);
        intent.putExtra("nama", namaData);
        startActivity(intent);
        overridePendingTransition(0, 0);
        finish();
    }

    public void MoveUserDataUserWD(View view) {
        String nimData = txtIdAdmin.getText().toString().trim();
        String namaData = txtNamaAdmin.getText().toString().trim();
        Intent intent = new Intent(DashboardAdminActivity.this, UserWalidosenAdminActivity.class);
        intent.putExtra("id_admin", nimData);
        intent.putExtra("nama", namaData);
        startActivity(intent);
        overridePendingTransition(0, 0);
        finish();
    }

    public void MoveUserDataUserAdmin(View view) {
        String nimData = txtIdAdmin.getText().toString().trim();
        String namaData = txtNamaAdmin.getText().toString().trim();
        Intent intent = new Intent(DashboardAdminActivity.this, UserAdminActivity.class);
        intent.putExtra("id_admin", nimData);
        intent.putExtra("nama", namaData);
        startActivity(intent);
        overridePendingTransition(0, 0);
        finish();
    }

    public void MoveUserDatakrs(View view) {
        String nimData = txtIdAdmin.getText().toString().trim();
        String namaData = txtNamaAdmin.getText().toString().trim();
        Intent intent = new Intent(DashboardAdminActivity.this, KrsAdminActivity.class);
        intent.putExtra("id_admin", nimData);
        intent.putExtra("nama", namaData);
        startActivity(intent);
        overridePendingTransition(0, 0);
        finish();
    }

    public void MoveUserDatakhs(View view) {
        String nimData = txtIdAdmin.getText().toString().trim();
        String namaData = txtNamaAdmin.getText().toString().trim();
        Intent intent = new Intent(DashboardAdminActivity.this, KhsAdminActivity.class);
        intent.putExtra("id_admin", nimData);
        intent.putExtra("nama", namaData);
        startActivity(intent);
        overridePendingTransition(0, 0);
        finish();
    }

    public void MoveUserDataMatkul(View view) {
        String nimData = txtIdAdmin.getText().toString().trim();
        String namaData = txtNamaAdmin.getText().toString().trim();
        Intent intent = new Intent(DashboardAdminActivity.this, MatkulAdminActivity.class);
        intent.putExtra("id_admin", nimData);
        intent.putExtra("nama", namaData);
        startActivity(intent);
        overridePendingTransition(0, 0);
        finish();
    }

    public void MoveUserDataTnilai(View view) {
        String nimData = txtIdAdmin.getText().toString().trim();
        String namaData = txtNamaAdmin.getText().toString().trim();
        Intent intent = new Intent(DashboardAdminActivity.this, TnilaiAdminActivity.class);
        intent.putExtra("id_admin", nimData);
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
                        startActivity(new Intent(getApplicationContext(), LoginAdminActivity.class));
                        finish();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }
}