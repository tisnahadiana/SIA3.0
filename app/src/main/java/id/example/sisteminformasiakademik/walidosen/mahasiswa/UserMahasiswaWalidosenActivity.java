package id.example.sisteminformasiakademik.walidosen.mahasiswa;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import id.example.sisteminformasiakademik.R;
import id.example.sisteminformasiakademik.admin.DashboardAdminActivity;
import id.example.sisteminformasiakademik.user.matkul.MataKuliahActivity;
import id.example.sisteminformasiakademik.user.matkul.MatkulAdapter;
import id.example.sisteminformasiakademik.user.matkul.MatkulData;
import id.example.sisteminformasiakademik.walidosen.DashboardWalidosenActivity;

public class UserMahasiswaWalidosenActivity extends AppCompatActivity {

    TextView txtNidnDM, txtNamaWaldosDM, txtProdiUSM, txtSemesterUSM, namaProdiUSM, noSemesterUSM;
    private RecyclerView recyclerView;
    private MahasiswaAdapter mahasiswaAdapter;
    private List<MahasiswaData> mahasiswaDataList;
//    Spinner spinnerMatkul;
    String UrlMahasiswaWaldos = "https://droomp.tech/walidosen/show_mahasiswa.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_mahasiswa_walidosen);
        getSupportActionBar().hide();

        txtNidnDM = findViewById(R.id.txtNIDNDataMHS);
        txtNamaWaldosDM = findViewById(R.id.txtNamaWaldosDataMHS);
        txtProdiUSM = findViewById(R.id.txtProdiWaldosUSM);
        txtSemesterUSM = findViewById(R.id.txtSemesterWaldosUSM);
        namaProdiUSM = findViewById(R.id.namaProdiWaldosUSM);
        noSemesterUSM = findViewById(R.id.noSemesterWaldosUSM);

        Intent i = getIntent();
        String tNim = i.getStringExtra("nidn");
        String tName = i.getStringExtra("namaDosen");
        String tProdi = i.getStringExtra("prodi");
        String tSemester = i.getStringExtra("semester");

        txtNidnDM.setText(tNim);
        txtNamaWaldosDM.setText(tName);
        txtProdiUSM.setText(tProdi);
        txtSemesterUSM.setText(tSemester);
        namaProdiUSM.setText(tProdi);
        noSemesterUSM.setText(tSemester);

        recyclerView = findViewById(R.id.rv_datamahasiswaWaldos);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mahasiswaDataList = new ArrayList<>();

        LoadDataMahasiswaWaldos();
    }

    private void LoadDataMahasiswaWaldos() {
        String prodi = txtProdiUSM.getText().toString();
        String semester = txtSemesterUSM.getText().toString();

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Mohon Menunggu.....");

        progressDialog.show();

        StringRequest request = new StringRequest(Request.Method.POST, UrlMahasiswaWaldos, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray j = new JSONArray(response);
                    // Parsea json
//                    mahasiswaDataList.clear();
                    for (int i = 0; i < j.length(); i++) {
                        try {
                            JSONObject object = j.getJSONObject(i);
                            String nim = object.getString("nim").trim();
                            String nama = object.getString("nama").trim();
                            String prodi = object.getString("prodi").trim();
                            String semester = object.getString("semester").trim();

                            MahasiswaData mahasiswaData = new MahasiswaData();
                            mahasiswaData.setNim(nim);
                            mahasiswaData.setNama(nama);
                            mahasiswaData.setProdi(prodi);
                            mahasiswaData.setSemester(semester);
                            mahasiswaDataList.add(mahasiswaData);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    mahasiswaAdapter = new MahasiswaAdapter(UserMahasiswaWalidosenActivity.this, mahasiswaDataList);
                    recyclerView.setAdapter(mahasiswaAdapter);
                    progressDialog.dismiss();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(UserMahasiswaWalidosenActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("prodi", prodi);
                params.put("semester", semester);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(UserMahasiswaWalidosenActivity.this);
        requestQueue.add(request);
    }

    public void moveDashboardDMWaldos(View view){
        String idAdmin = txtNidnDM.getText().toString().trim();
        String namaAdmin = txtNamaWaldosDM.getText().toString().trim();
        String prodiKHS = txtProdiUSM.getText().toString().trim();
        String semesterKHS = txtSemesterUSM.getText().toString().trim();
        Intent intent = new Intent(getApplicationContext(), DashboardWalidosenActivity.class);
        intent.putExtra("nidn", idAdmin);
        intent.putExtra("namaDosen", namaAdmin);
        intent.putExtra("prodi", prodiKHS);
        intent.putExtra("semester", semesterKHS);
        startActivity(intent);
        overridePendingTransition(0, 0);
        finish();
    }

    public void onBackPressed() {
        String idAdmin = txtNidnDM.getText().toString().trim();
        String namaAdmin = txtNamaWaldosDM.getText().toString().trim();
        String prodiKHS = txtProdiUSM.getText().toString().trim();
        String semesterKHS = txtSemesterUSM.getText().toString().trim();
        Intent intent = new Intent(getApplicationContext(), DashboardWalidosenActivity.class);
        intent.putExtra("nidn", idAdmin);
        intent.putExtra("namaDosen", namaAdmin);
        intent.putExtra("prodi", prodiKHS);
        intent.putExtra("semester", semesterKHS);
        startActivity(intent);
        overridePendingTransition(0, 0);
        finish();

    }
}