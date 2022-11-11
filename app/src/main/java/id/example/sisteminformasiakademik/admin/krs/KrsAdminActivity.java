package id.example.sisteminformasiakademik.admin.krs;

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
import java.util.Map;

import id.example.sisteminformasiakademik.R;
import id.example.sisteminformasiakademik.admin.DashboardAdminActivity;

public class KrsAdminActivity extends AppCompatActivity {

    TextView txtIdAdminKRS, txtNamaAdminKRS;
    FloatingActionButton fab_addKRS;
    Spinner spinnerProdiAdminKRS, spinnerSemesterKRSAdmin;
    String url = "https://droomp.tech/admin/krs/showkrs_admin.php";

    ArrayList<KrsData> list;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_krs_admin);
        getSupportActionBar().hide();

        txtIdAdminKRS = findViewById(R.id.txtIdAdminKRS);
        txtNamaAdminKRS = findViewById(R.id.txtNamaAdminKRS);
        fab_addKRS = findViewById(R.id.fab_addKRS);
        spinnerProdiAdminKRS = findViewById(R.id.spinnerProdiAdminKRS);
        spinnerSemesterKRSAdmin = findViewById(R.id.spinnerSemesterKRSAdmin);

        listView = findViewById(R.id.listViewKRSAdmin);
        list = new ArrayList<>();

        Intent i = getIntent();
        String tNim = i.getStringExtra("id_admin");
        String tName = i.getStringExtra("nama");

        txtIdAdminKRS.setText(tNim);
        txtNamaAdminKRS.setText(tName);

        fab_addKRS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String idAdmin = txtIdAdminKRS.getText().toString().trim();
                String namaAdmin = txtNamaAdminKRS.getText().toString().trim();
                Intent intent = new Intent(KrsAdminActivity.this, InputKrsActivity.class);
                intent.putExtra("id_admin", idAdmin);
                intent.putExtra("nama", namaAdmin);
                startActivity(intent);
                overridePendingTransition(0, 0);
                finish();
            }
        });

        spinnerProdiAdminKRS.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                show_krs();
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });

        spinnerSemesterKRSAdmin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                show_krs();
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });



    }

    public void show_krs() {

        String prodi = spinnerProdiAdminKRS.getSelectedItem().toString();
        String semester = spinnerSemesterKRSAdmin.getSelectedItem().toString();
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Mohon Menunggu.....");

        progressDialog.show();

        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
//
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

                            KrsData krsData = new KrsData();
                            krsData.setStr_nim(nim);
                            krsData.setStr_nama(nama);
                            krsData.setStr_prodi(prodi);
                            krsData.setStr_semester(semester);
                            list.add(krsData);
                            progressDialog.dismiss();
//                        list.add(new MahasiswaData(nim,nama,email,prodi,semester));
                        } catch (JSONException e){
                            e.printStackTrace();
                            progressDialog.dismiss();
                        }
                    }
                    Adapter adapter = new Adapter(KrsAdminActivity.this, list);
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
        RequestQueue queue = Volley.newRequestQueue(KrsAdminActivity.this);
        queue.add(request);

    }

    public void moveDashboardKrsData(View view){
        String idAdmin = txtIdAdminKRS.getText().toString().trim();
        String namaAdmin = txtNamaAdminKRS.getText().toString().trim();
        Intent intent = new Intent(getApplicationContext(), DashboardAdminActivity.class);
        intent.putExtra("id_admin", idAdmin);
        intent.putExtra("nama", namaAdmin);
        startActivity(intent);
        overridePendingTransition(0, 0);
        finish();
    }

    public void onBackPressed() {
        String idAdmin = txtIdAdminKRS.getText().toString().trim();
        String namaAdmin = txtNamaAdminKRS.getText().toString().trim();
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
    ArrayList<KrsData> model;
    String urlLihatKrsAdmin = "https://droomp.tech/admin/krs/lihatkrs_admin.php";

    public Adapter(Context context, ArrayList<KrsData> model){
        inflater=LayoutInflater.from(context);
        this.context = context;
        this.model = model;
    }

//    public void massage(String massage) {
//        Toast.makeText(context.getApplicationContext(), massage, Toast.LENGTH_LONG).show();
//    }

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

    TextView nim, nama, email, prodi, semester,txtIdkrslist,txtNamakrslist;
    Button lihat, edit, hapus;
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = inflater.inflate(R.layout.krs_list_admin, parent, false);

        lihat = view.findViewById(R.id.btn_lihat_krsadmin);
        edit = view.findViewById(R.id.btn_edit_krsadmin);
        hapus = view.findViewById(R.id.btn_hapus_krsadmin);

        nim = view.findViewById(R.id.txt_nimkrs_admin);
        nama = view.findViewById(R.id.txt_namakrs_admin);
        prodi = view.findViewById(R.id.txt_prodikrs_admin);
        semester = view.findViewById(R.id.txt_semesterkrs_admin);

        nim.setText(model.get(position).getStr_nim());
        nama.setText(model.get(position).getStr_nama());
        prodi.setText(model.get(position).getStr_prodi());
        semester.setText(model.get(position).getStr_semester());

        txtIdkrslist = view.findViewById(R.id.txtIdkrslist);
        txtNamakrslist = view.findViewById(R.id.txtNamakrslist);

        Intent i = ((Activity) context).getIntent();
        String tNim = i.getStringExtra("id_admin");
        String tName = i.getStringExtra("nama");

        txtIdkrslist.setText(tNim);
        txtNamakrslist.setText(tName);

        lihat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String nim = model.get(position).getStr_nim();
                String nama = model.get(position).getStr_nama();

                final ProgressDialog progressDialog = new ProgressDialog(context);
                progressDialog.setMessage("Mohon Menunggu.....");

                progressDialog.show();
                StringRequest request = new StringRequest(Request.Method.POST, urlLihatKrsAdmin,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    String result = jsonObject.getString("status");

                                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                                    if (result.equals("success")) {
                                        for (int i = 0; i < jsonArray.length(); i++) {

                                            JSONObject object = jsonArray.getJSONObject(i);
                                            String nim = object.getString("nim");
                                            String nama = object.getString("nama");
                                            String prodi = object.getString("prodi");
                                            String semester = object.getString("semester");
                                            String matkul1 = object.getString("matkul1");
                                            String sks1 = object.getString("sks1");
                                            String matkul2 = object.getString("matkul2");
                                            String sks2 = object.getString("sks2");
                                            String matkul3 = object.getString("matkul3");
                                            String sks3 = object.getString("sks3");
                                            String matkul4 = object.getString("matkul4");
                                            String sks4 = object.getString("sks4");
                                            String matkul5 = object.getString("matkul5");
                                            String sks5 = object.getString("sks5");
                                            String matkul6 = object.getString("matkul6");
                                            String sks6 = object.getString("sks6");
                                            String matkul7 = object.getString("matkul7");
                                            String sks7 = object.getString("sks7");
                                            String matkul8 = object.getString("matkul8");
                                            String sks8 = object.getString("sks8");
                                            String jumlahsks = object.getString("jumlahsks");
                                            String date = object.getString("date");
                                            String idAdmin = txtIdkrslist.getText().toString().trim();
                                            String namaAdmin = txtNamakrslist.getText().toString().trim();


                                            Intent intent = new Intent(context, LihatKrsActivity.class);
                                            intent.putExtra("id_admin", idAdmin);
                                            intent.putExtra("namaAdmin", namaAdmin);

                                            intent.putExtra("nim", nim);
                                            intent.putExtra("nama", nama);
                                            intent.putExtra("prodi", prodi);
                                            intent.putExtra("semester", semester);
                                            intent.putExtra("matkul1", matkul1);
                                            intent.putExtra("sks1", sks1);
                                            intent.putExtra("matkul2", matkul2);
                                            intent.putExtra("sks2", sks2);
                                            intent.putExtra("matkul3", matkul3);
                                            intent.putExtra("sks3", sks3);
                                            intent.putExtra("matkul4", matkul4);
                                            intent.putExtra("sks4", sks4);
                                            intent.putExtra("matkul5", matkul5);
                                            intent.putExtra("sks5", sks5);
                                            intent.putExtra("matkul6", matkul6);
                                            intent.putExtra("sks6", sks6);
                                            intent.putExtra("matkul7", matkul7);
                                            intent.putExtra("sks7", sks7);
                                            intent.putExtra("matkul8", matkul8);
                                            intent.putExtra("sks8", sks8);
                                            intent.putExtra("jumlahsks", jumlahsks);
                                            intent.putExtra("date", date);
                                            context.startActivity(intent);
                                            progressDialog.dismiss();
                                            Toast.makeText(context.getApplicationContext(), "Melihat Data", Toast.LENGTH_LONG).show();
                                        }
                                    } else {
                                        progressDialog.dismiss();
                                        Toast.makeText(context.getApplicationContext(), "Data tidak ditemukan", Toast.LENGTH_LONG).show();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(context.getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
                    }
                }) {
                    //                @Nullable
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();
                        params.put("nim", nim);
                        params.put("nama", nama);
                        return params;
                    }
                };
                RequestQueue queue = Volley.newRequestQueue(context);
                queue.add(request);
            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                String nim = model.get(position).getStr_nim();
                String nama = model.get(position).getStr_nama();

                final ProgressDialog progressDialog = new ProgressDialog(context);
                progressDialog.setMessage("Mohon Menunggu.....");

                progressDialog.show();
                StringRequest request = new StringRequest(Request.Method.POST, urlLihatKrsAdmin,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    String result = jsonObject.getString("status");

                                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                                    if (result.equals("success")) {
                                        for (int i = 0; i < jsonArray.length(); i++) {

                                            JSONObject object = jsonArray.getJSONObject(i);
                                            String nim = object.getString("nim");
                                            String nama = object.getString("nama");
                                            String prodi = object.getString("prodi");
                                            String semester = object.getString("semester");
                                            String matkul1 = object.getString("matkul1");
                                            String sks1 = object.getString("sks1");
                                            String matkul2 = object.getString("matkul2");
                                            String sks2 = object.getString("sks2");
                                            String matkul3 = object.getString("matkul3");
                                            String sks3 = object.getString("sks3");
                                            String matkul4 = object.getString("matkul4");
                                            String sks4 = object.getString("sks4");
                                            String matkul5 = object.getString("matkul5");
                                            String sks5 = object.getString("sks5");
                                            String matkul6 = object.getString("matkul6");
                                            String sks6 = object.getString("sks6");
                                            String matkul7 = object.getString("matkul7");
                                            String sks7 = object.getString("sks7");
                                            String matkul8 = object.getString("matkul8");
                                            String sks8 = object.getString("sks8");
                                            String jumlahsks = object.getString("jumlahsks");
                                            String date = object.getString("date");
                                            String idAdmin = txtIdkrslist.getText().toString().trim();
                                            String namaAdmin = txtNamakrslist.getText().toString().trim();


                                            Intent intent = new Intent(context, EditKrsActivity.class);
                                            intent.putExtra("id_admin", idAdmin);
                                            intent.putExtra("namaAdmin", namaAdmin);

                                            intent.putExtra("nim", nim);
                                            intent.putExtra("nama", nama);
                                            intent.putExtra("prodi", prodi);
                                            intent.putExtra("semester", semester);
                                            intent.putExtra("matkul1", matkul1);
                                            intent.putExtra("sks1", sks1);
                                            intent.putExtra("matkul2", matkul2);
                                            intent.putExtra("sks2", sks2);
                                            intent.putExtra("matkul3", matkul3);
                                            intent.putExtra("sks3", sks3);
                                            intent.putExtra("matkul4", matkul4);
                                            intent.putExtra("sks4", sks4);
                                            intent.putExtra("matkul5", matkul5);
                                            intent.putExtra("sks5", sks5);
                                            intent.putExtra("matkul6", matkul6);
                                            intent.putExtra("sks6", sks6);
                                            intent.putExtra("matkul7", matkul7);
                                            intent.putExtra("sks7", sks7);
                                            intent.putExtra("matkul8", matkul8);
                                            intent.putExtra("sks8", sks8);
                                            intent.putExtra("jumlahsks", jumlahsks);
                                            intent.putExtra("date", date);
                                            context.startActivity(intent);
                                            progressDialog.dismiss();
                                            Toast.makeText(context.getApplicationContext(), "Melihat Data", Toast.LENGTH_LONG).show();
                                        }
                                    } else {
                                        progressDialog.dismiss();
                                        Toast.makeText(context.getApplicationContext(), "Data tidak ditemukan", Toast.LENGTH_LONG).show();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(context.getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
                    }
                }) {
                    //                @Nullable
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();
                        params.put("nim", nim);
                        params.put("nama", nama);
                        return params;
                    }
                };
                RequestQueue queue = Volley.newRequestQueue(context);
                queue.add(request);
            }
        });

        hapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String nim = model.get(position).getStr_nim();
                String nama = model.get(position).getStr_nama();

                final ProgressDialog progressDialog = new ProgressDialog(context);
                progressDialog.setMessage("Mohon Menunggu.....");

                progressDialog.show();
                StringRequest request = new StringRequest(Request.Method.POST, urlLihatKrsAdmin,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    String result = jsonObject.getString("status");

                                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                                    if (result.equals("success")) {
                                        for (int i = 0; i < jsonArray.length(); i++) {

                                            JSONObject object = jsonArray.getJSONObject(i);
                                            String nim = object.getString("nim");
                                            String nama = object.getString("nama");
                                            String prodi = object.getString("prodi");
                                            String semester = object.getString("semester");
                                            String matkul1 = object.getString("matkul1");
                                            String sks1 = object.getString("sks1");
                                            String matkul2 = object.getString("matkul2");
                                            String sks2 = object.getString("sks2");
                                            String matkul3 = object.getString("matkul3");
                                            String sks3 = object.getString("sks3");
                                            String matkul4 = object.getString("matkul4");
                                            String sks4 = object.getString("sks4");
                                            String matkul5 = object.getString("matkul5");
                                            String sks5 = object.getString("sks5");
                                            String matkul6 = object.getString("matkul6");
                                            String sks6 = object.getString("sks6");
                                            String matkul7 = object.getString("matkul7");
                                            String sks7 = object.getString("sks7");
                                            String matkul8 = object.getString("matkul8");
                                            String sks8 = object.getString("sks8");
                                            String jumlahsks = object.getString("jumlahsks");
                                            String date = object.getString("date");
                                            String idAdmin = txtIdkrslist.getText().toString().trim();
                                            String namaAdmin = txtNamakrslist.getText().toString().trim();


                                            Intent intent = new Intent(context, HapusKrsActivity.class);
                                            intent.putExtra("id_admin", idAdmin);
                                            intent.putExtra("namaAdmin", namaAdmin);

                                            intent.putExtra("nim", nim);
                                            intent.putExtra("nama", nama);
                                            intent.putExtra("prodi", prodi);
                                            intent.putExtra("semester", semester);
                                            intent.putExtra("matkul1", matkul1);
                                            intent.putExtra("sks1", sks1);
                                            intent.putExtra("matkul2", matkul2);
                                            intent.putExtra("sks2", sks2);
                                            intent.putExtra("matkul3", matkul3);
                                            intent.putExtra("sks3", sks3);
                                            intent.putExtra("matkul4", matkul4);
                                            intent.putExtra("sks4", sks4);
                                            intent.putExtra("matkul5", matkul5);
                                            intent.putExtra("sks5", sks5);
                                            intent.putExtra("matkul6", matkul6);
                                            intent.putExtra("sks6", sks6);
                                            intent.putExtra("matkul7", matkul7);
                                            intent.putExtra("sks7", sks7);
                                            intent.putExtra("matkul8", matkul8);
                                            intent.putExtra("sks8", sks8);
                                            intent.putExtra("jumlahsks", jumlahsks);
                                            intent.putExtra("date", date);
                                            context.startActivity(intent);
                                            progressDialog.dismiss();
                                            Toast.makeText(context.getApplicationContext(), "Melihat Data", Toast.LENGTH_LONG).show();
                                        }
                                    } else {
                                        progressDialog.dismiss();
                                        Toast.makeText(context.getApplicationContext(), "Data tidak ditemukan", Toast.LENGTH_LONG).show();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(context.getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
                    }
                }) {
                    //                @Nullable
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();
                        params.put("nim", nim);
                        params.put("nama", nama);
                        return params;
                    }
                };
                RequestQueue queue = Volley.newRequestQueue(context);
                queue.add(request);
            }
        });

        return view;
    }




}
