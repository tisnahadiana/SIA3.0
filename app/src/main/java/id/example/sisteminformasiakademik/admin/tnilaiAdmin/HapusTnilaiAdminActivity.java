package id.example.sisteminformasiakademik.admin.tnilaiAdmin;

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
import id.example.sisteminformasiakademik.admin.khs.HapusKhsActivity;
import id.example.sisteminformasiakademik.admin.khs.KhsAdminActivity;

public class HapusTnilaiAdminActivity extends AppCompatActivity {

    TextView nimHapusTnilai, namaHapusTnilai, prodiHapusTnilai,semesterHapusTnilai, txtIdAdminHapusTnilai, txtNamaAdminHapusTnilai,txtImageUrlHapusTnilai;
    String nimData, urlimg;
    Button hapus;
    ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hapus_tnilai_admin);
        getSupportActionBar().hide();

        nimHapusTnilai = findViewById(R.id.nimHapusTnilai);
        namaHapusTnilai = findViewById(R.id.namaHapusTnilai);
        prodiHapusTnilai = findViewById(R.id.prodiHapusTnilai);
        semesterHapusTnilai = findViewById(R.id.semesterHapusTnilai);
        hapus = findViewById(R.id.btnHapusTnilaiAdmin);
        img = findViewById(R.id.imghpusDataTnilai);

        txtIdAdminHapusTnilai = findViewById(R.id.txtIdAdminHapusTnilai);
        txtNamaAdminHapusTnilai = findViewById(R.id.txtNamaAdminHapusTnilai);
        txtImageUrlHapusTnilai = findViewById(R.id.txtImageUrlHapusTnilai);

        nimHapusTnilai.setText(getIntent().getStringExtra("nimHPTNL"));
        namaHapusTnilai.setText(getIntent().getStringExtra("namaHPTNL"));
        prodiHapusTnilai.setText(getIntent().getStringExtra("prodiHPTNL"));
        semesterHapusTnilai.setText(getIntent().getStringExtra("semesterHPTNL"));
        txtImageUrlHapusTnilai.setText(getIntent().getStringExtra("imgHPTNL"));
        txtIdAdminHapusTnilai.setText(getIntent().getStringExtra("id_admin"));
        txtNamaAdminHapusTnilai.setText(getIntent().getStringExtra("nama"));
        urlimg = txtImageUrlHapusTnilai.getText().toString().trim();

        Glide.with(this).load(urlimg).into(img);


        hapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hapus_dataKHS();
            }
        });
    }


    public void hapus_dataKHS(){

        nimData = nimHapusTnilai.getText().toString().trim();

        String url = "https://droomp.tech/admin/tnilai/hapus_datatnilai_admin.php";
        final ProgressDialog progressDialog = new ProgressDialog(HapusTnilaiAdminActivity.this);
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
                                String idAdmin = txtIdAdminHapusTnilai.getText().toString().trim();
                                String namaAdmin = txtNamaAdminHapusTnilai.getText().toString().trim();
                                Intent intent = new Intent(HapusTnilaiAdminActivity.this, TnilaiAdminActivity.class);
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
        RequestQueue queue = Volley.newRequestQueue(HapusTnilaiAdminActivity.this);
        queue.add(request);

    }
    public void massage(String massage) {
        Toast.makeText(HapusTnilaiAdminActivity.this, massage, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }
}