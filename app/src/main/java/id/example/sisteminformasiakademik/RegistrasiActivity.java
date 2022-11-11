package id.example.sisteminformasiakademik;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
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

import java.util.HashMap;
import java.util.Map;

public class RegistrasiActivity extends AppCompatActivity {

    EditText ed_regnim, ed_regnama,ed_regemail,ed_regpassword;
    String str_nim,str_nama, str_email, str_password, str_prodi, str_semester;
    String url = "https://droomp.tech/register.php";
    Spinner ProdiRegistrasi, SemesterRegistrasi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrasi);
        getSupportActionBar().hide();

        ed_regnim = findViewById(R.id.ed_regnim);
        ed_regnama = findViewById(R.id.ed_regnama);
        ed_regemail = findViewById(R.id.ed_regemail);
        ed_regpassword = findViewById(R.id.ed_regpassword);
        ProdiRegistrasi = findViewById(R.id.spinnerProdiRegisterMS);
        SemesterRegistrasi = findViewById(R.id.spinnerSemesterRegister);

    }
    public void moveToLogin(View view){
        startActivity(new Intent(getApplicationContext(),LoginActivity.class));
        finish();
    }
    public void Register(View view){

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


            StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    progressDialog.dismiss();
                    ed_regnim.setText("");
                    ed_regnama.setText("");
                    ed_regemail.setText("");
                    ed_regpassword.setText("");
                    Toast.makeText(RegistrasiActivity.this, response, Toast.LENGTH_SHORT).show();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    progressDialog.dismiss();
                    Toast.makeText(RegistrasiActivity.this, error.getMessage().toString(), Toast.LENGTH_SHORT).show();
                }
            }


            ){
                @Nullable
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();

                    params.put("nim", str_nim);
                    params.put("nama", str_nama);
                    params.put("email", str_email);
                    params.put("password", str_password);
                    params.put("prodi", str_prodi);
                    params.put("semester", str_semester);
                    return params;
                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(RegistrasiActivity.this);

            requestQueue.add(request);


        }
    }

    public void onBackPressed() {

        new AlertDialog.Builder(this)
                .setMessage("Kembali ke halaman Login?")
                .setIcon(R.mipmap.ic_launcher)
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent i = new Intent(RegistrasiActivity.this,LoginActivity.class);
                        startActivity(i);
                        finish();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }
}