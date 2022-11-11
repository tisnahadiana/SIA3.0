package id.example.sisteminformasiakademik.admin.tnilaiAdmin;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.bumptech.glide.Glide;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import id.example.sisteminformasiakademik.R;
import id.example.sisteminformasiakademik.admin.khs.EditKhsActivity;
import id.example.sisteminformasiakademik.admin.khs.KhsAdminActivity;

public class EditTnilaiAdminActivity extends AppCompatActivity {

    TextView txtIdAdminEditTnilai, txtNamaAdminEditTnilai, txtDataNimEditTnilai, txtImageUrlEditTnilai;
    EditText ed_editNimTnilai, ed_editNamaTnilai;
    String encodeImageString,str_nim,str_nama, str_prodi, str_semester, str_nimDataEditKhs, urlimg;
    Spinner spEditProdiTnilaiAdmin, spEditSemesterTnilaiAdmin;
    Button upload, browse;
    Bitmap bitmap;
    ImageView img;
    String urlapi = "https://droomp.tech/admin/tnilai/edit_tnilai_admin.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_tnilai_admin);
        getSupportActionBar().hide();

        txtIdAdminEditTnilai = findViewById(R.id.txtIdAdminEditTnilai);
        txtNamaAdminEditTnilai = findViewById(R.id.txtNamaAdminEditTnilai);
        txtDataNimEditTnilai = findViewById(R.id.txtDataNimEditTnilai);

        Intent i = getIntent();
        String tNim = i.getStringExtra("id_admin");
        String tName = i.getStringExtra("nama");

        txtIdAdminEditTnilai.setText(tNim);
        txtNamaAdminEditTnilai.setText(tName);

        ed_editNimTnilai = findViewById(R.id.ed_editNimTnilai);
        ed_editNamaTnilai = findViewById(R.id.ed_editNamaTnilai);
        spEditProdiTnilaiAdmin = findViewById(R.id.spEditProdiTnilaiAdmin);
        spEditSemesterTnilaiAdmin = findViewById(R.id.spEditSemesterTnilaiAdmin);
        txtImageUrlEditTnilai = findViewById(R.id.txtImageUrlEditTnilai);
        img = findViewById(R.id.imgUploadEditTnilaiAdmin);

        ed_editNimTnilai.setText(getIntent().getStringExtra("nimEDTN"));
        ed_editNamaTnilai.setText(getIntent().getStringExtra("namaEDTN"));
        txtImageUrlEditTnilai.setText(getIntent().getStringExtra("imgTN"));
        txtDataNimEditTnilai.setText(getIntent().getStringExtra("nimEDTN"));
        urlimg = txtImageUrlEditTnilai.getText().toString().trim();



        upload = findViewById(R.id.btn_EditTnilaiAdmin);
        browse = findViewById(R.id.browseImgEditTnilaiAdmin);

        Glide.with(this).load(urlimg).into(img);

        browse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dexter.withActivity(EditTnilaiAdminActivity.this)
                        .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                        .withListener(new PermissionListener() {
                            @Override
                            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                                Intent intent = new Intent(Intent.ACTION_PICK);
                                intent.setType("image/*");
                                startActivityForResult(Intent.createChooser(intent, "Browse Image"), 1);
                            }

                            @Override
                            public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

                            }

                            @Override
                            public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                                permissionToken.continuePermissionRequest();
                            }
                        }).check();
            }
        });

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadDataTnilai();
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode==1 && resultCode==RESULT_OK){
            Uri filepath = data.getData();
            try {
                InputStream inputStream=getContentResolver().openInputStream(filepath);
                bitmap = BitmapFactory.decodeStream(inputStream);
                img.setImageBitmap(bitmap);
                encodeBitmapImage(bitmap);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void encodeBitmapImage(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100,byteArrayOutputStream);

        byte[] bytesofimage = byteArrayOutputStream.toByteArray();
        encodeImageString = android.util.Base64.encodeToString(bytesofimage, Base64.DEFAULT);
    }

    private void uploadDataTnilai() {

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Mohon Menunggu.....");
        progressDialog.show();
        str_nim = ed_editNimTnilai.getText().toString().trim();
        str_nama = ed_editNamaTnilai.getText().toString().trim();
        str_prodi = spEditProdiTnilaiAdmin.getSelectedItem().toString();
        str_semester = spEditSemesterTnilaiAdmin.getSelectedItem().toString();
        str_nimDataEditKhs = txtDataNimEditTnilai.getText().toString().trim();

        StringRequest request = new StringRequest(Request.Method.POST, urlapi, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                ed_editNimTnilai.setText("");
                ed_editNamaTnilai.setText("");
                img.setImageResource(R.drawable.ic_launcher_foreground);
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(),response.toString(), Toast.LENGTH_LONG).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(),error.toString(), Toast.LENGTH_LONG).show();
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String,String>();
                map.put("nimData", str_nimDataEditKhs);
                map.put("nim", str_nim);
                map.put("nama", str_nama);
                map.put("prodi", str_prodi);
                map.put("semester", str_semester);
                map.put("upload", encodeImageString);
                return map;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(request);
    }


    public void onBackPressed() {
        String idAdmin = txtIdAdminEditTnilai.getText().toString().trim();
        String namaAdmin = txtNamaAdminEditTnilai.getText().toString().trim();
        Intent intent = new Intent(getApplicationContext(), TnilaiAdminActivity.class);
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