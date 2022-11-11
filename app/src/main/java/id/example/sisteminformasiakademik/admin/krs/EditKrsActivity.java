package id.example.sisteminformasiakademik.admin.krs;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import id.example.sisteminformasiakademik.R;

public class EditKrsActivity extends AppCompatActivity {

    Spinner spinnerkrs, spinnerprodi;
    EditText ednama, ednim, edmatkul1, edjumlahsks1, edmatkul2, edjumlahsks2, edmatkul3, edjumlahsks3,
            edmatkul4, edjumlahsks4, edmatkul5, edjumlahsks5, edmatkul6, edjumlahsks6, edmatkul7, edjumlahsks7,
            edmatkul8, edjumlahsks8, edbatal1, edsksbatal1, edbatal2, edsksbatal2, edbatal3, edsksbatal3,
            edjumlahsks;
    TextView txtIdAdminInputkrs, txtNamaAdminInputkrs, txtNamaDataAdminEditkrs, txtNimDataAdminEditkrs;
    Button btnRefresh, btnEdit;
    String str_nimData, str_namaData, str_date, str_nim, str_nama,str_prodi, str_semester, str_matkul1, str_jumlah_sks1, str_matkul2, str_jumlah_sks2, str_matkul3, str_jumlah_sks3, str_matkul4, str_jumlah_sks4, str_matkul5, str_jumlah_sks5, str_matkul6, str_jumlah_sks6, str_matkul7, str_jumlah_sks7, str_matkul8, str_jumlah_sks8, str_batal1, str_batal_sks1, str_batal2, str_batal_sks2, str_batal3, str_batal_sks3, str_jumlahsks;
    String url = "https://droomp.tech/admin/krs/editkrs_admin.php";
    SimpleDateFormat datePatternformat = new SimpleDateFormat("dd-MM-yyy hh:mm a");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_krs);
        getSupportActionBar().hide();

        txtIdAdminInputkrs = findViewById(R.id.txtIdAdminEditkrs);
        txtNamaAdminInputkrs = findViewById(R.id.txtNamaAdminEditkrs);
        txtNamaDataAdminEditkrs = findViewById(R.id.txtNamaDataAdminEditkrs);
        txtNimDataAdminEditkrs = findViewById(R.id.txtNimDataAdminEditkrs);

        btnEdit = findViewById(R.id.btnEditKrsadmin);
        spinnerkrs = findViewById(R.id.spinnerKrsadminEdit);
        spinnerprodi = findViewById(R.id.spProdiKrsadminEdit);

        ednama = findViewById(R.id.ed_namaEditadmin);
        ednim = findViewById(R.id.ed_nimEditadmin);
        edmatkul1 = findViewById(R.id.ed_matkul1adminEdit);
        edjumlahsks1 = findViewById(R.id.ed_sks_matkul1adminEdit);
        edmatkul2 = findViewById(R.id.ed_matkul2adminEdit);
        edjumlahsks2 = findViewById(R.id.ed_sks_matkul2adminEdit);
        edmatkul3 = findViewById(R.id.ed_matkul3adminEdit);
        edjumlahsks3 = findViewById(R.id.ed_sks_matkul3adminEdit);
        edmatkul4 = findViewById(R.id.ed_matkul4adminEdit);
        edjumlahsks4 = findViewById(R.id.ed_sks_matkul4adminEdit);
        edmatkul5 = findViewById(R.id.ed_matkul5adminEdit);
        edjumlahsks5 = findViewById(R.id.ed_sks_matkul5adminEdit);
        edmatkul6 = findViewById(R.id.ed_matkul6adminEdit);
        edjumlahsks6 = findViewById(R.id.ed_sks_matkul6adminEdit);
        edmatkul7 = findViewById(R.id.ed_matkul7adminEdit);
        edjumlahsks7 = findViewById(R.id.ed_sks_matkul7adminEdit);
        edmatkul8 = findViewById(R.id.ed_matkul8adminEdit);
        edjumlahsks8 = findViewById(R.id.ed_sks_matkul8adminEdit);
        edbatal1 = findViewById(R.id.ed_batal_matkul1adminEdit);
        edsksbatal1 = findViewById(R.id.ed_batalsks_matkul1adminEdit);
        edbatal2 = findViewById(R.id.ed_batal_matkul2adminEdit);
        edsksbatal2 = findViewById(R.id.ed_batalsks_matkul2adminEdit);
        edbatal3 = findViewById(R.id.ed_batal_matkul3adminEdit);
        edsksbatal3 = findViewById(R.id.ed_batalsks_matkul3adminEdit);
        edjumlahsks = findViewById(R.id.ed_jumlah_sksadminEdit);

        Intent i = getIntent();
        String idadmin = i.getStringExtra("id_admin");
        String namaAdmin = i.getStringExtra("namaAdmin");
        String tNim = i.getStringExtra("nim");
        String tName = i.getStringExtra("nama");
        String prodi = i.getStringExtra("prodi");
        String semester = i.getStringExtra("semester");

        txtIdAdminInputkrs.setText(idadmin);
        txtNamaAdminInputkrs.setText(namaAdmin);
        txtNamaDataAdminEditkrs.setText(tName);
        txtNimDataAdminEditkrs.setText(tNim);
        ednama.setText(tName);
        ednim.setText(tNim);

        spinnerkrs.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                LoadMatkul();
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateKRSadmin();
            }
        });

    }

    private void UpdateKRSadmin() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Mohon Menunggu.....");

        progressDialog.show();
        str_nimData = txtNimDataAdminEditkrs.getText().toString().trim();
        str_namaData = txtNamaDataAdminEditkrs.getText().toString().trim();
        str_nim = ednim.getText().toString().trim();
        str_nama = ednama.getText().toString().trim();
        str_prodi = spinnerprodi.getSelectedItem().toString().trim();
        str_semester = spinnerkrs.getSelectedItem().toString().trim();
        str_matkul1 = edmatkul1.getText().toString().trim();
        str_jumlah_sks1 = edjumlahsks1.getText().toString().trim();
        str_matkul2 = edmatkul2.getText().toString().trim();
        str_jumlah_sks2 = edjumlahsks2.getText().toString().trim();
        str_matkul3 = edmatkul3.getText().toString().trim();
        str_jumlah_sks3 = edjumlahsks3.getText().toString().trim();
        str_matkul4 = edmatkul4.getText().toString().trim();
        str_jumlah_sks4 = edjumlahsks4.getText().toString().trim();
        str_matkul5 = edmatkul5.getText().toString().trim();
        str_jumlah_sks5 = edjumlahsks5.getText().toString().trim();
        str_matkul6 = edmatkul6.getText().toString().trim();
        str_jumlah_sks6 = edjumlahsks6.getText().toString().trim();
        str_matkul7 = edmatkul7.getText().toString().trim();
        str_jumlah_sks7 = edjumlahsks7.getText().toString().trim();
        str_matkul8 = edmatkul8.getText().toString().trim();
        str_jumlah_sks8 = edjumlahsks8.getText().toString().trim();
        str_batal1 = edbatal1.getText().toString().trim();
        str_batal_sks1 = edsksbatal1.getText().toString().trim();
        str_batal2 = edbatal2.getText().toString().trim();
        str_batal_sks2 = edsksbatal2.getText().toString().trim();
        str_batal3 = edbatal3.getText().toString().trim();
        str_batal_sks3 = edsksbatal3.getText().toString().trim();
        str_jumlahsks = edjumlahsks.getText().toString().trim();
        str_date = "Cianjur, " + datePatternformat.format(new Date().getTime());

        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String result = jsonObject.getString("status");

                    if (result.equals("success")) {
                        progressDialog.dismiss();
                        ednama.setText("");
                        ednim.setText("");
                        edmatkul1.setText("");
                        edjumlahsks1.setText("");
                        edmatkul2.setText("");
                        edjumlahsks2.setText("");
                        edmatkul3.setText("");
                        edjumlahsks3.setText("");
                        edmatkul4.setText("");
                        edjumlahsks4.setText("");
                        edmatkul5.setText("");
                        edjumlahsks5.setText("");
                        edmatkul6.setText("");
                        edjumlahsks6.setText("");
                        edmatkul7.setText("");
                        edjumlahsks7.setText("");
                        edmatkul8.setText("");
                        edjumlahsks8.setText("");
                        edbatal1.setText("");
                        edsksbatal1.setText("");
                        edbatal2.setText("");
                        edsksbatal2.setText("");
                        edbatal3.setText("");
                        edsksbatal3.setText("");
                        edjumlahsks.setText("");
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
                Toast.makeText(EditKrsActivity.this, error.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        }

        ) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();

                params.put("nimData", str_nimData);
                params.put("namaData", str_namaData);
                params.put("nim", str_nim);
                params.put("nama", str_nama);
                params.put("prodi", str_prodi);
                params.put("semester", str_semester);
                params.put("matkul1", str_matkul1);
                params.put("sksmatkul1", str_jumlah_sks1);
                params.put("matkul2", str_matkul2);
                params.put("sksmatkul2", str_jumlah_sks2);
                params.put("matkul3", str_matkul3);
                params.put("sksmatkul3", str_jumlah_sks3);
                params.put("matkul4", str_matkul4);
                params.put("sksmatkul4", str_jumlah_sks4);
                params.put("matkul5", str_matkul5);
                params.put("sksmatkul5", str_jumlah_sks5);
                params.put("matkul6", str_matkul6);
                params.put("sksmatkul6", str_jumlah_sks6);
                params.put("matkul7", str_matkul7);
                params.put("sksmatkul7", str_jumlah_sks7);
                params.put("matkul8", str_matkul8);
                params.put("sksmatkul8", str_jumlah_sks8);
                params.put("matkulbatal1", str_batal1);
                params.put("sksbatal1", str_batal_sks1);
                params.put("matkulbatal2", str_batal2);
                params.put("sksbatal2", str_batal_sks2);
                params.put("matkulbatal3", str_batal3);
                params.put("sksbatal3", str_batal_sks3);
                params.put("jumlahsks", str_jumlahsks);
                params.put("date", str_date);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(EditKrsActivity.this);
        requestQueue.add(request);

    }


    private void LoadMatkul() {

        String semester = spinnerkrs.getSelectedItem().toString();

        if (spinnerkrs.getSelectedItem().toString().equals("2")) {
            edmatkul1.setText("Pengantar AI");
            edjumlahsks1.setText("3");
            edmatkul2.setText("Web Programming");
            edjumlahsks2.setText("3");
            edmatkul3.setText("Algoritma &Pemrograman II");
            edjumlahsks3.setText("3");
            edmatkul4.setText("Pendidikan Agama");
            edjumlahsks4.setText("2");
            edmatkul5.setText("Fisika Dasar Lanjut");
            edjumlahsks5.setText("3");
            edmatkul6.setText("Kalkulus Lanjut");
            edjumlahsks6.setText("3");
            edmatkul7.setText("Bahasa Indonesia");
            edjumlahsks7.setText("2");
            edmatkul8.setText("Organisasi & Arsitektur Komputer");
            edjumlahsks8.setText("3");

            int sks1 = Integer.parseInt(edjumlahsks1.getText().toString());
            int sks2 = Integer.parseInt(edjumlahsks2.getText().toString());
            int sks3 = Integer.parseInt(edjumlahsks3.getText().toString());
            int sks4 = Integer.parseInt(edjumlahsks4.getText().toString());
            int sks5 = Integer.parseInt(edjumlahsks5.getText().toString());
            int sks6 = Integer.parseInt(edjumlahsks6.getText().toString());
            int sks7 = Integer.parseInt(edjumlahsks7.getText().toString());
            int sks8 = Integer.parseInt(edjumlahsks8.getText().toString());

            int jumlahsks = sks1 + sks2 + sks3 + sks4 + sks5 + sks6 + sks7 + sks8 ;
            edjumlahsks.setText(String.valueOf(jumlahsks));


        } else if (spinnerkrs.getSelectedItem().toString().equals("4")) {
            edmatkul1.setText("Game Komputer");
            edjumlahsks1.setText("3");
            edmatkul2.setText("Mobile Programming I");
            edjumlahsks2.setText("3");
            edmatkul3.setText("RPL II :Desain & Implementasi");
            edjumlahsks3.setText("3");
            edmatkul4.setText("Metode Numerik");
            edjumlahsks4.setText("3");
            edmatkul5.setText("Sistem Operasi");
            edjumlahsks5.setText("3");
            edmatkul6.setText("Sistem Data Base");
            edjumlahsks6.setText("3");
            edmatkul7.setText("Metode OOP");
            edjumlahsks7.setText("3");
            edmatkul8.setText("");
            edjumlahsks8.setText("");

            int sks1 = Integer.parseInt(edjumlahsks1.getText().toString());
            int sks2 = Integer.parseInt(edjumlahsks2.getText().toString());
            int sks3 = Integer.parseInt(edjumlahsks3.getText().toString());
            int sks4 = Integer.parseInt(edjumlahsks4.getText().toString());
            int sks5 = Integer.parseInt(edjumlahsks5.getText().toString());
            int sks6 = Integer.parseInt(edjumlahsks6.getText().toString());
            int sks7 = Integer.parseInt(edjumlahsks7.getText().toString());

            int jumlahsks = sks1 + sks2 + sks3 + sks4 + sks5 + sks6 + sks7;
            edjumlahsks.setText(String.valueOf(jumlahsks));

        } else if (spinnerkrs.getSelectedItem().toString().equals("6")) {
            edmatkul1.setText("Artificial Intelligence & Machine Learning");
            edjumlahsks1.setText("3");
            edmatkul2.setText("Analisis Sistem Inforamasi");
            edjumlahsks2.setText("3");
            edmatkul3.setText("Interfersonal Skill");
            edjumlahsks3.setText("2");
            edmatkul4.setText("Multimedia");
            edjumlahsks4.setText("3");
            edmatkul5.setText("Model & Simulasi");
            edjumlahsks5.setText("3");
            edmatkul6.setText("MK Lintas Prodi");
            edjumlahsks6.setText("3");
            edmatkul7.setText("MK Lintas Prodi");
            edjumlahsks7.setText("3");
            edmatkul8.setText("");
            edjumlahsks8.setText("");

            int sks1 = Integer.parseInt(edjumlahsks1.getText().toString());
            int sks2 = Integer.parseInt(edjumlahsks2.getText().toString());
            int sks3 = Integer.parseInt(edjumlahsks3.getText().toString());
            int sks4 = Integer.parseInt(edjumlahsks4.getText().toString());
            int sks5 = Integer.parseInt(edjumlahsks5.getText().toString());
            int sks6 = Integer.parseInt(edjumlahsks6.getText().toString());
            int sks7 = Integer.parseInt(edjumlahsks7.getText().toString());

            int jumlahsks = sks1 + sks2 + sks3 + sks4 + sks5 + sks6 + sks7;
            edjumlahsks.setText(String.valueOf(jumlahsks));

        } else if (spinnerkrs.getSelectedItem().toString().equals("8")) {
            edmatkul1.setText("Tugas Akhir/Skripsi");
            edjumlahsks1.setText("6");
            edmatkul2.setText("Mobile Programing");
            edjumlahsks2.setText("3");
            edmatkul3.setText("");
            edjumlahsks3.setText("");
            edmatkul4.setText("");
            edjumlahsks4.setText("");
            edmatkul5.setText("");
            edjumlahsks5.setText("");
            edmatkul6.setText("");
            edjumlahsks6.setText("");
            edmatkul7.setText("");
            edjumlahsks7.setText("");
            edmatkul8.setText("");
            edjumlahsks8.setText("");

            int sks1 = Integer.parseInt(edjumlahsks1.getText().toString());
            int sks2 = Integer.parseInt(edjumlahsks2.getText().toString());

            int jumlahsks = sks1 + sks2;
            edjumlahsks.setText(String.valueOf(jumlahsks));

        }


    }

    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setMessage("Ingin Kembali Ke Halaman Dashboard?")
                .setIcon(R.mipmap.ic_launcher)
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        String nimData = txtIdAdminInputkrs.getText().toString().trim();
                        String namaData = txtNamaAdminInputkrs.getText().toString().trim();
                        Intent intent = new Intent(getApplicationContext(), KrsAdminActivity.class);
                        intent.putExtra("id_admin", nimData);
                        intent.putExtra("nama", namaData);
                        startActivity(intent);
                        overridePendingTransition(0, 0);
                        finish();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }

    public void massage(String massage) {
        Toast.makeText(this, massage, Toast.LENGTH_LONG).show();
    }
}