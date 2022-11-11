package id.example.sisteminformasiakademik.admin.mahasiswa;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
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

import id.example.sisteminformasiakademik.LoginActivity;
import id.example.sisteminformasiakademik.R;
import id.example.sisteminformasiakademik.admin.admin.HapusAdminActivity;
import id.example.sisteminformasiakademik.admin.admin.UserAdminActivity;
import id.example.sisteminformasiakademik.user.DashboardActivity;

public class HapusMahasiswaActivity extends AppCompatActivity {

    TextView nim, nama, email, prodi, semester, txtIdAdminHapusMHS, txtNamaAdminHapusMHS;
    String nimData;
    Button hapus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hapus_mahasiswa);
        getSupportActionBar().hide();

        nim = findViewById(R.id.nimhapusdata);
        nama = findViewById(R.id.namaHapusdata);
        email = findViewById(R.id.emailHapusdata);
        prodi = findViewById(R.id.prodiHapusdata);
        semester = findViewById(R.id.semesterHapusdata);
        hapus = findViewById(R.id.btnHapusDataMHSAdmin);

        txtIdAdminHapusMHS = findViewById(R.id.txtIdAdminHapusdataMHS);
        txtNamaAdminHapusMHS = findViewById(R.id.txtNamaAdminHapusdataMHS);


        nim.setText(getIntent().getStringExtra("nimHP"));
        nama.setText(getIntent().getStringExtra("namaHP"));
        email.setText(getIntent().getStringExtra("emailHP"));
        prodi.setText(getIntent().getStringExtra("prodiHP"));
        semester.setText(getIntent().getStringExtra("semesterHP"));
        txtIdAdminHapusMHS.setText(getIntent().getStringExtra("id_admin"));
        txtNamaAdminHapusMHS.setText(getIntent().getStringExtra("nama"));

        hapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hapus_dataMHS();
            }
        });
    }

    public void hapus_dataMHS(){

        nimData = nim.getText().toString().trim();

        String url = "https://droomp.tech/admin/mahasiswa/delete_data.php";
        final ProgressDialog progressDialog = new ProgressDialog(HapusMahasiswaActivity.this);
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
                                String idAdmin = txtIdAdminHapusMHS.getText().toString().trim();
                                String namaAdmin = txtNamaAdminHapusMHS.getText().toString().trim();
                                Intent intent = new Intent(HapusMahasiswaActivity.this, UserMahasiswaAdminActivity.class);
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

                params.put("nim", nimData);
                return params;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(HapusMahasiswaActivity.this);
        queue.add(request);

    }
    public void massage(String massage) {
        Toast.makeText(HapusMahasiswaActivity.this, massage, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }
}