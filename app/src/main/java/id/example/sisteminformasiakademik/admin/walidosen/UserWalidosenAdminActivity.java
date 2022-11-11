package id.example.sisteminformasiakademik.admin.walidosen;

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
import id.example.sisteminformasiakademik.admin.admin.Lihat_data_admin;
import id.example.sisteminformasiakademik.admin.mahasiswa.EditMahasiswaActivity;
import id.example.sisteminformasiakademik.admin.mahasiswa.HapusMahasiswaActivity;
import id.example.sisteminformasiakademik.admin.mahasiswa.InputMahasiswaActivity;
import id.example.sisteminformasiakademik.admin.mahasiswa.Lihat_data_mahasiswa;
import id.example.sisteminformasiakademik.admin.mahasiswa.MahasiswaData;
import id.example.sisteminformasiakademik.admin.mahasiswa.UserMahasiswaAdminActivity;

public class UserWalidosenAdminActivity extends AppCompatActivity {

    TextView txtidAdmin, txtNamaAdmin;
    FloatingActionButton fabAddWD;
    Spinner spProdiWDAdmin, spSemWDAdmin;
    String url = "https://droomp.tech/admin/walidosen/show_walidosen_admin.php";

    ArrayList<WalidosenData> list;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_walidosen_admin);
        getSupportActionBar().hide();

        txtidAdmin = findViewById(R.id.txtIdAdminWD);
        txtNamaAdmin = findViewById(R.id.txtNamaAdminWD);
        fabAddWD = findViewById(R.id.fab_addWD);
        spProdiWDAdmin = findViewById(R.id.spinnerProdiWD);
        spSemWDAdmin = findViewById(R.id.spinnerNoMatkulWD);

        listView = findViewById(R.id.listViewUserWD);
        list = new ArrayList<>();

        Intent i = getIntent();
        String tNim = i.getStringExtra("id_admin");
        String tName = i.getStringExtra("nama");

        txtidAdmin.setText(tNim);
        txtNamaAdmin.setText(tName);

        fabAddWD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String idAdmin = txtidAdmin.getText().toString().trim();
                String namaAdmin = txtNamaAdmin.getText().toString().trim();
                Intent intent = new Intent(UserWalidosenAdminActivity.this, InputWalidosenActivity.class);
                intent.putExtra("id_admin", idAdmin);
                intent.putExtra("nama", namaAdmin);
                startActivity(intent);
                overridePendingTransition(0, 0);
                finish();
            }
        });

        spProdiWDAdmin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                show_walidosen();
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });

        spSemWDAdmin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                show_walidosen();
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });
    }

    public void show_walidosen() {

        String prodi = spProdiWDAdmin.getSelectedItem().toString();
        String semester = spSemWDAdmin.getSelectedItem().toString();
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
                            String nidn = object.getString("nidn");
                            String nama = object.getString("nama");
                            String email = object.getString("email");
                            String prodi = object.getString("prodi");
                            String semester = object.getString("semester");

                            WalidosenData walidosenData = new WalidosenData();
                            walidosenData.setNidn(nidn);
                            walidosenData.setNama(nama);
                            walidosenData.setEmail(email);
                            walidosenData.setProdi(prodi);
                            walidosenData.setSemester(semester);
                            list.add(walidosenData);
                            progressDialog.dismiss();
//                        list.add(new MahasiswaData(nim,nama,email,prodi,semester));
                        } catch (JSONException e){
                            e.printStackTrace();
                            progressDialog.dismiss();
                        }
                    }
                    Adapter adapter = new Adapter(UserWalidosenAdminActivity.this, list);
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
        RequestQueue queue = Volley.newRequestQueue(UserWalidosenAdminActivity.this);
        queue.add(request);

    }

    public void moveDashboardWDdata(View view){
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

class Adapter extends BaseAdapter {

    Context context;
    LayoutInflater inflater;
    ArrayList<WalidosenData> model;

    public Adapter(Context context, ArrayList<WalidosenData> model){
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

    TextView nidn, nama, email, prodi, semester, txtidAdmin ,txtNamaAdmin;
    Button lihat, edit, hapus;
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = inflater.inflate(R.layout.walidosen_list_admin, parent, false);

        lihat = view.findViewById(R.id.btn_lihat_WDadmin);
        edit = view.findViewById(R.id.btn_edit_WDadmin);
        hapus = view.findViewById(R.id.btn_hapus_WDadmin);

        nidn = view.findViewById(R.id.txt_nidnWD_admin);
        nama = view.findViewById(R.id.txt_namaWD_admin);
        email = view.findViewById(R.id.txt_emailWD_admin);
        prodi = view.findViewById(R.id.txt_prodiWD_admin);
        semester = view.findViewById(R.id.txt_semesterWD_admin);

        nidn.setText(model.get(position).getNidn());
        nama.setText(model.get(position).getNama());
        email.setText(model.get(position).getEmail());
        prodi.setText(model.get(position).getProdi());
        semester.setText(model.get(position).getSemester());

        txtidAdmin = view.findViewById(R.id.idAdminWDlist);
        txtNamaAdmin = view.findViewById(R.id.namaAdminWDlist);

        Intent i = ((Activity) context).getIntent();
        String tNim = i.getStringExtra("id_admin");
        String tName = i.getStringExtra("nama");

        txtidAdmin.setText(tNim);
        txtNamaAdmin.setText(tName);

        lihat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String idAdmin = txtidAdmin.getText().toString().trim();
                String namaAdmin = txtNamaAdmin.getText().toString().trim();
                Intent intent = new Intent(context, Lihat_data_walidosen.class);
                intent.putExtra("id_admin", idAdmin);
                intent.putExtra("nama", namaAdmin);
                intent.putExtra("nidnWD", model.get(position).getNidn());
                intent.putExtra("namaWD", model.get(position).getNama());
                intent.putExtra("emailWD", model.get(position).getEmail());
                intent.putExtra("prodiWD", model.get(position).getProdi());
                intent.putExtra("semesterWD", model.get(position).getSemester());
                context.startActivity(intent);
            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String idAdmin = txtidAdmin.getText().toString().trim();
                String namaAdmin = txtNamaAdmin.getText().toString().trim();
                Intent intented = new Intent(context, EditWalidosenActivity.class);
                intented.putExtra("id_admin", idAdmin);
                intented.putExtra("nama", namaAdmin);
                intented.putExtra("nidnEDWD", model.get(position).getNidn());
                intented.putExtra("namaEDWD", model.get(position).getNama());
                intented.putExtra("emailEDWD", model.get(position).getEmail());
                intented.putExtra("prodiEDWD", model.get(position).getProdi());
                intented.putExtra("semesterEDWD", model.get(position).getSemester());
                context.startActivity(intented);
            }
        });

        hapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String idAdmin = txtidAdmin.getText().toString().trim();
                String namaAdmin = txtNamaAdmin.getText().toString().trim();
                Intent intenth = new Intent(context, HapusWalidosenActivity.class);
                intenth.putExtra("id_admin", idAdmin);
                intenth.putExtra("nama", namaAdmin);
                intenth.putExtra("nimHPWD", model.get(position).getNidn());
                intenth.putExtra("namaHPWD", model.get(position).getNama());
                intenth.putExtra("emailHPWD", model.get(position).getEmail());
                intenth.putExtra("prodiHPWD", model.get(position).getProdi());
                intenth.putExtra("semesterHPWD", model.get(position).getSemester());
                context.startActivity(intenth);
            }
        });

        return view;
    }


}