package id.example.sisteminformasiakademik.admin.tnilaiAdmin;

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
import id.example.sisteminformasiakademik.admin.khs.EditKhsActivity;
import id.example.sisteminformasiakademik.admin.khs.HapusKhsActivity;
import id.example.sisteminformasiakademik.admin.khs.InputKhsActivity;
import id.example.sisteminformasiakademik.admin.khs.KhsAdminActivity;
import id.example.sisteminformasiakademik.admin.khs.KhsData;
import id.example.sisteminformasiakademik.admin.khs.LihatKhsActivity;

public class TnilaiAdminActivity extends AppCompatActivity {

    TextView txtIdAdminTnilai, txtNamaAdminTnilai;
    FloatingActionButton fab_addtNILAI;
    Spinner spinnerProdiTnilaiAdmin, spinnerSemesterTnilaiAdmin;
    String url = "https://droomp.tech/admin/tnilai/showtnilai_admin.php";

    ArrayList<TnilaiData> list;
    ListView listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tnilai_admin);
        getSupportActionBar().hide();

        txtIdAdminTnilai = findViewById(R.id.txtIdAdminTnilai);
        txtNamaAdminTnilai = findViewById(R.id.txtNamaAdminTnilai);
        fab_addtNILAI = findViewById(R.id.fab_addtNILAI);
        spinnerProdiTnilaiAdmin = findViewById(R.id.spinnerProdiTnilaiAdmin);
        spinnerSemesterTnilaiAdmin = findViewById(R.id.spinnerSemesterTnilaiAdmin);

        listView = findViewById(R.id.listViewTnilaiMHSAdmin);
        list = new ArrayList<>();

        Intent i = getIntent();
        String tNim = i.getStringExtra("id_admin");
        String tName = i.getStringExtra("nama");

        txtIdAdminTnilai.setText(tNim);
        txtNamaAdminTnilai.setText(tName);

        fab_addtNILAI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String idAdmin = txtIdAdminTnilai.getText().toString().trim();
                String namaAdmin = txtNamaAdminTnilai.getText().toString().trim();
                Intent intent = new Intent(TnilaiAdminActivity.this, InputTnilaiAdminActivity.class);
                intent.putExtra("id_admin", idAdmin);
                intent.putExtra("nama", namaAdmin);
                startActivity(intent);
                overridePendingTransition(0, 0);
                finish();
            }
        });


        spinnerProdiTnilaiAdmin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                showTnilai_Admin();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });

        spinnerSemesterTnilaiAdmin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                showTnilai_Admin();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });
    }


    public void showTnilai_Admin() {
        String prodi = spinnerProdiTnilaiAdmin.getSelectedItem().toString();
        String semester = spinnerSemesterTnilaiAdmin.getSelectedItem().toString();
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
                            String img = object.getString("tnilai_url");

                            TnilaiData tnilaiData = new TnilaiData();
                            tnilaiData.setNim(nim);
                            tnilaiData.setNama(nama);
                            tnilaiData.setProdi(prodi);
                            tnilaiData.setSemester(semester);
                            tnilaiData.setTnilai_url(img);
                            list.add(tnilaiData);
                            progressDialog.dismiss();
//                        list.add(new MahasiswaData(nim,nama,email,prodi,semester));
                        } catch (JSONException e){
                            e.printStackTrace();
                            progressDialog.dismiss();
                        }
                    }
                    Adapter adapter = new Adapter(TnilaiAdminActivity.this, list);
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
        RequestQueue queue = Volley.newRequestQueue(TnilaiAdminActivity.this);
        queue.add(request);
    }

    public void moveDashboardTnilaiShow(View view) {
        String idAdmin = txtIdAdminTnilai.getText().toString().trim();
        String namaAdmin = txtNamaAdminTnilai.getText().toString().trim();
        Intent intent = new Intent(getApplicationContext(), DashboardAdminActivity.class);
        intent.putExtra("id_admin", idAdmin);
        intent.putExtra("nama", namaAdmin);
        startActivity(intent);
        overridePendingTransition(0, 0);
        finish();
    }

    public void onBackPressed() {
        String idAdmin = txtIdAdminTnilai.getText().toString().trim();
        String namaAdmin = txtNamaAdminTnilai.getText().toString().trim();
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
    ArrayList<TnilaiData> model;

    public Adapter(Context context, ArrayList<TnilaiData> model){
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

    TextView nim, nama,prodi, semester,txtIdTnilailistview,txtNamaTnilailistview, txtImageTnilailistview;
    Button lihat, edit, hapus;
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = inflater.inflate(R.layout.tnilai_list_admin, parent, false);

        lihat = view.findViewById(R.id.btn_lihat_Tnilaiadmin);
        edit = view.findViewById(R.id.btn_edit_Tnilaiadmin);
        hapus = view.findViewById(R.id.btn_hapus_Tnilaiadmin);

        nim = view.findViewById(R.id.txt_nimTnilai_admin);
        nama = view.findViewById(R.id.txt_namaTnilai_admin);
        prodi = view.findViewById(R.id.txt_prodiTnilai_admin);
        semester = view.findViewById(R.id.txt_semesterTnilai_admin);
        txtImageTnilailistview = view.findViewById(R.id.txtImageTnilailistview);

        nim.setText(model.get(position).getNim());
        nama.setText(model.get(position).getNama());
        prodi.setText(model.get(position).getProdi());
        semester.setText(model.get(position).getSemester());
        txtImageTnilailistview.setText(model.get(position).getTnilai_url());

        txtIdTnilailistview = view.findViewById(R.id.txtIdTnilailistview);
        txtNamaTnilailistview = view.findViewById(R.id.txtNamaTnilailistview);

        Intent i = ((Activity) context).getIntent();
        String tNim = i.getStringExtra("id_admin");
        String tName = i.getStringExtra("nama");

        txtIdTnilailistview.setText(tNim);
        txtNamaTnilailistview.setText(tName);

        lihat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String idAdmin = txtIdTnilailistview.getText().toString().trim();
                String namaAdmin = txtNamaTnilailistview.getText().toString().trim();
                Intent intent = new Intent(context, LihatTnilaiAdminActivity.class);
                intent.putExtra("id_admin", idAdmin);
                intent.putExtra("nama", namaAdmin);
                intent.putExtra("nimLTNL", model.get(position).getNim());
                intent.putExtra("namaLTNL", model.get(position).getNama());
                intent.putExtra("prodiLTNL", model.get(position).getProdi());
                intent.putExtra("semesterLTNL", model.get(position).getSemester());
                intent.putExtra("imgLTNL", model.get(position).getTnilai_url());
                context.startActivity(intent);
            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String idAdmin = txtIdTnilailistview.getText().toString().trim();
                String namaAdmin = txtNamaTnilailistview.getText().toString().trim();
                Intent intented = new Intent(context, EditTnilaiAdminActivity.class);
                intented.putExtra("id_admin", idAdmin);
                intented.putExtra("nama", namaAdmin);
                intented.putExtra("nimEDTN", model.get(position).getNim());
                intented.putExtra("namaEDTN", model.get(position).getNama());
                intented.putExtra("prodiEDTN", model.get(position).getProdi());
                intented.putExtra("semesterEDTN", model.get(position).getSemester());
                intented.putExtra("imgTN", model.get(position).getTnilai_url());
                context.startActivity(intented);
            }
        });

        hapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String idAdmin = txtIdTnilailistview.getText().toString().trim();
                String namaAdmin = txtNamaTnilailistview.getText().toString().trim();
                Intent intenth = new Intent(context, HapusTnilaiAdminActivity.class);
                intenth.putExtra("id_admin", idAdmin);
                intenth.putExtra("nama", namaAdmin);
                intenth.putExtra("nimHPTNL", model.get(position).getNim());
                intenth.putExtra("namaHPTNL", model.get(position).getNama());
                intenth.putExtra("prodiHPTNL", model.get(position).getProdi());
                intenth.putExtra("semesterHPTNL", model.get(position).getSemester());
                intenth.putExtra("imgHPTNL", model.get(position).getTnilai_url());
                context.startActivity(intenth);
            }
        });

        return view;
    }


}
