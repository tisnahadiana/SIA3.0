package id.example.sisteminformasiakademik.admin.krs;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import id.example.sisteminformasiakademik.R;
import id.example.sisteminformasiakademik.admin.admin.HapusAdminActivity;
import id.example.sisteminformasiakademik.admin.admin.UserAdminActivity;

public class HapusKrsActivity extends AppCompatActivity {

    TextView txtIdAdminKRS,txtNamaAdminKRS,txtdate, txtNamaLihatKrs, txtNimLihatKrs, txtProdi, txtSemester, txtmatkul1, txtsks1, txtmatkul2, txtsks2, txtmatkul3, txtsks3, txtmatkul4, txtsks4, txtmatkul5, txtsks5, txtmatkul6, txtsks6, txtmatkul7, txtsks7, txtmatkul8, txtsks8, txtjumlahsks;
    public final int REQUEST_CODE = 100;
    Button btnHapusKrs;
    public static int REQUEST_PERMISSIONS = 1;
    boolean boolean_permission;
    boolean boolean_save;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hapus_krs);
        getSupportActionBar().hide();

        txtIdAdminKRS = findViewById(R.id.txtIdAdminHapusKRS);
        txtNamaAdminKRS = findViewById(R.id.txtNamaAdminHapusKRS);
        btnHapusKrs = findViewById(R.id.btnHapusKrsadmin);
        txtNimLihatKrs = findViewById(R.id.txtNimHapusKRSadmin);
        txtNamaLihatKrs = findViewById(R.id.txtNamaHapusKRSadmin);
        txtProdi = findViewById(R.id.txtprodiHapusKrsadmin);
        txtSemester = findViewById(R.id.txtsemesterHapusKrsadmin);
        txtmatkul1 = findViewById(R.id.tvMatkul1adminHapus);
        txtsks1 = findViewById(R.id.tvSks1adminHapus);
        txtmatkul2 = findViewById(R.id.tvMatkul2adminHapus);
        txtsks2 = findViewById(R.id.tvSks2adminHapus);
        txtmatkul3 = findViewById(R.id.tvMatkul3adminHapus);
        txtsks3 = findViewById(R.id.tvSks3adminHapus);
        txtmatkul4 = findViewById(R.id.tvMatkul4adminHapus);
        txtsks4 = findViewById(R.id.tvSks4adminHapus);
        txtmatkul5 = findViewById(R.id.tvMatkul5adminHapus);
        txtsks5 = findViewById(R.id.tvSks5adminHapus);
        txtmatkul6 = findViewById(R.id.tvMatkul6adminHapus);
        txtsks6 = findViewById(R.id.tvSks6adminHapus);
        txtmatkul7 = findViewById(R.id.tvMatkul7adminHapus);
        txtsks7 = findViewById(R.id.tvSks7adminHapus);
        txtmatkul8 = findViewById(R.id.tvMatkul8adminHapus);
        txtsks8 = findViewById(R.id.tvSks8adminHapus);
        txtjumlahsks = findViewById(R.id.tvJumlahsksadminHapus);
        txtdate = findViewById(R.id.dateKrsadminHapus);

        btnHapusKrs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hapusKRS();
            }
        });

        Intent i = getIntent();
        String admin = i.getStringExtra("id_admin");
        String nama = i.getStringExtra("namaAdmin");
        String tNim = i.getStringExtra("nim");
        String tName = i.getStringExtra("nama");
        String prodi = i.getStringExtra("prodi");
        String semester = i.getStringExtra("semester");
        String matkul1 = i.getStringExtra("matkul1");
        String sks1 = i.getStringExtra("sks1");
        String matkul2 = i.getStringExtra("matkul2");
        String sks2 = i.getStringExtra("sks2");
        String matkul3 = i.getStringExtra("matkul3");
        String sks3 = i.getStringExtra("sks3");
        String matkul4 = i.getStringExtra("matkul4");
        String sks4 = i.getStringExtra("sks4");
        String matkul5 = i.getStringExtra("matkul5");
        String sks5 = i.getStringExtra("sks5");
        String matkul6 = i.getStringExtra("matkul6");
        String sks6 = i.getStringExtra("sks6");
        String matkul7 = i.getStringExtra("matkul7");
        String sks7 = i.getStringExtra("sks7");
        String matkul8 = i.getStringExtra("matkul8");
        String sks8 = i.getStringExtra("sks8");
        String jumlahsks = i.getStringExtra("jumlahsks");
        String date = i.getStringExtra("date");


        txtNimLihatKrs.setText(tNim);
        txtNamaLihatKrs.setText(tName);
        txtProdi.setText(prodi);
        txtSemester.setText(semester);
        txtmatkul1.setText(matkul1);
        txtsks1.setText(sks1);
        txtmatkul2.setText(matkul2);
        txtsks2.setText(sks2);
        txtmatkul3.setText(matkul3);
        txtsks3.setText(sks3);
        txtmatkul4.setText(matkul4);
        txtsks4.setText(sks4);
        txtmatkul5.setText(matkul5);
        txtsks5.setText(sks5);
        txtmatkul6.setText(matkul6);
        txtsks6.setText(sks6);
        txtmatkul7.setText(matkul7);
        txtsks7.setText(sks7);
        txtmatkul8.setText(matkul8);
        txtsks8.setText(sks8);
        txtjumlahsks.setText(jumlahsks);
        txtdate.setText(date);

        txtIdAdminKRS.setText(admin);
        txtNamaAdminKRS.setText(nama);
    }

    private void hapusKRS() {

        String nim = txtNimLihatKrs.getText().toString().trim();
        String nama = txtNamaLihatKrs.getText().toString().trim();

        String url = "https://droomp.tech/admin/krs/deletekrs_admin.php";
        final ProgressDialog progressDialog = new ProgressDialog(HapusKrsActivity.this);
        progressDialog.setMessage("Mohon Menunggu.....");

        progressDialog.show();
        StringRequest request = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String result = jsonObject.getString("status");

                            if (result.equals("success")) {
                                progressDialog.dismiss();
                                massage("Hapus Data Berhasil");
                                String idAdmin = txtIdAdminKRS.getText().toString().trim();
                                String namaAdmin = txtNamaAdminKRS.getText().toString().trim();
                                Intent intent = new Intent(HapusKrsActivity.this, KrsAdminActivity.class);
                                intent.putExtra("id_admin", idAdmin);
                                intent.putExtra("nama", namaAdmin);
                                startActivity(intent);
                                finish();
                            }else {
                                progressDialog.dismiss();
                                massage("Error");

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                massage(error.getMessage());
                progressDialog.dismiss();
            }
        }) {
            //                @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                params.put("nim", nim);
                params.put("nama", nama);
                return params;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(HapusKrsActivity.this);
        queue.add(request);
    }

    public void massage(String massage) {
        Toast.makeText(HapusKrsActivity.this, massage, Toast.LENGTH_LONG).show();
    }

    public void onBackPressed() {
        String nimData = txtIdAdminKRS.getText().toString().trim();
        String namaData = txtNamaAdminKRS.getText().toString().trim();
        Intent intent = new Intent(getApplicationContext(), KrsAdminActivity.class);
        intent.putExtra("id_admin", nimData);
        intent.putExtra("nama", namaData);
        startActivity(intent);
        overridePendingTransition(0, 0);
        finish();
    }
}