package id.example.sisteminformasiakademik.admin.mahasiswa;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
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
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import id.example.sisteminformasiakademik.R;
import id.example.sisteminformasiakademik.admin.DashboardAdminActivity;

public class UserMahasiswaAdminActivity extends AppCompatActivity {

    TextView txtidAdmin, txtNamaAdmin;
    FloatingActionButton fabAdd;
    Spinner spProdiMHSAdmin, spSemMHSAdmin;
    String url = "https://droomp.tech/admin/mahasiswa/show_mahasiswa_admin.php";

    ArrayList<MahasiswaData> list;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_mahasiswa_admin);
        getSupportActionBar().hide();

        txtidAdmin = findViewById(R.id.txtIdAdminDM);
        txtNamaAdmin = findViewById(R.id.txtNamaAdminDM);
        fabAdd = findViewById(R.id.fab_add);
        spProdiMHSAdmin = findViewById(R.id.spinnerProdiAdmin);
        spSemMHSAdmin = findViewById(R.id.spinnerNoMatkulAdmin);

        listView = findViewById(R.id.listViewUserMHSAdmin);
        list = new ArrayList<>();

        Intent i = getIntent();
        String tNim = i.getStringExtra("id_admin");
        String tName = i.getStringExtra("nama");

        txtidAdmin.setText(tNim);
        txtNamaAdmin.setText(tName);

        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String idAdmin = txtidAdmin.getText().toString().trim();
                String namaAdmin = txtNamaAdmin.getText().toString().trim();
                Intent intent = new Intent(UserMahasiswaAdminActivity.this, InputMahasiswaActivity.class);
                intent.putExtra("id_admin", idAdmin);
                intent.putExtra("nama", namaAdmin);
                startActivity(intent);
                overridePendingTransition(0, 0);
                finish();
            }
        });

        spProdiMHSAdmin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                show_mahasiswa();
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });

        spSemMHSAdmin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                show_mahasiswa();
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });


    }

    public void show_mahasiswa() {

        String prodi = spProdiMHSAdmin.getSelectedItem().toString();
        String semester = spSemMHSAdmin.getSelectedItem().toString();
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Mohon Menunggu.....");

        progressDialog.show();

        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
//                    JSONObject jsonObject = new JSONObject(response);
//                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    JSONArray j = new JSONArray(response);
                    list.clear();
                    for (int i=0; i<j.length(); i++){
                        try {
//                            JSONObject getData = jsonArray.getJSONObject(i);
                            JSONObject object = j.getJSONObject(i);
                            String nim = object.getString("nim");
                            String nama = object.getString("nama");
                            String email = object.getString("email");
                            String prodi = object.getString("prodi");
                            String semester = object.getString("semester");

                            MahasiswaData mahasiswaData = new MahasiswaData();
                            mahasiswaData.setNim(nim);
                            mahasiswaData.setNama(nama);
                            mahasiswaData.setEmail(email);
                            mahasiswaData.setProdi(prodi);
                            mahasiswaData.setSemester(semester);
                            list.add(mahasiswaData);
                            progressDialog.dismiss();
//                        list.add(new MahasiswaData(nim,nama,email,prodi,semester));
                        } catch (JSONException e){
                            e.printStackTrace();
                            progressDialog.dismiss();
                        }
                    }
                    Adapter adapter = new Adapter(UserMahasiswaAdminActivity.this, list);
                    listView.setAdapter(adapter);


                    progressDialog.dismiss();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        }) {
            //                @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                params.put("prodi", prodi);
                params.put("semester", semester);
                return params;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(UserMahasiswaAdminActivity.this);
        queue.add(request);

    }


    public void moveDashboardMhsData(View view) {
        String idAdmin = txtidAdmin.getText().toString().trim();
        String namaAdmin = txtNamaAdmin.getText().toString().trim();
        Intent intent = new Intent(getApplicationContext(), DashboardAdminActivity.class);
        intent.putExtra("id_admin", idAdmin);
        intent.putExtra("nama", namaAdmin);
        startActivity(intent);
        overridePendingTransition(0, 0);
        finish();
    }

    public void onBackPressed() {
        String idAdmin = txtidAdmin.getText().toString().trim();
        String namaAdmin = txtNamaAdmin.getText().toString().trim();
        Intent intent = new Intent(getApplicationContext(), DashboardAdminActivity.class);
        intent.putExtra("id_admin", idAdmin);
        intent.putExtra("nama", namaAdmin);
        startActivity(intent);
        overridePendingTransition(0, 0);
        finish();

    }
}

