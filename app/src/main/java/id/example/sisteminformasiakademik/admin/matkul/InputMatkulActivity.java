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
import id.example.sisteminformasiakademik.admin.mahasiswa.InputMahasiswaActivity;
import id.example.sisteminformasiakademik.admin.mahasiswa.UserMahasiswaAdminActivity;

public class InputMatkulActivity extends AppCompatActivity {

    TextView txtIdAdminInputMatkul, txtNamaAdminInputMatkul;
    EditText ed_inputNamaMatkul, ed_inputSKS;
    String str_matkul,str_sks,str_prodi, str_semester;
    String url = "https://droomp.tech/admin/matkul/savedata_matkul.php";
    Spinner spInputProdiMatkul, spInputSemesterMatkul;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_matkul);
        getSupportActionBar().hide();

        txtIdAdminInputMatkul = findViewById(R.id.txtIdAdminInputMatkul);
        txtNamaAdminInputMatkul = findViewById(R.id.txtNamaAdminInputMatkul);

        Intent i = getIntent();
        String tNim = i.getStringExtra("id_admin");
        String tName = i.getStringExtra("nama");

        txtIdAdminInputMatkul.setText(tNim);
        txtNamaAdminInputMatkul.setText(tName);

        ed_inputNamaMatkul = findViewById(R.id.ed_inputNamaMatkul);
        ed_inputSKS = findViewById(R.id.ed_inputSKS);

        spInputProdiMatkul = findViewById(R.id.spInputProdiMatkul);
        spInputSemesterMatkul = findViewById(R.id.spInputSemesterMatkul);
    }

    public void InputDataMatkul(View view){

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Mohon Menunggu.....");

        if (ed_inputNamaMatkul.getText().toString().equals("")){
            Toast.makeText(this, "Masukan Nama Mata Kuliah", Toast.LENGTH_SHORT).show();
        }
        else if (ed_inputSKS.getText().toString().equals("")){
            Toast.makeText(this, "Masukan Jumlah SKS", Toast.LENGTH_SHORT).show();
        }
        else{

            progressDialog.show();
            str_matkul = ed_inputNamaMatkul.getText().toString().trim();
            str_sks = ed_inputSKS.getText().toString().trim();
            str_prodi = spInputProdiMatkul.getSelectedItem().toString();
            str_semester = spInputSemesterMatkul.getSelectedItem().toString();


            StringRequest request = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                String result = jsonObject.getString("status");

                                if (result.equals("success")) {

                                    ed_inputNamaMatkul.setText("");
                                    ed_inputSKS.setText("");

                                    progressDialog.dismiss();
                                    massage("Input Data Berhasil");

                                } else if (result.equals("Data Matkul Sudah Ada")){
                                    progressDialog.dismiss();
                                    massage("Data Matkul Sudah Ada");
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

                    params.put("nama_mk", str_matkul);
                    params.put("sks", str_sks);
                    params.put("prodi", str_prodi);
                    params.put("semester", str_semester);
                    return params;
                }
            };
            RequestQueue queue = Volley.newRequestQueue(InputMatkulActivity.this);
            queue.add(request);

        }
    }

    public void onBackPressed() {
        String idAdmin = txtIdAdminInputMatkul.getText().toString().trim();
        String namaAdmin = txtNamaAdminInputMatkul.getText().toString().trim();
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