package id.example.sisteminformasiakademik.user.matkul;

import static androidx.fragment.app.FragmentManager.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
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
import id.example.sisteminformasiakademik.user.menu.MenuActivity;

public class MataKuliahActivity extends AppCompatActivity {

    TextView txtNimMatkul, txtNamaMatkul;
    private RecyclerView recyclerView;
    private MatkulAdapter matkulAdapter;
    private List<MatkulData> matkulDataList;
    Spinner spinnerMatkul;
    String UrlMatkul = "https://droomp.tech/user/show_matkul.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mata_kuliah);

        getSupportActionBar().hide();
        txtNimMatkul = findViewById(R.id.txtNimMatkul);
        txtNamaMatkul = findViewById(R.id.txtNamaMatkul);

        Intent i = getIntent();
        String tNim = i.getStringExtra("nim");
        String tName = i.getStringExtra("nama");

        txtNimMatkul.setText(tNim);
        txtNamaMatkul.setText(tName);

        spinnerMatkul = findViewById(R.id.spinnerMatkul);
        recyclerView = findViewById(R.id.recyclerViewMatkul);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        matkulDataList = new ArrayList<>();


        spinnerMatkul.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                LoadMatkul();
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });


    }

    private void LoadMatkul() {

//        String semester = String.valueOf(spinnerMatkul.getSelectedItem());
        String semester = spinnerMatkul.getSelectedItem().toString();

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Mohon Menunggu.....");

        progressDialog.show();

        StringRequest request = new StringRequest(Request.Method.POST, UrlMatkul, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray j = new JSONArray(response);
                    // Parsea json
                    matkulDataList.clear();
                    for (int i = 0; i < j.length(); i++) {
                        try {
                            JSONObject object = j.getJSONObject(i);
                            String namaMatkul = object.getString("nama_mk").trim();
                            String sks = object.getString("sks").trim();

                            MatkulData matkulData = new MatkulData();
                            matkulData.setNama_matkul(namaMatkul);
                            matkulData.setSks(sks);
                            matkulDataList.add(matkulData);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    matkulAdapter = new MatkulAdapter(MataKuliahActivity.this, matkulDataList);
                    recyclerView.setAdapter(matkulAdapter);
                    progressDialog.dismiss();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(MataKuliahActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("semester", semester);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(MataKuliahActivity.this);
        requestQueue.add(request);
    }

    public void moveMenuMatkul(View view){
        String nimData = txtNimMatkul.getText().toString().trim();
        String namaData = txtNamaMatkul.getText().toString().trim();
        Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
        intent.putExtra("nim", nimData);
        intent.putExtra("nama", namaData);
        startActivity(intent);
        overridePendingTransition(0, 0);
        finish();
    }

    public void onBackPressed() {
        String nimData = txtNimMatkul.getText().toString().trim();
        String namaData = txtNamaMatkul.getText().toString().trim();
        Intent intent = new Intent(getApplicationContext(),MenuActivity.class);
        intent.putExtra("nim", nimData);
        intent.putExtra("nama", namaData);
        startActivity(intent);
        overridePendingTransition(0, 0);
        finish();

    }
}