package id.example.sisteminformasiakademik;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
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

import id.example.sisteminformasiakademik.admin.DashboardAdminActivity;
import id.example.sisteminformasiakademik.walidosen.DashboardWalidosenActivity;

public class LoginWalidosenActivity extends AppCompatActivity {

    private static final String FILE_NAME = "myFileWalidosen";
    EditText ed_nidn, ed_password;
    String str_nidn, str_password;
    Button btnloginDosen;
    ProgressDialog progressDialog;
    CheckBox checkBox;
    String url = "https://droomp.tech/walidosen/login_walidosen.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_walidosen);
        getSupportActionBar().hide();

        ed_nidn = findViewById(R.id.ed_NIDN);
        ed_password = findViewById(R.id.ed_pass_waldos);
        btnloginDosen = findViewById(R.id.btn_login_waldos);
        checkBox = findViewById(R.id.cb_waldos);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading....");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);


        SharedPreferences sharedPreferences = getSharedPreferences(FILE_NAME, MODE_PRIVATE);
        String nimShared = sharedPreferences.getString("nidnShare", "");
        String passwordShared = sharedPreferences.getString("passwordShare", "");


        ed_nidn.setText(nimShared);
        ed_password.setText(passwordShared);

        btnloginDosen.setOnClickListener(v -> UserLoginProccessWaldos());

    }

    private void UserLoginProccessWaldos() {
        String nidn = ed_nidn.getText().toString();
        String password = ed_password.getText().toString();

        if (checkBox.isChecked()) {
            StoredDataUsingSharedPref(nidn, password);
        }

        if (ed_nidn.getText().toString().equals("")) {
            Toast.makeText(this, "Masukan NIDN", Toast.LENGTH_SHORT).show();
        } else if (ed_password.getText().toString().equals("")) {
            Toast.makeText(this, "Masukan Password", Toast.LENGTH_SHORT).show();
        } else {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Mohon Menunggu.....");

            progressDialog.show();
            str_nidn = ed_nidn.getText().toString().trim();
            str_password = ed_password.getText().toString().trim();
            StringRequest request = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                String result = jsonObject.getString("status");

                                JSONArray jsonArray = jsonObject.getJSONArray("data");
                                if (result.equals("success")) {
                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject object = jsonArray.getJSONObject(i);
                                        String nidn = object.getString("nidn");
                                        String nama = object.getString("nama");
                                        String prodi = object.getString("prodi");
                                        String semester = object.getString("semester");

                                        Intent intent = new Intent(LoginWalidosenActivity.this, DashboardWalidosenActivity.class);
                                        intent.putExtra("nidn", nidn);
                                        intent.putExtra("namaDosen", nama);
                                        intent.putExtra("prodi", prodi);
                                        intent.putExtra("semester", semester);
                                        startActivity(intent);
                                        finish();
                                        progressDialog.dismiss();
                                        massage("Login Berhasil");
                                    }
                                } else {
                                    progressDialog.dismiss();
                                    massage("User Wali dosen Tidak Ditemukan");
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
                    params.put("nidn", nidn);
                    params.put("password", password);
                    return params;
                }
            };
            RequestQueue queue = Volley.newRequestQueue(LoginWalidosenActivity.this);
            queue.add(request);
        }
    }

    private void StoredDataUsingSharedPref(String id_admin, String password) {

        SharedPreferences.Editor editor = getSharedPreferences(FILE_NAME, MODE_PRIVATE).edit();
        editor.putString("nidnShare", id_admin);
        editor.putString("passwordShare", password);
        editor.apply();

    }


    public void onBackPressed() {
        Intent i = new Intent(LoginWalidosenActivity.this, LoginActivity.class);
        startActivity(i);
        finish();
    }

    public void massage(String massage) {
        Toast.makeText(this, massage, Toast.LENGTH_LONG).show();
    }
}
