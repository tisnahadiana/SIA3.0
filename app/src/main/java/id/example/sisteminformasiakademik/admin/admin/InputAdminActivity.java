package id.example.sisteminformasiakademik.admin.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
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
import id.example.sisteminformasiakademik.admin.mahasiswa.InputMahasiswaActivity;
import id.example.sisteminformasiakademik.admin.mahasiswa.UserMahasiswaAdminActivity;

public class InputAdminActivity extends AppCompatActivity {

    TextView txtIdAdminInputADM, txtNamaAdminInputADM;
    EditText ed_regID, ed_nama,ed_email,ed_password;
    String str_ID,str_nama, str_email, str_password;
    String url = "https://droomp.tech/admin/admin/savedata_admin.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_admin);
        getSupportActionBar().hide();

        txtIdAdminInputADM = findViewById(R.id.txtIdAdminInputADM);
        txtNamaAdminInputADM = findViewById(R.id.txtNamaAdminInputADM);

        Intent i = getIntent();
        String tNim = i.getStringExtra("id_admin");
        String tName = i.getStringExtra("nama");

        txtIdAdminInputADM.setText(tNim);
        txtNamaAdminInputADM.setText(tName);

        ed_regID = findViewById(R.id.ed_inputID);
        ed_nama = findViewById(R.id.ed_inputNamaADM);
        ed_email = findViewById(R.id.ed_inputEmail);
        ed_password = findViewById(R.id.ed_inputPass);
    }

    public void InputDataAdmin(View view){

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Mohon Menunggu.....");

        if (ed_regID.getText().toString().equals("")){
            Toast.makeText(this, "Masukan ID", Toast.LENGTH_SHORT).show();
        }
        else if (ed_nama.getText().toString().equals("")){
            Toast.makeText(this, "Masukan Nama", Toast.LENGTH_SHORT).show();
        }
        else if (ed_email.getText().toString().equals("")){
            Toast.makeText(this, "Masukan Email", Toast.LENGTH_SHORT).show();
        }
        else if (ed_password.getText().toString().equals("")){
            Toast.makeText(this, "Masukan Password", Toast.LENGTH_SHORT).show();
        }
        else{

            progressDialog.show();
            str_ID = ed_regID.getText().toString().trim();
            str_nama = ed_nama.getText().toString().trim();
            str_email = ed_email.getText().toString().trim();
            str_password = ed_password.getText().toString().trim();

            StringRequest request = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                String result = jsonObject.getString("status");

                                if (result.equals("success")) {

                                    ed_regID.setText("");
                                    ed_nama.setText("");
                                    ed_email.setText("");
                                    ed_password.setText("");
                                    progressDialog.dismiss();
                                    massage("Input Data Berhasil");

                                } else if (result.equals("Data User Sudah Ada")){
                                    progressDialog.dismiss();
                                    massage("Data Admin Sudah Ada");
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

                    params.put("ID", str_ID);
                    params.put("nama", str_nama);
                    params.put("email", str_email);
                    params.put("password", str_password);
                    return params;
                }
            };
            RequestQueue queue = Volley.newRequestQueue(InputAdminActivity.this);
            queue.add(request);

        }
    }

    public void onBackPressed() {
        String idAdmin = txtIdAdminInputADM.getText().toString().trim();
        String namaAdmin = txtNamaAdminInputADM.getText().toString().trim();
        Intent intent = new Intent(getApplicationContext(), UserAdminActivity.class);
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