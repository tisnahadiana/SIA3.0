package id.example.sisteminformasiakademik.admin.admin;

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

public class HapusAdminActivity extends AppCompatActivity {

    TextView id, nama, email, txtIdAdminHapusMHS, txtNamaAdminHapusMHS;
    String idData;
    Button hapus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hapus_admin);
        getSupportActionBar().hide();

        id = findViewById(R.id.idhapusdataADM);
        nama = findViewById(R.id.namaHapusADM);
        email = findViewById(R.id.emailHapusADM);
        hapus = findViewById(R.id.btnHapusADM);
        txtIdAdminHapusMHS = findViewById(R.id.txtIdAdminHapusMHS);
        txtNamaAdminHapusMHS = findViewById(R.id.txtNamaAdminHapusMHS);

        id.setText(getIntent().getStringExtra("id_adminHP"));
        nama.setText(getIntent().getStringExtra("namaHP"));
        email.setText(getIntent().getStringExtra("emailHP"));
        txtIdAdminHapusMHS.setText(getIntent().getStringExtra("id_admin"));
        txtNamaAdminHapusMHS.setText(getIntent().getStringExtra("nama"));

        hapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hapus_dataAdmin();
            }
        });
    }

    private void hapus_dataAdmin() {

        idData = id.getText().toString().trim();

        String url = "https://droomp.tech/admin/admin/delete_data_admin.php";
        final ProgressDialog progressDialog = new ProgressDialog(HapusAdminActivity.this);
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
                                Intent intent = new Intent(HapusAdminActivity.this, UserAdminActivity.class);
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

                params.put("id", idData);
                return params;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(HapusAdminActivity.this);
        queue.add(request);

    }
    public void massage(String massage) {
        Toast.makeText(HapusAdminActivity.this, massage, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }
}