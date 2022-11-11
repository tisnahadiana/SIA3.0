package id.example.sisteminformasiakademik.admin.matkul;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

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
import java.util.Map;

import id.example.sisteminformasiakademik.R;
import id.example.sisteminformasiakademik.admin.DashboardAdminActivity;
import id.example.sisteminformasiakademik.admin.mahasiswa.EditMahasiswaActivity;
import id.example.sisteminformasiakademik.admin.mahasiswa.HapusMahasiswaActivity;
import id.example.sisteminformasiakademik.admin.mahasiswa.InputMahasiswaActivity;
import id.example.sisteminformasiakademik.admin.mahasiswa.Lihat_data_mahasiswa;
import id.example.sisteminformasiakademik.admin.mahasiswa.MahasiswaData;
import id.example.sisteminformasiakademik.admin.mahasiswa.UserMahasiswaAdminActivity;

public class MatkulAdminActivity extends AppCompatActivity {

    TextView txtidAdminMatkul, txtNamaAdminMatkul;
    FloatingActionButton fabAddMatkul;
    Spinner spProdiMatkulAdmin, spSemesterMatkulAdmin;
    String url = "https://droomp.tech/admin/matkul/show_matkul.php";

    ArrayList<MatkulData> list;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matkul_admin);
        getSupportActionBar().hide();

        txtidAdminMatkul = findViewById(R.id.txtIdAdminMatkul);
        txtNamaAdminMatkul = findViewById(R.id.txtNamaAdminMatkul);
        fabAddMatkul = findViewById(R.id.fab_addMatkul);
        spProdiMatkulAdmin = findViewById(R.id.spProdiMatkulADM);
        spSemesterMatkulAdmin = findViewById(R.id.spNoSemesterMatkulADM);

        listView = findViewById(R.id.listViewUserMatkulAdmin);
        list = new ArrayList<>();

        Intent i = getIntent();
        String tNim = i.getStringExtra("id_admin");
        String tName = i.getStringExtra("nama");

        txtidAdminMatkul.setText(tNim);
        txtNamaAdminMatkul.setText(tName);

        fabAddMatkul.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String idAdmin = txtidAdminMatkul.getText().toString().trim();
                String namaAdmin = txtNamaAdminMatkul.getText().toString().trim();
                Intent intent = new Intent(MatkulAdminActivity.this, InputMatkulActivity.class);
                intent.putExtra("id_admin", idAdmin);
                intent.putExtra("nama", namaAdmin);
                startActivity(intent);
                overridePendingTransition(0, 0);
                finish();
            }
        });

        spProdiMatkulAdmin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                show_matkul();
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });

        spSemesterMatkulAdmin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                show_matkul();
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });


    }


    public void show_matkul() {

        String prodi = spProdiMatkulAdmin.getSelectedItem().toString();
        String semester = spSemesterMatkulAdmin.getSelectedItem().toString();
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
                            String nama_mk = object.getString("nama_mk");
                            String sks = object.getString("sks");
                            String prodi = object.getString("prodi");
                            String semester = object.getString("semester");

                            MatkulData matkulData = new MatkulData();
                            matkulData.setNama_mk(nama_mk);
                            matkulData.setSks(sks);
                            matkulData.setProdi(prodi);
                            matkulData.setSemester(semester);
                            list.add(matkulData);
                            progressDialog.dismiss();
//                        list.add(new MahasiswaData(nim,nama,email,prodi,semester));
                        } catch (JSONException e){
                            e.printStackTrace();
                            progressDialog.dismiss();
                        }
                    }
                    Adapter adapter = new Adapter(MatkulAdminActivity.this, list);
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
        RequestQueue queue = Volley.newRequestQueue(MatkulAdminActivity.this);
        queue.add(request);

    }


    public void moveDashboardMatkulAdmin(View view){
        String idAdmin = txtidAdminMatkul.getText().toString().trim();
        String namaAdmin = txtNamaAdminMatkul.getText().toString().trim();
        Intent intent = new Intent(getApplicationContext(), DashboardAdminActivity.class);
        intent.putExtra("id_admin", idAdmin);
        intent.putExtra("nama", namaAdmin);
        startActivity(intent);
        overridePendingTransition(0, 0);
        finish();
    }

    public void onBackPressed() {
        String idAdmin = txtidAdminMatkul.getText().toString().trim();
        String namaAdmin = txtNamaAdminMatkul.getText().toString().trim();
        Intent intent = new Intent(getApplicationContext(), DashboardAdminActivity.class);
        intent.putExtra("id_admin", idAdmin);
        intent.putExtra("nama", namaAdmin);
        startActivity(intent);
        overridePendingTransition(0, 0);
        finish();

    }
}


