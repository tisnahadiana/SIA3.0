package id.example.sisteminformasiakademik.admin.tnilaiAdmin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.pdf.PdfDocument;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Locale;

import id.example.sisteminformasiakademik.R;
import id.example.sisteminformasiakademik.admin.khs.KhsAdminActivity;
import id.example.sisteminformasiakademik.admin.khs.LihatKhsActivity;

public class LihatTnilaiAdminActivity extends AppCompatActivity {

    TextView txtIdAdminLihatTnilai, txtNamaAdminLihatTnilai,nim, nama,prodi, semester, imgURLKhs;
    ImageView imgLihatTnilai;
    String url;
    Button btnDownloadTnilai;

    public static int REQUEST_PERMISSIONS = 1;
    boolean boolean_permission;
    boolean boolean_save;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lihat_tnilai);
        getSupportActionBar().hide();
        fn_permission();

        txtIdAdminLihatTnilai = findViewById(R.id.txtIdAdminLihatTnilai);
        txtNamaAdminLihatTnilai = findViewById(R.id.txtNamaAdminLihatTnilai);
        btnDownloadTnilai = findViewById(R.id.btnDownloadTnilai);

        Intent i = getIntent();
        String tNim = i.getStringExtra("id_admin");
        String tName = i.getStringExtra("nama");

        txtIdAdminLihatTnilai.setText(tNim);
        txtNamaAdminLihatTnilai.setText(tName);

        nim = findViewById(R.id.nimLihatdataTnilai);
        nama = findViewById(R.id.namaLihatdataTnilai);
        prodi = findViewById(R.id.prodiLihatdataTnilai);
        semester = findViewById(R.id.semesterLihatdataTnilai);
        imgURLKhs = findViewById(R.id.txtImageUrlLihatTnilai);
        imgLihatTnilai = findViewById(R.id.imgLihatTnilai);


        nim.setText(getIntent().getStringExtra("nimLTNL"));
        nama.setText(getIntent().getStringExtra("namaLTNL"));
        prodi.setText(getIntent().getStringExtra("prodiLTNL"));
        semester.setText(getIntent().getStringExtra("semesterLTNL"));
        imgURLKhs.setText(getIntent().getStringExtra("imgLTNL"));
        url = imgURLKhs.getText().toString().trim();

        Glide.with(this).load(url).into(imgLihatTnilai);

        btnDownloadTnilai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createPDFKHS();
            }
        });
    }

    public void onBackPressed() {
        String idAdmin = txtIdAdminLihatTnilai.getText().toString().trim();
        String namaAdmin = txtNamaAdminLihatTnilai.getText().toString().trim();
        Intent intent = new Intent(getApplicationContext(), TnilaiAdminActivity.class);
        intent.putExtra("id_admin", idAdmin);
        intent.putExtra("nama", namaAdmin);
        startActivity(intent);
        overridePendingTransition(0, 0);
        finish();

    }

    private void createPDFKHS() {
        progressDialog = new ProgressDialog(LihatTnilaiAdminActivity.this);
        progressDialog.setMessage("Please wait");
        progressDialog.show();
        PdfDocument document = new PdfDocument();
        Paint paint = new Paint(Paint.FILTER_BITMAP_FLAG); ;
        Paint forLinePaint = new Paint();
        PdfDocument.PageInfo  pageInfo  = new PdfDocument.PageInfo.Builder(450, 700, 1).create();
        PdfDocument.Page page = document.startPage(pageInfo);
        Canvas canvas = page.getCanvas();

        paint.setAntiAlias(false);
        paint.setFilterBitmap(false);
        paint.setDither(true);
//        mImageViewFilter = (ImageView) findViewById(R.id.imageViewAdapter);
        Bitmap bitmap = ((BitmapDrawable)imgLihatTnilai.getDrawable()).getBitmap();
        canvas.drawBitmap(bitmap, 0,0,paint);

        document.finishPage(page);

        String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString() + "/Admin/Transkrip/";
        File dir = new File(path);
        if (!dir.exists())
            dir.mkdirs();

        File filePath = new File(dir,"Transkrip-UNPI-"+ ""+nama.getText().toString()+ ".pdf");

        try {
            document.writeTo(new FileOutputStream(filePath));
            boolean_save=true;
            Toast.makeText(this, "PDF Tersimpan Di Penyimpanan Internal/Download/Admin/Transkrip", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Something wrong: " + e.toString(), Toast.LENGTH_LONG).show();
        }

        // close the document
        document.close();


        progressDialog.dismiss();

    }



    private void fn_permission() {
        if ((ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) ||
                (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)) {

            if ((ActivityCompat.shouldShowRequestPermissionRationale(LihatTnilaiAdminActivity.this, android.Manifest.permission.READ_EXTERNAL_STORAGE))) {
            } else {
                ActivityCompat.requestPermissions(LihatTnilaiAdminActivity.this, new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_PERMISSIONS);

            }

            if ((ActivityCompat.shouldShowRequestPermissionRationale(LihatTnilaiAdminActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE))) {
            } else {
                ActivityCompat.requestPermissions(LihatTnilaiAdminActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        REQUEST_PERMISSIONS);

            }
        } else {
            boolean_permission = true;


        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSIONS) {

            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                boolean_permission = true;


            } else {
                Toast.makeText(getApplicationContext(), "Please allow the permission", Toast.LENGTH_LONG).show();

            }
        }
    }

    public void massage(String massage) {
        Toast.makeText(this, massage, Toast.LENGTH_LONG).show();
    }
}