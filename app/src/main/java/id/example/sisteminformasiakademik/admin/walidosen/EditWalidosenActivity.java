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
import id.example.sisteminformasiakademik.admin.mahasiswa.EditMahasiswaActivity;
import id.example.sisteminformasiakademik.admin.mahasiswa.UserMahasiswaAdminActivity;

public class EditWalidosenActivity extends AppCompatActivity {

    TextView txtIdAdminEditWD, txtNamaAdminEditWD, txtNidnWDEdit;
    EditText ed_regnim, ed_regnama,ed_regemail;
    String str_nidn,str_nama, str_email,str_prodi, str_semester, str_nidnData;
    String url = "https://droomp.tech/admin/walidosen/editdata_walidosen.php";
    Spinner ProdiRegistrasi, SemesterRegistrasi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_walidosen);
        getSupportActionBar().hide();

        txtIdAdminEditWD = findViewById(R.id.txtIdAdminEditWD);
        txtNamaAdminEditWD = findViewById(R.id.txtNamaAdminEditWD);

        Intent i = getIntent();
        String tNim = i.getStringExtra("id_admin");
        String tName = i.getStringExtra("nama");

        txtIdAdminEditWD.setText(tNim);
        txtNamaAdminEditWD.setText(tName);

        ed_regnim = findViewById(R.id.ed_editNidnWD);
        ed_regnama = findViewById(R.id.ed_editNamaWD);
        ed_regemail = findViewById(R.id.ed_EditEmailWD);
        ProdiRegistrasi = findViewById(R.id.spEditProdiWDAdmin);
        SemesterRegistrasi = findViewById(R.id.spEditSemesterWDAdmin);
        txtNidnWDEdit = findViewById(R.id.txtNidnWDEdit);

        ed_regnim.setText(getIntent().getStringExtra("nidnEDWD"));
        ed_regnama.setText(getIntent().getStringExtra("namaEDWD"));
        ed_regemail.setText(getIntent().getStringExtra("emailEDWD"));
        txtNidnWDEdit.setText(getIntent().getStringExtra("nidnEDWD"));
    }

    public void EditDataWD(View view){

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Mohon Menunggu.....");

        progressDialog.show();
        str_nidn = ed_regnim.getText().toString().trim();
        str_nama = ed_regnama.getText().toString().trim();
        str_email = ed_regemail.getText().toString().trim();

        str_prodi = ProdiRegistrasi.getSelectedItem().toString();
        str_semester = SemesterRegistrasi.getSelectedItem().toString();
        str_nidnData = txtNidnWDEdit.getText().toString().trim();


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

                params.put("nidnData", str_nidnData);
                params.put("nidn", str_nidn);
                params.put("nama", str_nama);
                params.put("email", str_email);
                params.put("prodi", str_prodi);
                params.put("semester", str_semester);
                return params;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(EditWalidosenActivity.this);
        queue.add(request);

    }

    public void onBackPressed() {
        String idAdmin = txtIdAdminEditWD.getText().toString().trim();
        String namaAdmin = txtNamaAdminEditWD.getText().toString().trim();
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