package id.example.sisteminformasiakademik.admin.mahasiswa;

import androidx.annotation.Nullable;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import id.example.sisteminformasiakademik.LoginActivity;
import id.example.sisteminformasiakademik.R;
import id.example.sisteminformasiakademik.RegistrasiActivity;
import id.example.sisteminformasiakademik.admin.DashboardAdminActivity;
import id.example.sisteminformasiakademik.user.DashboardActivity;

public class InputMahasiswaActivity extends AppCompatActivity {

    TextView txtIdAdminInputmhs, txtNamaAdminInputmhs;
    EditText ed_regnim, ed_regnama,ed_regemail,ed_regpassword;
    String str_nim,str_nama, str_email, str_password, str_prodi, str_semester;
    String url = "https://droomp.tech/admin/mahasiswa/savedata.php";
    Spinner ProdiRegistrasi, SemesterRegistrasi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_mahasiswa);
        getSupportActionBar().hide();

        txtIdAdminInputmhs = findViewById(R.id.txtIdAdminInputmhs);
        txtNamaAdminInputmhs = findViewById(R.id.txtNamaAdminInputmhs);

        Intent i = getIntent();
        String tNim = i.getStringExtra("id_admin");
        String tName = i.getStringExtra("nama");

        txtIdAdminInputmhs.setText(tNim);
        txtNamaAdminInputmhs.setText(tName);

        ed_regnim = findViewById(R.id.ed_inputNim);
        ed_regnama = findViewById(R.id.ed_inputNama);
        ed_regemail = findViewById(R.id.ed_inputEmail);
        ed_regpassword = findViewById(R.id.ed_inputPass);
        ProdiRegistrasi = findViewById(R.id.spInputProdiMHSAdmin);
        SemesterRegistrasi = findViewById(R.id.spInputSemesterMHSAdmin);
    }

    public void InputDataMHS(View view){

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Mohon Menunggu.....");

        if (ed_regnim.getText().toString().equals("")){
            Toast.makeText(this, "Masukan Nim", Toast.LENGTH_SHORT).show();
        }
        else if (ed_regnama.getText().toString().equals("")){
            Toast.makeText(this, "Masukan Nama", Toast.LENGTH_SHORT).show();
        }
        else if (ed_regemail.getText().toString().equals("")){
            Toast.makeText(this, "Masukan Email", Toast.LENGTH_SHORT).show();
        }
        else if (ed_regpassword.getText().toString().equals("")){
            Toast.makeText(this, "Masukan Password", Toast.LENGTH_SHORT).show();
        }
        else{

            progressDialog.show();
            str_nim = ed_regnim.getText().toString().trim();
            str_nama = ed_regnama.getText().toString().trim();
            str_email = ed_regemail.getText().toString().trim();
            str_password = ed_regpassword.getText().toString().trim();
            str_prodi = ProdiRegistrasi.getSelectedItem().toString();
            str_semester = SemesterRegistrasi.getSelectedItem().toString();


            StringRequest request = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                String result = jsonObject.getString("status");

                                if (result.equals("success")) {

                                        ed_regnim.setText("");
                                        ed_regnama.setText("");
                                        ed_regemail.setText("");
                                        ed_regpassword.setText("");
                                        progressDialog.dismiss();
                                        massage("Input Data Berhasil");

                                } else if (result.equals("Data User Sudah Ada")){
                                    progressDialog.dismiss();
                                    massage("Data User Sudah Ada");
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

                    params.put("nim", str_nim);
                    params.put("nama", str_nama);
                    params.put("email", str_email);
                    params.put("password", str_password);
                    params.put("prodi", str_prodi);
                    params.put("semester", str_semester);
                    return params;
                }
            };
            RequestQueue queue = Volley.newRequestQueue(InputMahasiswaActivity.this);
            queue.add(request);

        }
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