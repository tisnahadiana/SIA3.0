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
import id.example.sisteminformasiakademik.admin.mahasiswa.EditMahasiswaActivity;
import id.example.sisteminformasiakademik.admin.mahasiswa.UserMahasiswaAdminActivity;

public class EditAdminActivity extends AppCompatActivity {

    TextView txtIdAdminEditADM, txtNamaAdminEditADM, txtIDadmEdit;
    EditText ed_id, ed_nama,ed_email;
    String str_id,str_nama, str_email,str_idData;
    String url = "https://droomp.tech/admin/admin/editdata_admin.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_admin);
        getSupportActionBar().hide();

        txtIdAdminEditADM = findViewById(R.id.txtIdAdminEditADM);
        txtNamaAdminEditADM = findViewById(R.id.txtNamaAdminEditADM);

        Intent i = getIntent();
        String tNim = i.getStringExtra("id_admin");
        String tName = i.getStringExtra("nama");

        txtIdAdminEditADM.setText(tNim);
        txtNamaAdminEditADM.setText(tName);

        ed_id = findViewById(R.id.ed_editIDAdmin);
        ed_nama = findViewById(R.id.ed_editNamaADM);
        ed_email = findViewById(R.id.ed_EditEmailADM);
        txtIDadmEdit = findViewById(R.id.txtIDadmEdit);

        ed_id.setText(getIntent().getStringExtra("id_adminED"));
        ed_nama.setText(getIntent().getStringExtra("namaEDadmin"));
        ed_email.setText(getIntent().getStringExtra("emailEDadm"));
        txtIDadmEdit.setText(getIntent().getStringExtra("id_adminED"));
    }

    public void EditDataAdmin(View view){

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Mohon Menunggu.....");

        progressDialog.show();
        str_id = ed_id.getText().toString().trim();
        str_nama = ed_nama.getText().toString().trim();
        str_email = ed_email.getText().toString().trim();
;
        str_idData = txtIDadmEdit.getText().toString().trim();


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

                params.put("idData", str_idData);
                params.put("id", str_id);
                params.put("nama", str_nama);
                params.put("email", str_email);
                return params;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(EditAdminActivity.this);
        queue.add(request);

    }

    public void onBackPressed() {
        String idAdmin = txtIdAdminEditADM.getText().toString().trim();
        String namaAdmin = txtNamaAdminEditADM.getText().toString().trim();
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