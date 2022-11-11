package id.example.sisteminformasiakademik.admin.matkul;

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
import id.example.sisteminformasiakademik.admin.mahasiswa.HapusMahasiswaActivity;
import id.example.sisteminformasiakademik.admin.mahasiswa.UserMahasiswaAdminActivity;

public class HapusMatkulActivity extends AppCompatActivity {

    TextView txtIdAdminHapusMatkul, txtNamaAdminHapusMatkul,namaMatkul, SKS, prodi, semester, namaMatkulData;
    String MatkulData;
    Button hapus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hapus_matkul);
        getSupportActionBar().hide();

        txtIdAdminHapusMatkul = findViewById(R.id.txtIdAdminHapusMatkul);
        txtNamaAdminHapusMatkul = findViewById(R.id.txtNamaAdminHapusMatkul);
        namaMatkulData = findViewById(R.id.namaMatkulData);

        Intent i = getIntent();
        String tNim = i.getStringExtra("id_admin");
        String tName = i.getStringExtra("nama");

        txtIdAdminHapusMatkul.setText(tNim);
        txtNamaAdminHapusMatkul.setText(tName);

        namaMatkul = findViewById(R.id.namaMatkulHapusdata);
        SKS = findViewById(R.id.sksHapusdata);
        prodi = findViewById(R.id.prodiMatkulHapusdata);
        semester = findViewById(R.id.semesterMatkulHapusdata);
        hapus = findViewById(R.id.btnHapusMatkulDataAdmin);


        txtIdAdminHapusMatkul.setText(getIntent().getStringExtra("id_admin"));
        txtNamaAdminHapusMatkul.setText(getIntent().getStringExtra("nama"));
        namaMatkul.setText(getIntent().getStringExtra("namaMatkulHP"));
        SKS.setText(getIntent().getStringExtra("sksMatkulHP"));
        prodi.setText(getIntent().getStringExtra("prodiMatkulHP"));
        semester.setText(getIntent().getStringExtra("semesterMatkulHP"));
        namaMatkulData.setText(getIntent().getStringExtra("namaMatkulHP"));

        hapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hapus_dataMatkul();
            }
        });
    }


    public void hapus_dataMatkul(){

        MatkulData = namaMatkulData.getText().toString().trim();

        String url = "https://droomp.tech/admin/matkul/delete_data_matkul.php";
        final ProgressDialog progressDialog = new ProgressDialog(HapusMatkulActivity.this);
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
                                String idAdmin = txtIdAdminHapusMatkul.getText().toString().trim();
                                String namaAdmin = txtNamaAdminHapusMatkul.getText().toString().trim();
                                Intent intent = new Intent(HapusMatkulActivity.this, MatkulAdminActivity.class);
                                intent.putExtra("id_admin", idAdmin);
                                intent.putExtra("nama", namaAdmin);
                                startActivity(intent);
                                finish();
                            }else {
                                massage("Error");
                                progressDialog.dismiss();
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

                params.put("nama_mk", MatkulData);
                return params;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(HapusMatkulActivity.this);
        queue.add(request);

    }
    public void massage(String massage) {
        Toast.makeText(HapusMatkulActivity.this, massage, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }
}