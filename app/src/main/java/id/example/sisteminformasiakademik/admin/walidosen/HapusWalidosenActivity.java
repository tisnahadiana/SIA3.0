package id.example.sisteminformasiakademik.admin.walidosen;

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
import id.example.sisteminformasiakademik.admin.mahasiswa.HapusMahasiswaActivity;
import id.example.sisteminformasiakademik.admin.mahasiswa.UserMahasiswaAdminActivity;

public class HapusWalidosenActivity extends AppCompatActivity {

    TextView nidn, nama, email, prodi, semester,txtIdAdminHapusWD,txtNamaAdminHapusWD;
    String nidnData;
    Button hapus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hapus_walidosen);
        getSupportActionBar().hide();

        nidn = findViewById(R.id.nidnhapusdataWD);
        nama = findViewById(R.id.namaHapusWD);
        email = findViewById(R.id.emailHapusWD);
        prodi = findViewById(R.id.prodiHapusWD);
        semester = findViewById(R.id.semesterHapusWD);
        hapus = findViewById(R.id.btnHapusMHSAdmin);

        txtIdAdminHapusWD = findViewById(R.id.txtIdAdminHapusWD);
        txtNamaAdminHapusWD = findViewById(R.id.txtNamaAdminHapusWD);

        txtIdAdminHapusWD.setText(getIntent().getStringExtra("id_admin"));
        txtNamaAdminHapusWD.setText(getIntent().getStringExtra("nama"));
        nidn.setText(getIntent().getStringExtra("nimHPWD"));
        nama.setText(getIntent().getStringExtra("namaHPWD"));
        email.setText(getIntent().getStringExtra("emailHPWD"));
        prodi.setText(getIntent().getStringExtra("prodiHPWD"));
        semester.setText(getIntent().getStringExtra("semesterHPWD"));

        hapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hapus_dataWD();
            }
        });
    }

    public void hapus_dataWD(){

        nidnData = nidn.getText().toString().trim();

        String url = "https://droomp.tech/admin/walidosen/delete_data_walidosen.php";
        final ProgressDialog progressDialog = new ProgressDialog(HapusWalidosenActivity.this);
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
                                String idAdmin = txtIdAdminHapusWD.getText().toString().trim();
                                String namaAdmin = txtNamaAdminHapusWD.getText().toString().trim();
                                Intent intent = new Intent(HapusWalidosenActivity.this, UserWalidosenAdminActivity.class);
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

                params.put("nidn", nidnData);
                return params;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(HapusWalidosenActivity.this);
        queue.add(request);

    }
    public void massage(String massage) {
        Toast.makeText(HapusWalidosenActivity.this, massage, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }
}