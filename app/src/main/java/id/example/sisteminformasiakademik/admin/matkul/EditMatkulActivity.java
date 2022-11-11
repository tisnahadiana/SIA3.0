package id.example.sisteminformasiakademik.admin.matkul;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import id.example.sisteminformasiakademik.R;
import id.example.sisteminformasiakademik.admin.mahasiswa.EditMahasiswaActivity;
import id.example.sisteminformasiakademik.admin.mahasiswa.UserMahasiswaAdminActivity;

public class EditMatkulActivity extends AppCompatActivity {

    TextView txtIdAdminEditMatkul, txtNamaAdminEditMatkul, txtNamaDataMatkulEdit;
    EditText ed_editNamaMatkul, ed_editSKSMatkul;
    String str_namaMK,str_sks, str_prodi, str_semester, str_NamaMatkulData;
    String url = "https://droomp.tech/admin/matkul/editdata_matkul.php";
    Spinner spEditProdiMatkulAdmin, spEditSemesterMatkulAdmin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_matkul);
        getSupportActionBar().hide();

        txtIdAdminEditMatkul = findViewById(R.id.txtIdAdminEditMatkul);
        txtNamaAdminEditMatkul = findViewById(R.id.txtNamaAdminEditMatkul);

        Intent i = getIntent();
        String tNim = i.getStringExtra("id_admin");
        String tName = i.getStringExtra("nama");

        txtIdAdminEditMatkul.setText(tNim);
        txtNamaAdminEditMatkul.setText(tName);

        ed_editNamaMatkul = findViewById(R.id.ed_editNamaMatkul);
        ed_editSKSMatkul = findViewById(R.id.ed_editSKSMatkul);
        spEditProdiMatkulAdmin = findViewById(R.id.spEditProdiMatkulAdmin);
        spEditSemesterMatkulAdmin = findViewById(R.id.spEditSemesterMatkulAdmin);
        txtNamaDataMatkulEdit = findViewById(R.id.txtNamaDataMatkulEdit);

        ed_editNamaMatkul.setText(getIntent().getStringExtra("namaMatkulED"));
        ed_editSKSMatkul.setText(getIntent().getStringExtra("sksED"));
        txtNamaDataMatkulEdit.setText(getIntent().getStringExtra("namaMatkulED"));

    }


    public void EditDataMatkul(View view){

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Mohon Menunggu.....");

        progressDialog.show();
        str_namaMK = ed_editNamaMatkul.getText().toString().trim();
        str_sks = ed_editSKSMatkul.getText().toString().trim();
        str_prodi = spEditProdiMatkulAdmin.getSelectedItem().toString();
        str_semester = spEditSemesterMatkulAdmin.getSelectedItem().toString();
        str_NamaMatkulData = txtNamaDataMatkulEdit.getText().toString().trim();


        StringRequest request = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String result = jsonObject.getString("status");

                            if (result.equals("success")) {

                                progressDialog.dismiss();
                                massage("Edit Data Berhasil");

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
                progressDialog.dismiss();
                massage(error.getMessage());
            }
        }) {
            //                @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                params.put("namaMKData", str_NamaMatkulData);
                params.put("nama_mk", str_namaMK);
                params.put("sks", str_sks);
                params.put("prodi", str_prodi);
                params.put("semester", str_semester);
                return params;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(EditMatkulActivity.this);
        queue.add(request);

    }

    public void onBackPressed() {
        String idAdmin = txtIdAdminEditMatkul.getText().toString().trim();
        String namaAdmin = txtNamaAdminEditMatkul.getText().toString().trim();
        Intent intent = new Intent(getApplicationContext(), MatkulAdminActivity.class);
        intent.putExtra("id_admin", idAdmin);
        intent.putExtra("nama", namaAdmin);
        startActivity(intent);
        overridePendingTransition(0, 0);
        finish();

    }

    public void massage(String massage) {
        Toast.makeText(this, massage, Toast.LENGTH_LONG).show();
    }
}