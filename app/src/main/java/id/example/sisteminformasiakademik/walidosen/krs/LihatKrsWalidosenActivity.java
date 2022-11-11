package id.example.sisteminformasiakademik.walidosen.krs;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import id.example.sisteminformasiakademik.R;
import id.example.sisteminformasiakademik.admin.krs.KrsAdminActivity;
import id.example.sisteminformasiakademik.admin.krs.LihatKrsActivity;

public class LihatKrsWalidosenActivity extends AppCompatActivity {

    TextView txtnidnKRS,txtNamaDosenKRS,txtdate, txtNamaLihatKrs, txtNimLihatKrs, txtProdi, txtSemester, txtmatkul1, txtsks1, txtmatkul2, txtsks2, txtmatkul3, txtsks3, txtmatkul4, txtsks4, txtmatkul5, txtsks5, txtmatkul6, txtsks6, txtmatkul7, txtsks7, txtmatkul8, txtsks8, txtjumlahsks;
    public final int REQUEST_CODE = 100;
    Button btnSimpanKrs;
    public static int REQUEST_PERMISSIONS = 1;
    boolean boolean_permission;
    boolean boolean_save;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lihat_krs_walidosen);
        getSupportActionBar().hide();
        fn_permission();

        txtnidnKRS = findViewById(R.id.txtnidnKRSlihatkrsWD);
        txtNamaDosenKRS = findViewById(R.id.txtNamaDosenKRSlihatkrsWD);
        btnSimpanKrs = findViewById(R.id.btnSimpanKrsPDFWD);
        txtNimLihatKrs = findViewById(R.id.txtNimLihatKRSWD);
        txtNamaLihatKrs = findViewById(R.id.txtNamaLihatKRSWD);
        txtProdi = findViewById(R.id.txtprodiLihatKrsWD);
        txtSemester = findViewById(R.id.txtsemesterLihatKrsWD);
        txtmatkul1 = findViewById(R.id.tvMatkul1WD);
        txtsks1 = findViewById(R.id.tvSks1WD);
        txtmatkul2 = findViewById(R.id.tvMatkul2WD);
        txtsks2 = findViewById(R.id.tvSks2WD);
        txtmatkul3 = findViewById(R.id.tvMatkul3WD);
        txtsks3 = findViewById(R.id.tvSks3WD);
        txtmatkul4 = findViewById(R.id.tvMatkul4WD);
        txtsks4 = findViewById(R.id.tvSks4WD);
        txtmatkul5 = findViewById(R.id.tvMatkul5WD);
        txtsks5 = findViewById(R.id.tvSks5WD);
        txtmatkul6 = findViewById(R.id.tvMatkul6WD);
        txtsks6 = findViewById(R.id.tvSks6WD);
        txtmatkul7 = findViewById(R.id.tvMatkul7WD);
        txtsks7 = findViewById(R.id.tvSks7WD);
        txtmatkul8 = findViewById(R.id.tvMatkul8WD);
        txtsks8 = findViewById(R.id.tvSks8WD);
        txtjumlahsks = findViewById(R.id.tvJumlahsksWD);
        txtdate = findViewById(R.id.dateKrsWD);

        btnSimpanKrs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog = new ProgressDialog(LihatKrsWalidosenActivity.this);
                progressDialog.setMessage("Please wait");
                progressDialog.show();
                printKRS();
                progressDialog.dismiss();
            }
        });

        Intent i = getIntent();
        String nidn = i.getStringExtra("nidn");
        String namaDosen = i.getStringExtra("namaDosen");
        String tNim = i.getStringExtra("nim");
        String tName = i.getStringExtra("nama");
        String prodi = i.getStringExtra("prodi");
        String semester = i.getStringExtra("semester");
        String matkul1 = i.getStringExtra("matkul1");
        String sks1 = i.getStringExtra("sks1");
        String matkul2 = i.getStringExtra("matkul2");
        String sks2 = i.getStringExtra("sks2");
        String matkul3 = i.getStringExtra("matkul3");
        String sks3 = i.getStringExtra("sks3");
        String matkul4 = i.getStringExtra("matkul4");
        String sks4 = i.getStringExtra("sks4");
        String matkul5 = i.getStringExtra("matkul5");
        String sks5 = i.getStringExtra("sks5");
        String matkul6 = i.getStringExtra("matkul6");
        String sks6 = i.getStringExtra("sks6");
        String matkul7 = i.getStringExtra("matkul7");
        String sks7 = i.getStringExtra("sks7");
        String matkul8 = i.getStringExtra("matkul8");
        String sks8 = i.getStringExtra("sks8");
        String jumlahsks = i.getStringExtra("jumlahsks");
        String date = i.getStringExtra("date");


        txtNimLihatKrs.setText(tNim);
        txtNamaLihatKrs.setText(tName);
        txtProdi.setText(prodi);
        txtSemester.setText(semester);
        txtmatkul1.setText(matkul1);
        txtsks1.setText(sks1);
        txtmatkul2.setText(matkul2);
        txtsks2.setText(sks2);
        txtmatkul3.setText(matkul3);
        txtsks3.setText(sks3);
        txtmatkul4.setText(matkul4);
        txtsks4.setText(sks4);
        txtmatkul5.setText(matkul5);
        txtsks5.setText(sks5);
        txtmatkul6.setText(matkul6);
        txtsks6.setText(sks6);
        txtmatkul7.setText(matkul7);
        txtsks7.setText(sks7);
        txtmatkul8.setText(matkul8);
        txtsks8.setText(sks8);
        txtjumlahsks.setText(jumlahsks);
        txtdate.setText(date);

        txtnidnKRS.setText(nidn);
        txtNamaDosenKRS.setText(namaDosen);
    }

    public void onBackPressed() {
        String nimData = txtnidnKRS.getText().toString().trim();
        String namaData = txtNamaDosenKRS.getText().toString().trim();
        String prodi = txtProdi.getText().toString().trim();
        String semester = txtSemester.getText().toString().trim();
        Intent intent = new Intent(getApplicationContext(), KrsWalidosenActivity.class);
        intent.putExtra("nidn", nimData);
        intent.putExtra("namaDosen", namaData);
        intent.putExtra("prodi", prodi);
        intent.putExtra("semester", semester);
        startActivity(intent);
        overridePendingTransition(0, 0);
        finish();
    }

    private void printKRS() {
        String nama = txtNamaLihatKrs.getText().toString();
        String nim = txtNimLihatKrs.getText().toString();
        String prodi = txtProdi.getText().toString();
        String semester = txtSemester.getText().toString();

        String matkul1 = txtmatkul1.getText().toString();
        String sks1 = txtsks1.getText().toString();
        String matkul2 = txtmatkul2.getText().toString();
        String sks2 = txtsks2.getText().toString();
        String matkul3 = txtmatkul3.getText().toString();
        String sks3 = txtsks3.getText().toString();
        String matkul4 = txtmatkul4.getText().toString();
        String sks4 = txtsks4.getText().toString();
        String matkul5 = txtmatkul5.getText().toString();
        String sks5 = txtsks5.getText().toString();
        String matkul6 = txtmatkul6.getText().toString();
        String sks6 = txtsks6.getText().toString();
        String matkul7 = txtmatkul7.getText().toString();
        String sks7 = txtsks7.getText().toString();
        String matkul8 = txtmatkul8.getText().toString();
        String sks8 = txtsks8.getText().toString();

        String jumlahsks = txtjumlahsks.getText().toString();
        String date = txtdate.getText().toString();

        PdfDocument document = new PdfDocument();
        Paint paint = new Paint();
        Paint forLinePaint = new Paint();
        PdfDocument.PageInfo  pageInfo  = new PdfDocument.PageInfo.Builder(250, 400, 1).create();
        PdfDocument.Page page = document.startPage(pageInfo);
        Canvas canvas = page.getCanvas();

        paint.setTextSize(15.5f);
        paint.setColor(Color.rgb(0, 0, 0));

        canvas.drawText("Universitas Putra Indonesia", 40, 20, paint);
        paint.setTextSize(9.5f);
        canvas.drawText("Jln.Dr.Muwardi No.66 Telp. (0263) 262604", 50, 40, paint);
        canvas.drawText("By Pass - Cianjur 43215", 80, 50, paint);

        forLinePaint.setStyle(Paint.Style.FILL_AND_STROKE);
        forLinePaint.setPathEffect(new DashPathEffect(new float[]{5, 5}, 0));
        forLinePaint.setStrokeWidth(2);
        canvas.drawLine(20, 60, 240, 60, forLinePaint);

        paint.setTextSize(10.5f);
        canvas.drawText("KARTU RENCANA STUDI", 80, 75, paint);

        paint.setTextSize(8.5f);
        canvas.drawText("Nama          : " + nama, 20, 90, paint);
        canvas.drawText("NIM             : " + nim, 20, 100, paint);
        canvas.drawText("Prodi           : " + prodi, 20, 110, paint);
        canvas.drawText("Semester   : " + semester, 20, 120, paint);
        canvas.drawLine(20, 140, 240, 140, forLinePaint);

        canvas.drawText("Nama Mata Kuliah", 40, 155, paint);
        canvas.drawText("SKS", 200, 155, paint);
        canvas.drawLine(20, 165, 240, 165, forLinePaint);

        canvas.drawText("" + matkul1, 20, 175, paint);
        canvas.drawText("" + sks1, 205, 175, paint);
        canvas.drawText("" + matkul2, 20, 190, paint);
        canvas.drawText("" + sks2, 205, 190, paint);
        canvas.drawText("" + matkul3, 20, 205, paint);
        canvas.drawText("" + sks3, 205, 205, paint);
        canvas.drawText("" + matkul4, 20, 220, paint);
        canvas.drawText("" + sks4, 205, 220, paint);
        canvas.drawText("" + matkul5, 20, 235, paint);
        canvas.drawText("" + sks5, 205, 235, paint);
        canvas.drawText("" + matkul6, 20, 250, paint);
        canvas.drawText("" + sks6, 205, 250, paint);
        canvas.drawText("" + matkul7, 20, 265, paint);
        canvas.drawText("" + sks7, 205, 265, paint);
        canvas.drawText("" + matkul8, 20, 280, paint);
        canvas.drawText("" + sks8, 205, 280, paint);

        canvas.drawText("Jumlah SKS : ", 140, 285, paint);
        canvas.drawText("" + jumlahsks, 200, 285, paint);
        canvas.drawLine(20, 295, 240, 295, forLinePaint);

        canvas.drawText("" + date, 120, 310, paint);
        canvas.drawText("Mengetahui : ", 115, 325, paint);
        canvas.drawText("Bagian Akademik", 20, 340, paint);
        canvas.drawText("Dosen Wali", 115, 340, paint);
        canvas.drawText("Mahasiswa", 200, 340, paint);

        canvas.drawText("................", 35, 375, paint);
        canvas.drawText("................", 115, 375, paint);
        canvas.drawText("................", 200, 375, paint);

        document.finishPage(page);

        String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString() + "/Wali Dosen/KRS UNPI/";
        File dir = new File(path);
        if (!dir.exists())
            dir.mkdirs();

        File filePath = new File(dir,"KRS-UNPI-"+ ""+txtNamaLihatKrs.getText().toString()+ ".pdf");

        try {
            document.writeTo(new FileOutputStream(filePath));
            boolean_save=true;
            Toast.makeText(this, "PDF Tersimpan Di Penyimpanan Internal/Download/Wali Dosen/KRS UNPI", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Something wrong: " + e.toString(), Toast.LENGTH_LONG).show();
        }

        // close the document
        document.close();

    }

    private void fn_permission() {
        if ((ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) ||
                (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)) {

            if ((ActivityCompat.shouldShowRequestPermissionRationale(LihatKrsWalidosenActivity.this, android.Manifest.permission.READ_EXTERNAL_STORAGE))) {
            } else {
                ActivityCompat.requestPermissions(LihatKrsWalidosenActivity.this, new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_PERMISSIONS);

            }

            if ((ActivityCompat.shouldShowRequestPermissionRationale(LihatKrsWalidosenActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE))) {
            } else {
                ActivityCompat.requestPermissions(LihatKrsWalidosenActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
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
}