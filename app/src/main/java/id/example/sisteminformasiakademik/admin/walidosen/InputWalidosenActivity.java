package id.example.sisteminformasiakademik.admin.walidosen;

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
import id.example.sisteminformasiakademik.admin.mahasiswa.InputMahasiswaActivity;
import id.example.sisteminformasiakademik.admin.mahasiswa.UserMahasiswaAdminActivity;

public class InputWalidosenActivity extends AppCompatActivity {

    TextView txtIdAdminInputWD, txtNamaAdminInputWD;
    EditText ed_nidn, ed_namaWD,ed_emailWD,ed_passwordWD;
    String str_nidn,str_nama, str_email, str_password, str_prodi, str_semester;
    String url = "https://droomp.tech/admin/walidosen/savedata_walidosen.php";
    Spinner ProdiWD, SemesterWD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_walidosen);
        getSupportActionBar().hide();

        txtIdAdminInputWD = findViewById(R.id.txtIdAdminInputWD);
        txtNamaAdminInputWD = findViewById(R.id.txtNamaAdminInputWD);

        Intent i = getIntent();
        String tNim = i.getStringExtra("id_admin");
        String tName = i.getStringExtra("nama");

        txtIdAdminInputWD.setText(tNim);
        txtNamaAdminInputWD.setText(tName);

        ed_nidn = findViewById(R.id.ed_inputNidnWD);
        ed_namaWD = findViewById(R.id.ed_inputNamaWD);
        ed_emailWD = findViewById(R.id.ed_inputEmailWD);
        ed_passwordWD = findViewById(R.id.ed_inputPassWD);
        ProdiWD = findViewById(R.id.spInputProdiWDAdmin);
        SemesterWD = findViewById(R.id.spInputSemesterWDAdmin);
    }

    public void InputDataWD(View view){

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Mohon Menunggu.....");

        if (ed_nidn.getText().toString().equals("")){
            Toast.makeText(this, "Masukan NIDN", Toast.LENGTH_SHORT).show();
        }
        else if (ed_namaWD.getText().toString().equals("")){
            Toast.makeText(this, "Masukan Nama", Toast.LENGTH_SHORT).show();
        }
        else if (ed_emailWD.getText().toString().equals("")){
            Toast.makeText(this, "Masukan Email", Toast.LENGTH_SHORT).show();
        }
        else if (ed_passwordWD.getText().toString().equals("")){
            Toast.makeText(this, "Masukan Password", Toast.LENGTH_SHORT).show();
        }
        else{

            progressDialog.show();
            str_nidn = ed_nidn.getText().toString().trim();
            str_nama = ed_namaWD.getText().toString().trim();
            str_email = ed_emailWD.getText().toString().trim();
            str_password = ed_passwordWD.getText().toString().trim();
            str_prodi = ProdiWD.getSelectedItem().toString();
            str_semester = SemesterWD.getSelectedItem().toString();


            StringRequest request = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                String result = jsonObject.getString("status");

                                if (result.equals("success")) {

                                    ed_nidn.setText("");
                                    ed_namaWD.setText("");
                                    ed_emailWD.setText("");
                                    ed_passwordWD.setText("");
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

                    params.put("nidn", str_nidn);
                    params.put("nama", str_nama);
                    params.put("email", str_email);
                    params.put("password", str_password);
                    params.put("prodi", str_prodi);
                    params.put("semester", str_semester);
                    return params;
                }
            };
            RequestQueue queue = Volley.newRequestQueue(InputWalidosenActivity.this);
            queue.add(request);

        }
    }

    public void onBackPressed() {
        String idAdmin = txtIdAdminInputWD.getText().toString().trim();
        String namaAdmin = txtNamaAdminInputWD.getText().toString().trim();
        Intent intent = new Intent(getApplicationContext(), UserWalidosenAdminActivity.class);
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