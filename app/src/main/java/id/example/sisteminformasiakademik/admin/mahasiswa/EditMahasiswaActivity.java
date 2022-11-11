package id.example.sisteminformasiakademik.admin.mahasiswa;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

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

public class EditMahasiswaActivity extends AppCompatActivity {

    TextView txtIdAdminInputmhs, txtNamaAdminInputmhs, txtNimMHS;
    EditText ed_regnim, ed_regnama,ed_regemail,ed_regpassword;
    String str_nim,str_nama, str_email, str_prodi, str_semester, str_nimData;
    String url = "https://droomp.tech/admin/mahasiswa/editdata.php";
    Spinner ProdiRegistrasi, SemesterRegistrasi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_mahasiswa);
        getSupportActionBar().hide();

        txtIdAdminInputmhs = findViewById(R.id.txtIdAdminEditmhs);
        txtNamaAdminInputmhs = findViewById(R.id.txtNamaAdminEditmhs);

        Intent i = getIntent();
        String tNim = i.getStringExtra("id_admin");
        String tName = i.getStringExtra("nama");

        txtIdAdminInputmhs.setText(tNim);
        txtNamaAdminInputmhs.setText(tName);

        ed_regnim = findViewById(R.id.ed_editNimMHS);
        ed_regnama = findViewById(R.id.ed_editNamaMHS);
        ed_regemail = findViewById(R.id.ed_EditEmailMHS);
        ed_regpassword = findViewById(R.id.ed_inputPass);
        ProdiRegistrasi = findViewById(R.id.spEditProdiMHSAdmin);
        SemesterRegistrasi = findViewById(R.id.spEditSemesterMHSAdmin);
        txtNimMHS = findViewById(R.id.txtNimMHSEdit);

        ed_regnim.setText(getIntent().getStringExtra("nimED"));
        ed_regnama.setText(getIntent().getStringExtra("namaED"));
        ed_regemail.setText(getIntent().getStringExtra("emailED"));
        txtNimMHS.setText(getIntent().getStringExtra("nimED"));


    }

    public void EditData(View view){

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Mohon Menunggu.....");

            progressDialog.show();
            str_nim = ed_regnim.getText().toString().trim();
            str_nama = ed_regnama.getText().toString().trim();
            str_email = ed_regemail.getText().toString().trim();

            str_prodi = ProdiRegistrasi.getSelectedItem().toString();
            str_semester = SemesterRegistrasi.getSelectedItem().toString();
            str_nimData = txtNimMHS.getText().toString().trim();


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

                    params.put("nimData", str_nimData);
                    params.put("nim", str_nim);
                    params.put("nama", str_nama);
                    params.put("email", str_email);
                    params.put("prodi", str_prodi);
                    params.put("semester", str_semester);
                    return params;
                }
            };
            RequestQueue queue = Volley.newRequestQueue(EditMahasiswaActivity.this);
            queue.add(request);

    }

    public void onBackPressed() {
        String idAdmin = txtIdAdminInputmhs.getText().toString().trim();
        String namaAdmin = txtNamaAdminInputmhs.getText().toString().trim();
        Intent intent = new Intent(getApplicationContext(), UserMahasiswaAdminActivity.class);
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