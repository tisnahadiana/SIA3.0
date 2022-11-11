package id.example.sisteminformasiakademik.user.krs;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
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
import com.kyanogen.signatureview.SignatureView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import id.example.sisteminformasiakademik.LoginActivity;
import id.example.sisteminformasiakademik.R;
import id.example.sisteminformasiakademik.user.DashboardActivity;
import id.example.sisteminformasiakademik.user.menu.MenuActivity;

public class IsiKrsActivity extends AppCompatActivity {

    Spinner spinnerkrs, spinnerprodi;
    EditText edmatkul1, edjumlahsks1, edmatkul2, edjumlahsks2, edmatkul3, edjumlahsks3,
            edmatkul4, edjumlahsks4, edmatkul5, edjumlahsks5, edmatkul6, edjumlahsks6, edmatkul7, edjumlahsks7,
            edmatkul8, edjumlahsks8, edbatal1, edsksbatal1, edbatal2, edsksbatal2, edbatal3, edsksbatal3,
            edjumlahsks;
    TextView txtNimKrs, txtNamaKrs;
    Button btnRefresh, btnKirim, btnLihat, clear,save;
    String str_date, str_nim, str_nama,str_prodi, str_semester, str_matkul1, str_jumlah_sks1, str_matkul2, str_jumlah_sks2, str_matkul3, str_jumlah_sks3, str_matkul4, str_jumlah_sks4, str_matkul5, str_jumlah_sks5, str_matkul6, str_jumlah_sks6, str_matkul7, str_jumlah_sks7, str_matkul8, str_jumlah_sks8, str_batal1, str_batal_sks1, str_batal2, str_batal_sks2, str_batal3, str_batal_sks3, str_jumlahsks;
    String url = "https://droomp.tech/krs.php";
    String urlLihatKrs = "https://droomp.tech/lihat_krs.php";
    SimpleDateFormat datePatternformat = new SimpleDateFormat("dd-MM-yyy hh:mm a");

    public final int REQUEST_CODE = 100;

