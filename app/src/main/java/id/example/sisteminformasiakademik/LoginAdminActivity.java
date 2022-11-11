package id.example.sisteminformasiakademik;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
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
import id.example.sisteminformasiakademik.user.DashboardActivity;

public class LoginAdminActivity extends AppCompatActivity {

    private static final String FILE_NAME = "myFileAdmin";
    EditText ed_id_admin, ed_password;
    String str_id_admin, str_password;
    Button btnloginAdmin;
    ProgressDialog progressDialog;
    CheckBox checkBox;
    String url = "https://droomp.tech/admin/login_admin.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_admin);
        getSupportActionBar().hide();

        ed_id_admin = findViewById(R.id.ed_id_admin);
        ed_password = findViewById(R.id.ed_pass_admin);
        btnloginAdmin = findViewById(R.id.btn_login_admin);
        checkBox = findViewById(R.id.cb_admin);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading....");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);


        SharedPreferences sharedPreferences = getSharedPreferences(FILE_NAME, MODE_PRIVATE);
        String nimShared = sharedPreferences.getString("idAdminShare", "");
        String passwordShared = sharedPreferences.getString("passwordShare", "");


        ed_id_admin.setText(nimShared);
        ed_password.setText(passwordShared);

        btnloginAdmin.setOnClickListener(v -> UserLoginProccessAdmin());

    }

    private void UserLoginProccessAdmin() {
        String id_admin = ed_id_admin.getText().toString();
        String password = ed_password.getText().toString();

        if (checkBox.isChecked()) {
            StoredDataUsingSharedPref(id_admin, password);
        }

        if (ed_id_admin.getText().toString().equals("")) {
            Toast.makeText(this, "Masukan ID Admin", Toast.LENGTH_SHORT).show();
        } else if (ed_password.getText().toString().equals("")) {
            Toast.makeText(this, "Masukan Password", Toast.LENGTH_SHORT).show();
        } else {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Mohon Menunggu.....");

            progressDialog.show();
            str_id_admin = ed_id_admin.getText().toString().trim();
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
                                        String id_admin = object.getString("id_admin");
                                        String nama = object.getString("nama");

                                        Intent intent = new Intent(LoginAdminActivity.this, DashboardAdminActivity.class);
                                        intent.putExtra("id_admin", id_admin);
                                        intent.putExtra("nama", nama);
                                        startActivity(intent);
                                        finish();
                                        progressDialog.dismiss();
                                        massage("Login Berhasil");
                                    }
                                } else {
                                    progressDialog.dismiss();
                                    massage("User Admin Tidak Ditemukan");
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
                    params.put("id_admin", id_admin);
                    params.put("password", password);
                    return params;
                }
            };
            RequestQueue queue = Volley.newRequestQueue(LoginAdminActivity.this);
            queue.add(request);
        }
    }

    private void StoredDataUsingSharedPref(String id_admin, String password) {

        SharedPreferences.Editor editor = getSharedPreferences(FILE_NAME, MODE_PRIVATE).edit();
        editor.putString("idAdminShare", id_admin);
        editor.putString("passwordShare", password);
        editor.apply();

    }



    public void onBackPressed() {
        Intent i = new Intent(LoginAdminActivity.this, LoginActivity.class);
        startActivity(i);
        finish();
    }

    public void massage(String massage) {
        Toast.makeText(this, massage, Toast.LENGTH_LONG).show();
    }
}