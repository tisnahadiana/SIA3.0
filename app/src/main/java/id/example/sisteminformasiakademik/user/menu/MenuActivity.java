package id.example.sisteminformasiakademik.user.menu;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import id.example.sisteminformasiakademik.LoginActivity;
import id.example.sisteminformasiakademik.R;
import id.example.sisteminformasiakademik.user.DashboardActivity;
import id.example.sisteminformasiakademik.user.matkul.MataKuliahActivity;

public class MenuActivity extends AppCompatActivity {

    TextView txtNimMenu, txtNamaMenu;
    private Bitmap bitmap;
    CircleImageView profile_image;
    private static String URL_UPLOAD = "https://droomp.tech/upload.php";
    String getNIM;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        getSupportActionBar().hide();
        Button btnKeluar = findViewById(R.id.btnKeluar);
        Button btn_upload_photo = findViewById(R.id.btnUploadPhoto);
        txtNimMenu = findViewById(R.id.txtNimMenu);
        txtNamaMenu = findViewById(R.id.txtNamaMenu);
        profile_image = findViewById(R.id.ProfileImage);


        btnKeluar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MenuActivity.this);
                builder.setMessage("Are you sure you want to exit?")
                        .setIcon(R.mipmap.ic_launcher)
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                                finish();
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();
            }
        });


        Intent i = getIntent();
        String tNim = i.getStringExtra("nim");
        String tName = i.getStringExtra("nama");
        getNIM = i.getStringExtra("nim");

        txtNimMenu.setText(tNim);
        txtNamaMenu.setText(tName);


        //initialize and assign variable
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        //set home selected
        bottomNavigationView.setSelectedItemId(R.id.navdashboard);

        //perform itemselectedlistener
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){

                    case R.id.navdashboard:
                        String nimData2 = txtNimMenu.getText().toString().trim();
                        String namaData2 = txtNamaMenu.getText().toString().trim();
                        Intent intent2 = new Intent(getApplicationContext(), DashboardActivity.class);
                        intent2.putExtra("nim", nimData2);
                        intent2.putExtra("nama", namaData2);
                        startActivity(intent2);
                        return true;
                    case R.id.navmenu:
//                        String nimData = txtNimMenu.getText().toString().trim();
//                        String namaData = txtNamaMenu.getText().toString().trim();
//                        Intent intent = new Intent(getApplicationContext(),MenuActivity.class);
//                        intent.putExtra("nim", nimData);
//                        intent.putExtra("nama", namaData);
//                        startActivity(intent);
                        return true;
                }
                return false;
            }
        });

        btn_upload_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseFile();
            }
        });

//        Picasso.get().load(strImage).memoryPolicy(MemoryPolicy.NO_CACHE).networkPolicy(NetworkPolicy.NO_CACHE).into(profile_image);
    }


    public void onBackPressed() {
        String nimData = txtNimMenu.getText().toString().trim();
        String namaData = txtNamaMenu.getText().toString().trim();
        Intent intent = new Intent(getApplicationContext(),DashboardActivity.class);
        intent.putExtra("nim", nimData);
        intent.putExtra("nama", namaData);
        startActivity(intent);
        finish();

    }

    private void chooseFile(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"),1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null){
            Uri filePath = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                profile_image.setImageBitmap(bitmap);
            } catch (IOException e){
                e.printStackTrace();
            }
            Intent i = getIntent();
            String tNim = i.getStringExtra("nim");
            UploadPicture(tNim,getStringImage(bitmap));
        }
    }

    private void UploadPicture(final String nim, final String photo) {
        String nimData = txtNimMenu.getText().toString();
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Uploading...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_UPLOAD,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
//                        Log.i(TAG, response.toString());
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");

                            if (success.equals("1")){
                                Toast.makeText(MenuActivity.this, "Success!", Toast.LENGTH_SHORT).show();
                            }
                        }catch (JSONException e){
                            e.printStackTrace();
                            progressDialog.dismiss();
                            Toast.makeText(MenuActivity.this, "Try Again!" + e.toString(), Toast.LENGTH_SHORT).show();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(MenuActivity.this, "Try Again!" + error.toString(), Toast.LENGTH_SHORT).show();
                    }
                })
        {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("nim", nimData);
                params.put("photo", photo);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    public String getStringImage(Bitmap bitmap){

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);

        byte[] imageByteArray = byteArrayOutputStream.toByteArray();
        String encodedImage = Base64.encodeToString(imageByteArray, Base64.DEFAULT);

        return encodedImage;
    }

    public void moveToisiKHS(View view){
        String nimData = txtNimMenu.getText().toString().trim();
        String namaData = txtNamaMenu.getText().toString().trim();
        Intent intent = new Intent(getApplicationContext(), KhsActivity.class);
        intent.putExtra("nim", nimData);
        intent.putExtra("nama", namaData);
        startActivity(intent);
        overridePendingTransition(0,0);
        finish();
    }

    public void moveToStruktur(View view){
        String nimData = txtNimMenu.getText().toString().trim();
        String namaData = txtNamaMenu.getText().toString().trim();
        Intent intent = new Intent(getApplicationContext(), StrukturActivity.class);
        intent.putExtra("nim", nimData);
        intent.putExtra("nama", namaData);
        startActivity(intent);
        overridePendingTransition(0,0);
        finish();
    }

    public void moveToMatkul(View view){
        String nimData = txtNimMenu.getText().toString().trim();
        String namaData = txtNamaMenu.getText().toString().trim();
        Intent intent = new Intent(getApplicationContext(), MataKuliahActivity.class);
        intent.putExtra("nim", nimData);
        intent.putExtra("nama", namaData);
        startActivity(intent);
        overridePendingTransition(0,0);
        finish();
    }

    public void moveToTnilai(View view){
        String nimData = txtNimMenu.getText().toString().trim();
        String namaData = txtNamaMenu.getText().toString().trim();
        Intent intent = new Intent(getApplicationContext(), TNilaiActivity.class);
        intent.putExtra("nim", nimData);
        intent.putExtra("nama", namaData);
        startActivity(intent);
        overridePendingTransition(0,0);
        finish();
    }


}