class Adapter extends BaseAdapter {

    Context context;
    LayoutInflater inflater;
    ArrayList<MatkulData> model;

    public Adapter(Context context, ArrayList<MatkulData> model){
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

    TextView nama_mk, sks, prodi, semester, txtIdMatkullist ,txtNamaMatkullist;
    Button lihat, edit, hapus;
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = inflater.inflate(R.layout.matkul_list_admin, parent, false);

        lihat = view.findViewById(R.id.btn_lihat_matkuladmin);
        edit = view.findViewById(R.id.btn_edit_matkuladmin);
        hapus = view.findViewById(R.id.btn_hapus_matkuladmin);

        nama_mk = view.findViewById(R.id.txt_namaMatkul_admin);
        sks = view.findViewById(R.id.txt_SKS_admin);
        prodi = view.findViewById(R.id.txt_prodiMatkul_admin);
        semester = view.findViewById(R.id.txt_semesterMatkul_admin);

        nama_mk.setText(model.get(position).getNama_mk());
        sks.setText(model.get(position).getSks());
        prodi.setText(model.get(position).getProdi());
        semester.setText(model.get(position).getSemester());

        txtIdMatkullist = view.findViewById(R.id.txtIdMatkullist);
        txtNamaMatkullist = view.findViewById(R.id.txtNamaMatkullist);

        Intent i = ((Activity) context).getIntent();
        String tNim = i.getStringExtra("id_admin");
        String tName = i.getStringExtra("nama");

        txtIdMatkullist.setText(tNim);
        txtNamaMatkullist.setText(tName);

        lihat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String idAdmin = txtIdMatkullist.getText().toString().trim();
                String namaAdmin = txtNamaMatkullist.getText().toString().trim();
                Intent intent = new Intent(context, LihatMatkulActivity.class);
                intent.putExtra("id_admin", idAdmin);
                intent.putExtra("nama", namaAdmin);
                intent.putExtra("namaMatkul", model.get(position).getNama_mk());
                intent.putExtra("sksLihat", model.get(position).getSks());
                intent.putExtra("prodiLihat", model.get(position).getProdi());
                intent.putExtra("semesterLihat", model.get(position).getSemester());
                context.startActivity(intent);
            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String idAdmin = txtIdMatkullist.getText().toString().trim();
                String namaAdmin = txtNamaMatkullist.getText().toString().trim();
                Intent intented = new Intent(context, EditMatkulActivity.class);
                intented.putExtra("id_admin", idAdmin);
                intented.putExtra("nama", namaAdmin);
                intented.putExtra("namaMatkulED", model.get(position).getNama_mk());
                intented.putExtra("sksED", model.get(position).getSks());
                intented.putExtra("prodiMatkulED", model.get(position).getProdi());
                intented.putExtra("semesterMatkulED", model.get(position).getSemester());
                context.startActivity(intented);
            }
        });

        hapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String idAdmin = txtIdMatkullist.getText().toString().trim();
                String namaAdmin = txtNamaMatkullist.getText().toString().trim();
                Intent intenth = new Intent(context, HapusMatkulActivity.class);
                intenth.putExtra("id_admin", idAdmin);
                intenth.putExtra("nama", namaAdmin);
                intenth.putExtra("namaMatkulHP", model.get(position).getNama_mk());
                intenth.putExtra("sksMatkulHP", model.get(position).getSks());
                intenth.putExtra("prodiMatkulHP", model.get(position).getProdi());
                intenth.putExtra("semesterMatkulHP", model.get(position).getSemester());
                context.startActivity(intenth);
            }
        });

        return view;
    }


}