    Bitmap bitmap,scaleBitmap;
    SignatureView signatureView;
    int path;
    private static final String Image_DIRECTORY="/signdemo";
    ScrollView scroolViewkrs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_isi_krs);

        getSupportActionBar().hide();

        spinnerkrs = findViewById(R.id.spinnerKrs);
        spinnerprodi = findViewById(R.id.spProdiKrsmahasiswa);
        txtNimKrs = findViewById(R.id.txtNimKrs);
        txtNamaKrs = findViewById(R.id.txtNamaKrs);
        btnRefresh = findViewById(R.id.btnRefreshsks);
        btnKirim = findViewById(R.id.btnKirimKrs);
        btnLihat = findViewById(R.id.btnLihatKrsUser);

        edmatkul1 = findViewById(R.id.ed_matkul1);
        edjumlahsks1 = findViewById(R.id.ed_sks_matkul1);
        edmatkul2 = findViewById(R.id.ed_matkul2);
        edjumlahsks2 = findViewById(R.id.ed_sks_matkul2);
        edmatkul3 = findViewById(R.id.ed_matkul3);
        edjumlahsks3 = findViewById(R.id.ed_sks_matkul3);
        edmatkul4 = findViewById(R.id.ed_matkul4);
        edjumlahsks4 = findViewById(R.id.ed_sks_matkul4);
        edmatkul5 = findViewById(R.id.ed_matkul5);
        edjumlahsks5 = findViewById(R.id.ed_sks_matkul5);
        edmatkul6 = findViewById(R.id.ed_matkul6);
        edjumlahsks6 = findViewById(R.id.ed_sks_matkul6);
        edmatkul7 = findViewById(R.id.ed_matkul7);
        edjumlahsks7 = findViewById(R.id.ed_sks_matkul7);
        edmatkul8 = findViewById(R.id.ed_matkul8);
        edjumlahsks8 = findViewById(R.id.ed_sks_matkul8);
        edbatal1 = findViewById(R.id.ed_batal_matkul1);
        edsksbatal1 = findViewById(R.id.ed_batalsks_matkul1);
        edbatal2 = findViewById(R.id.ed_batal_matkul2);
        edsksbatal2 = findViewById(R.id.ed_batalsks_matkul2);
        edbatal3 = findViewById(R.id.ed_batal_matkul3);
        edsksbatal3 = findViewById(R.id.ed_batalsks_matkul3);
        edjumlahsks = findViewById(R.id.ed_jumlah_sks);

        Intent i = getIntent();
        String tNim = i.getStringExtra("nim");
        String tName = i.getStringExtra("nama");

        txtNimKrs.setText(tNim);
        txtNamaKrs.setText(tName);

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

        btnLihat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveToLihatKrs();
            }
        });

    }

    private void requestAllPermission() {
        ActivityCompat.requestPermissions(IsiKrsActivity.this, new String[]{READ_EXTERNAL_STORAGE,
                WRITE_EXTERNAL_STORAGE}, REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(IsiKrsActivity.this, "Permission Granted", Toast.LENGTH_SHORT).show();

            } else {
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void RefreshSKS(View view) {
        if (spinnerkrs.getSelectedItem().toString().equals("2")) {
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
            int sks1 = Integer.parseInt(edjumlahsks1.getText().toString());
            int sks2 = Integer.parseInt(edjumlahsks2.getText().toString());
            int sks3 = Integer.parseInt(edjumlahsks3.getText().toString());
            int sks4 = Integer.parseInt(edjumlahsks4.getText().toString());
            int sks5 = Integer.parseInt(edjumlahsks5.getText().toString());
            int sks6 = Integer.parseInt(edjumlahsks6.getText().toString());
            int sks7 = Integer.parseInt(edjumlahsks7.getText().toString());

            int jumlahsks = sks1 + sks2 + sks3 + sks4 + sks5 + sks6 + sks7;
            edjumlahsks.setText(String.valueOf(jumlahsks));

        }else if (spinnerkrs.getSelectedItem().toString().equals("8")) {
            int sks1 = Integer.parseInt(edjumlahsks1.getText().toString());
            int sks2 = Integer.parseInt(edjumlahsks2.getText().toString());

            int jumlahsks = sks1 + sks2;
            edjumlahsks.setText(String.valueOf(jumlahsks));
        }


    }

    public void isiKRS(View view) {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Mohon Menunggu.....");

        if (edmatkul1.getText().toString().equals("")) {
            Toast.makeText(this, "Masukan Nama", Toast.LENGTH_SHORT).show();
        } else if (edjumlahsks1.getText().toString().equals("")) {
            Toast.makeText(this, "Masukan Nim", Toast.LENGTH_SHORT).show();
        } else {

            progressDialog.show();
            str_nim = txtNimKrs.getText().toString().trim();
            str_nama = txtNamaKrs.getText().toString().trim();
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
            str_date = "Cianjur, "+datePatternformat.format(new Date().getTime());

            StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    progressDialog.dismiss();
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
                    Toast.makeText(IsiKrsActivity.this, response, Toast.LENGTH_SHORT).show();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    progressDialog.dismiss();
                    Toast.makeText(IsiKrsActivity.this, error.getMessage().toString(), Toast.LENGTH_SHORT).show();
                }
            }

            ) {
                @Nullable
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();

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

            RequestQueue requestQueue = Volley.newRequestQueue(IsiKrsActivity.this);

            requestQueue.add(request);


        }
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
                        String nimData = txtNimKrs.getText().toString().trim();
                        String namaData = txtNamaKrs.getText().toString().trim();
                        Intent intent = new Intent(getApplicationContext(), DashboardActivity.class);
                        intent.putExtra("nim", nimData);
                        intent.putExtra("nama", namaData);
                        startActivity(intent);
                        overridePendingTransition(0, 0);
                        finish();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }

    public void moveDashboardKRS(View view){
        String nimData = txtNimKrs.getText().toString().trim();
        String namaData = txtNamaKrs.getText().toString().trim();
        Intent intent = new Intent(getApplicationContext(), DashboardActivity.class);
        intent.putExtra("nim", nimData);
        intent.putExtra("nama", namaData);
        startActivity(intent);
        overridePendingTransition(0, 0);
        finish();
    }

    public void moveToLihatKrs() {
        String nim = txtNimKrs.getText().toString();
        String nama = txtNamaKrs.getText().toString();

            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Mohon Menunggu.....");

            progressDialog.show();
            StringRequest request = new StringRequest(Request.Method.POST, urlLihatKrs,
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
                                        String prodi = object.getString("prodi");
                                        String semester = object.getString("semester");
                                        String matkul1 = object.getString("matkul1");
                                        String sks1 = object.getString("sks1");
                                        String matkul2 = object.getString("matkul2");
                                        String sks2 = object.getString("sks2");
                                        String matkul3 = object.getString("matkul3");
                                        String sks3 = object.getString("sks3");
                                        String matkul4 = object.getString("matkul4");
                                        String sks4 = object.getString("sks4");
                                        String matkul5 = object.getString("matkul5");
                                        String sks5 = object.getString("sks5");
                                        String matkul6 = object.getString("matkul6");
                                        String sks6 = object.getString("sks6");
                                        String matkul7 = object.getString("matkul7");
                                        String sks7 = object.getString("sks7");
                                        String matkul8 = object.getString("matkul8");
                                        String sks8 = object.getString("sks8");
                                        String jumlahsks = object.getString("jumlahsks");
                                        String date = object.getString("date");

                                        Intent intent = new Intent(IsiKrsActivity.this, LihatKrsUserActivity.class);
                                        intent.putExtra("nim", nim);
                                        intent.putExtra("nama", nama);
                                        intent.putExtra("prodi", prodi);
                                        intent.putExtra("semester", semester);
                                        intent.putExtra("matkul1", matkul1);
                                        intent.putExtra("sks1", sks1);
                                        intent.putExtra("matkul2", matkul2);
                                        intent.putExtra("sks2", sks2);
                                        intent.putExtra("matkul3", matkul3);
                                        intent.putExtra("sks3", sks3);
                                        intent.putExtra("matkul4", matkul4);
                                        intent.putExtra("sks4", sks4);
                                        intent.putExtra("matkul5", matkul5);
                                        intent.putExtra("sks5", sks5);
                                        intent.putExtra("matkul6", matkul6);
                                        intent.putExtra("sks6", sks6);
                                        intent.putExtra("matkul7", matkul7);
                                        intent.putExtra("sks7", sks7);
                                        intent.putExtra("matkul8", matkul8);
                                        intent.putExtra("sks8", sks8);
                                        intent.putExtra("jumlahsks", jumlahsks);
                                        intent.putExtra("date", date);
                                        startActivity(intent);
                                        finish();
                                        progressDialog.dismiss();
                                        massage("Melihat Data");
                                    }
                                } else {
                                    progressDialog.dismiss();
                                    massage("Data Tidak Ditemukan");
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
                    params.put("nama", nama);
                    return params;
                }
            };
            RequestQueue queue = Volley.newRequestQueue(IsiKrsActivity.this);
            queue.add(request);
    }

    public void massage(String massage) {
        Toast.makeText(this, massage, Toast.LENGTH_LONG).show();
    }
}