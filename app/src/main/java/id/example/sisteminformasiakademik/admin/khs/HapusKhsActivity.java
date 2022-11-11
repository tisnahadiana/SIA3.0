package id.example.sisteminformasiakademik.admin.khs;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import id.example.sisteminformasiakademik.R;
import id.example.sisteminformasiakademik.admin.mahasiswa.HapusMahasiswaActivity;
import id.example.sisteminformasiakademik.admin.mahasiswa.UserMahasiswaAdminActivity;

public class HapusKhsActivity extends AppCompatActivity {

    TextView nimHapusDKhs, namaHapusDKhs, prodiHapusDKHS,semesterHapusDKHS, txtIdAdminHapusDKHS, txtNamaAdminHapusDKHS,txtImageUrlHapusDKHS;
    String nimData, urlimg;
    Button hapus;
    ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hapus_khs);
        getSupportActionBar().hide();

        nimHapusDKhs = findViewById(R.id.nimHapusDKhs);
        namaHapusDKhs = findViewById(R.id.namaHapusDKhs);
        prodiHapusDKHS = findViewById(R.id.prodiHapusDKHS);
        semesterHapusDKHS = findViewById(R.id.semesterHapusDKHS);
        hapus = findViewById(R.id.btnHapusDKHSAdmin);
        img = findViewById(R.id.imghpusDataKhs);

        txtIdAdminHapusDKHS = findViewById(R.id.txtIdAdminHapusDKHS);
        txtNamaAdminHapusDKHS = findViewById(R.id.txtNamaAdminHapusDKHS);
        txtImageUrlHapusDKHS = findViewById(R.id.txtImageUrlHapusDKHS);


        nimHapusDKhs.setText(getIntent().getStringExtra("nimHPKHS"));
        namaHapusDKhs.setText(getIntent().getStringExtra("namaHPKHS"));
        prodiHapusDKHS.setText(getIntent().getStringExtra("prodiHPKHS"));
        semesterHapusDKHS.setText(getIntent().getStringExtra("semesterHPKHS"));
        txtImageUrlHapusDKHS.setText(getIntent().getStringExtra("imgHPKHS"));
        txtIdAdminHapusDKHS.setText(getIntent().getStringExtra("id_admin"));
        txtNamaAdminHapusDKHS.setText(getIntent().getStringExtra("nama"));
        urlimg = txtImageUrlHapusDKHS.getText().toString().trim();

        Glide.with(this).load(urlimg).into(img);


        hapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hapus_dataKHS();
            }
        });
    }

    public void hapus_dataKHS(){

        nimData = nimHapusDKhs.getText().toString().trim();

        String url = "https://droomp.tech/admin/khs/hapus_datakhs_admin.php";
        final ProgressDialog progressDialog = new ProgressDialog(HapusKhsActivity.this);
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
                                String idAdmin = txtIdAdminHapusDKHS.getText().toString().trim();
                                String namaAdmin = txtNamaAdminHapusDKHS.getText().toString().trim();
                                Intent intent = new Intent(HapusKhsActivity.this, KhsAdminActivity.class);
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
        RequestQueue queue = Volley.newRequestQueue(HapusKhsActivity.this);
        queue.add(request);

    }
    public void massage(String massage) {
        Toast.makeText(HapusKhsActivity.this, massage, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }
}