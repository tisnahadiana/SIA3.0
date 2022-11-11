package id.example.sisteminformasiakademik.user.menu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
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

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import id.example.sisteminformasiakademik.R;
import id.example.sisteminformasiakademik.user.ModelImage;
import id.example.sisteminformasiakademik.user.MyAdapter;

public class KhsActivity extends AppCompatActivity {

    TextView txtNimKhs, txtNamaKhs, txtImageUrl,urlImageAdapterView;
    RecyclerView recyclerView;
    MyAdapter myAdapter;
    List<ModelImage> imageList;
    ModelImage modelImage;
    LinearLayoutManager linearLayoutManager;
    ImageView imageView,mImageViewFilter;

    Button btnDownloadKHS;
    public static int REQUEST_PERMISSIONS = 1;
    boolean boolean_permission;
    boolean boolean_save;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_khs);
        fn_permission();

        getSupportActionBar().hide();
        Intent i = getIntent();
        String tNim = i.getStringExtra("nim");
        String tName = i.getStringExtra("nama");
        String img = i.getStringExtra("img");
        txtNimKhs = findViewById(R.id.txtNimKhs);
        txtNamaKhs = findViewById(R.id.txtNamaKhs);
        txtImageUrl = findViewById(R.id.txtImageUrl);

        txtNimKhs.setText(tNim);
        txtNamaKhs.setText(tName);
        txtImageUrl.setText(img);

        recyclerView = findViewById(R.id.recyclerView);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        imageList = new ArrayList<>();
        myAdapter = new MyAdapter(this, imageList);
        recyclerView.setAdapter(myAdapter);
        imageView = findViewById(R.id.imageViewAdapter);

        fetchImagesKHS();

        btnDownloadKHS = findViewById(R.id.btnDownloadKHS);
        btnDownloadKHS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createPDFKHS();
            }
        });

    }

    public void fetchImagesKHS(){

        String nim = txtNimKhs.getText().toString();

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Mohon Menunggu.....");

        progressDialog.show();
        StringRequest request = new StringRequest(Request.Method.POST, "https://droomp.tech/fetchimages.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");

                            JSONArray jsonArray = jsonObject.getJSONArray("data");

                            if (success.equals("1")){

                                for (int i=0;i<jsonArray.length();i++) {
                                    JSONObject object = jsonArray.getJSONObject(i);

                                    String nim = object.getString("nim");
                                    String imageurl = object.getString("image");

                                    String url = imageurl;
//                                    "https://droomp.tech/khs/"+
                                    modelImage = new ModelImage(nim, url);
                                    imageList.add(modelImage);
                                    myAdapter.notifyDataSetChanged();

                                    progressDialog.dismiss();

                                }
                            } else {
                                progressDialog.dismiss();
                                massage("Data Tidak Ditemukan");
                                btnDownloadKHS.setVisibility(View.GONE);
                            }

                        } catch (JSONException e) {
                            progressDialog.dismiss();
                            e.printStackTrace();
                            btnDownloadKHS.setVisibility(View.GONE);
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(KhsActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }) {
            //@Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("nim", nim);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);

    }

    public void moveMenuKhs(View view){
        String nimData = txtNimKhs.getText().toString().trim();
        String namaData = txtNamaKhs.getText().toString().trim();
        Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
        intent.putExtra("nim", nimData);
        intent.putExtra("nama", namaData);
        startActivity(intent);
        overridePendingTransition(0, 0);
        finish();
    }

    public void onBackPressed() {
        String nimData = txtNimKhs.getText().toString().trim();
        String namaData = txtNamaKhs.getText().toString().trim();
        Intent intent = new Intent(getApplicationContext(),MenuActivity.class);
        intent.putExtra("nim", nimData);
        intent.putExtra("nama", namaData);
        startActivity(intent);
        overridePendingTransition(0, 0);
        finish();

    }

    private void createPDFKHS() {
        progressDialog = new ProgressDialog(KhsActivity.this);
        progressDialog.setMessage("Please wait");
        progressDialog.show();
        PdfDocument document = new PdfDocument();
            Paint paint = new Paint(Paint.FILTER_BITMAP_FLAG); ;
            Paint forLinePaint = new Paint();
            PdfDocument.PageInfo  pageInfo  = new PdfDocument.PageInfo.Builder(700, 1000, 1).create();
            PdfDocument.Page page = document.startPage(pageInfo);
            Canvas canvas = page.getCanvas();

        paint.setAntiAlias(false);
        paint.setFilterBitmap(false);
        paint.setDither(true);
            mImageViewFilter = (ImageView) findViewById(R.id.imageViewAdapter);
            Bitmap bitmap = ((BitmapDrawable)mImageViewFilter.getDrawable()).getBitmap();
            canvas.drawBitmap(bitmap, 0,0,paint);

            document.finishPage(page);

            String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString() + "/KHS/";
            File dir = new File(path);
            if (!dir.exists())
                dir.mkdirs();

            File filePath = new File(dir,"KHS-UNPI-"+ ""+txtNamaKhs.getText().toString()+ ".pdf");

            try {
                document.writeTo(new FileOutputStream(filePath));
                boolean_save=true;
                Toast.makeText(this, "PDF Tersimpan Di Penyimpanan Internal/Download/KHS", Toast.LENGTH_SHORT).show();
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

            if ((ActivityCompat.shouldShowRequestPermissionRationale(KhsActivity.this, android.Manifest.permission.READ_EXTERNAL_STORAGE))) {
            } else {
                ActivityCompat.requestPermissions(KhsActivity.this, new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_PERMISSIONS);

            }

            if ((ActivityCompat.shouldShowRequestPermissionRationale(KhsActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE))) {
            } else {
                ActivityCompat.requestPermissions(KhsActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
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