class Adapter extends BaseAdapter{

    Context context;
    LayoutInflater inflater;
    ArrayList<MahasiswaData> model;

    public Adapter(Context context, ArrayList<MahasiswaData> model){
        inflater=LayoutInflater.from(context);
        this.context=context;
        this.model = model;
    }

    @Override
    public int getCount() {
        return model.size();
    }

    @Override
    public Object getItem(int position) {
        return model.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    TextView nim, nama, email, prodi, semester,txtIdmhslist,txtNamamhslist;
    Button lihat, edit, hapus;
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = inflater.inflate(R.layout.mahasiswa_list_admin, parent, false);

        lihat = view.findViewById(R.id.btn_lihat_mhsadmin);
        edit = view.findViewById(R.id.btn_edit_mhsadmin);
        hapus = view.findViewById(R.id.btn_hapus_mhsadmin);

        nim = view.findViewById(R.id.txt_nimMHS_admin);
        nama = view.findViewById(R.id.txt_namaMHS_admin);
        email = view.findViewById(R.id.txt_emailMHS_admin);
        prodi = view.findViewById(R.id.txt_prodiMHS_admin);
        semester = view.findViewById(R.id.txt_semesterMHS_admin);

        nim.setText(model.get(position).getNim());
        nama.setText(model.get(position).getNama());
        email.setText(model.get(position).getEmail());
        prodi.setText(model.get(position).getProdi());
        semester.setText(model.get(position).getSemester());

        txtIdmhslist = view.findViewById(R.id.txtIdmhslist);
        txtNamamhslist = view.findViewById(R.id.txtNamamhslist);

        Intent i = ((Activity) context).getIntent();
        String tNim = i.getStringExtra("id_admin");
        String tName = i.getStringExtra("nama");

        txtIdmhslist.setText(tNim);
        txtNamamhslist.setText(tName);

        lihat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String idAdmin = txtIdmhslist.getText().toString().trim();
                String namaAdmin = txtNamamhslist.getText().toString().trim();
                Intent intent = new Intent(context, Lihat_data_mahasiswa.class);
                intent.putExtra("id_admin", idAdmin);
                intent.putExtra("nama", namaAdmin);
                intent.putExtra("nimMHS", model.get(position).getNim());
                intent.putExtra("namaMHS", model.get(position).getNama());
                intent.putExtra("emailMHS", model.get(position).getEmail());
                intent.putExtra("prodiMHS", model.get(position).getProdi());
                intent.putExtra("semesterMHS", model.get(position).getSemester());
                context.startActivity(intent);
            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String idAdmin = txtIdmhslist.getText().toString().trim();
                String namaAdmin = txtNamamhslist.getText().toString().trim();
                Intent intented = new Intent(context, EditMahasiswaActivity.class);
                intented.putExtra("id_admin", idAdmin);
                intented.putExtra("nama", namaAdmin);
                intented.putExtra("nimED", model.get(position).getNim());
                intented.putExtra("namaED", model.get(position).getNama());
                intented.putExtra("emailED", model.get(position).getEmail());
                intented.putExtra("prodiED", model.get(position).getProdi());
                intented.putExtra("semesterED", model.get(position).getSemester());
                context.startActivity(intented);
            }
        });

        hapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String idAdmin = txtIdmhslist.getText().toString().trim();
                String namaAdmin = txtNamamhslist.getText().toString().trim();
                Intent intenth = new Intent(context, HapusMahasiswaActivity.class);
                intenth.putExtra("id_admin", idAdmin);
                intenth.putExtra("nama", namaAdmin);
                intenth.putExtra("nimHP", model.get(position).getNim());
                intenth.putExtra("namaHP", model.get(position).getNama());
                intenth.putExtra("emailHP", model.get(position).getEmail());
                intenth.putExtra("prodiHP", model.get(position).getProdi());
                intenth.putExtra("semesterHP", model.get(position).getSemester());
                context.startActivity(intenth);
            }
        });

        return view;
    }


}