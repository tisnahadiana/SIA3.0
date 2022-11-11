package id.example.sisteminformasiakademik.admin.khs;

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
import id.example.sisteminformasiakademik.admin.mahasiswa.HapusMahasiswaActivity;

public class KhsAdminActivity extends AppCompatActivity {

    TextView txtIdAdminKhs, txtNamaAdminKhs;
    FloatingActionButton fab_addKHS;
    Spinner spinnerProdikhsAdmin, spinnerSemesterKhsAdmin;
    String url = "https://droomp.tech/admin/khs/showkhs_admin.php";

    ArrayList<KhsData> list;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_khs_admin);
        getSupportActionBar().hide();

        txtIdAdminKhs = findViewById(R.id.txtIdAdminKhs);
        txtNamaAdminKhs = findViewById(R.id.txtNamaAdminKhs);
        fab_addKHS = findViewById(R.id.fab_addKHS);
        spinnerProdikhsAdmin = findViewById(R.id.spinnerProdikhsAdmin);
        spinnerSemesterKhsAdmin = findViewById(R.id.spinnerSemesterKhsAdmin);

        listView = findViewById(R.id.listViewKhsMHSAdmin);
        list = new ArrayList<>();

        Intent i = getIntent();
        String tNim = i.getStringExtra("id_admin");
        String tName = i.getStringExtra("nama");

        txtIdAdminKhs.setText(tNim);
        txtNamaAdminKhs.setText(tName);

        fab_addKHS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String idAdmin = txtIdAdminKhs.getText().toString().trim();
                String namaAdmin = txtNamaAdminKhs.getText().toString().trim();
                Intent intent = new Intent(KhsAdminActivity.this, InputKhsActivity.class);
                intent.putExtra("id_admin", idAdmin);
                intent.putExtra("nama", namaAdmin);
                startActivity(intent);
                overridePendingTransition(0, 0);
                finish();
            }
        });


        spinnerProdikhsAdmin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                showKhsMHS_Admin();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });

        spinnerSemesterKhsAdmin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                showKhsMHS_Admin();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });
    }

    public void showKhsMHS_Admin() {
        String prodi = spinnerProdikhsAdmin.getSelectedItem().toString();
        String semester = spinnerSemesterKhsAdmin.getSelectedItem().toString();
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
                            String prodi = object.getString("prodi");
                            String semester = object.getString("semester");
                            String img = object.getString("khs_url");

                            KhsData khsData = new KhsData();
                            khsData.setNim(nim);
                            khsData.setNama(nama);
                            khsData.setProdi(prodi);
                            khsData.setSemester(semester);
                            khsData.setImg(img);
                            list.add(khsData);
                            progressDialog.dismiss();
//                        list.add(new MahasiswaData(nim,nama,email,prodi,semester));
                        } catch (JSONException e){
                            e.printStackTrace();
                            progressDialog.dismiss();
                        }
                    }
                    Adapter adapter = new Adapter(KhsAdminActivity.this, list);
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
        RequestQueue queue = Volley.newRequestQueue(KhsAdminActivity.this);
        queue.add(request);
    }

    public void moveDashboardKhsDataShow(View view) {
        String idAdmin = txtIdAdminKhs.getText().toString().trim();
        String namaAdmin = txtNamaAdminKhs.getText().toString().trim();
        Intent intent = new Intent(getApplicationContext(), DashboardAdminActivity.class);
        intent.putExtra("id_admin", idAdmin);
        intent.putExtra("nama", namaAdmin);
        startActivity(intent);
        overridePendingTransition(0, 0);
        finish();
    }

    public void onBackPressed() {
        String idAdmin = txtIdAdminKhs.getText().toString().trim();
        String namaAdmin = txtNamaAdminKhs.getText().toString().trim();
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
    ArrayList<KhsData> model;

    public Adapter(Context context, ArrayList<KhsData> model){
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

    TextView nim, nama,prodi, semester,txtIdKHSlistview,txtNamaKHSlistview, txt_imgUrlkhs_admin;
    Button lihat, edit, hapus;
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = inflater.inflate(R.layout.khs_list_admin, parent, false);

        lihat = view.findViewById(R.id.btn_lihat_khsadmin);
        edit = view.findViewById(R.id.btn_edit_khsadmin);
        hapus = view.findViewById(R.id.btn_hapus_khsadmin);

        nim = view.findViewById(R.id.txt_nimkhs_admin);
        nama = view.findViewById(R.id.txt_namakhs_admin);
        prodi = view.findViewById(R.id.txt_prodikhs_admin);
        semester = view.findViewById(R.id.txt_semesterkhs_admin);
        txt_imgUrlkhs_admin = view.findViewById(R.id.txt_imgUrlkhs_admin);

        nim.setText(model.get(position).getNim());
        nama.setText(model.get(position).getNama());
        prodi.setText(model.get(position).getProdi());
        semester.setText(model.get(position).getSemester());
        txt_imgUrlkhs_admin.setText(model.get(position).getImg());

        txtIdKHSlistview = view.findViewById(R.id.txtIdKHSlistview);
        txtNamaKHSlistview = view.findViewById(R.id.txtNamaKHSlistview);

        Intent i = ((Activity) context).getIntent();
        String tNim = i.getStringExtra("id_admin");
        String tName = i.getStringExtra("nama");

        txtIdKHSlistview.setText(tNim);
        txtNamaKHSlistview.setText(tName);

        lihat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String idAdmin = txtIdKHSlistview.getText().toString().trim();
                String namaAdmin = txtNamaKHSlistview.getText().toString().trim();
                Intent intent = new Intent(context, LihatKhsActivity.class);
                intent.putExtra("id_admin", idAdmin);
                intent.putExtra("nama", namaAdmin);
                intent.putExtra("nimLKHS", model.get(position).getNim());
                intent.putExtra("namaLKHS", model.get(position).getNama());
                intent.putExtra("prodiLKHS", model.get(position).getProdi());
                intent.putExtra("semesterLKHS", model.get(position).getSemester());
                intent.putExtra("imgLKHS", model.get(position).getImg());
                context.startActivity(intent);
            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String idAdmin = txtIdKHSlistview.getText().toString().trim();
                String namaAdmin = txtNamaKHSlistview.getText().toString().trim();
                Intent intented = new Intent(context, EditKhsActivity.class);
                intented.putExtra("id_admin", idAdmin);
                intented.putExtra("nama", namaAdmin);
                intented.putExtra("nimEDKHS", model.get(position).getNim());
                intented.putExtra("namaEDKHS", model.get(position).getNama());
                intented.putExtra("prodiEDKHS", model.get(position).getProdi());
                intented.putExtra("semesterEDKHS", model.get(position).getSemester());
                intented.putExtra("imgLKHS", model.get(position).getImg());
                context.startActivity(intented);
            }
        });

        hapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String idAdmin = txtIdKHSlistview.getText().toString().trim();
                String namaAdmin = txtNamaKHSlistview.getText().toString().trim();
                Intent intenth = new Intent(context, HapusKhsActivity.class);
                intenth.putExtra("id_admin", idAdmin);
                intenth.putExtra("nama", namaAdmin);
                intenth.putExtra("nimHPKHS", model.get(position).getNim());
                intenth.putExtra("namaHPKHS", model.get(position).getNama());
                intenth.putExtra("prodiHPKHS", model.get(position).getProdi());
                intenth.putExtra("semesterHPKHS", model.get(position).getSemester());
                intenth.putExtra("imgHPKHS", model.get(position).getImg());
                context.startActivity(intenth);
            }
        });

        return view;
    }


}



