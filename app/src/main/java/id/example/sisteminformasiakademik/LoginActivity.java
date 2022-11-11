package id.example.sisteminformasiakademik;


import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
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

import id.example.sisteminformasiakademik.user.DashboardActivity;


public class LoginActivity extends AppCompatActivity {

    private static final String FILE_NAME = "myFile";
    EditText ed_nim, ed_password;
    String str_nim, str_password, nim_mahasiswa;
    Button btnlogin, showForgotPasswordDialog;
    ProgressDialog progressDialog;
    CheckBox checkBox;
    String url = "https://droomp.tech/login.php";
    String ForgotPasswordUrl = "https://droomp.tech/forgot.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getSupportActionBar().hide();
        ed_nim = findViewById(R.id.ed_nim);
        ed_password = findViewById(R.id.ed_regpassword);
        btnlogin = findViewById(R.id.button3);
        checkBox = findViewById(R.id.checkBoxIngatSaya);
        showForgotPasswordDialog = findViewById(R.id.btnLupaPassword);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading....");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);


        SharedPreferences sharedPreferences = getSharedPreferences(FILE_NAME, MODE_PRIVATE);
        String nimShared = sharedPreferences.getString("nimShare", "");
        String passwordShared = sharedPreferences.getString("passwordShare", "");


        ed_nim.setText(nimShared);
        ed_password.setText(passwordShared);


        showForgotPasswordDialog.setOnClickListener(v -> UserForgotPasswordWithEmail());

        btnlogin.setOnClickListener(v -> UserLoginProccess());


    }

    private void UserLoginProccess() {
        String nim = ed_nim.getText().toString();
        String password = ed_password.getText().toString();

        if (checkBox.isChecked()) {
            StoredDataUsingSharedPref(nim, password);
        }

        if (ed_nim.getText().toString().equals("")) {
            Toast.makeText(this, "Masukan Nim", Toast.LENGTH_SHORT).show();
        } else if (ed_password.getText().toString().equals("")) {
            Toast.makeText(this, "Masukan Password", Toast.LENGTH_SHORT).show();
        } else {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Mohon Menunggu.....");

            progressDialog.show();
            str_nim = ed_nim.getText().toString().trim();
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
                                        String nim = object.getString("nim");
                                        String nama = object.getString("nama");

                                        Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
                                        intent.putExtra("nim", nim);
                                        intent.putExtra("nama", nama);
                                        startActivity(intent);
                                        finish();
                                        progressDialog.dismiss();
                                        massage("Login Berhasil");
                                    }
                                } else {
                                    progressDialog.dismiss();
                                    massage("Pengguna Tidak Ditemukan");
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
                    params.put("nim", nim);
                    params.put("password", password);
                    return params;
                }
            };
            RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
            queue.add(request);
        }
    }

    private void StoredDataUsingSharedPref(String nim, String password) {

        SharedPreferences.Editor editor = getSharedPreferences(FILE_NAME, MODE_PRIVATE).edit();
        editor.putString("nimShare", nim);
        editor.putString("passwordShare", password);
        editor.apply();

    }

    private void UserForgotPasswordWithEmail() {
        View forgot_password_layout = LayoutInflater.from(this).inflate(R.layout.lupa_password, null);
        EditText forgotEmail = forgot_password_layout.findViewById(R.id.edtLupaEmail);
        Button btnForgotPass = forgot_password_layout.findViewById(R.id.btnResetPassword);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("LUPA PASSWORD");
        builder.setView(forgot_password_layout);
        builder.setCancelable(true);
        AlertDialog dialog = builder.create();
        dialog.show();

        btnForgotPass.setOnClickListener(v -> {

            progressDialog.show();
            String email = forgotEmail.getText().toString().trim();
            if (email.isEmpty()) {
                progressDialog.dismiss();
                massage("Enter a email");
            } else {
                StringRequest stringRequest = new StringRequest(Request.Method.POST, ForgotPasswordUrl, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject object = new JSONObject(response);
                            String mail = object.getString("mail");
                            if (mail.equals("send")) {
                                progressDialog.dismiss();
                                dialog.dismiss();
                                massage("Email are Successfully  send");
                            } else {
                                progressDialog.dismiss();
                                massage(response);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        dialog.dismiss();
                        massage(error.toString());
                    }
                }) {

                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> forgotparams = new HashMap<String, String>();

                        forgotparams.put("email", email);
                        return forgotparams;
                    }
                };

                RequestQueue requestQueue = Volley.newRequestQueue(LoginActivity.this);
                requestQueue.add(stringRequest);

            }
        });
    }

    public void moveToRegistrasi(View view) {
        Intent i = new Intent(LoginActivity.this, RegistrasiActivity.class);
        startActivity(i);
        finish();
    }

    public void moveToAdmin(View view) {
        Intent i = new Intent(LoginActivity.this, LoginAdminActivity.class);
        startActivity(i);
        finish();
    }

    public void moveToWalidosen(View view) {
        Intent i = new Intent(LoginActivity.this, LoginWalidosenActivity.class);
        startActivity(i);
        finish();
    }

    public void onBackPressed() {

        new AlertDialog.Builder(this)
                .setMessage("Are you sure you want to exit?")
                .setIcon(R.mipmap.ic_launcher)
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        System.exit(0);
                    }

                })
                .setNegativeButton("No", null)
                .show();
    }

    public void massage(String massage) {
        Toast.makeText(this, massage, Toast.LENGTH_LONG).show();
    }

}